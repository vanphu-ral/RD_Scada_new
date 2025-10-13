import { ChangeDetectorRef, Component, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { SharedModule } from '../../../../../share.module';
import _ from 'lodash';
import { MonitorService } from '../../service/monitor.service';
import { AutoComplete } from 'primeng/autocomplete';
import { PlanningWoService } from '../../service/planning-wo.service';
import { Util } from '../../../../core/utils/utils-function';
import { WOHistoryErrorService } from '../../service/wo-history-error.service';

@Component({
    selector: 'app-warning-dialog',
    imports: [SharedModule, FormsModule],
    templateUrl: './warning.dialog.html',
    styleUrls: ['./warning.dialog.scss'],
})
export class WarningDialog {

    data: any = {};

    messages: any[] = [];
    private audio = new Audio('assets/sound/warning.mp3');
    private intervalId: any;


    constructor(
        public ref: DynamicDialogRef,
        public config: DynamicDialogConfig,
        private woHistoryError: WOHistoryErrorService
    ) {
        this.data = config.data;
    }

    ngOnInit(): void {
        this.messages = this.config.data?.message || [];
        this.audio.volume = 1.0;
        const playSound = () => {
            this.audio.currentTime = 0;
            this.audio.play().catch(() => { });
        };
        playSound();
        this.intervalId = setInterval(playSound, 3000);
    }

    confirm() {
        this.stopSound();
        const errors = _.map(this.messages, item => { return { ...item, status: 1 } })
        this.woHistoryError.updateDetailErrors(errors).subscribe(res => { })
        this.ref.close(true);
    }

    close() {
        this.stopSound();
        this.ref.close(false);
    }

    private stopSound() {
        clearInterval(this.intervalId);
        this.audio.pause();
        this.audio.currentTime = 0;
    }
}
