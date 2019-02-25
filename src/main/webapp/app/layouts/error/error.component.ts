import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'jhi-error',
    templateUrl: './error.component.html'
})
export class ErrorComponent implements OnInit {
    errorMessage: string;
    error403: boolean;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        //从路由里面获取data，并把error403属性和errorMessage赋值到自己组件里面的属性中。
        this.route.data.subscribe(routeData => {
            if (routeData.error403) {
                this.error403 = routeData.error403;
            }
            if (routeData.errorMessage) {
                this.errorMessage = routeData.errorMessage;
            }
        });
    }
}
