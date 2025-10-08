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
    selector: 'app-list-erorr-by-device-dialog',
    imports: [SharedModule, FormsModule],
    templateUrl: './list-erorr-by-device.dialog.html',
    styleUrls: ['./list-erorr-by-device.dialog.scss'],
})
export class ListErorrByDeviceDialog {

    data: any;
    listErorrs: any[] = [];

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
        this.listErorrs = _.get(this.data, 'errors', []);
        this.cdr.detectChanges();
    }



    close() {
        this.ref.close();
    }
}
