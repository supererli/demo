import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// 代码也类似，十分简单。值得注意的是setting并没单独的service。用户信息保存是AccountService的一部分，直接调用即可。
import { SERVER_API_URL } from 'app/app.constants';

@Injectable({ providedIn: 'root' })
export class Register {
    constructor(private http: HttpClient) {}

    save(account: any): Observable<any> {
        return this.http.post(SERVER_API_URL + 'api/register', account);
    }
}
