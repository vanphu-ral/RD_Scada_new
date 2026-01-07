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

    listDataFCTATE: any[] = [];
    listDataLUYEN: any[] = [];

    loading: boolean = false;


    constructor(private cdr: ChangeDetectorRef, private grapSqlService: GrapSqlService, private planningWoService: PlanningWoService, private detailParamsService: DetailParamsService) { }

    ngOnInit(): void {
        this.filter.option = 'serialBoard'
    }

    search() {
        if (!this.filter.serial || !this.filter.option) return;
        this.loading = true;
        this.detailParamsService.getTestInfoBySerial(this.filter.serial).subscribe(res => {
            _.map(res, item => item.detailParams = JSON.parse(item.detailParams))
            console.log(res);
            
            this.listDataFCTATE = _.filter(res, item => item.machineType == 1)
            this.listDataLUYEN = _.filter(res, item => item.machineType == 2)
            this.cdr.detectChanges();
        })
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
        const rawData = this.listDataFCTATE;
        if (!rawData || rawData.length === 0) {
            alert('Không có dữ liệu để xuất!');
            return;
        }
        const detailKeysSet = new Set<string>();
        rawData.forEach(item => {
            try {
                const detailObj = item.detailParams.data
                Object.keys(detailObj).forEach(key => detailKeysSet.add(key));
            } catch (e) {
                console.error('Lỗi phân tích JSON cho paramsID:', item.paramsID, e);
            }
        });
        const detailKeys = Array.from(detailKeysSet);
        const processedData = rawData.map(item => {
            let flatItem: any = {
                'paramsID': item.paramsID,
            };
            let detailObj: any = item.detailParams.data;
            try {
                detailObj = JSON.parse(item.detailParams);
            } catch (e) {
                console.error('Không thể phân tích JSON khi tạo dữ liệu phẳng:', item.paramsID);
            }
            detailKeys.forEach(key => {
                flatItem[key] = detailObj[key] !== undefined ? detailObj[key] : null;
            });
            return flatItem;
        });
        const workSheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(processedData);
        const workBook: XLSX.WorkBook = { Sheets: { 'Data': workSheet }, SheetNames: ['Data'] };
        const excelBuffer: any = XLSX.write(workBook, { bookType: 'xlsx', type: 'array' });
        this.saveAsExcelFile(excelBuffer, 'DetailParams_Export');
    }

    exportExcelLuyen() {
        // 1. Kiểm tra dữ liệu đầu vào
        if (!this.listDataLUYEN || this.listDataLUYEN.length === 0) {
            return;
        }

        const exportData: any[] = [];

        // 2. Duyệt qua từng Serial (Dòng cha)
        this.listDataLUYEN.forEach(item => {
            const childData = item.detailParams?.data;

            if (Array.isArray(childData) && childData.length > 0) {
                // Duyệt qua 10 dòng con bên trong mỗi Serial
                childData.forEach((child: any) => {
                    exportData.push({
                        'Serial': item.serial,
                        'Work Order': item.workOrder,
                        'Test Result': item.results,
                        'Date': child.date,
                        'Temperature (Temp)': child.Temp,
                        'Voltage In (U_in)': child.U_in,
                        'Voltage Out (U_out)': child.U_out,
                        'Current Out (I_out)': child.I_out,
                        'Machine Type': 'Luyện'
                    });
                });
            } else {
                // Nếu chẳng may có dòng cha mà không có data con, vẫn xuất dòng cha
                exportData.push({
                    'Serial': item.serial,
                    'Work Order': item.workOrder,
                    'Test Result': item.results,
                    'Machine Type': 'Luyện'
                });
            }
        });

        // 3. Khởi tạo Worksheet từ mảng đã làm phẳng
        const workSheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(exportData);

        // 4. Cấu hình độ rộng các cột (tùy chọn để file Excel đẹp hơn)
        const wscols = [
            { wch: 30 }, // Serial
            { wch: 15 }, // Work Order
            { wch: 10 }, // Test Result
            { wch: 25 }, // Date
            { wch: 15 }, // Temp
            { wch: 15 }, // U_in
            { wch: 15 }, // U_out
            { wch: 15 }  // I_out
        ];
        workSheet['!cols'] = wscols;

        // 5. Tạo Workbook và tải file
        const workBook: XLSX.WorkBook = {
            Sheets: { 'Dữ liệu máy Luyện': workSheet },
            SheetNames: ['Dữ liệu máy Luyện']
        };

        const excelBuffer: any = XLSX.write(workBook, { bookType: 'xlsx', type: 'array' });
        this.saveAsExcelFile(excelBuffer, 'Export_May_Luyen');
    }

    private saveAsExcelFile(buffer: any, fileName: string): void {
        const data: Blob = new Blob([buffer], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8' });
        const fileURL = URL.createObjectURL(data);
        const link = document.createElement('a');
        link.href = fileURL;
        link.setAttribute('download', fileName + '_' + new Date().getTime() + '.xlsx');
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }

}