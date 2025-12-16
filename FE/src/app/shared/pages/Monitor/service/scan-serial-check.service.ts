import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BaseApiService } from '../../../service/base-api.service';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ScanSerialCheck extends BaseApiService<any> {
  constructor(http: HttpClient) {
    super(http, 'api/scanSerialChecks');
  }

  getErrorBySerial(serialItem: string, code: string): Observable<any> {
    let params = new HttpParams();
    if(serialItem) {
      params = params.set('serialItem', serialItem);
    };
    if(code) {
      params = params.set('code', code);
    };
    return this.http.get<any>(`${this['fullBaseUrl']}/check`, { params, withCredentials: true });
  }

  getWorkOrderDetails(workOrder: string): Observable<any[]> {
    const url = `${this['fullBaseUrl']}/by-workorder/${workOrder}`;
    return this.http.get<any[]>(url, { withCredentials: true });
  }
}
