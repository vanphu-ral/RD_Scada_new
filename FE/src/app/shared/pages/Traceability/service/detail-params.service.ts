import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BaseApiService } from '../../../service/base-api.service';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class DetailParamsService extends BaseApiService<any> {
  constructor(http: HttpClient) {
    super(http, 'api/detail-params');
  }

  searchDetailParams(keyword: string): Observable<any[]> {
    const params = new HttpParams().set('q', keyword);
    return this.http.get<any[]>(`${this['fullBaseUrl']}/search`, { params, withCredentials: true });
  }

  searchDetailParamsBySerial(serialValue: string): Observable<any[]> {
    const params = new HttpParams().set('serial', serialValue);
    return this.http.get<any[]>(`${this['fullBaseUrl']}/search-details`, { params, withCredentials: true });
  }

  getDetailParamsByWorkOrder(workOrder: string): Observable<any[]> {
    if (!workOrder) {
      return new Observable(observer => {
        observer.next([]);
        observer.complete();
      });
    }
    let params = new HttpParams().set('workOrder', workOrder);
    return this.http.get<any[]>(`${this['fullBaseUrl']}/by-workorder`, { params, withCredentials: true });
  }

  getTestInfoBySerial(serial: string): Observable<any[]> {
    const url = `${this['fullBaseUrl']}/${serial}`;
    return this.http.get<any[]>(url, { withCredentials: true });
  }
}
