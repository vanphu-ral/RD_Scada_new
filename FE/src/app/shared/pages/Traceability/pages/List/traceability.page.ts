import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, NgZone } from '@angular/core';
import { SharedModule } from '../../../../../share.module';
import { PlanningWoService } from '../../../Monitor/service/planning-wo.service';
import { GrapSqlService } from '../../../../service/grap-sql.service';
import _ from 'lodash';
import { DetailParamsService } from '../../service/detail-params.service';
import * as XLSX from 'xlsx';

@Component({
    selector: 'app-traceability',
    standalone: true,
    templateUrl: './traceability.page.html',
    styleUrls: ['./traceability.page.scss'],
    imports: [CommonModule, SharedModule]
})
export class TraceabilityPage {

    data: any = { planningWO: null, scanSerialChecks: [] };
    filter: any = {
        serialBoard: '',
        serialItem: '',
    }
    optionSerial: any[] = [
        { name: 'Serial Board', value: 'serialBoard' },
        { name: 'Serial Item', value: 'serialItem' },
    ];
    listBOMInfo: any[] = [];
    listMaterialCheck: any[] = [];
    listMaterialUse: any[] = [];
    listMaterialNotRecommend: any[] = [];

    listpqcBomQuantity: any[] = [];
    listpqcBomErrorDetail: any[] = [];

    listDetailParamsBySerial: any[] = [];

    loading: boolean = false;


    constructor(private cdr: ChangeDetectorRef, private grapSqlService: GrapSqlService, private planningWoService: PlanningWoService, private detailParamsService: DetailParamsService) { }

    ngOnInit(): void {
        this.filter.option = 'serialBoard'
    }

    search() {
        if (!this.filter.serial || !this.filter.option) return;
        this.loading = true;
        const apiCall =
            this.filter.option === 'serialBoard'
                ? this.planningWoService.filterBySerialBoard(this.filter.serial)
                : this.planningWoService.filterBySerialItem(this.filter.serial);

        apiCall.subscribe({
            next: (res: any) => {
                this.data = res ? { ...res } : null;
                this.grapSqlService.QmsToDoiTraInfoByLotNumber(_.get(res, 'planningWO.lotNumber')).subscribe(res => {
                    this.listBOMInfo = _.get(res, 'data.qmsToDoiTraInfoByLotNumber.pqcBomWorkOrder') || [];
                    this.listMaterialCheck = this.mergePqcData(
                        _.get(res, 'data.qmsToDoiTraInfoByLotNumber.pqcCheckNVL') || [],
                        _.get(res, 'data.qmsToDoiTraInfoByLotNumber.pqcCheckTestNVL') || []
                    );
                    this.listMaterialUse = _.get(res, 'data.qmsToDoiTraInfoByLotNumber.pqcScan100Pass') || [];
                    this.listMaterialNotRecommend = _.get(res, 'data.qmsToDoiTraInfoByLotNumber.pqcScan100Fail') || [];
                    this.listpqcBomQuantity = _.get(res, 'data.qmsToDoiTraInfoByLotNumber.pqcBomQuantity') || [];
                    this.listpqcBomErrorDetail = _.get(res, 'data.qmsToDoiTraInfoByLotNumber.pqcBomErrorDetail') || [];
                    this.loading = false;
                    this.filter.serial = '';
                    this.cdr.detectChanges();
                })
                this.detailParamsService.getDetailParamsByWorkOrder(_.get(res, 'planningWO.woId')).subscribe(res => {
                    console.log(res);
                    this.listDetailParamsBySerial = res
                    this.cdr.detectChanges();
                })
                this.cdr.detectChanges();
            },
            error: () => {
                this.data = null;
                this.cdr.detectChanges();
            }
        });
    }

    clearData() {
        this.data = null;
        this.cdr.detectChanges();
    }

    mergePqcData(checkNVL: any[], testNVL: any[]) {
        const checkInfoMap = new Map();
        checkNVL.forEach(info => {
            checkInfoMap.set(String(info.id), info);
        });
        const mergedArray = testNVL.map(item => {
            const generalInfo = checkInfoMap.get(String(item.pqcDrawNvlId));
            if (generalInfo) {
                const infoToAdd = { ...generalInfo };
                delete infoToAdd.id;
                return {
                    ...item,
                    ...infoToAdd
                };
            }
            return item;
        });
        return mergedArray;
    }

    getQuantity(id: number) {
        const quantity = this.listpqcBomQuantity.find(item => item.pqcBomWorkOrderId === id);
        return quantity ? _.get(quantity, 'quantity', 0) : 0;
    }

    getError(id: number) {
        const quantity = this.listpqcBomErrorDetail.find(item => item.pqcBomWorkOrderId === id);
        return quantity ? _.get(quantity, 'quantity', 0) : 0;
    }

    getPartNumber(qr: string) {
        const parts = qr.split('#');
        return parts[1] || '';
    }

    getRowSpan(checkPerson: string): number {
        return this.listMaterialCheck.filter(x => x.CheckPerson === checkPerson).length;
    }

    shouldShowPerson(index: number): boolean {
        if (index === 0) return true;
        return this.listMaterialCheck[index].CheckPerson !== this.listMaterialCheck[index - 1].CheckPerson;
    }

    exportExcel() {
        const rawData = this.listDetailParamsBySerial;

        if (!rawData || rawData.length === 0) {
            alert('Không có dữ liệu để xuất!');
            return;
        }

        // 1. TẠO DANH SÁCH TẤT CẢ CÁC KEY DUY NHẤT TỪ detailParams
        const detailKeysSet = new Set<string>();

        // Duyệt qua tất cả các bản ghi để thu thập các key
        rawData.forEach(item => {
            try {
                const detailObj = JSON.parse(item.detailParams);
                Object.keys(detailObj).forEach(key => detailKeysSet.add(key));
            } catch (e) {
                console.error('Lỗi phân tích JSON cho paramsID:', item.paramsID, e);
            }
        });

        const detailKeys = Array.from(detailKeysSet);

        // 2. TẠO CẤU TRÚC DỮ LIỆU PHẲNG (FLAT DATA)
        const processedData = rawData.map(item => {
            // Bắt đầu với các trường tiêu chuẩn
            let flatItem: any = {
                'paramsID': item.paramsID,
                'programName': item.programName,
                'fixLR': item.fixLR,
                'fixID': item.fixID,
                'startTestTime': item.startTestTime,
                'endTestTime': item.endTestTime,
                'timeElapsed': item.timeElapsed,
                'results': item.results,
                // Loại bỏ cột detailParams thô
            };

            let detailObj: any = {};
            try {
                detailObj = JSON.parse(item.detailParams);
            } catch (e) {
                console.error('Không thể phân tích JSON khi tạo dữ liệu phẳng:', item.paramsID);
            }

            // Thêm các key từ detailParams vào đối tượng phẳng
            detailKeys.forEach(key => {
                // Dùng giá trị nếu có, nếu không thì để null hoặc chuỗi rỗng
                flatItem[key] = detailObj[key] !== undefined ? detailObj[key] : null;
            });

            return flatItem;
        });

        // 3. XUẤT FILE EXCEL (Sử dụng SheetJS)
        const workSheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(processedData);
        const workBook: XLSX.WorkBook = { Sheets: { 'Data': workSheet }, SheetNames: ['Data'] };

        const excelBuffer: any = XLSX.write(workBook, { bookType: 'xlsx', type: 'array' });
        this.saveAsExcelFile(excelBuffer, 'DetailParams_Export');
    }

    private saveAsExcelFile(buffer: any, fileName: string): void {
        const data: Blob = new Blob([buffer], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8' });
        const fileURL = URL.createObjectURL(data);

        // Tự động tải file
        const link = document.createElement('a');
        link.href = fileURL;
        link.setAttribute('download', fileName + '_' + new Date().getTime() + '.xlsx');
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }

}