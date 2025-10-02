import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BaseApiService } from '../../../service/base-api.service';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class MonitorService extends BaseApiService<any> {
  constructor(http: HttpClient) {
    super(http, 'api/planningworkorder');
  }

  override getAll(page: number = 0): Observable<any[]> {
   return this.http.get<any>(`${this['fullBaseUrl']}?page=${page}`);
  }
}
