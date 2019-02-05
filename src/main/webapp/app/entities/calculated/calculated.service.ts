import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICalculated } from 'app/shared/model/calculated.model';

type EntityResponseType = HttpResponse<ICalculated>;
type EntityArrayResponseType = HttpResponse<ICalculated[]>;

@Injectable({ providedIn: 'root' })
export class CalculatedService {
    public resourceUrl = SERVER_API_URL + 'api/calculateds';

    constructor(protected http: HttpClient) {}

    create(calculated: ICalculated): Observable<EntityResponseType> {
        return this.http.post<ICalculated>(this.resourceUrl, calculated, { observe: 'response' });
    }

    update(calculated: ICalculated): Observable<EntityResponseType> {
        return this.http.put<ICalculated>(this.resourceUrl, calculated, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICalculated>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICalculated[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
