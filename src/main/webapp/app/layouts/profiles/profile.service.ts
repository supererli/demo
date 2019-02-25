import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ProfileInfo } from './profile-info.model';
import { map } from 'rxjs/operators';

/**
 * 与后端api/profile-info进行交互，获取后端服务的profile信息
 * */
@Injectable({ providedIn: 'root' })
export class ProfileService {
    private infoUrl = SERVER_API_URL + 'management/info';
    private profileInfo: Promise<ProfileInfo>;

    constructor(private http: HttpClient) {}

    //返回一个Promise
    getProfileInfo(): Promise<ProfileInfo> {
        if (!this.profileInfo) {
            //直接调用http.get方法通讯
            this.profileInfo = this.http
                .get<ProfileInfo>(this.infoUrl, { observe: 'response' })
                .pipe(
                    map((res: HttpResponse<ProfileInfo>) => {
                        const data = res.body;
                        //构造ProfileInfo结构体
                        const pi = new ProfileInfo();
                        pi.activeProfiles = data['activeProfiles'];
                        const displayRibbonOnProfiles = data['display-ribbon-on-profiles'].split(',');
                        if (pi.activeProfiles) {
                            const ribbonProfiles = displayRibbonOnProfiles.filter(profile => pi.activeProfiles.includes(profile));
                            if (ribbonProfiles.length !== 0) {
                                pi.ribbonEnv = ribbonProfiles[0];
                            }
                            pi.inProduction = pi.activeProfiles.includes('prod');
                            pi.swaggerEnabled = pi.activeProfiles.includes('swagger');
                        }
                        return pi;
                        //http.get是Observable,所以要调用toPromise转换
                    })
                )
                .toPromise();
        }
        return this.profileInfo;
    }
}
