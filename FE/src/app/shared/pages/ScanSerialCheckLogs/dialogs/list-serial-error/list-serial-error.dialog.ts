import { ChangeDetectorRef, Component, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { SharedModule } from '../../../../../share.module';
import _ from 'lodash';
import { Util } from '../../../../core/utils/utils-function';

@Component({
    selector: 'app-list-serial-error-dialog',
    imports: [SharedModule, FormsModule],
    templateUrl: './list-serial-error.dialog.html',
    styleUrls: ['./list-serial-error.dialog.scss'],
})
export class ListSerialErrorDialog {

    data: any;
    listDeviceAndStage: any[] = [];
    isNewListDevice: boolean = false;

    constructor(
        public ref: DynamicDialogRef,
        public config: DynamicDialogConfig,
        private cdr: ChangeDetectorRef
    ) {
        this.data = config.data;
    }

    exportExcel() {
        const header = {
            'Serial': 'serialCheck',
            'Kết quả': 'result',
            'Thời gian check': 'timeCheck',
            'Lệnh SX xuất hiện': 'wo',
            'Người check': 'userName'
        }
        Util.exportExcel(this.data, header, `Danh sách serial scan ngày ${this.data[0].timeCheck}`);
    }
    close() {
        this.ref.close();
    }
}
