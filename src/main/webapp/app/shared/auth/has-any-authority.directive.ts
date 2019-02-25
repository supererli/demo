import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';

/**
 * @whatItDoes Conditionally includes an HTML element if current user has any
 * of the authorities passed as the `expression`.
 *
 * @howToUse
 * ```
 *     <some-element *jhiHasAnyAuthority="'ROLE_ADMIN'">...</some-element>
 *
 *     <some-element *jhiHasAnyAuthority="['ROLE_ADMIN', 'ROLE_USER']">...</some-element>
 * ```
 */
/**
 * 通过判断权限列表，决定页面某个标签是否显示。通过注入principal进行判断。其判断有点类似ngif的指令，根据输入的值，调用viewContainerRef重新绘制页面。
 * */
@Directive({
    selector: '[jhiHasAnyAuthority]'
})
export class HasAnyAuthorityDirective {
    private authorities: string[];

    constructor(
        private accountService: AccountService,
        private templateRef: TemplateRef<any>,
        private viewContainerRef: ViewContainerRef
    ) {}

    @Input()
    set jhiHasAnyAuthority(value: string | string[]) {
        this.authorities = typeof value === 'string' ? [value] : value;
        this.updateView();
        // Get notified each time authentication state changes.
        this.accountService.getAuthenticationState().subscribe(identity => this.updateView());
    }

    private updateView(): void {
        const hasAnyAuthority = this.accountService.hasAnyAuthority(this.authorities);
        this.viewContainerRef.clear();
        if (hasAnyAuthority) {
            this.viewContainerRef.createEmbeddedView(this.templateRef);
        }
    }
}
