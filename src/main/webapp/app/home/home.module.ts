import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DemoSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';

@NgModule({
    // 引入共享模块，并且初始化HOME_ROUTE，由于HOME_ROUTE的path是''，所以默认会加载这个页面进来。
    imports: [DemoSharedModule, RouterModule.forChild([HOME_ROUTE])],
    //声明主页组件
    declarations: [HomeComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DemoHomeModule {}
