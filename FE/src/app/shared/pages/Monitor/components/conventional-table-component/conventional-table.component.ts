import { CommonModule, isPlatformBrowser } from '@angular/common';
import { SharedModule } from '../../../../../share.module';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';
import { ChangeDetectorRef, Component, inject, Input, OnInit, PLATFORM_ID } from '@angular/core';

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
        const exportData = this.data.map((row: any) => ({
            'StageNum': row.stageNum,
            'Machine': row.machineName,
            'Serial board': row.serialBoard,
            'Serial PCS': row.serialItem,
            'Thời gian scan': row.timeScan ? new Date(row.timeScan).toLocaleString('vi-VN') : '',
            'Thời gian sửa serial': row.timeCheck ? new Date(row.timeCheck).toLocaleString('vi-VN') : '',
            'Status': row.serialStatus
        }));
        const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(exportData);
        const workbook: XLSX.WorkBook = { Sheets: { 'Dữ liệu': worksheet }, SheetNames: ['Dữ liệu'] };
        const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
        const blob: Blob = new Blob([excelBuffer], { type: 'application/octet-stream' });
        saveAs(blob, `DuLieu_Serial_${new Date().toLocaleDateString('vi-VN')}.xlsx`);
    }


}