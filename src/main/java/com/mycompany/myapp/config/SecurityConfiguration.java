package com.mycompany.myapp.config;

import com.mycompany.myapp.security.*;
import com.mycompany.myapp.security.jwt.*;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import javax.annotation.PostConstruct;

@Configuration
//启SS的全局开关,本质上，SS提供基于Filter的web安全性支持。其初始化一个DelegatingFIlterProxy完成对http请求的逐层过滤。当然，配置也就是控制这个过滤器Filter的控制范围，控制力度
@EnableWebSecurity
//启用全局方法安全的开关
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
//这三个bean都是jhipster.jar提供的工具bean，用来在restful调用时出现成功，失败，以及登出请求时候的处理器Handler。
//    目前4.10以及不用了，@Import(SecurityProblemSupport.class)，使用了zalando的problemSupport作为替代品
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserDetailsService userDetailsService;

    private final TokenProvider tokenProvider;

    private final CorsFilter corsFilter;

    private final SecurityProblemSupport problemSupport;

    public SecurityConfiguration(AuthenticationManagerBuilder authenticationManagerBuilder, UserDetailsService userDetailsService, TokenProvider tokenProvider, CorsFilter corsFilter, SecurityProblemSupport problemSupport) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
//        UserDetailsService实际注入的是security/UserDetailsService类,完成从数据库jhi_user表中读取用户信息（包括权限)
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
//        corsFilter配置本身是在WebConfigurer里面的，这里引入依赖的目的是为了UsernamePasswordAuthenticationFilter之前加入corsFilter（下标为5）进行CORS的校验
        this.corsFilter = corsFilter;
        this.problemSupport = problemSupport;
    }

    @PostConstruct
    public void init() {
        try {
            authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //HttpMethod.OPTIONS和以下url不纳入ss的控制范围（高层次控制）
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/app/**/*.{js,html}")
            .antMatchers("/i18n/**")
            .antMatchers("/content/**")
            .antMatchers("/h2-console/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //设置CookieCsrfToken Repository 为false，提高安全性
        http
            .csrf()
            .disable()
            //在corsFilter之前增加UsernamePasswordAuthenticationFilter，并设置验证错误处理为http401UnauthorizedEntryPoint
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(problemSupport)
            .accessDeniedHandler(problemSupport)
        .and()
            //配置禁止X-Frame-Options，防止iframe内框架调用，提高安全性
            .headers()
            .frameOptions()
            .disable()
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            //配置以下URL的权限控制（如management需要ADMIN权限），可以看出，这里的权限控制是通过硬编码方式进行的
//        重点是authorizeRequests()的相关配置,是SS实现对通用角色控制的地方。
            .authorizeRequests()
            .antMatchers("/api/register").permitAll()
            .antMatchers("/api/activate").permitAll()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/account/reset-password/init").permitAll()
            .antMatchers("/api/account/reset-password/finish").permitAll()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
        .and()
            .apply(securityConfigurerAdapter());

    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
}
