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
    listLines: any[] = [];
    selectLine: any = {};

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
        this.listDeviceAndStage = _.map(_.get(this.data, 'listDevices'), item => {
            return {
                ...item.machine,
                woId: this.data.data.woId
            }
        })
        console.log(this.listDeviceAndStage);
        
    }

    save() {
        console.log(this.listDeviceAndStage);
        
        // this.ref.close(this.listDeviceAndStage);
    }

    close() {
        this.ref.close();
    }
}
