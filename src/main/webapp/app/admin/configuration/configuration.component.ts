import { Component, OnInit } from '@angular/core';

import { JhiConfigurationService } from './configuration.service';

@Component({
    selector: 'jhi-configuration',
    templateUrl: './configuration.component.html'
})
export class JhiConfigurationComponent implements OnInit {
    allConfiguration: any = null;
    configuration: any = null;
    configKeys: any[];
    filter: string;
    orderProp: string;
    reverse: boolean;

    constructor(private configurationService: JhiConfigurationService) {
        this.configKeys = [];
        this.filter = '';
        this.orderProp = 'prefix';
        this.reverse = false;
    }

    keys(dict): Array<string> {
        return dict === undefined ? [] : Object.keys(dict);
    }

    // 在init是加载所有的属性
    ngOnInit() {
        //获取configprops所有配置，并保存其key值
        this.configurationService.get().subscribe(configuration => {
            this.configuration = configuration;

            for (const config of configuration) {
                if (config.properties !== undefined) {
                    this.configKeys.push(Object.keys(config.properties));
                }
            }
        });

        //获取/env所有配置
        this.configurationService.getEnv().subscribe(configuration => {
            this.allConfiguration = configuration;
        });
    }
}
