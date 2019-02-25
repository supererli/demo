import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITimeTable } from 'app/shared/model/time-table.model';
import { AccountService } from 'app/core';
import { TimeTableService } from './time-table.service';

@Component({
    selector: 'jhi-time-table',
    templateUrl: './time-table.component.html'
})
export class TimeTableComponent implements OnInit, OnDestroy {
    timeTables: ITimeTable[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected timeTableService: TimeTableService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.timeTableService.query().subscribe(
            (res: HttpResponse<ITimeTable[]>) => {
                this.timeTables = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTimeTables();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITimeTable) {
        return item.id;
    }

    registerChangeInTimeTables() {
        this.eventSubscriber = this.eventManager.subscribe('timeTableListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
