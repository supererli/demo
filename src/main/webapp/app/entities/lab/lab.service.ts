import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILab } from 'app/shared/model/lab.model';

type EntityResponseType = HttpResponse<ILab>;
type EntityArrayResponseType = HttpResponse<ILab[]>;

@Injectable({ providedIn: 'root' })
export class LabService {
    public resourceUrl = SERVER_API_URL + 'api/labs';

    constructor(protected http: HttpClient) {}

    create(lab: ILab): Observable<EntityResponseType> {
        return this.http.post<ILab>(this.resourceUrl, lab, { observe: 'response' });
    }

    update(lab: ILab): Observable<EntityResponseType> {
        return this.http.put<ILab>(this.resourceUrl, lab, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILab>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILab[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
