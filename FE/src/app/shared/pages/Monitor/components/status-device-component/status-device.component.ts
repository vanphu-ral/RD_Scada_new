import { CommonModule, isPlatformBrowser } from '@angular/common'; // <-- THÊM isPlatformBrowser
import { SharedModule } from '../../../../../share.module';
import { ApplicationConfigService } from '../../../../core/config/application-config.service';
import { ChangeDetectorRef, Component, inject, Input, OnInit, PLATFORM_ID } from '@angular/core';

@Component({
    selector: 'app-status-device-component',
    standalone: true,
    templateUrl: './status-device.component.html',
    styleUrls: ['./status-device.component.scss'],
    imports: [CommonModule, SharedModule]
})
export class StatusDeviceComponent implements OnInit {

    @Input() data: any

    constructor(private cd: ChangeDetectorRef) { }

    ngOnInit() {

    }

    
}