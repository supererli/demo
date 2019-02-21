import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

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
        const copy = this.convertDateFromClient(lab);
        return this.http
            .post<ILab>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(lab: ILab): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(lab);
        return this.http
            .put<ILab>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ILab>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILab[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(lab: ILab): ILab {
        const copy: ILab = Object.assign({}, lab, {
            labTime: lab.labTime != null && lab.labTime.isValid() ? lab.labTime.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.labTime = res.body.labTime != null ? moment(res.body.labTime) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((lab: ILab) => {
                lab.labTime = lab.labTime != null ? moment(lab.labTime) : null;
            });
        }
        return res;
    }
}
