import { enableProdMode } from '@angular/core';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';

/**
 * prod.config.ts启动时候用的配置，根据传入的DEBUG_INFO_ENABLED决定是否启动生产模式。（DEBUG_INFO_ENABLED实际是根据用的dev还是prod的webpack文件判断的）
 * */
export function ProdConfig() {
    // disable debug data on prod profile to improve performance
    if (!DEBUG_INFO_ENABLED) {
        enableProdMode();
    }
}
