import { Component, OnInit, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';

import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from 'app/shared';
import { LoginModalService } from 'app/core';
import { Register } from './register.service';

// 组件都使用模板表单的构造方式，通过在html中定义ngModel进行数据绑定，通过定义内部状态，控制表单的提示信息。
@Component({
    selector: 'jhi-register',
    templateUrl: './register.component.html'
})
export class RegisterComponent implements OnInit, AfterViewInit {
    confirmPassword: string;
    doNotMatch: string;
    error: string;
    errorEmailExists: string;
    errorUserExists: string;
    registerAccount: any;
    success: boolean;
    modalRef: NgbModalRef;
    //注入语言服务，登陆框，注入服务，以及标签本身，绘制器

    constructor(
        private languageService: JhiLanguageService,
        private loginModalService: LoginModalService,
        private registerService: Register,
        private elementRef: ElementRef,
        private renderer: Renderer
    ) {}

    ngOnInit() {
        this.success = false;
        this.registerAccount = {};
    }

    //默认激活login按钮
    ngAfterViewInit() {
        this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#login'), 'focus', []);
    }

    register() {
        //并非实时判断两个input的匹配程度，提交的时候才会判断，如果不匹配，就提示错误
        if (this.registerAccount.password !== this.confirmPassword) {
            this.doNotMatch = 'ERROR';
        } else {
            //两个密码框相符，则进行注册接口调用
            this.doNotMatch = null;
            this.error = null;
            this.errorUserExists = null;
            this.errorEmailExists = null;
            this.languageService.getCurrent().then(key => {
                this.registerAccount.langKey = key;
                this.registerService.save(this.registerAccount).subscribe(
                    () => {
                        this.success = true;
                    },
                    response => this.processError(response)
                );
            });
        }
    }

    //通用的登陆框显示
    openLogin() {
        this.modalRef = this.loginModalService.open();
    }

    //错误的response处理，出错时候返回type表示不同错误
    private processError(response: HttpErrorResponse) {
        this.success = null;
        if (response.status === 400 && response.error.type === LOGIN_ALREADY_USED_TYPE) {
            this.errorUserExists = 'ERROR';
        } else if (response.status === 400 && response.error.type === EMAIL_ALREADY_USED_TYPE) {
            this.errorEmailExists = 'ERROR';
        } else {
            this.error = 'ERROR';
        }
    }
}
