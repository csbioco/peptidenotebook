import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReagent } from 'app/shared/model/reagent.model';

type EntityResponseType = HttpResponse<IReagent>;
type EntityArrayResponseType = HttpResponse<IReagent[]>;

@Injectable({ providedIn: 'root' })
export class ReagentService {
    public resourceUrl = SERVER_API_URL + 'api/reagents';

    constructor(protected http: HttpClient) {}

    create(reagent: IReagent): Observable<EntityResponseType> {
        return this.http.post<IReagent>(this.resourceUrl, reagent, { observe: 'response' });
    }

    update(reagent: IReagent): Observable<EntityResponseType> {
        return this.http.put<IReagent>(this.resourceUrl, reagent, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IReagent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IReagent[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
