import { Route } from '@angular/router';

import { ActivateComponent } from './activate.component';
import { UserRouteAccessService } from 'app/core';

export const activateRoute: Route = {
    path: 'activate',
    component: ActivateComponent,
    data: {
        //无需权限
        authorities: [],
        pageTitle: 'activate.title'
    },
    //路由守卫为UserRouteAccessService，由于authorities是空数组，始终是允许的。但是守卫会调用principal.identity()从后端获取用户信息。
    canActivate: [UserRouteAccessService]
};
