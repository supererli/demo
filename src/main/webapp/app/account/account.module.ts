import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DemoSharedModule } from 'app/shared';

import {
    PasswordStrengthBarComponent,
    RegisterComponent,
    ActivateComponent,
    PasswordComponent,
    PasswordResetInitComponent,
    PasswordResetFinishComponent,
    SettingsComponent,
    accountState
} from './';

@NgModule({
    //引入共享模块，并forChild加载accountState路由
    imports: [DemoSharedModule, RouterModule.forChild(accountState)],
    //声明内部的组件（相对简单，没有指令）
    declarations: [
        ActivateComponent,
        RegisterComponent,
        PasswordComponent,
        PasswordStrengthBarComponent,
        PasswordResetInitComponent,
        PasswordResetFinishComponent,
        SettingsComponent
    ],
    //声明内部的服务，都是与后端通讯的封装
    //provisers
    //已分析过，支持‘-‘命名
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DemoAccountModule {}
