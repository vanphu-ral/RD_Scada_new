import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, NgZone } from '@angular/core';
import { SharedModule } from '../../../../../share.module';
import { PlanningWoService } from '../../../Monitor/service/planning-wo.service';
import { GrapSqlService } from '../../../../service/grap-sql.service';
import _ from 'lodash';

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


    constructor(private cdr: ChangeDetectorRef, private grapSqlService: GrapSqlService, private planningWoService: PlanningWoService) { }

    ngOnInit(): void {
    }

    search() {
        if (!this.filter.serial || !this.filter.option) return;
        const apiCall =
            this.filter.option === 'serialBoard'
                ? this.planningWoService.filterBySerialBoard(this.filter.serial)
                : this.planningWoService.filterBySerialItem(this.filter.serial);

        apiCall.subscribe({
            next: (res: any) => {
                this.data = res ? { ...res } : null;
                console.log(res);
                this.grapSqlService.QmsToDoiTraInfoByLotNumber(_.get(res, 'planningWO.lotNumber')).subscribe(res => {
                    console.log(res);
                    this.listBOMInfo = _.get(res, 'data.qmsToDoiTraInfoByLotNumber.pqcBomWorkOrder') || [];
                    this.listMaterialCheck = this.mergePqcData(
                        _.get(res, 'data.qmsToDoiTraInfoByLotNumber.pqcCheckNVL') || [],
                        _.get(res, 'data.qmsToDoiTraInfoByLotNumber.pqcCheckTestNVL') || []
                    );
                    console.log(this.listMaterialCheck);
                    
                    this.listMaterialUse = _.get(res, 'data.qmsToDoiTraInfoByLotNumber.pqcScan100Pass') || [];
                    this.listMaterialNotRecommend = _.get(res, 'data.qmsToDoiTraInfoByLotNumber.pqcScan100Fail') || [];
                    this.listpqcBomQuantity = _.get(res, 'data.qmsToDoiTraInfoByLotNumber.pqcBomQuantity') || [];
                    this.listpqcBomErrorDetail = _.get(res, 'data.qmsToDoiTraInfoByLotNumber.pqcBomErrorDetail') || [];
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

}