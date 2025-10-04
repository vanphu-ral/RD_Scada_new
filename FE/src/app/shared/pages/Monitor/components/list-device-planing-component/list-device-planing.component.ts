import { CommonModule, isPlatformBrowser } from '@angular/common'; // <-- THÊM isPlatformBrowser
import { SharedModule } from '../../../../../share.module';
import { ApplicationConfigService } from '../../../../core/config/application-config.service';
import { ChangeDetectorRef, Component, inject, Input, OnInit, PLATFORM_ID } from '@angular/core';

@Component({
    selector: 'app-list-device-planing-component',
    standalone: true,
    templateUrl: './list-device-planing.component.html',
    styleUrls: ['./list-device-planing.component.scss'],
    imports: [CommonModule, SharedModule]
})
export class ListDevicePlaningComponent implements OnInit {

    @Input() data: any[] = []

    constructor(private cd: ChangeDetectorRef) { }

    ngOnInit() {
        
    }

    getTotalInput(detailQuantity: any[]): number {
        if (!detailQuantity || detailQuantity.length === 0) {
            return 0;
        }

        // Sử dụng phương thức 'reduce' để tính tổng
        return detailQuantity.reduce((sum, quatity) => sum + quatity.numberInput, 0);
    }

    getTotalOutput(detailQuantity: any[]): number {
        if (!detailQuantity || detailQuantity.length === 0) {
            return 0;
        }

        // Sử dụng phương thức 'reduce' để tính tổng
        return detailQuantity.reduce((sum, quatity) => sum + quatity.numberOutput, 0);
    }
    
}