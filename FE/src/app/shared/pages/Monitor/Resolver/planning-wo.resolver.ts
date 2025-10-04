import { Injectable, inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { tap } from 'rxjs/operators';
import { PlanningWoService } from '../service/planning-wo.service';

@Injectable({ providedIn: 'root' })
export class PlanningWoResolve {
  constructor(
    private service: PlanningWoService,
    private router: Router
  ) { }
  resolve(route: ActivatedRouteSnapshot): Observable<any | null> {
    const id = route.params['id'];
    if (id) {
      return this.service.getWoInfor(id).pipe(
        tap(planingWo => {
          if (!planingWo) {
            this.router.navigate(['404']);
          }
        })
      );
    }

    return of(null);
  }
}
