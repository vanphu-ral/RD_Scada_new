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
    return this.http.post<any>(this['fullBaseUrl'], entity, {withCredentials: true});
  }

  getPlanningWOs(filters: any, page: number, size: number): Observable<any> {
    let params = new HttpParams()
      .set('page', page)
      .set('size', size);
    if(filters.planningWorkOrderId) {
      params = params.set('planningWorkOrderId', filters.planningWorkOrderId);
    }
    if(filters.sapWoId) {
      params = params.set('sapWoId', filters.sapWoId);
    }
    if(filters.productOrderId) {
      params = params.set('productOrderId', filters.productOrderId);
    }
    if(filters.branchName) {
      params = params.set('branchName', filters.branchName);
    }
    if(filters.branchCode) {
      params = params.set('branchCode', filters.branchCode);
    }
    if(filters.groupName) {
      params = params.set('groupName', filters.groupName);
    }
    if(filters.groupCode) {
      params = params.set('groupCode', filters.groupCode);
    }
    if(filters.status) {
      params = params.set('status', filters.status);
    }
    if(filters.woId) {
      params = params.set('woId', filters.woId);
    }
    if(filters.lotNumber) {
      params = params.set('lotNumber', filters.lotNumber);
    }
    if(filters.productCode) {
      params = params.set('productCode', filters.productCode);
    }
    if(filters.productName) {
      params = params.set('productName', filters.productName);
    }
    if(filters.quantityPlan) {
      params = params.set('quantityPlan', filters.quantityPlan);
    }

    return this.http.get<any>(this['fullBaseUrl'], { params, withCredentials: true });
  }

  getWoInfor(woId: string): Observable<any> {
    return this.http.get<any>(`${this['fullBaseUrl']}/info/${woId}`, {withCredentials: true});
  }

  getWoDetailInfor(woId: string): Observable<any> {
    return this.http.get<any>(`${this['fullBaseUrl']}/detail/${woId}`, {withCredentials: true});
  }

  getWoErrorInfor(woId: string): Observable<any> {
    return this.http.get<any>(`${this['fullBaseUrl']}/error/info/${woId}`, {withCredentials: true});
  }

  filterBySerialItem(serialItem: any): Observable<any> {
    let params = new HttpParams();
    if (serialItem) {
      params = params.set('serialItem', serialItem);
    }
    return this.http.get<any>(`${this['fullBaseUrl']}/serial-item`, { params, withCredentials: true });
  }

  filterBySerialBoard(serialBoard: any): Observable<any> {
    let params = new HttpParams();
    if (serialBoard) {
      params = params.set('serialBoard', serialBoard);
    }
    return this.http.get<any>(`${this['fullBaseUrl']}/serial-board`, { params, withCredentials: true });
  }

  insertListDevietoWo(listDevices: any[]): Observable<any> {
    return this.http.post<any>(`${this['fullBaseUrl']}/insert`, listDevices, {withCredentials: true});
  }
}
