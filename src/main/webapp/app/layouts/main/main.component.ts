import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRouteSnapshot, NavigationEnd } from '@angular/router';

import { JhiLanguageHelper } from 'app/core';

// 其jhi-main为jhi-main，也就是说它会替换掉index里面的angular入口<jhi-main>标签内容，加入我们需要的组件内容。
@Component({
    selector: 'jhi-main',
    templateUrl: './main.component.html'
})
export class JhiMainComponent implements OnInit {
    constructor(private jhiLanguageHelper: JhiLanguageHelper, private router: Router) {}

    //从路由参数中获得pageTitle的属性值，如果没有就用默认的工程名，
    private getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
        let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : 'demoApp';
        if (routeSnapshot.firstChild) {
            title = this.getPageTitle(routeSnapshot.firstChild) || title;
        }
        return title;
    }

    ngOnInit() {
        //注册监听路由变化的事件，当NavigationEnd事件才改标题。
        this.router.events.subscribe(event => {
            if (event instanceof NavigationEnd) {
                //注意这里的update最终会调用translative服务，实现多语种支持。
                this.jhiLanguageHelper.updateTitle(this.getPageTitle(this.router.routerState.snapshot.root));
            }
        });
    }
}
