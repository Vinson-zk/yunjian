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
* @Title: ZKSecTestHelperConfigurationAfter.java 
* @author Vinson 
* @Package com.zk.security.helper 
* @Description: TODO(simple description this file what to do. ) 
* @date Aug 4, 2021 12:32:15 AM 
* @version V1.0 
*/
package com.zk.security.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.zk.core.utils.ZKEnvironmentUtils;
import com.zk.core.utils.ZKLocaleUtils;
import com.zk.core.web.filter.ZKDelegatingFilterProxyRegistrationBean;
import com.zk.core.web.utils.ZKWebUtils;

/** 
* @ClassName: ZKSecTestHelperConfigurationAfter 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
@ImportResource(locations = { "classpath:test_spring_ctx_sec.xml" })
@AutoConfigureAfter(value = { ZKSecTestHelperConfigurationBefore.class })
public class ZKSecTestHelperConfigurationAfter {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    public void before(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        System.out.println("[^_^:20210705-1808-001] ===== " + this.getClass() + " class before ");

        ZKEnvironmentUtils.initContext(applicationContext);
//        ZKLocaleUtils.setLocale(ZKLocaleUtils.valueOf("en_US"));
//        ZKLocaleUtils.setLocale(ZKLocaleUtils.valueOf("zh_CN"));
//        // # ??????????????????????????????????????? localeResolver ???????????????
        ZKWebUtils
                .setLocale(ZKLocaleUtils.distributeLocale(ZKEnvironmentUtils.getString("zk.default.locale", "zh_CN")));

        // ????????? RequestMappingHandlerAdapter ??? ignoreDefaultModelOnRedirect=true,
        // ??????????????????????????????????????????????????????
        requestMappingHandlerAdapter.setIgnoreDefaultModelOnRedirect(true);
        System.out.println("[^_^:20210705-1808-001] ----- " + this.getClass() + " class before ");
    }

    /********************************************************************************/
    /*** zk security ?????? */
    /********************************************************************************/

    /**
     * ????????? - DelegatingFilterProxyRegistrationBean
     *
     * @Title: zkServerServletContextInitializer
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Oct 28, 2019 4:11:07 PM
     * @return
     * @return ZKServerServletContextInitializer
     */
    @Bean
    public ZKDelegatingFilterProxyRegistrationBean shiroFilterProxyRegistrationBean() {
        String filterName = "zkSecFilter";
        ZKDelegatingFilterProxyRegistrationBean zkDfprb = new ZKDelegatingFilterProxyRegistrationBean(filterName);
        zkDfprb.setName(filterName);
        zkDfprb.addUrlPatterns("/*");
        zkDfprb.setOrder(Ordered.LOWEST_PRECEDENCE);
        return zkDfprb;
    }

}
