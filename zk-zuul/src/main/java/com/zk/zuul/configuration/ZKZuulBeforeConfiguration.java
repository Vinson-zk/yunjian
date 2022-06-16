/** 
* Copyright (c) 2004-2020 ZK-Vinson Technologies, Inc.
* address: 
* All rights reserved. 
* 
* This software is the confidential and proprietary information of 
* ZK-Vinson Technologies, Inc. ("Confidential Information"). You shall not 
* disclose such Confidential Information and shall use it only in 
* accordance with the terms of the license agreement you entered into 
* with ZK-Vinson. 
*
* @Title: ZKZuulBeforeConfiguration.java 
* @author Vinson 
* @Package com.zk.zuul.configuration 
* @Description: TODO(simple description this file what to do. ) 
* @date Jun 14, 2022 7:07:11 PM 
* @version V1.0 
*/
package com.zk.zuul.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.EnableWebMvcConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.zk.core.utils.ZKEnvironmentUtils;
import com.zk.core.utils.ZKLocaleUtils;
import com.zk.core.web.utils.ZKWebUtils;
import com.zk.log.interceptor.ZKLogAccessInterceptor;

/** 
* @ClassName: ZKZuulBeforeConfiguration 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
@Configuration
@ImportResource(locations = {
        "classpath:xmlConfig/spring_ctx_application.xml",
        "classpath:xmlConfig/spring_ctx_zuul_application.xml",
        "classpath:xmlConfig/spring_ctx_mvc.xml" })
@PropertySource(encoding = "UTF-8", value = { 
        "classpath:zk.log.properties" })
@AutoConfigureBefore(value = {
    ZKZuulJdbcConfiguration.class, 
    ZKZuulRedisConfiguration.class, 
    ZKZuulSecConfiguration.class,
    ZKZuulAfterConfiguration.class,
    EnableWebMvcConfiguration.class,
    ServletWebServerFactoryAutoConfiguration.class,
})
public class ZKZuulBeforeConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

//    @PostConstruct
//    public void postConstruct() {
//        // 方法在 @Autowired before 后执行
//        System.out.println("[^_^:20220614-1940-001] ===== ZKZuulBeforeConfiguration class postConstruct "
//                + this.applicationContext);
//    }

    @Autowired
    public void before(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        System.out.println("[^_^:20220614-1940-001] ===== ZKZuulBeforeConfiguration class before ");

        ZKEnvironmentUtils.initContext(applicationContext);
//        ZKLocaleUtils.setLocale(ZKLocaleUtils.valueOf("en_US"));
//        ZKLocaleUtils.setLocale(ZKLocaleUtils.valueOf("zh_CN"));
//        // # 默认语言；注意这里不影响到 localeResolver 的默认语言
        ZKWebUtils.setLocale(
                ZKLocaleUtils.distributeLocale(ZKEnvironmentUtils.getString("zk.zuul.default.locale", "zh_CN")));

        // 设置下 RequestMappingHandlerAdapter 的 ignoreDefaultModelOnRedirect=true,
        // 这样可以提高效率，避免不必要的检索。
        requestMappingHandlerAdapter.setIgnoreDefaultModelOnRedirect(true);
        System.out.println("[^_^:20220614-1940-001] ----- ZKZuulBeforeConfiguration class before ");
    }

    /**
     * 记录日志拦截器
     *
     * @Title: logAccessInterceptor
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Jun 14, 2022 11:59:49 AM
     * @return
     * @return ZKLogAccessInterceptor
     */
    @Bean
    public ZKLogAccessInterceptor logAccessInterceptor() {
        return new ZKLogAccessInterceptor();
    }

}
