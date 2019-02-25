/**
 * SharedModule整合了Lib模块，Common模块两个子模块的内容，并且重新导出，作为整个的共享模块（工具模块）使用。
 * */
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { DemoSharedLibsModule, DemoSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
    //引入两个子模块
    imports: [DemoSharedLibsModule, DemoSharedCommonModule],
    //声明自己的组件，指令
    declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
    //声明通用的服务，主要，由于不是在主模块provider了，只有引入SharedModule的才能使用这些
    //实际上，其他所有的子模块都有引入SharedLibsModule。
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    //模块的入口组件--SharedModule并不会被路由懒加载到，所有声明并没有意义
    entryComponents: [JhiLoginModalComponent],
    exports: [DemoSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
    //语言模式，CUSTOM_ELEMENTS_SCHEMA是angular提供的一种模式，允许在命名时使用“-”。
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DemoSharedModule {
    static forRoot() {
        return {
            ngModule: DemoSharedModule
        };
    }
}
