import { Injectable } from '@angular/core';
import { Apollo } from 'apollo-angular';
import { GET_QMS_TO_DOI_TRA_INFO_BY_LOT_NUMBER } from '../grapsql/main.grapsql';
@Injectable({ providedIn: 'root' })
export class GrapSqlService {
    constructor(private apollo: Apollo) { }


    QmsToDoiTraInfoByLotNumber(lotNumber: string) {
        return this.apollo.query({
            query: GET_QMS_TO_DOI_TRA_INFO_BY_LOT_NUMBER,
            variables: { lotNumber }, 
        });
    }
}
