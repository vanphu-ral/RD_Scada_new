import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BaseApiService } from '../../../service/base-api.service';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class WOHistoryErrorService extends BaseApiService<any> {
  constructor(http: HttpClient) {
    super(http, 'api/woErrorHistory');
  }

  getErrorByWoId(woId: string): Observable<any> {
    return this.http.get<any>(`${this['fullBaseUrl']}/history/workOrder/${woId}`, { withCredentials: true });
  }

  updateDetailErrors(messages: any[]): Observable<void> {
    return this.http.post<void>(`${this['fullBaseUrl']}/history/updates`, messages, { withCredentials: true });
  }

}
