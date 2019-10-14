import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDispositivoNoAsociado } from 'app/shared/model/dispositivo-no-asociado.model';

type EntityResponseType = HttpResponse<IDispositivoNoAsociado>;
type EntityArrayResponseType = HttpResponse<IDispositivoNoAsociado[]>;

@Injectable({ providedIn: 'root' })
export class DispositivoNoAsociadoService {
    public resourceUrl = SERVER_API_URL + 'api/dispositivo-no-asociados';

    constructor(protected http: HttpClient) {}

    create(dispositivoNoAsociado: IDispositivoNoAsociado): Observable<EntityResponseType> {
        return this.http.post<IDispositivoNoAsociado>(this.resourceUrl, dispositivoNoAsociado, { observe: 'response' });
    }

    update(dispositivoNoAsociado: IDispositivoNoAsociado): Observable<EntityResponseType> {
        return this.http.put<IDispositivoNoAsociado>(this.resourceUrl, dispositivoNoAsociado, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDispositivoNoAsociado>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDispositivoNoAsociado[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
