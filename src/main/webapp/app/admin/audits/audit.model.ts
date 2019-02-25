import { AuditData } from './audit-data.model';

// 表示服务器返回的审计内容模型，与org.springframework.boot.actuate.audit.AuditEvent对应。
export class Audit {
    constructor(public data: AuditData, public principal: string, public timestamp: string, public type: string) {}
}
