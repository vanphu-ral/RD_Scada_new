import { CommonModule, isPlatformBrowser } from '@angular/common';
import { SharedModule } from '../../../../../share.module';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';
import { ChangeDetectorRef, Component, inject, Input, OnInit, PLATFORM_ID } from '@angular/core';
import { Util } from '../../../../core/utils/utils-function';
import { ScanSerialCheck } from '../../service/scan-serial-check.service';
import { DetailParamsService } from '../../../Traceability/service/detail-params.service';
import _ from 'lodash';

@Component({
    selector: 'app-conventional-table-component',
    standalone: true,
    templateUrl: './conventional-table.component.html',
    styleUrls: ['./conventional-table.component.scss'],
    imports: [CommonModule, SharedModule]
})
export class ConventionalTableComponent implements OnInit {

    @Input() data: any[] = []
    @Input() model: any = {}
    filterDate?: Date;

    constructor(private cd: ChangeDetectorRef, private detailParamService: DetailParamsService) { }

    ngOnInit() {
        this.data = this.data.map(x => ({
            ...x,
            timeScan: x.timeScan ? new Date(x.timeScan) : null,
            timeCheck: x.timeCheck ? new Date(x.timeCheck) : null
        }));
    }

    exportExcel(): void {
        if (!this.data || this.data.length === 0) {
            console.warn('Không có dữ liệu chính để xuất.');
            return;
        }
        const workOrderId = this.model.planningWO.woId;
        const workbook = XLSX.utils.book_new();
        const mainHeaderKeys = ['StageNum', 'Machine', 'Serial board', 'Serial PCS', 'Thời gian scan', 'Thời gian sửa serial', 'Status'];
        const mainData = this.data.map((item: any) => ({
            'StageNum': item.stageNum,
            'Machine': item.machineName,
            'Serial board': item.serialBoard,
            'Serial PCS': item.serialItem,
            'Thời gian scan': item.timeScan,
            'Thời gian sửa serial': item.timeCheck,
            'Status': item.serialStatus
        }));
        const worksheet1 = XLSX.utils.json_to_sheet(mainData, { header: mainHeaderKeys, skipHeader: false });
        XLSX.utils.book_append_sheet(workbook, worksheet1, 'Danh sách serial');
        this.detailParamService.getDetailParamsByWorkOrder(workOrderId)
            .subscribe({
                next: (listData: any[]) => {
                    const dataFCTATE = _.filter(listData, (item: any) => item.machineType == 1);
                    const dataLUYEN = _.filter(listData, (item: any) => item.machineType == 2);
                    let dynamicParamKeysFCT: Set<string> = new Set();
                    const detailDataFCT = dataFCTATE.map((item: any) => {
                        const baseData: any = { 'paramsID': item.paramsID };
                        let paramDetails = {};
                        if (item.detailParams) {
                            try {
                                const parsed = JSON.parse(item.detailParams);
                                paramDetails = parsed.data || parsed;
                                Object.keys(paramDetails).forEach(key => dynamicParamKeysFCT.add(key));
                            } catch (e) {
                                baseData['JSON Parse Error'] = 'Invalid JSON';
                            }
                        }
                        return { ...baseData, ...paramDetails };
                    });

                    const fixedHeaders = ['paramsID'];
                    const fullHeaderFCT = [...fixedHeaders, ...Array.from(dynamicParamKeysFCT)];
                    const worksheetFCT = XLSX.utils.json_to_sheet(detailDataFCT, { header: fullHeaderFCT });
                    XLSX.utils.book_append_sheet(workbook, worksheetFCT, 'Chi tiết FCT');
                    let detailDataLUYEN: any[] = [];
                    let dynamicParamKeysLUYEN: Set<string> = new Set();

                    dataLUYEN.forEach((item: any) => {
                        if (item.detailParams) {
                            try {
                                const parsed = JSON.parse(item.detailParams);
                                const dataArray = parsed.data; 

                                if (Array.isArray(dataArray)) {
                                    dataArray.forEach((point: any) => {
                                        const row = {
                                            'paramsID': item.paramsID,
                                            'serialBoard': item.serialBoard,
                                            'serialItem': item.serialItem,
                                            ...point 
                                        };
                                        detailDataLUYEN.push(row);
                                        Object.keys(point).forEach(key => dynamicParamKeysLUYEN.add(key));
                                    });
                                }
                            } catch (e) {
                                console.error('Lỗi parse JSON LUYEN:', e);
                            }
                        }
                    });

                    const fixedHeadersLUYEN = ['paramsID', 'serialBoard', 'serialItem'];
                    const fullHeaderLUYEN = [...fixedHeadersLUYEN, ...Array.from(dynamicParamKeysLUYEN)];
                    const worksheetLUYEN = XLSX.utils.json_to_sheet(detailDataLUYEN, { header: fullHeaderLUYEN });
                    XLSX.utils.book_append_sheet(workbook, worksheetLUYEN, 'Chi tiết LUYEN');
                    const filename = `Báo cáo serial ${workOrderId}.xlsx`;
                    XLSX.writeFile(workbook, filename);
                },
                error: (err) => {
                    console.error('Lỗi khi tải dữ liệu chi tiết:', err);
                    XLSX.writeFile(workbook, `Báo cáo serial ${workOrderId}_Error.xlsx`);
                }
            });
    }

}