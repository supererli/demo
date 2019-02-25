import { NgModule } from '@angular/core';

import { DemoSharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent } from './';

/**
 * 定义了内部（JHipster）实现的语言帮助（显示Title），警告组件两个部分，
 奇怪的是，有的组件是放Shared顶层的，有的又放到Common里面，是因为语言帮助和警告框更有通用性吧
 * */
@NgModule({
    imports: [DemoSharedLibsModule],
    declarations: [FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent],
    exports: [DemoSharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent]
})
export class DemoSharedCommonModule {}
