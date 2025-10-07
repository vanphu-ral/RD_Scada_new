import { CommonModule, isPlatformBrowser } from '@angular/common'; // <-- THÊM isPlatformBrowser
import { SharedModule } from '../../../../../share.module';
import { ApplicationConfigService } from '../../../../core/config/application-config.service';
import { ChangeDetectorRef, Component, inject, Input, OnInit, PLATFORM_ID } from '@angular/core';

@Component({
    selector: 'base-chart-component',
    standalone: true,
    template: `
    <div class="widget-content relative" appFullscreen #fs="appFullscreen">
      <i class="fa-solid fa-expand fullscreen-btn" (click)="fs.toggleFullscreen()"></i>
      <h5>{{ title}}</h5>
      <p-chart type="pie" [data]="data" [options]="options" class="w-full md:w-[30rem]" />
    </div>
    `,
    imports: [CommonModule, SharedModule],
    styles: [`.widget-content {
    position: relative;
    text-align: center;
}

.fullscreen-btn {
  position: absolute;
  top: -0;
  right: 5px;
  border: none;
  cursor: pointer;
  border-radius: 4px;
  padding: 4px;
  font-size: 20px;
  z-index: 10;
}`]
})
export class BaseChartComponent implements OnInit {

    @Input() data: any
    @Input() options: any
    @Input() title: any

    constructor(private cd: ChangeDetectorRef) { }

    ngOnInit() {

    }


}