import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
//calendar
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { Ng2Webstorage } from 'ngx-webstorage';
import { NgJhipsterModule } from 'ng-jhipster';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { DemoSharedModule } from 'app/shared';
import { DemoCoreModule } from 'app/core';
import { DemoAppRoutingModule } from './app-routing.module';
import { DemoHomeModule } from './home/home.module';
import { DemoAccountModule } from './account/account.module';
import { DemoEntityModule } from './entities/entity.module';
import * as moment from 'moment';
// jhipster-needle-angular-add-module-import JHipster will add new module here
/**
 * 引入第三方css样式，vendor.ts引入vendor.css，vendor.css引入boot-strap.css和font-awesome.css
 * */
import { JhiMainComponent, NavbarComponent, FooterComponent, PageRibbonComponent, ActiveMenuDirective, ErrorComponent } from './layouts';

@NgModule({
    //引入其他子模块
    imports: [
        BrowserModule, //angular浏览器模块，仅主模块需要import
        DemoAppRoutingModule, //引入路由模块
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }), //给存储的cookie统一增加前缀，防止命名冲突
        NgJhipsterModule.forRoot({
            // set below to true to make alerts look like toast
            alertAsToast: false,
            alertTimeout: 5000,
            i18nEnabled: true,
            defaultI18nLang: 'en'
        }),
        DemoSharedModule.forRoot(), //共享模块
        DemoCoreModule,
        DemoHomeModule, //首页模块
        DemoAccountModule, //账号管理模块
        // jhipster-needle-angular-add-module JHipster will add new module here
        DemoEntityModule //实体模块
    ],
    //主模块自己的组件需要声明(layout中的)
    declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
    //主模块自己的服务需要声明(layout中的)，根据注入器规则，这里声明的service是整个工程都可以直接用的。
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true
        }
    ],
    ////声明引导模块（仅主模块才有）
    bootstrap: [JhiMainComponent]
})
export class DemoAppModule {
    constructor(private dpConfig: NgbDatepickerConfig) {
        this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
    }
}
