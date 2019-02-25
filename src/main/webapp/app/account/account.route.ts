import { Routes } from '@angular/router';

// 每个component组件都需占主体部分，因此path各不相同。
import { activateRoute, passwordRoute, passwordResetFinishRoute, passwordResetInitRoute, registerRoute, settingsRoute } from './';

const ACCOUNT_ROUTES = [activateRoute, passwordRoute, passwordResetFinishRoute, passwordResetInitRoute, registerRoute, settingsRoute];

export const accountState: Routes = [
    {
        path: '',
        children: ACCOUNT_ROUTES
    }
];
