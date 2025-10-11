import { CommonModule } from '@angular/common';
import { SharedModule } from '../../../../../share.module';
import { ChangeDetectorRef, Component, Input, OnChanges } from '@angular/core';
import { NgxGaugeModule } from 'ngx-gauge';

@Component({
  selector: 'gauge-production-output-component',
  standalone: true,
  template: `
  <div class="widget-content relative" appFullscreen #fs="appFullscreen">
    <i class="fa-solid fa-expand fullscreen-btn" (click)="fs.toggleFullscreen()"></i>
    <h5>Tiến độ sản lượng</h5>
    <ngx-gauge
      [type]="'full'"
      [value]="percentValue"
      [min]="0"
      [max]="100"
      [size]="300"
      [thick]="12"
      [cap]="'round'"
      [thresholds]="gaugeThresholds"
      [foregroundColor]="textColor"
      [backgroundColor]="backgroundColor">
      <ngx-gauge-value>
        <div [style.color]="textColor" style="font-size: 40px; font-weight: bold;">
          {{ percentValue.toFixed(2) }} %
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
      top: 0;
      right: 5px;
      border: none;
      cursor: pointer;
      border-radius: 4px;
      padding: 4px;
      font-size: 20px;
      z-index: 10;
    }
  `]
})
export class GaugeProductionOutputComponent implements OnChanges {
  @Input() data: any;

  public percentValue = 0;
  public textColor = '#0D6EFD';
  public backgroundColor = 'white';

  public gaugeThresholds = {
    '0': { color: 'red' },
    '75': { color: 'yellow' },
    '85': { color: 'green' }
  };

  constructor(private cd: ChangeDetectorRef) { }

  ngOnChanges(): void {
    this.updateValueAndColor();
  }

  updateValueAndColor(): void {
    if (this.data?.planningWO?.quantityPlan > 0) {
      this.percentValue = (this.data.planningWO.totalNumberOutput / this.data.planningWO.quantityPlan) * 100;
    } else {
      this.percentValue = 0;
    }

    if (this.percentValue < 75) {
      this.textColor = 'red';
    } else if (this.percentValue < 85) {
      this.textColor = 'orange';
    } else {
      this.textColor = 'green';
    }

    this.cd.detectChanges();
  }
}
