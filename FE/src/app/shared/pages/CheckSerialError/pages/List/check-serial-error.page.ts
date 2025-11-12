import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, NgZone } from '@angular/core';
import { SharedModule } from '../../../../../share.module';
import { PlanningWoService } from '../../../Monitor/service/planning-wo.service';
import { GrapSqlService } from '../../../../service/grap-sql.service';
import _ from 'lodash';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { WarningSerialErrorDialog } from '../../dialogs/warning-serial-error-dialog/warning-serial-error.dialog';
import { ScanSerialCheck } from '../../../Monitor/service/scan-serial-check.service';

@Component({
    selector: 'app-check-serial-error',
    standalone: true,
    templateUrl: './check-serial-error.page.html',
    styleUrls: ['./check-serial-error.page.scss'],
    imports: [CommonModule, SharedModule]
})
export class CheckSerialErrorPage {

    data: any[] = [];
    filter: any = {
        serialBoard: '',
        serialItem: '',
    }
    optionSerial: any[] = [
        { name: 'Serial Board', value: 'serialBoard' },
        { name: 'Serial Item', value: 'serialItem' },
    ];
    listSerial: any[] = [];
    serial: any
    loading: boolean = false;
    ref?: DynamicDialogRef


    constructor(private cdr: ChangeDetectorRef, private grapSqlService: GrapSqlService, private planningWoService: PlanningWoService, private dialogService: DialogService, private scanSerialCheckService: ScanSerialCheck) { }

    ngOnInit(): void {
    }

    search() {
        if (!this.filter.serial) return;
        if(this.filter.serial.split('-').pop() != this.filter.key){
            this.openDialog();
        }
        this.loading = true;
        this.scanSerialCheckService.getErrorBySerial(this.filter.serial).subscribe({
            next: (res: any) => {
                this.data = res.planningWOS
                this.listSerial = res.checkSerialResults.sort((a: any, b: any) => {
                    const serialA = a.serialType ?? '';
                    const serialB = b.serialType ?? '';
                    const isAlphaNumA = /[a-zA-Z]/.test(serialA);
                    const isAlphaNumB = /[a-zA-Z]/.test(serialB);
                    if (isAlphaNumA && !isAlphaNumB) return -1;
                    if (!isAlphaNumA && isAlphaNumB) return 1;
                    return serialA.localeCompare(serialB, undefined, { numeric: true });
                });
                this.loading = false;
                this.serial = this.filter.serial
                this.filter.serial = '';
                if(this.data.length > 1 && this.data[0].woId != this.data[1].woId) this.openDialog();
                this.cdr.detectChanges();
            },
            error: () => {
                this.loading = false;
                this.cdr.detectChanges();
            }
        });
    }

    openDialog() {
        this.ref = this.dialogService.open(WarningSerialErrorDialog, {
            header: 'Lỗi',
            width: '50%',
            contentStyle: { "max-height": "500px", "overflow": "auto" },
            modal: true,
            data: this.data
        });

        this.ref.onClose.subscribe((res: any) => {
            if (res) {
                // this.search();
            }
        });
    }

}