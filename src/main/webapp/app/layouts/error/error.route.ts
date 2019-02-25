import { Routes } from '@angular/router';

import { ErrorComponent } from './error.component';

/**
 * 定义出现错误时候的路由地址，error和accessdenied两个url都会跳转到这个统一的提示页面ErrorComponent，唯一区别是accessdenied时候，route的data增加了一个error403: true的属性。
 * */
export const errorRoute: Routes = [
    {
        path: 'error',
        component: ErrorComponent,
        data: {
            authorities: [],
            pageTitle: 'error.title'
        }
    },
    {
        path: 'accessdenied',
        component: ErrorComponent,
        data: {
            authorities: [],
            pageTitle: 'error.title',
            error403: true
        }
    }
];
