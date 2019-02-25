import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgJhipsterModule } from 'ng-jhipster';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { CookieModule } from 'ngx-cookie';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

/**
 * SharedLibsModule比较简单，引用了NgbModule，NgJhipsterModule，InfiniteScrollModule，CookieModule，并且初始化了它们，重新导出了它们。
 值得主要的是，这里是初始化ng-jhipster的地方，参见
 TODO：链接ng-jhipster源码1-顶层

 链接：https://www.jianshu.com/p/fa57696c7aef
 * */

@NgModule({
    imports: [NgbModule.forRoot(), InfiniteScrollModule, CookieModule.forRoot(), FontAwesomeModule],
    exports: [FormsModule, CommonModule, NgbModule, NgJhipsterModule, InfiniteScrollModule, FontAwesomeModule]
})
export class DemoSharedLibsModule {
    static forRoot() {
        return {
            ngModule: DemoSharedLibsModule
        };
    }
}
