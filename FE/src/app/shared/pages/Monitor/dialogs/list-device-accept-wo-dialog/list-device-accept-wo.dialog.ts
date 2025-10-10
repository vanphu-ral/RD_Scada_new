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
        this.listDeviceAndStage = [
            {
                stage: 'LR-01',
                stageNum: 1,
                device: 'IOT-01',
                isAccept: true
            },
            {
                stage: 'LR-02',
                stageNum: 2,
                device: 'IOT-02',
                isAccept: true
            },
            {
                stage: 'LR-03',
                stageNum: 3,
                device: 'IOT-03',
                isAccept: true
            },
            {
                stage: 'LR-04',
                stageNum: 4,
                device: 'IOT-04',
                isAccept: true
            },
            {
                stage: 'LR-05',
                stageNum: 5,
                device: 'IOT-05',
                isAccept: true
            },
        ]
    }



    close() {
        this.ref.close();
    }
}
