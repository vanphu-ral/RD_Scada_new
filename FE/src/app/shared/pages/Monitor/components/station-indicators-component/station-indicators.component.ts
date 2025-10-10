import { CommonModule, isPlatformBrowser } from '@angular/common'; // <-- THÊM isPlatformBrowser
import { SharedModule } from '../../../../../share.module';
import { ApplicationConfigService } from '../../../../core/config/application-config.service';
import { ChangeDetectorRef, Component, effect, inject, Input, OnInit, PLATFORM_ID } from '@angular/core';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ListDeviceAcceptWODialogDialog } from '../../dialogs/list-device-accept-wo-dialog/list-device-accept-wo.dialog';
import { AccountService } from '../../../../core/auth/account/account.service';

@Component({
    selector: 'app-station-indicators-component',
    standalone: true,
    templateUrl: './station-indicators.component.html',
    styleUrls: ['./station-indicators.component.scss'],
    imports: [CommonModule, SharedModule]
})
export class StationIndicatorsComponent implements OnInit {

    @Input() data: any
    ref?: DynamicDialogRef;
    account: any

    constructor(private cd: ChangeDetectorRef, private dialogService: DialogService, private accountService: AccountService) {
        effect(() => {
            this.account = this.accountService.trackAccount()();
        });
    }

    ngOnInit() {

    }

    openConfigDialog() {
        this.ref = this.dialogService.open(ListDeviceAcceptWODialogDialog, {
            header: 'Cấu hình thiết bị tham gia vào lệnh sản xuất',
            width: '1200px',
            modal: true,
            data: {},
        });
        this.ref.onClose.subscribe((result) => {
            if (result) {
            }
        });
    }

}