import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';

/**
 * 仅拦截请求Request,这里用Jhipster推荐的JWT方式存储会话认证信息。'Bearer '这个是魔数，前后端都按照这个方式设置和读取。
 参见\security\jwtJWTFilter.java。
 * */
@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor(private localStorage: LocalStorageService, private sessionStorage: SessionStorageService) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // 判断一下是否是向SERVER_API_URL的请求，如果是测试test的就不拦截
        if (!request || !request.url || (/^http/.test(request.url) && !(SERVER_API_URL && request.url.startsWith(SERVER_API_URL)))) {
            return next.handle(request);
        }

        //从localStorage和sessionStorage两个地方取出认证Token，并加上'Bearer '放到请求头中。
        const token = this.localStorage.retrieve('authenticationToken') || this.sessionStorage.retrieve('authenticationToken');
        if (!!token) {
            request = request.clone({
                setHeaders: {
                    Authorization: 'Bearer ' + token
                }
            });
        }
        return next.handle(request);
    }
}
