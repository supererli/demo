package com.mycompany.myapp.config;

/**
 *
 * @ProjectName:
 * @Package:        com.mycompany.myapp.config
 * @ClassName:      Constants
 * @Description:    项目常量配置
 * @Author:         chenkangli
 * @CreateDate:     15:16 2019/2/21
 * @UpdateUser:
 * @UpdateDate:     15:16 2019/2/21
 * @UpdateRemark:
 * @Version:        1.0
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String DEFAULT_LANGUAGE = "en";
    
    private Constants() {
    }
}
