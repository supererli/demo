import { Directive, OnInit, ElementRef, Renderer, Input } from '@angular/core';
import { TranslateService, LangChangeEvent } from '@ngx-translate/core';

/**
 * 定义了jhiActiveMenu属性指令，用来作为激活语种的辅助指令。默认菜单的激活状态是routerLinkActive根据url进行判断的，但是语种的选择与激活是与url无关的，因此必须自己判断那一个语种是激活的。
 核心代码为updateActivFlag()方法
 * */
@Directive({
    selector: '[jhiActiveMenu]'
})
export class ActiveMenuDirective implements OnInit {
    @Input() jhiActiveMenu: string;

    constructor(private el: ElementRef, private renderer: Renderer, private translateService: TranslateService) {}

    ngOnInit() {
        this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
            this.updateActiveFlag(event.lang);
        });
        this.updateActiveFlag(this.translateService.currentLang);
    }

    // 就是给属性的标签元素上增加或者删除'active'属性。
    updateActiveFlag(selectedLanguage) {
        if (this.jhiActiveMenu === selectedLanguage) {
            this.renderer.setElementClass(this.el.nativeElement, 'active', true);
        } else {
            this.renderer.setElementClass(this.el.nativeElement, 'active', false);
        }
    }
}
