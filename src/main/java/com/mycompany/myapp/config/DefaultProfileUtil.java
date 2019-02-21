package com.mycompany.myapp.config;

import io.github.jhipster.config.JHipsterConstants;

import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;

import java.util.*;

/**
 *
 * @ProjectName:
 * @Package:        com.mycompany.myapp.config
 * @ClassName:      DefaultProfileUtil
 * @Description:    默认属性文件工具
 * @Author:         chenkangli
 * @CreateDate:     15:20 2019/2/21
 * @UpdateUser:
 * @UpdateDate:     15:20 2019/2/21
 * @UpdateRemark:
 * @Version:        1.0
 */
public final class DefaultProfileUtil {

    private static final String SPRING_PROFILE_DEFAULT = "spring.profiles.default";

    private DefaultProfileUtil() {
    }

    /**
     * Set a default to use when no profile is configured.
     *
     * @param app the Spring application
     */
    public static void addDefaultProfile(SpringApplication app) {
        Map<String, Object> defProperties = new HashMap<>();
        /*
        * The default profile to use when no other profiles are defined
        * This cannot be set in the <code>application.yml</code> file.
        * See https://github.com/spring-projects/spring-boot/issues/1219
        */
        defProperties.put(SPRING_PROFILE_DEFAULT, JHipsterConstants.SPRING_PROFILE_DEVELOPMENT);
        app.setDefaultProperties(defProperties);
    }

    /**
     * Get the profiles that are applied else get default profiles.
     *
     * @param env spring environment
     * @return profiles
     */
    public static String[] getActiveProfiles(Environment env) {
        String[] profiles = env.getActiveProfiles();
        if (profiles.length == 0) {
            return env.getDefaultProfiles();
        }
        return profiles;
    }
}
