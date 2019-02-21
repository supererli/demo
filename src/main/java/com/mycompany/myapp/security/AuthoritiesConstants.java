package com.mycompany.myapp.security;

/**
 * Constants for Spring Security authorities.
 */
/**
 *
 * @ProjectName:
 * @Package:        com.mycompany.myapp.security
 * @ClassName:      AuthoritiesConstants
 * @Description:    用户权限的常量设置
 * @Author:         chenkangli
 * @CreateDate:     15:36 2019/2/21
 * @UpdateUser:
 * @UpdateDate:     15:36 2019/2/21
 * @UpdateRemark:
 * @Version:        1.0
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
