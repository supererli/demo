import { Routes } from '@angular/router';

import { auditsRoute, configurationRoute, docsRoute, healthRoute, logsRoute, metricsRoute, userMgmtRoute } from './';

import { UserRouteAccessService } from 'app/core';

const ADMIN_ROUTES = [auditsRoute, configurationRoute, docsRoute, healthRoute, logsRoute, ...userMgmtRoute, metricsRoute];

export const adminState: Routes = [
    {
        path: '',
        data: {
            //在子路由的顶层增加ROLE_ADMIN的权限要求
            authorities: ['ROLE_ADMIN']
        },
        //有权限控制，就必须有路由守卫
        canActivate: [UserRouteAccessService],
        children: ADMIN_ROUTES
    }
];
