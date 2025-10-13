import { CommonModule, isPlatformBrowser } from '@angular/common';
import { ChangeDetectorRef, Component, inject, OnInit, PLATFORM_ID } from '@angular/core';
import { SharedModule } from '../../../../../share.module';
import { ApplicationConfigService } from '../../../../core/config/application-config.service';
import { BasePageComponent } from '../../../../core/base-page-component/base-page.component';
import { PlanningWoService } from '../../service/planning-wo.service';
import { StationIndicatorsComponent } from '../../components/station-indicators-component/station-indicators.component';
import { ControlPanelComponent } from '../../components/control-panel-component/control-panel.component';
import { StatusDeviceComponent } from '../../components/status-device-component/status-device.component';
import { ListDevicePlaningComponent } from '../../components/list-device-planing-component/list-device-planing.component';
import { BaseChartComponent } from '../../components/base-chart-component/base-chart.component';
import { GauGePointPpmComponent } from '../../components/gauge-point-ppm-component/gauge-point-ppm.component';
import { ConventionalTableComponent } from '../../components/conventional-table-component/conventional-table.component';
import { NgxGaugeModule } from 'ngx-gauge';
import { GaugeProductionOutputComponent } from '../../components/gauge-production-output-component/gauge-production-output.component';
import { ActivatedRoute, ActivatedRouteSnapshot } from '@angular/router';
import { filter, interval, Subscription } from 'rxjs';
import { Util } from '../../../../core/utils/utils-function';
import { SocketService } from '../../../../service/socket.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import _ from 'lodash';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { WarningDialog } from '../../dialogs/warning-dialog/warning.dialog';
import { WOHistoryErrorService } from '../../service/wo-history-error.service';
@Component({
    selector: 'app-monitor-detail',
    standalone: true,
    templateUrl: './monitor-detail.page.html',
    styleUrls: ['./monitor-detail.page.scss'],
    imports: [
        CommonModule,
        SharedModule,
        StationIndicatorsComponent,
        ControlPanelComponent,
        StatusDeviceComponent,
        ListDevicePlaningComponent,
        BaseChartComponent,
        GauGePointPpmComponent,
        ConventionalTableComponent,
        GaugeProductionOutputComponent,
    ]
})
export class MonitorDetailPage extends BasePageComponent<any> implements OnInit {

    data: any;
    options: any;
    listDevices: any[] = [];
    chartDataErorrGroup: any;
    chartOptionErorrGroup: any;
    chartDataErorrByMechine: any;
    chartOptionErorrByMechine: any;
    chartDataErorrByStage: any;
    chartOptionErorrByStage: any;
    scanSerialChecks: any[] = [];
    platformId = inject(PLATFORM_ID);
    private refreshSub?: Subscription;

    messages: any[] = [];
    private sub!: Subscription;

    showWarning = false;
    warningMessage = '';
    private ref?: DynamicDialogRef;

    constructor(protected override apiService: PlanningWoService, private socketService: SocketService,
        private dialogService: DialogService, private woHistoryError: WOHistoryErrorService) {
        super(apiService);
    }

    override ngOnInit(): void {
        super.ngOnInit();
        this.callDataFrequency();
        this.socketService.connect();
        this.refreshSub = interval(180000).subscribe(() => {
            this.callDataFrequency();
        });
        this.woHistoryError.getErrorByWoId(this.model.planningWO.woId).subscribe((data: any) => {
            this.messages = _.filter(data, { status: 0 });
            if (this.messages.length > 0) {
                this.openWarningDialog();
            }
        });
        this.sub = this.socketService.currentMessage$
            .pipe(filter((msg) => !!msg)) // chỉ xử lý khi có dữ liệu thật
            .subscribe((msg) => {
                console.log('📩 Nhận message realtime từ Kafka:', msg);
                if(_.isEqual(_.get(msg, 'workOrder'), this.model.planningWO.woId)) {
                    this.messages.push(msg);
                    if (this.ref) {
                        const instance = (this.ref as any)._componentRef?.instance;
                        if (instance?.updateMessages) {
                            instance.updateMessages(this.messages);
                        }
                    } else {
                        this.openWarningDialog();
                    }
                }
            });
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
        this.socketService.disconnect();
    }

    callDataFrequency(): void {
        const id = this.route.snapshot.paramMap.get('id');
        if (!id) return;
        this.apiService.getWoDetailInfor(id).subscribe((data: any) => {
            this.formatData(data.productionOrderModelDetails);
            this.chartDataErorrGroup = this.getChartDataErrorByGroup(
                data.productionOrderModelDetails,
                this.model.errorCommonScadas
            );
            this.chartOptionErorrGroup = this.getDefaultChartOption();
            this.chartDataErorrByMechine = this.getChartDataError(data.productionOrderModelDetails);
            this.chartOptionErorrByMechine = this.getDefaultChartOption();
            this.chartDataErorrByStage = this.getChartDataErorrByStage(data.productionOrderModelDetails);
            this.chartOptionErorrByStage = this.getDefaultChartOption();
            this.cdr.detectChanges();
        });
        this.apiService.getWoErrorInfor(id).subscribe((data: any) => {
            this.scanSerialChecks = data.scanSerialChecks.map((x: any) => ({
                ...x,
                timeScan: x.timeScan ? new Date(x.timeScan) : null
            }));
            this.cdr.detectChanges();
        });
    }

    formatData(data: any[]): void {
        if (!data) return;
        const total = data.reduce(
            (acc, item) => {
                const details = item.machineGroupDetails?.machineDetails || [];
                this.listDevices.push(...details);
                details.forEach((detail: any) => {
                    if (detail.errors?.length) {
                        detail.errors.forEach((error: any) => (acc.quantity += error.quantity || 0));
                    }
                    if (detail.detailQuantity?.length) {
                        detail.detailQuantity.forEach((q: any) => {
                            acc.numberInput += q.numberInput || 0;
                            acc.numberOutput += q.numberOutput || 0;
                        });
                    } else {
                        acc.numberInput += detail.numberInput || 0;
                        acc.numberOutput += detail.numberOutput || 0;
                    }
                });
                return acc;
            },
            { numberInput: 0, numberOutput: 0, quantity: 0 }
        );
        this.model.planningWO.totalNumberInput = total.numberInput;
        this.model.planningWO.totalNumberOutput = total.numberOutput;
        this.model.planningWO.totalQuantity = total.quantity;

        this.model = { ...this.model };
    }

    getChartDataErrorByGroup(
        productionOrderModelDetails: any[],
        errorCommonScadas: any[]
    ): any {
        const errorGroupTotals: Record<string, number> = {};
        for (const item of productionOrderModelDetails || []) {
            const machineDetails = item?.machineGroupDetails?.machineDetails || [];
            for (const detail of machineDetails) {
                const errors = detail?.errors || [];
                for (const err of errors) {
                    if (err?.quantity > 0) {
                        const matchedError = errorCommonScadas.find(
                            e => e.id === err.errorId
                        );
                        const groupName = matchedError?.errGroup || 'Nhóm khác';
                        errorGroupTotals[groupName] = (errorGroupTotals[groupName] || 0) + err.quantity;
                    }
                }
            }
        }
        const labels: string[] = Object.keys(errorGroupTotals);
        const data: number[] = Object.values(errorGroupTotals);
        const colors = [
            '#42A5F5', '#66BB6A', '#FFA726', '#FF6384',
            '#AA66CC', '#FF4444', '#0099CC', '#00C851',
            '#F9A825', '#8E24AA', '#26C6DA', '#D4E157'
        ];
        return {
            labels,
            datasets: [
                {
                    label: 'Tổng lỗi theo nhóm',
                    data,
                    backgroundColor: colors.slice(0, labels.length),
                    borderWidth: 1
                }
            ]
        };
    }


    getChartDataErorrByStage(productionOrderModelDetails: any[]): any {
        const stageTotals: Record<number, number> = {};

        for (const item of productionOrderModelDetails) {
            const machineDetails = item?.machineGroupDetails?.machineDetails || [];
            for (const detail of machineDetails) {
                const stageId = detail?.machine?.stageId;
                const errors = detail?.errors || [];
                if (!stageId) continue;

                const totalQuantity = errors.reduce((sum: number, err: any) => sum + (err?.quantity || 0), 0);
                stageTotals[stageId] = (stageTotals[stageId] || 0) + totalQuantity;
            }
        }
        const labels = Object.keys(stageTotals).map(id => `Stage ${id}`);
        const data = Object.values(stageTotals);
        const colors = [
            '#42A5F5', '#66BB6A', '#FFA726', '#FF6384',
            '#AA66CC', '#FF4444', '#0099CC', '#00C851'
        ];
        return {
            labels,
            datasets: [
                {
                    data,
                    backgroundColor: colors.slice(0, labels.length)
                }
            ]
        };
    }

    getChartDataError(productionOrderModelDetails: any[]): any {
        const errorTotals: Record<string, number> = {};
        for (const item of productionOrderModelDetails || []) {
            const machineDetails = item?.machineGroupDetails?.machineDetails || [];
            for (const detail of machineDetails) {
                const errors = detail?.errors || [];
                for (const err of errors) {
                    if (err?.quantity > 0) {
                        const name = err?.errorName?.trim() || 'Không xác định';
                        errorTotals[name] = (errorTotals[name] || 0) + err.quantity;
                    }
                }
            }
        }
        const labels: string[] = Object.keys(errorTotals);
        const data: number[] = Object.values(errorTotals);
        const colors = [
            '#42A5F5', '#66BB6A', '#FFA726', '#FF6384',
            '#AA66CC', '#FF4444', '#0099CC', '#00C851',
            '#F9A825', '#8E24AA', '#26C6DA', '#D4E157'
        ];
        return {
            labels,
            datasets: [
                {
                    label: 'Tổng lỗi',
                    data,
                    backgroundColor: colors.slice(0, labels.length),
                    borderWidth: 1
                }
            ]
        };
    }


    getDefaultChartOption() {
        return {
            responsive: true,
            plugins: {
                legend: {
                    display: false
                },
                tooltip: {
                    callbacks: {
                        label: (context: any) => {
                            const dataset = context.dataset.data;
                            const total = dataset.reduce((sum: number, val: number) => sum + val, 0);
                            const value = context.raw;
                            const percent = ((value / total) * 100).toFixed(1);
                            return `${context.label}: ${percent}%`;
                        }
                    }
                }
            }
        };
    }

    openWarningDialog() {
        if (this.ref) {
            (this.ref as any).componentRef.instance.message = this.messages;
            return;
        }
        this.ref = this.dialogService.open(WarningDialog, {
            header: 'Cảnh báo hệ thống',
            width: 'auto',
            modal: true,
            closable: false,
            data: { message: this.messages }
        });
        this.ref.onClose.subscribe((confirmed) => {
            if (confirmed) {
                console.log('✅ Người dùng đã xác nhận cảnh báo');
                this.messages = [];
            } else {
                console.log('❌ Người dùng đã đóng cảnh báo');
            }
            this.ref = undefined;
        });
    }



    override save(): void { }
}