import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, signal } from '@angular/core';
import { SharedModule } from '../../../../../share.module';
import { HttpClient } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { PlanningWoService } from '../../../Monitor/service/planning-wo.service';
import { Util } from '../../../../core/utils/utils-function';
import { ProductOrderModel } from '../../../../models/Traceability/product-order.model';

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


    constructor(private cdr: ChangeDetectorRef, private route: ActivatedRoute, private planningWoService: PlanningWoService) { }

    ngOnInit(): void {
    }

    search() {
        if (Util.isEmpty(this.filter.serial) || Util.isEmpty(this.filter.option)) return;

        const apiCall =
            this.filter.option === 'serialBoard'
                ? this.planningWoService.filterBySerialBoard(this.filter.serial)
                : this.planningWoService.filterBySerialItem(this.filter.serial);

        apiCall.subscribe({
            next: (res: any) => {
                this.data = res ? { ...res } : null;
                this.cdr.detectChanges();
            },
            error: (err) => {
                this.data = null;
                this.cdr.detectChanges();
            }
        });
    }

    clearData() {
        this.data = null;
        this.cdr.detectChanges();
    }

}