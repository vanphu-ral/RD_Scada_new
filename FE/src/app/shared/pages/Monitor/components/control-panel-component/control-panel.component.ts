import { CommonModule, isPlatformBrowser } from '@angular/common'; // <-- THÊM isPlatformBrowser
import { SharedModule } from '../../../../../share.module';
import { ApplicationConfigService } from '../../../../core/config/application-config.service';
import { ChangeDetectorRef, Component, inject, Input, OnInit, PLATFORM_ID } from '@angular/core';

@Component({
    selector: 'app-control-panel-component',
    standalone: true,
    templateUrl: './control-panel.component.html',
    styleUrls: ['./control-panel.component.scss'],
    imports: [CommonModule, SharedModule]
})
export class ControlPanelComponent implements OnInit {

    @Input() data: any

    constructor(private cd: ChangeDetectorRef) { }

    ngOnInit() {

    }

    
}