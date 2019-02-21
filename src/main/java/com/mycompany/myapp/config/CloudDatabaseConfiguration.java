package com.mycompany.myapp.config;

import io.github.jhipster.config.JHipsterConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @ProjectName:
 * @Package:        com.mycompany.myapp.config
 * @ClassName:      CloudDatabaseConfiguration
 * @Description:    cloud数据库配置
 * @Author:         chenkangli
 * @CreateDate:     15:15 2019/2/21
 * @UpdateUser:
 * @UpdateDate:     15:15 2019/2/21
 * @UpdateRemark:
 * @Version:        1.0
 */
@Configuration
@Profile(JHipsterConstants.SPRING_PROFILE_CLOUD)
public class CloudDatabaseConfiguration extends AbstractCloudConfig {

    private final Logger log = LoggerFactory.getLogger(CloudDatabaseConfiguration.class);
    
    private final String CLOUD_CONFIGURATION_HIKARI_PREFIX = "spring.datasource.hikari";

    @Bean
    @ConfigurationProperties(CLOUD_CONFIGURATION_HIKARI_PREFIX)
    public DataSource dataSource() {
        log.info("Configuring JDBC datasource from a cloud provider");
        return connectionFactory().dataSource();
    }
}
