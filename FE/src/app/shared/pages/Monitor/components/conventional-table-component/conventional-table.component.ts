import { CommonModule, isPlatformBrowser } from '@angular/common';
import { SharedModule } from '../../../../../share.module';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';
import { ChangeDetectorRef, Component, inject, Input, OnInit, PLATFORM_ID } from '@angular/core';
import { Util } from '../../../../core/utils/utils-function';
import { ScanSerialCheck } from '../../service/scan-serial-check.service';

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

    constructor(private cd: ChangeDetectorRef, private scanSerialCheckService: ScanSerialCheck) { }

    ngOnInit() {
        this.data = this.data.map(x => ({
            ...x,
            timeScan: x.timeScan ? new Date(x.timeScan) : null,
            timeCheck: x.timeCheck ? new Date(x.timeCheck) : null
        }));
        this.scanSerialCheckService.getWorkOrderDetails(this.model.planningWO.woId)
            .subscribe(res => {
                console.log(res);
                
            });
                
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
        this.scanSerialCheckService.getWorkOrderDetails(workOrderId)
            .subscribe({
                next: (listData: any[]) => {
                    let dynamicParamKeys: Set<string> = new Set();
                    const detailData = listData.map((row: any[]) => {
                        const baseData: any = {
                            'MachineName': row[0],
                            'serialBoard': row[1],
                            'serialItem': row[2],
                            'serialStatus': row[3],
                            'serialCheck': row[4],
                            'timeScan': row[5],
                            'timeCheck': row[6],
                            'resultCheck': row[7],
                            'paramsID': row[8],
                            'serialID': row[9],
                            'programName': row[10],
                            'fixLR': row[11],
                            'fixID': row[12],
                            'startTestTime': row[13],
                            'endTestTime': row[14],
                            'timeElapsed': row[15],
                            'results': row[16],
                        };
                        const jsonParamString = row[17] as string;
                        let paramDetails = {};
                        if (jsonParamString) {
                            try {
                                paramDetails = JSON.parse(jsonParamString);
                                Object.keys(paramDetails).forEach(key => {
                                    dynamicParamKeys.add(key);
                                });
                            } catch (e) {
                                console.error('Lỗi khi phân tích JSON row[17]:', e);
                                baseData['JSON Parse Error'] = 'Invalid JSON';
                            }
                        }
                        return { ...baseData, ...paramDetails };
                    });
                    const fixedHeaders = [
                        'MachineName',
                        'serialBoard',
                        'serialItem',
                        'serialStatus',
                        'serialCheck',
                        'timeScan',
                        'timeCheck',
                        'resultCheck',
                        'paramsID',
                        'serialID',
                        'programName',
                        'fixLR',
                        'fixID',
                        'startTestTime',
                        'endTestTime',
                        'timeElapsed',
                        'results'
                    ];
                    fixedHeaders.forEach(key => dynamicParamKeys.delete(key));
                    const fullHeader = [...fixedHeaders, ...Array.from(dynamicParamKeys)];
                    const worksheet2 = XLSX.utils.json_to_sheet(detailData, { header: fullHeader, skipHeader: false });
                    XLSX.utils.book_append_sheet(workbook, worksheet2, 'Chi tiết FCT'); // Đổi tên sheet
                    const filename = `Báo cáo serial ${workOrderId}.xlsx`;
                    XLSX.writeFile(workbook, filename);
                },
                error: (err) => {
                    console.error('Lỗi khi tải dữ liệu chi tiết:', err);
                    const filename = `Báo cáo serial ${workOrderId}_Error.xlsx`;
                    XLSX.writeFile(workbook, filename);
                }
            });
    }

}