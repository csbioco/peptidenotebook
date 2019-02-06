import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProtocolentry } from 'app/shared/model/protocolentry.model';

type EntityResponseType = HttpResponse<IProtocolentry>;
type EntityArrayResponseType = HttpResponse<IProtocolentry[]>;

@Injectable({ providedIn: 'root' })
export class ProtocolentryService {
    public resourceUrl = SERVER_API_URL + 'api/protocolentries';

    constructor(protected http: HttpClient) {}

    create(protocolentry: IProtocolentry): Observable<EntityResponseType> {
        return this.http.post<IProtocolentry>(this.resourceUrl, protocolentry, { observe: 'response' });
    }

    update(protocolentry: IProtocolentry): Observable<EntityResponseType> {
        return this.http.put<IProtocolentry>(this.resourceUrl, protocolentry, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProtocolentry>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProtocolentry[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    querycal(id: number): Observable<EntityArrayResponseType> {
        return this.http.get<IProtocolentry[]>(`${SERVER_API_URL + 'api/protocolentriescal'}/${id}`, { observe: 'response' });
    }
}
