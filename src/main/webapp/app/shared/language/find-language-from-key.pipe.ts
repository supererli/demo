import { Pipe, PipeTransform } from '@angular/core';
/**
 * 定义了一个findLanguageFromKey管道，进行语种简写到语种全称之间的对照。
 * */
//jhipster-needle-××这样的注释,一般说明这边的代码是由jhipster的jhi命令进行管理的，通常情况下，我们不需要手工编辑修改
@Pipe({ name: 'findLanguageFromKey' })
export class FindLanguageFromKeyPipe implements PipeTransform {
    private languages: any = {
        en: { name: 'English' }
        // jhipster-needle-i18n-language-key-pipe - JHipster will add/remove languages in this object
    };
    transform(lang: string): string {
        return this.languages[lang].name;
    }
}
