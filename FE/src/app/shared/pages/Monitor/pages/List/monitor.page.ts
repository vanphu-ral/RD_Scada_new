import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, signal } from '@angular/core';
import { SharedModule } from '../../../../../share.module';
import { HttpClient } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { MonitorService } from '../../service/monitor.service';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { AddWODialogComponent } from '../../dialogs/add-wo-dialog/add-wo.dialog';
import { PlanningWoService } from '../../service/planning-wo.service';

@Component({
    selector: 'app-monitor',
    standalone: true,
    templateUrl: './monitor.page.html',
    styleUrls: ['./monitor.page.scss'],
    imports: [CommonModule, SharedModule]
})
export class MonitorPage {
    data: any[] = [];
    totalRecords: number = 0;
    pageSize: number = 10;
    ref?: DynamicDialogRef;
    loading: boolean = false;
    filters = {
        branchCode: '',
        productCode: '',
        status: '',
    }

    constructor(private router: Router, private route: ActivatedRoute, private planningWoService: PlanningWoService, private cdr: ChangeDetectorRef, private dialogService: DialogService) { }

    ngOnInit(): void {
        this.loadData(0, this.pageSize);
    }

    loadData(page: number, size: number) {
        this.loading = true;
        this.planningWoService.getPlanningWOs(this.filters, page, size).subscribe({
            next: (res) => {
                this.data = res.content;
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
        const size = event.rows;
        this.loadData(page, size);
    }

    onSearch() {
        this.loadData(0, this.pageSize);
    }

    addWODialog() {
        this.ref = this.dialogService.open(AddWODialogComponent, {
            header: 'Danh sách mã WO',
            width: '1200px',
            modal: true,
            data: {},
        });
        this.ref.onClose.subscribe((result) => {
            if (result) {
                this.loadData(0, this.pageSize);
            }
        });
    }

    viewItem(item: any) {
        this.router.navigate([item.id, 'view'], { relativeTo: this.route });
    }

}