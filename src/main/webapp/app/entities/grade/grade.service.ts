import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGrade } from 'app/shared/model/grade.model';

type EntityResponseType = HttpResponse<IGrade>;
type EntityArrayResponseType = HttpResponse<IGrade[]>;

@Injectable({ providedIn: 'root' })
export class GradeService {
    public resourceUrl = SERVER_API_URL + 'api/grades';

    constructor(protected http: HttpClient) {}

    create(grade: IGrade): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(grade);
        return this.http
            .post<IGrade>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(grade: IGrade): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(grade);
        return this.http
            .put<IGrade>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IGrade>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IGrade[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(grade: IGrade): IGrade {
        const copy: IGrade = Object.assign({}, grade, {
            gradeTime: grade.gradeTime != null && grade.gradeTime.isValid() ? grade.gradeTime.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.gradeTime = res.body.gradeTime != null ? moment(res.body.gradeTime) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((grade: IGrade) => {
                grade.gradeTime = grade.gradeTime != null ? moment(grade.gradeTime) : null;
            });
        }
        return res;
    }
}
