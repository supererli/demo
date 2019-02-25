import { Component, OnDestroy, OnInit } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';

/**
 * JhiAlertErrorComponent和JhiAlertComponent两个组件的外观（template）是一直的，但是内部逻辑有所区别。JhiAlertComponent就完全依赖alertService，使用服务进行显示提示框。
 * */
@Component({
    selector: 'jhi-alert',
    template: `
        <div class="alerts" role="alert">
            <div *ngFor="let alert of alerts" [ngClass]="setClasses(alert)">
                <ngb-alert *ngIf="alert && alert.type && alert.msg" [type]="alert.type" (close)="alert.close(alerts)">
                    <pre [innerHTML]="alert.msg"></pre>
                </ngb-alert>
            </div>
        </div>
    `
})
export class JhiAlertComponent implements OnInit, OnDestroy {
    alerts: any[];

    constructor(private alertService: JhiAlertService) {}

    ngOnInit() {
        this.alerts = this.alertService.get();
    }

    setClasses(alert) {
        return {
            toast: !!alert.toast,
            [alert.position]: true
        };
    }

    ngOnDestroy() {
        this.alerts = [];
    }
}
