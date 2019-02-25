import { Route } from '@angular/router';

import { RegisterComponent } from './register.component';
// 可以看到registerRoute并没有声明路由守卫，因为要注册，那么必然没有用户信息，也就无需获取用户信息了。
export const registerRoute: Route = {
    path: 'register',
    component: RegisterComponent,
    data: {
        authorities: [],
        pageTitle: 'register.title'
    }
};
