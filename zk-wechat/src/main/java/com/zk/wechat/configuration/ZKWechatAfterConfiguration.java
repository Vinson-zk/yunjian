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
* @Title: ZKWechatAfterConfiguration.java 
* @author Vinson 
* @Package com.zk.wechat.configuration 
* @Description: TODO(simple description this file what to do. ) 
* @date May 16, 2022 11:31:25 AM 
* @version V1.0 
*/
package com.zk.wechat.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.zk.framwwork.feign.interceptor.ZKFeignRequestInterceptor;
import com.zk.security.ticket.ZKSecTicketManager;

/** 
* @ClassName: ZKWechatAfterConfiguration 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKWechatAfterConfiguration {

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public ZKFeignRequestInterceptor feignRequestInterceptor(ZKSecTicketManager redisTicketManager) {
        ZKFeignRequestInterceptor feignRequestInterceptor = new ZKFeignRequestInterceptor(redisTicketManager,
                this.appName);
        return feignRequestInterceptor;
    }

}
