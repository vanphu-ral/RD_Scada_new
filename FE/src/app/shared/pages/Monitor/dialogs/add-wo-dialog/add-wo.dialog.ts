import { ChangeDetectorRef, Component, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { SharedModule } from '../../../../../share.module';
import _ from 'lodash';
import { MonitorService } from '../../service/monitor.service';
import { AutoComplete } from 'primeng/autocomplete';
import { PlanningWoService } from '../../service/planning-wo.service';
import { Util } from '../../../../core/utils/utils-function';

@Component({
    selector: 'app-add-wo-dialog',
    imports: [SharedModule, FormsModule],
    templateUrl: './add-wo.dialog.html',
    styleUrls: ['./add-wo.dialog.scss'],
})
export class AddWODialogComponent {

    data: any;
    listWOs: any[] = [];
    totalRecords: number = 0;
    pageSize: number = 10;
    loading: boolean = false;
    filters = {
        woId: '',
        sapWoId: '',
        productCode: ''
    };

    constructor(
        public ref: DynamicDialogRef,
        public config: DynamicDialogConfig,
        private monitorService: MonitorService,
        private planningWOService: PlanningWoService,
        private cdr: ChangeDetectorRef
    ) {
        this.data = config.data;
    }

    ngOnInit() {
        this.loadData(0, this.pageSize);
    }

    loadData(page: number, size: number) {
        this.loading = true;
        this.monitorService.getAllCanSearch(this.filters, page, size).subscribe({
            next: (res: any) => {
                this.listWOs = res.content;
                this.totalRecords = res.totalElements;
                this.loading = false;
                this.cdr.detectChanges();
            },
            error: (err) => {
                console.error(err);
                this.loading = false;
            }
        });
    }

    onPage(event: any) {
        const page = event.first / event.rows;
        this.pageSize = event.rows;
        this.loadData(page, this.pageSize);
    }

    onSearch() {
        this.loadData(0, this.pageSize);
    }


    selectWO(item: any) {
        this.planningWOService.create(item).subscribe(res => {
            Util.toastMessage('Thêm lệnh sản xuất thành công', 'success');
            this.ref.close(res);
        })
    }

    close() {
        this.ref.close();
    }
}
