import { CommonModule, isPlatformBrowser } from '@angular/common'; // <-- THÊM isPlatformBrowser
import { SharedModule } from '../../../../../share.module';
import { ApplicationConfigService } from '../../../../core/config/application-config.service';
import { ChangeDetectorRef, Component, inject, Input, OnInit, PLATFORM_ID } from '@angular/core';

@Component({
    selector: 'app-station-indicators-component',
    standalone: true,
    templateUrl: './station-indicators.component.html',
    styleUrls: ['./station-indicators.component.scss'],
    imports: [CommonModule, SharedModule]
})
export class StationIndicatorsComponent implements OnInit {

    @Input() data: any

    constructor(private cd: ChangeDetectorRef) { }

    ngOnInit() {

    }

    
}