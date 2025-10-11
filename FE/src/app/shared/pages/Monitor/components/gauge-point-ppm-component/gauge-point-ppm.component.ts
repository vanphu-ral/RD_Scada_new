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
        [value]="ppmValue"
        [min]="gaugeMin"
        [max]="gaugeMax"
        [size]="350" 
        [thick]="12"
        [cap]="'round'"
        [markers]="markets"
        [thresholds]="gaugeThresholds"
        [foregroundColor]="'#2ce91eff'"
        [backgroundColor]="backgroundColor">
        <ngx-gauge-value>
          <div style="font-size: 40px; font-weight: bold;" [style.color]="textColor">
            {{ ppmValue.toFixed(2) }} PPM
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
  private lastData: any;

  public gaugeMin = 0;
  public gaugeMax = 100;
  public gaugeLabel = 'PPM';
  public gaugeType = 'arch';
  public gaugeThresholds = {
    '0': { color: 'red' },
    '75': { color: 'yellow' },
    '85': { color: 'green' }
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
    '25': { color: 'gray', type: 'triangle', size: 5, label: '25', font: '10px arial' },
    '50': { color: 'gray', type: 'triangle', size: 5, label: '50', font: '10px arial' },
    '75': { color: 'gray', type: 'triangle', size: 5, label: '75', font: '10px arial' },
    '85': { color: 'gray', type: 'triangle', size: 5, label: '85', font: '10px arial' },
    '100': { color: 'gray', type: 'triangle', size: 5, label: '100', font: '10px arial' },
  };

  ppmValue = 0;
  textColor = '#0D6EFD';

  public backgroundColor = 'white';

  constructor(private cd: ChangeDetectorRef) { }

  ngOnInit(): void {

  }

  ngOnChanges() {
    if (this.data && this.data !== this.lastData) {
      this.lastData = this.data;
      this.calculatePPM();
    }
  }

  calculatePPM() {
    if (this.data?.planningWO?.totalNumberOutput > 0) {
      this.ppmValue =
        (this.data.planningWO.totalQuantity * 1000000) /
        (this.data.planningWO.totalNumberOutput * this.data.planningWO.quota);
    } else {
      this.ppmValue = 0;
    }
    this.updateTextColor();
  }

  updateTextColor() {
    if (this.ppmValue < 75) {
      this.textColor = 'red';
    } else if (this.ppmValue < 85) {
      this.textColor = 'orange';
    } else {
      this.textColor = 'green';
    }
  }


}