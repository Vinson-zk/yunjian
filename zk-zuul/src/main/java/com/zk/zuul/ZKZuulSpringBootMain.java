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
* @Title: ZKZuulSpringBootMain.java 
* @author Vinson 
* @Package com.zk.zuul 
* @Description: TODO(simple description this file what to do. ) 
* @date Jun 14, 2022 7:08:16 PM 
* @version V1.0 
*/
package com.zk.zuul;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/** 
* @ClassName: ZKZuulSpringBootMain 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
@SpringBootApplication(exclude = { 
        DataSourceAutoConfiguration.class, 
        TransactionAutoConfiguration.class,
//        HibernateJpaAutoConfiguration.class,
//        MongoAutoConfiguration.class,
//        WebMvcAutoConfiguration.class,
})
@EnableZuulProxy
@EnableEurekaClient
@EnableTransactionManagement(proxyTargetClass = true)
//@AutoConfigureOrder(value = Ordered.HIGHEST_PRECEDENCE)
@ComponentScan(basePackages = { "com.zk.zuul.*", "com.zk.log.*" })
public class ZKZuulSpringBootMain {

    protected static Logger log = LoggerFactory.getLogger(ZKZuulSpringBootMain.class);

    public static void main(String[] args) {
        try {
            log.info("[^_^:20220614-1912-001]========================================");
            log.info("[^_^:20220614-1912-001]=== zk zuul ?????? ... ... ");
            log.info("[^_^:20220614-1912-001]========================================");
            run(args);
            log.info("[^_^:20220614-1912-001]----------------------------------------");

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    // ??????
    public static ConfigurableApplicationContext run(String[] args) {
        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder();
        springApplicationBuilder = springApplicationBuilder.sources(ZKZuulSpringBootMain.class);

        /*** ?????????????????????????????????????????? ***/
        // ??????????????????????????????[file:./config/, file:./, classpath:/config/, classpath:/]
        springApplicationBuilder = springApplicationBuilder.properties("spring.config.location=classpath:/");
        // ?????????????????????????????????applicaiton?????????????????????????????????????????? properties/yml ???????????????????????????????????????","
        // ??????
        springApplicationBuilder = springApplicationBuilder.properties("spring.config.name=zk,zk.zuul,zk.zuul.env");

        SpringApplication springApplication = springApplicationBuilder.build();
        springApplication.setWebApplicationType(WebApplicationType.SERVLET);

        return springApplication.run(args);
    }

    // ??????
    public static int exit(ConfigurableApplicationContext ctx) {
        return SpringApplication.exit(ctx);
    }

}
