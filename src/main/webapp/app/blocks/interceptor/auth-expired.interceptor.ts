import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { LoginService } from 'app/core/login/login.service';
/**
 * JHipster的拦截器实现
 * */
@Injectable()
export class AuthExpiredInterceptor implements HttpInterceptor {
    constructor(private loginService: LoginService) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(
            tap(
                (event: HttpEvent<any>) => {},
                (err: any) => {
                    if (err instanceof HttpErrorResponse) {
                        // 仅拦截Response，实现任何通讯请求，只要服务器返回了401，就执行登出操作。
                        if (err.status === 401) {
                            this.loginService.logout();
                        }
                    }
                }
            )
        );
    }
}
