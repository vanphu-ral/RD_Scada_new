import { Directive, ElementRef, Renderer2, EventEmitter, Output, OnDestroy } from '@angular/core';

@Directive({
  selector: '[appFullscreen]',
  exportAs: 'appFullscreen'
})
export class FullscreenDirective implements OnDestroy {
  @Output() fullscreenChange = new EventEmitter<boolean>();
  private listener!: () => void;

  constructor(private el: ElementRef, private renderer: Renderer2) {
    this.listener = this.renderer.listen(document, 'fullscreenchange', () => {
      const isFs = !!document.fullscreenElement;
      const elem = this.el.nativeElement as HTMLElement;

      if (isFs && document.fullscreenElement === elem) {
        this.renderer.addClass(elem, 'fullscreen-active');
      } else {
        this.renderer.removeClass(elem, 'fullscreen-active');
      }

      this.fullscreenChange.emit(isFs);
    });
  }

  toggleFullscreen() {
    const elem = this.el.nativeElement as HTMLElement;

    if (!document.fullscreenElement) {
      elem.requestFullscreen();
    } else {
      document.exitFullscreen();
    }
  }

  ngOnDestroy() {
    if (this.listener) this.listener();
  }
}
