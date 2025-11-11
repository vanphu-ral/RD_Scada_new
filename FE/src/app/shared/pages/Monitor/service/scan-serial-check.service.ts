import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BaseApiService } from '../../../service/base-api.service';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ScanSerialCheck extends BaseApiService<any> {
  constructor(http: HttpClient) {
    super(http, 'api/scanSerialChecks');
  }

  getErrorBySerial(serialItem: string): Observable<any> {
    let params = new HttpParams().set('serialItem', serialItem);
    return this.http.get<any>(`${this['fullBaseUrl']}/check`, { params, withCredentials: true });
  }
}
