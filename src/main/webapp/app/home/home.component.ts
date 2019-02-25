import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LoginModalService, AccountService, Account } from 'app/core';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;

    //构造器，注入principal服务，loginModalService登录框服务，eventManager实际管理
    constructor(
        private accountService: AccountService,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager
    ) {}

    //判断是否用户已登录
    ngOnInit() {
        this.accountService.identity().then(account => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
    }

    //向eventManager注册用户注册成功的消息
    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.accountService.identity().then(account => {
                this.account = account;
            });
        });
    }

    //判断用户是否已经登录
    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }

    //显示登录框
    login() {
        this.modalRef = this.loginModalService.open();
    }
}
