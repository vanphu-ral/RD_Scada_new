import { CommonModule, isPlatformBrowser } from '@angular/common'; // <-- THÊM isPlatformBrowser
import { SharedModule } from '../../../../../share.module';
import { ChangeDetectorRef, Component, inject, Input, OnInit, PLATFORM_ID } from '@angular/core';
import { NgxGaugeModule } from 'ngx-gauge';

@Component({
  selector: 'gauge-point-ppm-component',
  standalone: true,
  template: `
  <div class="widget-content relative" appFullscreen #fs="appFullscreen">
  <i class="fa-solid fa-expand fullscreen-btn" (click)="fs.toggleFullscreen()"></i>
  <h5>Chỉ số PPM</h5>
        <ngx-gauge
  [type]="'arch'"
  [value]="data.planningWO.totalNumberOutput > 0 ? (data.planningWO.totalQuantity * 1000000) / (data.planningWO.totalNumberOutput * data.planningWO.quota) : 0"
  [min]="gaugeMin"
  [max]="gaugeMax"
  [size]="350" 
  [thick]="12"
  [cap]="'round'"
  [markers]="markets"
  [thresholds]="gaugeThresholds"
  [thresholds]="gaugeOptions"        [foregroundColor]="'#2ce91eff'"   [backgroundColor]="backgroundColor" >
  <ngx-gauge-value>
    <div style="font-size: 40px; font-weight: bold; color: #0D6EFD;">
      {{ data.planningWO.totalNumberOutput > 0 ? ((data.planningWO.totalQuantity * 1000000) / (data.planningWO.totalNumberOutput * data.planningWO.quota)).toFixed(2) : 0 }} PPM
    </div>
  </ngx-gauge-value>
</ngx-gauge>
</div>
    `,
  imports: [CommonModule, SharedModule, NgxGaugeModule],
  styles: [`
      .widget-content {
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
      }`
    ]
})
export class GauGePointPpmComponent implements OnInit {

  @Input() data: any
  public gaugeMin = 0;
  public gaugeMax = 1500;
  public gaugeLabel = 'PPM';
  public gaugeType = 'arch';
  public gaugeThresholds = {
    '0': { color: 'green' },
    '500': { color: 'yellow' },
    '910': { color: 'red' }
  };

  public gaugeOptions = {
    hasNeedle: true,
    needleColor: 'white',
    needleUpdateSpeed: 1000,
    arcColors: ['rgb(230,230,230)', 'lightgray'],
    arcDelimiters: [100],
    rangeLabel: ['0', '100'],
  };

  public markets = {
    '0': { color: '#00000000', size: 0, label: '0', font: '10px arial' },
    '250': { color: 'gray', type: 'triangle', size: 5, label: '250', font: '10px arial' },
    '500': { color: 'gray', type: 'triangle', size: 5, label: '500', font: '10px arial' },
    '705': { color: 'gray', type: 'triangle', size: 5, label: '705', font: '10px arial' },
    '910': { color: 'gray', type: 'triangle', size: 5, label: '910', font: '10px arial' },
    '1205': { color: 'gray', type: 'triangle', size: 5, label: '1205', font: '10px arial' },
    '1500': { color: '#00000000', size: 0, label: '1500', font: '10px arial' },
  };

  public backgroundColor = 'white';

  constructor(private cd: ChangeDetectorRef) { }

  ngOnInit() {

  }


}