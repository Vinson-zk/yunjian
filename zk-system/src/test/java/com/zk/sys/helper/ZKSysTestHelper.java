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
* @Title: ZKSysTestHelper.java 
* @author Vinson 
* @Package com.zk.sys.helper 
* @Description: TODO(simple description this file what to do. ) 
* @date Aug 5, 2020 12:27:32 PM 
* @version V1.0 
*/
package com.zk.sys.helper;
/** 
* @ClassName: ZKSysTestHelper 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zk.sys.ZKSysSpringBootMain;
import com.zk.sys.configuration.ZKSysConfiguration;
import com.zk.sys.configuration.ZKSysMvcConfiguration;

@SpringBootApplication(exclude = { 
        DataSourceAutoConfiguration.class
        , TransactionAutoConfiguration.class
        , HibernateJpaAutoConfiguration.class
//        , MongoAutoConfiguration.class
//        , WebMvcAutoConfiguration.class
        , EurekaDiscoveryClientConfiguration.class
})
//@EnableEurekaClient
@EnableTransactionManagement(proxyTargetClass = true)
//@ComponentScan(basePackages = { "com.zk.server.central.filter" })
//@ServletComponentScan(basePackages = { "com.zk.server.central.filter" })
@ImportAutoConfiguration(classes = { 
        ZKSysConfiguration.class
//        , ZKMongoAutoConfiguration.class
        , ZKSysMvcConfiguration.class
//        , ZKSysShiroConfiguration.class 
})
@PropertySource(encoding = "UTF-8", value = { "classpath:zk.sys.jdbc.properties", "classpath:zk.sys.mongo.properties" })
//@AutoConfigureOrder(value = Ordered.HIGHEST_PRECEDENCE)
@ComponentScan(basePackages = { "com.zk.sys.*" })
public class ZKSysTestHelper {

    static ConfigurableApplicationContext ctx = null;

    public static ConfigurableApplicationContext getMainCtx() {
        if (ctx == null) {
            log.info("[^_^:20200805-1508-001]========================================");
            log.info("[^_^:20200805-1508-001]=== zk system  ???????????? ... ... ");
            log.info("[^_^:20200805-1508-001]========================================");
            ctx = ZKSysSpringBootMain.run(new String[] {});
            log.info("[^_^:20200805-1508-001]----------------------------------------");
        }
        return ctx;
    }

    protected static Logger log = LoggerFactory.getLogger(ZKSysTestHelper.class);

    // ??????
    public static ConfigurableApplicationContext run(String[] args) {

        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder();
        springApplicationBuilder = springApplicationBuilder.sources(ZKSysTestHelper.class);

        /*** ?????????????????????????????????????????? ***/
        // ??????????????????????????????[file:./config/, file:./, classpath:/config/, classpath:/]
        springApplicationBuilder = springApplicationBuilder.properties("spring.config.location=classpath:/");
        // ?????????????????????????????????applicaiton?????????????????????????????????????????? properties/yml ???????????????????????????????????????","
        // ??????
        springApplicationBuilder = springApplicationBuilder.properties("spring.config.name=zk,zk.sys");

        SpringApplication springApplication = springApplicationBuilder.build();
        springApplication.setWebApplicationType(WebApplicationType.SERVLET);

        return springApplication.run(args);
    }

    // ??????
    public static int exit(ConfigurableApplicationContext ctx) {
        return SpringApplication.exit(ctx);
    }


}
