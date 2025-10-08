import { CommonModule, isPlatformBrowser } from '@angular/common';
import { SharedModule } from '../../../../../share.module';
import { ApplicationConfigService } from '../../../../core/config/application-config.service';
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
            timeScan: x.timeScan ? new Date(x.timeScan) : null
        }));
        
    }

}