import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';

@Injectable({ providedIn: 'root' })
export class ActivateService {
    //注入封装过的http客户端
    constructor(private http: HttpClient) {}

    //设置params，调用通讯API，返回Observable
    get(key: string): Observable<any> {
        return this.http.get(SERVER_API_URL + 'api/activate', {
            params: new HttpParams().set('key', key)
        });
    }
}
