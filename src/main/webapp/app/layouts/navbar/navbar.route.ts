import { Route } from '@angular/router';

import { NavbarComponent } from './navbar.component';

export const navbarRoute: Route = {
    path: '',
    component: NavbarComponent,
    // 这里指定了Route的outlet，注意就和main.component.html里面的对应起来了。
    outlet: 'navbar'
};
