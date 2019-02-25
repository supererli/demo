import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ITEMS_PER_PAGE } from 'app/shared';
import { Audit } from './audit.model';
import { AuditsService } from './audits.service';

// 由于使用了日期控件，所以还是值得一看的。在ngOnInit()中就初始化结束时间为明天，而开始时间为前一个月，然后开始调用onChangeDate()查询。
// onChangeDate()是实际与后端通讯的地方，调用了auditsService的query(),根据返回结果更新页面内容。其中在报文头“X-Total-Count”会返回查询的总数目，有利于分页，而Pageable不用放这些信息。

@Component({
    selector: 'jhi-audit',
    templateUrl: './audits.component.html'
})
export class AuditsComponent implements OnInit, OnDestroy {
    audits: Audit[];
    fromDate: string;
    itemsPerPage: any;
    links: any;
    queryCount: number;
    page: number;
    routeData: any;
    predicate: any;
    previousPage: any;
    reverse: boolean;
    toDate: string;
    totalItems: number;

    constructor(
        private auditsService: AuditsService,
        private alertService: JhiAlertService,
        private parseLinks: JhiParseLinks,
        private activatedRoute: ActivatedRoute,
        private datePipe: DatePipe,
        private router: Router
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    ngOnInit() {
        this.today();
        this.previousMonth();
        this.loadAll();
    }

    ngOnDestroy() {
        this.routeData.unsubscribe();
    }

    previousMonth() {
        const dateFormat = 'yyyy-MM-dd';
        let fromDate: Date = new Date();

        if (fromDate.getMonth() === 0) {
            fromDate = new Date(fromDate.getFullYear() - 1, 11, fromDate.getDate());
        } else {
            fromDate = new Date(fromDate.getFullYear(), fromDate.getMonth() - 1, fromDate.getDate());
        }

        this.fromDate = this.datePipe.transform(fromDate, dateFormat);
    }

    today() {
        const dateFormat = 'yyyy-MM-dd';
        // Today + 1 day - needed if the current day must be included
        const today: Date = new Date();
        today.setDate(today.getDate() + 1);
        const date = new Date(today.getFullYear(), today.getMonth(), today.getDate());
        this.toDate = this.datePipe.transform(date, dateFormat);
    }

    loadAll() {
        this.auditsService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort(),
                fromDate: this.fromDate,
                toDate: this.toDate
            })
            .subscribe(
                (res: HttpResponse<Audit[]>) => this.onSuccess(res.body, res.headers),
                (res: HttpResponse<any>) => this.onError(res.body)
            );
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/admin/audits'], {
            queryParams: {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        this.audits = data;
    }

    private onError(error) {
        this.alertService.error(error.error, error.message, null);
    }
}
