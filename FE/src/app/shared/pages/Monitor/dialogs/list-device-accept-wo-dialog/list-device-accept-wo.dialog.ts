import { ChangeDetectorRef, Component, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { SharedModule } from '../../../../../share.module';
import _ from 'lodash';
import { MonitorService } from '../../service/monitor.service';
import { AutoComplete } from 'primeng/autocomplete';
import { PlanningWoService } from '../../service/planning-wo.service';
import { Util } from '../../../../core/utils/utils-function';

@Component({
    selector: 'app-list-device-accept-wo-dialog',
    imports: [SharedModule, FormsModule],
    templateUrl: './list-device-accept-wo.dialog.html',
    styleUrls: ['./list-device-accept-wo.dialog.scss'],
})
export class ListDeviceAcceptWODialogDialog {

    data: any;
    listDeviceAndStage: any[] = [];
    isNewListDevice: boolean = false;

    constructor(
        public ref: DynamicDialogRef,
        public config: DynamicDialogConfig,
        private monitorService: MonitorService,
        private planningWOService: PlanningWoService,
        private cdr: ChangeDetectorRef
    ) {
        this.data = config.data;
    }

    ngOnInit() {
        this.cdr.detectChanges();
        this.isNewListDevice = _.get(this.data, 'isNewList');
        if(this.isNewListDevice) {
            this.listDeviceAndStage = _.map(_.get(this.data, 'listDevices'), item => {
                return {
                    ...item.machine,
                    machineName: item.machine.machineName,
                    stageId: item.machine.stageId,
                    workOrder: this.data.data.woId,
                    status: 0
                }
            })
        } else {
            this.listDeviceAndStage = _.get(this.data, 'listDevices');
        }
    }

    save() {
        const data = _.map(this.listDeviceAndStage, item => {
            return {
                machineName: item.machineName,
                stageId: item.stageId,
                status: item.status,
                workOrder: item.workOrder
            }
        })
        this.planningWOService.insertListDevietoWo(data).subscribe(res => {
            Util.toastMessage('Thêm thư viện cho WO thành công', 'success');
            this.close();
        })
    }

    close() {
        this.ref.close();
    }
}
