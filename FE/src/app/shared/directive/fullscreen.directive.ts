import { Directive, ElementRef } from '@angular/core';

@Directive({
  selector: '[appFullscreen]',
  exportAs: 'appFullscreen'
})
export class FullscreenDirective {
  constructor(private el: ElementRef) {}

  toggleFullscreen() {
    const elem = this.el.nativeElement as HTMLElement;

    if (!document.fullscreenElement) {
      elem.requestFullscreen();
    } else {
      document.exitFullscreen();
    }
  }
}
