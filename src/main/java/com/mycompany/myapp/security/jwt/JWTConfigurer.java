package com.mycompany.myapp.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @ProjectName:
 * @Package:        com.mycompany.myapp.security.jwt
 * @ClassName:      JWTConfigurer
 * @Description:作为SecurityConfigurerAdapter的子类,通过configure(HttpSecurity http)对SS进行配置,本质为new 出一个JWTFilter,并放到UsernamePasswordAuthenticationFilter之前.
 * @Author:         chenkangli
 * @CreateDate:     15:01 2019/2/22
 * @UpdateUser:
 * @UpdateDate:     15:01 2019/2/22
 * @UpdateRemark:
 * @Version:        1.0
 */
public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private TokenProvider tokenProvider;

    public JWTConfigurer(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JWTFilter customFilter = new JWTFilter(tokenProvider);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
