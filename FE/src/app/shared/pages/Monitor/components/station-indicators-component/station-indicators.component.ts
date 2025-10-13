import { CommonModule, isPlatformBrowser } from '@angular/common'; // <-- THÊM isPlatformBrowser
import { SharedModule } from '../../../../../share.module';
import { ApplicationConfigService } from '../../../../core/config/application-config.service';
import { ChangeDetectorRef, Component, effect, inject, Input, OnInit, PLATFORM_ID } from '@angular/core';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ListDeviceAcceptWODialogDialog } from '../../dialogs/list-device-accept-wo-dialog/list-device-accept-wo.dialog';
import { AccountService } from '../../../../core/auth/account/account.service';
import { ActivatedRoute } from '@angular/router';
import { PlanningWoService } from '../../service/planning-wo.service';
import _ from 'lodash';
import { Util } from '../../../../core/utils/utils-function';

@Component({
    selector: 'app-station-indicators-component',
    standalone: true,
    templateUrl: './station-indicators.component.html',
    styleUrls: ['./station-indicators.component.scss'],
    imports: [CommonModule, SharedModule]
})
export class StationIndicatorsComponent implements OnInit {

    @Input() data: any
    @Input() listNewDevices: any[] = []
    ref?: DynamicDialogRef;
    listDevices: any[] = []
    account: any
    IsNewList: boolean = false

    constructor(private apiService: PlanningWoService, private dialogService: DialogService, private accountService: AccountService) {
        effect(() => {
            this.account = this.accountService.trackAccount()();
        });
    }

    ngOnInit() {
    }

    async openConfigDialog() {
        await this.loadDataPromise();
        this.ref = this.dialogService.open(ListDeviceAcceptWODialogDialog, {
            header: 'Cấu hình thiết bị tham gia vào lệnh sản xuất',
            width: '1200px',
            modal: true,
            data: {
                data: this.data,
                listDevices: this.listDevices,
                isNewList: this.IsNewList
            },
        });
    }

    loadDataPromise(): Promise<void> {
        return new Promise(resolve => {
            this.apiService.getWoInfor(this.data.planningWO.id).subscribe((data: any) => {
                this.IsNewList = false
                this.listDevices = _.get(data, 'machinesDetailResponses');
                if (Util.isEmptyArray(this.listDevices)) {
                    this.IsNewList = true
                    this.listDevices = _.map(this.listNewDevices, item => ({
                        ...item.machine,
                        machineName: item.machine.machineName,
                        stageId: item.machine.stageId,
                        workOrder: this.data.planningWO.woId,
                        status: 1
                    }));
                }
                resolve();
            });
        });
    }


}