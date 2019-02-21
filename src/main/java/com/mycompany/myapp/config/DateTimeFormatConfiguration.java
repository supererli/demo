package com.mycompany.myapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @ProjectName:
 * @Package:        com.mycompany.myapp.config
 * @ClassName:      DateTimeFormatConfiguration
 * @Description:    日期格式化配置，可自定义converter
 * @Author:         chenkangli
 * @CreateDate:     15:18 2019/2/21
 * @UpdateUser:
 * @UpdateDate:     15:18 2019/2/21
 * @UpdateRemark:
 * @Version:        1.0
 */
@Configuration
public class DateTimeFormatConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(registry);
    }
}
