import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BaseApiService } from '../../../service/base-api.service';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class MonitorService extends BaseApiService<any> {
  constructor(http: HttpClient) {
    super(http, 'api/planningworkorder');
  }

  getAllCanSearch(filter: any, page: number, size: number): Observable<any> {
    let params = new HttpParams()
      .set('page', page)
      .set('size', size);
    if (filter.woId) {
      params = params.set('woId', filter.woId);
    }
    if (filter.sapWoId) {
      params = params.set('sapWoId', filter.sapWoId);
    }
    if (filter.productCode) {
      params = params.set('productCode', filter.productCode);
    }
    return this.http.get<any>(`${this['fullBaseUrl']}/latest`, { withCredentials: true, params });
  }
}
