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
    // Thiết lập tham số truy vấn (Query Parameter)
    const params = new HttpParams().set('serial', serialValue);

    // Thực hiện cuộc gọi GET
    return this.http.get<any[]>(`${this['fullBaseUrl']}/search-details`, { params, withCredentials: true });
  }
}
