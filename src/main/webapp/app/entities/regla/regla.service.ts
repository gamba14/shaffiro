import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRegla } from 'app/shared/model/regla.model';

type EntityResponseType = HttpResponse<IRegla>;
type EntityArrayResponseType = HttpResponse<IRegla[]>;

@Injectable({ providedIn: 'root' })
export class ReglaService {
    public resourceUrl = SERVER_API_URL + 'api/reglas';

    constructor(protected http: HttpClient) {}

    create(regla: IRegla): Observable<EntityResponseType> {
        return this.http.post<IRegla>(this.resourceUrl, regla, { observe: 'response' });
    }

    update(regla: IRegla): Observable<EntityResponseType> {
        return this.http.put<IRegla>(this.resourceUrl, regla, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRegla>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRegla[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
