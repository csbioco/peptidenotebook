import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICalculatedEntry } from 'app/shared/model/calculated-entry.model';

type EntityResponseType = HttpResponse<ICalculatedEntry>;
type EntityArrayResponseType = HttpResponse<ICalculatedEntry[]>;

@Injectable({ providedIn: 'root' })
export class CalculatedEntryService {
    public resourceUrl = SERVER_API_URL + 'api/calculated-entries';

    constructor(protected http: HttpClient) {}

    create(calculatedEntry: ICalculatedEntry): Observable<EntityResponseType> {
        return this.http.post<ICalculatedEntry>(this.resourceUrl, calculatedEntry, { observe: 'response' });
    }

    update(calculatedEntry: ICalculatedEntry): Observable<EntityResponseType> {
        return this.http.put<ICalculatedEntry>(this.resourceUrl, calculatedEntry, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICalculatedEntry>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICalculatedEntry[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    querybycalid(id: number): Observable<EntityArrayResponseType> {
        return this.http.get<any>(`${SERVER_API_URL + 'api/calculated-entriesbycal'}/${id}`, { observe: 'response' });
    }
}
