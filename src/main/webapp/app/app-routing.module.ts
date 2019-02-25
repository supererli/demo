import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
//具体的路由ROUTERS还是定义在layout中的
import { errorRoute, navbarRoute } from './layouts';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';

const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];

@NgModule({
    imports: [
        //根据layout中路由定义，初始化路由
        //设置了使用hash，url中就会出现“#”，这样兼容性更好些，用户有多少人会去看url呢？
        RouterModule.forRoot(
            [
                ...LAYOUT_ROUTES,
                {
                    path: 'admin',
                    loadChildren: './admin/admin.module#DemoAdminModule'
                }
            ],
            { useHash: true, enableTracing: DEBUG_INFO_ENABLED }
        )
    ],
    exports: [RouterModule] ////重新导出路由模块给其他模块用（forChild）
})
export class DemoAppRoutingModule {}
