import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAas } from 'app/shared/model/aas.model';

type EntityResponseType = HttpResponse<IAas>;
type EntityArrayResponseType = HttpResponse<IAas[]>;

@Injectable({ providedIn: 'root' })
export class AasService {
    public resourceUrl = SERVER_API_URL + 'api/aas';

    constructor(protected http: HttpClient) {}

    create(aas: IAas): Observable<EntityResponseType> {
        return this.http.post<IAas>(this.resourceUrl, aas, { observe: 'response' });
    }

    update(aas: IAas): Observable<EntityResponseType> {
        return this.http.put<IAas>(this.resourceUrl, aas, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAas>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAas[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    deleteuse(): Observable<HttpResponse<any>> {
        const deleteUrl = SERVER_API_URL + 'api/aasall';
        return this.http.delete<any>(deleteUrl, { observe: 'response' });
    }

    add20aa(): Observable<HttpResponse<any>> {
        const deleteUrl = SERVER_API_URL + 'api/addaas';
        return this.http.get<any>(deleteUrl, { observe: 'response' });
    }
}
