package com.mycompany.myapp.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

/**
 *
 * @ProjectName:
 * @Package:        com.mycompany.myapp.config
 * @ClassName:      CacheConfiguration
 * @Description:    缓存配置
 * @Author:         chenkangli
 * @CreateDate:     15:15 2019/2/21
 * @UpdateUser:
 * @UpdateDate:     15:15 2019/2/21
 * @UpdateRemark:
 * @Version:        1.0
 */
@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.mycompany.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Course.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Course.class.getName() + ".labs", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Teacher.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Teacher.class.getName() + ".courses", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Teacher.class.getName() + ".grades", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Grade.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Grade.class.getName() + ".teachers", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Grade.class.getName() + ".students", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Lab.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Lab.class.getName() + ".courses", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Student.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
