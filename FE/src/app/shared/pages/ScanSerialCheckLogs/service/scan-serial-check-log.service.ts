import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BaseApiService } from '../../../service/base-api.service';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ScanSerialCheckLogsService extends BaseApiService<any> {
  constructor(http: HttpClient) {
    super(http, 'api/scanCheckSerialLogs');
  }

  override delete(log: any): Observable<void> {
    return this.http.request<void>('delete', `${this['fullBaseUrl']}`, { body: log, withCredentials: true });
  }
}
