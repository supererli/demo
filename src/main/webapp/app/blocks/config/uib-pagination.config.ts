import { Injectable } from '@angular/core';
import { NgbPaginationConfig } from '@ng-bootstrap/ng-bootstrap';
import { ITEMS_PER_PAGE } from 'app/shared';

/**
 * uib-pagination.config.ts用来构造通用的PaginationConfig分页配置，可根据工程实际使用情况修改
 * */
@Injectable({ providedIn: 'root' })
export class PaginationConfig {
    // tslint:disable-next-line: no-unused-variable
    constructor(private config: NgbPaginationConfig) {
        config.boundaryLinks = true;
        config.maxSize = 5;
        config.pageSize = ITEMS_PER_PAGE;
        config.size = 'sm';
    }
}
