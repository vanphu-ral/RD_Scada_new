import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BaseApiService, CreateEntity } from '../../../service/base-api.service';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class PlanningWoService extends BaseApiService<any> {
  constructor(http: HttpClient) {
    super(http, 'api/planningwo');
  }

  override create(entity: CreateEntity<any>): Observable<any> {
    delete entity.id;
    return this.http.post<any>(this['fullBaseUrl'], entity);
  }

  getPlanningWOs(filters: any, page: number, size: number): Observable<any> {
    let params = new HttpParams()
      .set('page', page)
      .set('size', size);

    if (filters.branchCode) {
      params = params.set('branchCode', filters.branchCode);
    }
    if (filters.productCode) {
      params = params.set('productCode', filters.productCode);
    }
    if (filters.status) {
      params = params.set('status', filters.status);
    }

    return this.http.get<any>(this['fullBaseUrl'], { params });
  }

  getWoInfor(woId: string): Observable<any> {
    return this.http.get<any>(`${this['fullBaseUrl']}/info/${woId}`);
  }
}
