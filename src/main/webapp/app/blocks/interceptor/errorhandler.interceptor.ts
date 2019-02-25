import { Injectable } from '@angular/core';
import { JhiEventManager } from 'ng-jhipster';
import { HttpInterceptor, HttpRequest, HttpErrorResponse, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

/**
 * 仅拦截响应Response，针对3种错误，才使用eventManager广播httpError。
 * JhiAlertErrorComponent会接收这个错误，提示一个错误Alert给用户。
 * */

@Injectable()
export class ErrorHandlerInterceptor implements HttpInterceptor {
    constructor(private eventManager: JhiEventManager) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(
            tap(
                (event: HttpEvent<any>) => {},
                (err: any) => {
                    if (err instanceof HttpErrorResponse) {
                        //401未授权错误，无响应说明text，路径为/api/account用户操作。
                        if (!(err.status === 401 && (err.message === '' || (err.url && err.url.includes('/api/account'))))) {
                            this.eventManager.broadcast({ name: 'demoApp.httpError', content: err });
                        }
                    }
                }
            )
        );
    }
}
