import { Component, OnInit } from '@angular/core';
import { ProfileService } from './profile.service';
import { ProfileInfo } from './profile-info.model';

@Component({
    selector: 'jhi-page-ribbon',
    template: `
        <div class="ribbon" *ngIf="ribbonEnv">
            <a href="" jhiTranslate="global.ribbon.{{ribbonEnv}}">{{ ribbonEnv }}</a>
        </div>
    `,
    styleUrls: ['page-ribbon.scss']
})
export class PageRibbonComponent implements OnInit {
    profileInfo: ProfileInfo;
    ribbonEnv: string;

    constructor(private profileService: ProfileService) {}

    // 在Init时就获取Profile，也就是说，在打开页面必然加载<jhi-page-ribbon>，也就是说必然会调用profileService与后台交互，获取ProfileInfo
    ngOnInit() {
        this.profileService.getProfileInfo().then(profileInfo => {
            this.profileInfo = profileInfo;
            this.ribbonEnv = profileInfo.ribbonEnv;
        });
    }
}
