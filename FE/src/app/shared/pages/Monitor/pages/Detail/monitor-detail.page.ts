import { CommonModule, isPlatformBrowser } from '@angular/common'; // <-- THÊM isPlatformBrowser
import { SharedModule } from '../../../../../share.module';
import { ApplicationConfigService } from '../../../../core/config/application-config.service';
import { ChangeDetectorRef, Component, inject, OnInit, PLATFORM_ID } from '@angular/core';
import { StationIndicatorsComponent } from '../../components/station-indicators-component/station-indicators.component';
import { ControlPanelComponent } from '../../components/control-panel-component/control-panel.component';
import { StatusDeviceComponent } from '../../components/status-device-component/status-device.component';
import { ListDevicePlaningComponent } from '../../components/list-device-planing-component/list-device-planing.component';
import { BasePageComponent } from '../../../../core/base-page-component/base-page.component';
import { PlanningWoService } from '../../service/planning-wo.service';
import { BaseChartComponent } from '../../components/base-chart-component/base-chart.component';
import { NgxGaugeModule } from 'ngx-gauge';
import { GauGePointPpmComponent } from '../../components/gauge-point-ppm-component/gauge-point-ppm.component';
import { ConventionalTableComponent } from '../../components/conventional-table-component/conventional-table.component';

@Component({
    selector: 'app-monitor-detail',
    standalone: true,
    templateUrl: './monitor-detail.page.html',
    styleUrls: ['./monitor-detail.page.scss'],
    imports: [CommonModule, SharedModule, StationIndicatorsComponent, ControlPanelComponent,
        StatusDeviceComponent, ListDevicePlaningComponent, BaseChartComponent,
        GauGePointPpmComponent, ConventionalTableComponent]
})
export class MonitorDetailPage extends BasePageComponent<any> {

    data: any;
    options: any;
    listDevices: any[] = [];

    platformId = inject(PLATFORM_ID);
    configService = inject(ApplicationConfigService);

    chartDataErorrGroup: any;
    chartOptionErorrGroup: any;

    chartDataErorrByMechine: any;
    chartOptionErorrByMechine: any;

    constructor(protected override apiService: PlanningWoService) {
        super(apiService);
    }

    override ngOnInit(): void {
        super.ngOnInit();
        this.formatData(this.model.productionOrderModelDetails);
        console.log(this.model);
        this.initChart();
        this.chartDataErorrGroup = this.getChartDataErorrGroup(this.model.errorCommonScadas); // Gọi hàm chuyển đổi ở trên

        this.chartOptionErorrGroup = {
            responsive: true,
            plugins: {
                legend: {
                    position: 'bottom'
                },
                tooltip: {
                    callbacks: {
                        label: function (context: any) {
                            const label = context.label || '';
                            const value = context.raw;
                            return `${label}: ${value}`;
                        }
                    }
                }
            }
        };

        this.chartDataErorrByMechine = this.convertErrorDataToChart(this.model.productionOrderModelDetails);

        this.chartOptionErorrByMechine = {
            responsive: true,
            plugins: {
                legend: {
                    position: 'bottom'
                },
                tooltip: {
                    callbacks: {
                        label: function (context: any) {
                            const label = context.label || '';
                            const value = context.raw;
                            return `${label}: ${value}`;
                        }
                    }
                }
            }
        };
    }

    formatData(data: any[]) {
        const total = data.reduce(
            (acc, item) => {
                const details = item.machineGroupDetails?.machineDetails || [];
                this.listDevices.push(...details);
                details.forEach((detail: any) => {
                    if (detail.errors && detail.errors.length > 0) {
                        detail.errors.forEach((error: any) => {
                            acc.quantity += error.quantity || 0;
                        });
                    }
                    if (detail.detailQuantity && detail.detailQuantity.length > 0) {
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

        console.log("Tổng Input:", total.numberInput);
        console.log("Tổng Output:", total.numberOutput);
        console.log("Tổng Quantity lỗi:", total.quantity);
        console.log(this.listDevices);

    }

    initChart() {
        if (isPlatformBrowser(this.platformId)) {
            const documentStyle = getComputedStyle(document.documentElement);
            const textColor = documentStyle.getPropertyValue('--text-color');

            this.data = {
                datasets: [
                    {
                        data: [540, 325, 702],
                        backgroundColor: [documentStyle.getPropertyValue('--p-cyan-500'), documentStyle.getPropertyValue('--p-orange-500'), documentStyle.getPropertyValue('--p-gray-500')],
                        hoverBackgroundColor: [documentStyle.getPropertyValue('--p-cyan-400'), documentStyle.getPropertyValue('--p-orange-400'), documentStyle.getPropertyValue('--p-gray-400')]
                    }
                ]
            };

            this.options = {
                // Thêm cutout cho biểu đồ doughnut nếu bạn muốn (như ví dụ trước)
                // cutout: '60%', 
                plugins: {
                    legend: {
                        labels: {
                            usePointStyle: true,
                            color: textColor
                        }
                    }
                }
            };
            this.cdr.markForCheck(); // Giữ lại để đảm bảo cập nhật UI
        }
    }

    getChartDataErorrGroup(errorCommonScadas: any[]) {
        const countByGroup: { [key: string]: number } = {};

        for (const item of errorCommonScadas) {
            const group = item.errGroup || 'Không xác định';
            countByGroup[group] = (countByGroup[group] || 0) + 1;
        }

        const labels = Object.keys(countByGroup);
        const data = Object.values(countByGroup);

        const backgroundColors = [
            '#42A5F5', '#66BB6A', '#FFA726', '#FF6384',
            '#AA66CC', '#FF4444', '#0099CC', '#00C851'
        ];

        return {
            labels,
            datasets: [
                {
                    data,
                    backgroundColor: backgroundColors.slice(0, labels.length)
                }
            ]
        };
    }

    convertErrorDataToChart(productionOrderModelDetails: any[]): any {
        const labels: string[] = [];
        const data: number[] = [];
        const backgroundColors = [
          '#42A5F5', '#66BB6A', '#FFA726', '#FF6384', 
          '#AA66CC', '#FF4444', '#0099CC', '#00C851'
        ];
      
        for (const item of productionOrderModelDetails) {
          const machine = item?.machineGroupDetails?.machineDetails?.[0]?.machine;
          const errors = item?.machineGroupDetails?.machineDetails?.[0]?.errors || [];
      
          if (!machine) continue;
      
          const totalQuantity = errors.reduce((sum: number, err: any) => {
            return sum + (err?.quantity || 0);
          }, 0);
      
          labels.push(machine.machineName);
          data.push(totalQuantity);
        }
      
        return {
          labels,
          datasets: [
            {
              data,
              backgroundColor: backgroundColors.slice(0, labels.length)
            }
          ]
        };
      }
      





    override save(): void {

    }
}