import { CommonModule, isPlatformBrowser } from '@angular/common'; // <-- THÊM isPlatformBrowser
import { SharedModule } from '../../../../../share.module';
import { ApplicationConfigService } from '../../../../core/config/application-config.service';
import { ChangeDetectorRef, Component, inject, Input, OnInit, PLATFORM_ID } from '@angular/core';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ListErorrByDeviceDialog } from '../../dialogs/list-erorr-by-device-dialog/list-erorr-by-device.dialog';

@Component({
    selector: 'app-list-device-planing-component',
    standalone: true,
    templateUrl: './list-device-planing.component.html',
    styleUrls: ['./list-device-planing.component.scss'],
    imports: [CommonModule, SharedModule]
})
export class ListDevicePlaningComponent implements OnInit {

    @Input() data: any[] = []

    ref?: DynamicDialogRef;

    constructor(private cd: ChangeDetectorRef, private dialogService: DialogService) { }

    ngOnInit() {
        
    }

    getTotalInput(detailQuantity: any[]): number {
        if (!detailQuantity || detailQuantity.length === 0) {
            return 0;
        }
        return detailQuantity.reduce((sum, quatity) => sum + quatity.numberInput, 0);
    }

    getTotalOutput(detailQuantity: any[]): number {
        if (!detailQuantity || detailQuantity.length === 0) {
            return 0;
        }
        return detailQuantity.reduce((sum, quatity) => sum + quatity.numberOutput, 0);
    }

    openErrorDialog(data: any) {
        this.ref = this.dialogService.open(ListErorrByDeviceDialog, {
            header: 'Danh sách lỗi',
            width: '1200px',
            modal: true,
            data: data,
        });
        this.ref.onClose.subscribe((result) => {
            if (result) {
            }
        });
    }
    
}