import { CommonModule, isPlatformBrowser } from '@angular/common';
import { SharedModule } from '../../../../../share.module';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';
import { ChangeDetectorRef, Component, inject, Input, OnInit, PLATFORM_ID } from '@angular/core';
import { Util } from '../../../../core/utils/utils-function';

@Component({
    selector: 'app-conventional-table-component',
    standalone: true,
    templateUrl: './conventional-table.component.html',
    styleUrls: ['./conventional-table.component.scss'],
    imports: [CommonModule, SharedModule]
})
export class ConventionalTableComponent implements OnInit {

    @Input() data: any[] = []
    filterDate?: Date;

    constructor(private cd: ChangeDetectorRef) { }

    ngOnInit() {
        this.data = this.data.map(x => ({
            ...x,
            timeScan: x.timeScan ? new Date(x.timeScan) : null,
            timeCheck: x.timeCheck ? new Date(x.timeCheck) : null
        }));
    }

    exportExcel() {
        if (!this.data || this.data.length === 0) {
            return;
        }
        const header = {
            'StageNum': 'stageNum',
            'Machine': 'machineName',
            'Serial board': 'serialBoard',
            'Serial PCS': 'serialItem',
            'Thời gian scan': 'timeScan',
            'Thời gian sửa serial': 'timeCheck',
            'Status': 'serialStatus'
        }
        Util.exportExcel(this.data, header, `Danh sách serial`);
    }


}