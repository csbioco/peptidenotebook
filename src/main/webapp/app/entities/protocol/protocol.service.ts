import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProtocol } from 'app/shared/model/protocol.model';

type EntityResponseType = HttpResponse<IProtocol>;
type EntityArrayResponseType = HttpResponse<IProtocol[]>;

@Injectable({ providedIn: 'root' })
export class ProtocolService {
    public resourceUrl = SERVER_API_URL + 'api/protocols';

    constructor(protected http: HttpClient) {}

    create(protocol: IProtocol): Observable<EntityResponseType> {
        return this.http.post<IProtocol>(this.resourceUrl, protocol, { observe: 'response' });
    }

    update(protocol: IProtocol): Observable<EntityResponseType> {
        return this.http.put<IProtocol>(this.resourceUrl, protocol, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProtocol>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProtocol[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
