import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, NgZone } from '@angular/core';
import { SharedModule } from '../../../../../share.module';
import { PlanningWoService } from '../../../Monitor/service/planning-wo.service';
import { GrapSqlService } from '../../../../service/grap-sql.service';
import _ from 'lodash';
import { ScanSerialCheckLogsService } from '../../service/scan-serial-check-log.service';

@Component({
    selector: 'app-scan-serial-check-log',
    standalone: true,
    templateUrl: './scan-serial-check-log.page.html',
    styleUrls: ['./scan-serial-check-log.page.scss'],
    imports: [CommonModule, SharedModule]
})
export class ScanSerialCheckLogsPage {

    data: any[] = [];
    loading: boolean = false;

    constructor(private cdr: ChangeDetectorRef, private ScanSerialCheckLogsService: ScanSerialCheckLogsService) { }

    ngOnInit(): void {
        this.loadData();
    }

    loadData() {
        this.ScanSerialCheckLogsService.getAll().subscribe((res: any) => {
            this.data = res.data;
            
            this.data = Object.values(
                res.reduce((acc: any, item: any) => {
                    const date = item.timeCheck.split(' ')[0]; // lấy yyyy-mm-dd
                    const key = `${date}_${item.userName}`;

                    if (!acc[key]) {
                        acc[key] = {
                            date,
                            userName: item.userName,
                            totalSerial: 0,
                            items: []
                        };
                    }

                    acc[key].items.push(item);
                    acc[key].totalSerial += 1;
                    return acc;
                }, {} as Record<string, any>)
            );
            this.cdr.detectChanges();
        });
    }

    view(data: any) {

    }

    delete(data: any) {
        this.ScanSerialCheckLogsService.delete(data.items[0]).subscribe(() => {
            this.loadData();
        });
    }

    exportExcel(data: any) {

    }

}