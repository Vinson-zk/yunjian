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
* @Title: ZKZuulSecConfiguration.java 
* @author Vinson 
* @Package com.zk.zuul.configuration 
* @Description: TODO(simple description this file what to do. ) 
* @date Jun 14, 2022 7:07:27 PM 
* @version V1.0 
*/
package com.zk.zuul.configuration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.EnableWebMvcConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;

import com.zk.core.redis.ZKJedisOperatorStringKey;
import com.zk.core.web.filter.ZKDelegatingFilterProxyRegistrationBean;
import com.zk.framework.security.realm.ZKDistributedRealm;
import com.zk.security.ticket.ZKSecTicketManager;
import com.zk.security.ticket.support.redis.ZKSecRedisTicketManager;
import com.zk.security.web.mgt.ZKSecWebSecurityManager;
import com.zk.security.web.rememberMe.ZKSecCookieRememberMemanager;
import com.zk.security.web.support.spring.ZKSecStaticMethodMatcherPointcutAdvisor;

/** 
* @ClassName: ZKZuulSecConfiguration 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
@Configuration
@AutoConfigureBefore(value = { ZKZuulAfterConfiguration.class })
@AutoConfigureAfter(value = {
//        ZKMongoAutoConfiguration.class,
        ZKZuulRedisConfiguration.class,
        EnableWebMvcConfiguration.class, 
        ServletWebServerFactoryAutoConfiguration.class})
@ImportResource(locations = { "classpath:xmlConfig/spring_ctx_zuul_sec.xml" })
public class ZKZuulSecConfiguration {

    @Bean
    public ZKSecStaticMethodMatcherPointcutAdvisor zkSecStaticMethodMatcherPointcutAdvisor() {
        return new ZKSecStaticMethodMatcherPointcutAdvisor();
    }

    @Bean
    public ZKDistributedRealm secRealm(ZKSecRedisTicketManager secRedisTicketManager) {
        ZKDistributedRealm realm = new ZKDistributedRealm();
//        realm.setSecUserService(secUserService);
        realm.setTicketManager(secRedisTicketManager);
        return realm;
    }

//    @Bean
//    public ZKSecMongoTicketManager zkSecMongoTicketManager(MongoTemplate mongoTemplate) {
//        return new ZKSecMongoTicketManager(mongoTemplate);
//    }

    @Primary
    @Bean
    public ZKSecRedisTicketManager redisTicketManager(ZKJedisOperatorStringKey jedisOperatorStringKey) {
        return new ZKSecRedisTicketManager(jedisOperatorStringKey);
    }

    @Bean
    public ZKSecCookieRememberMemanager secCookieRememberMemanager() {
        ZKSecCookieRememberMemanager secCookieRememberMemanager = new ZKSecCookieRememberMemanager();
        return secCookieRememberMemanager;
    }

    @Bean
    public ZKSecWebSecurityManager zkSecWebSecurityManager(ZKSecCookieRememberMemanager rememberMeManager,
            ZKSecTicketManager zkSecTicketManager, ZKDistributedRealm secRealm) {
        ZKSecWebSecurityManager sm = new ZKSecWebSecurityManager();
        sm.setTicketManager(zkSecTicketManager);
        sm.setRememberMeManager(rememberMeManager);
        sm.setRealm(secRealm);
        return sm;
    }

    /**
     * ????????? - ZKDelegatingFilterProxyRegistrationBean
     *
     * @Title: secFilterProxyRegistrationBean
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Apr 21, 2022 9:48:22 AM
     * @return ZKDelegatingFilterProxyRegistrationBean
     */
    @Bean
    @ConditionalOnBean(value = { ZKSecWebSecurityManager.class, ZKSecStaticMethodMatcherPointcutAdvisor.class })
    public ZKDelegatingFilterProxyRegistrationBean secFilterProxyRegistrationBean() {
        String filterName = "zkSecFilter";
        ZKDelegatingFilterProxyRegistrationBean zkDfprb = new ZKDelegatingFilterProxyRegistrationBean(filterName);
        zkDfprb.setName(filterName);
        zkDfprb.addUrlPatterns("/*");
        zkDfprb.setOrder(Ordered.LOWEST_PRECEDENCE);
        return zkDfprb;
    }

//    /**
//     * ??????????????????????????????
//     *
//     * @Title: getSecAuthService
//     * @Description: TODO(simple description this method what to do.)
//     * @author Vinson
//     * @date May 11, 2022 3:52:24 PM
//     * @return
//     * @return ZKSecAuthService<String>
//     */
//    @Bean
//    @Primary
//    public ZKSecAuthService<String> getSecAuthService() {
//        ZKSecAuthService<String> bean = new ZKSysSecAuthService();
//        return bean;
//    }

}
