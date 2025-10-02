import { CommonModule, isPlatformBrowser } from '@angular/common'; // <-- THÊM isPlatformBrowser
import { SharedModule } from '../../../../../share.module';
import { ApplicationConfigService } from '../../../../core/config/application-config.service';
import { ChangeDetectorRef, Component, inject, OnInit, PLATFORM_ID } from '@angular/core';

@Component({
    selector: 'app-monitor-detail',
    standalone: true,
    templateUrl: './monitor-detail.page.html',
    styleUrls: ['./monitor-detail.page.scss'],
    imports: [CommonModule, SharedModule]
})
export class MonitorDetailPage implements OnInit {
    data: any;
    options: any;

    platformId = inject(PLATFORM_ID);
    configService = inject(ApplicationConfigService);

    constructor(private cd: ChangeDetectorRef) { }

    ngOnInit() {
        this.initChart();
    }

    initChart() {
        if (isPlatformBrowser(this.platformId)) {
            const documentStyle = getComputedStyle(document.documentElement);
            const textColor = documentStyle.getPropertyValue('--text-color');

            this.data = {
                labels: ['A', 'B', 'C'],
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
            this.cd.markForCheck(); // Giữ lại để đảm bảo cập nhật UI
        }
    }

}