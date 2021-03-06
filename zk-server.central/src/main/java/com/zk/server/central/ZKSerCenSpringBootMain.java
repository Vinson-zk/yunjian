/**   
 * Copyright (c) 2004-2014 Vinson Technologies, Inc.
 * address: 
 * All rights reserved. 
 * 
 * This software is the confidential and proprietary information of 
 * Vinson Technologies, Inc. ("Confidential Information").  You shall not 
 * disclose such Confidential Information and shall use it only in 
 * accordance with the terms of the license agreement you entered into 
 * with Vinson. 
 *
 * @Title: ZKSerCenSpringBootMain.java 
 * @author Vinson 
 * @Package com.zk.server.central 
 * @Description: TODO(simple description this file what to do.) 
 * @date Dec 19, 2019 7:25:28 PM 
 * @version V1.0   
*/
package com.zk.server.central;

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
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zk.server.central.configuration.ZKSerCenConfiguration;
import com.zk.server.central.configuration.ZKSerCenMvcConfiguration;
import com.zk.server.central.configuration.ZKSerCenShiroConfiguration;

/**
 * 
 * @ClassName: ZKSerCenSpringBootMain
 * @Description: TODO(simple description this class what to do.)
 * @author Vinson
 * @version 1.0
 */
//@SpringCloudApplication
@SpringBootApplication(exclude = { 
        DataSourceAutoConfiguration.class, 
        HibernateJpaAutoConfiguration.class, 
//        MongoAutoConfiguration.class, 
//        WebMvcAutoConfiguration.class, 
        TransactionAutoConfiguration.class
})
@EnableEurekaServer
@EnableTransactionManagement(proxyTargetClass = true)
//@ComponentScan(basePackages = { "com.zk.server.central.filter" })
//@ServletComponentScan(basePackages = { "com.zk.server.central.filter" })
@ImportAutoConfiguration(classes = { 
        ZKSerCenConfiguration.class,
//      ZKMongoAutoConfiguration.class, 
        ZKSerCenMvcConfiguration.class, 
        ZKSerCenShiroConfiguration.class })
@PropertySource(encoding = "UTF-8", value = { "classpath:zk.ser.cen.jdbc.properties",
        "classpath:zk.ser.cen.mongo.properties" })
//@AutoConfigureOrder(value = Ordered.HIGHEST_PRECEDENCE)
@ComponentScan(basePackages = { "com.zk.server.central.*" })
public class ZKSerCenSpringBootMain {

    protected static Logger log = LoggerFactory.getLogger(ZKSerCenSpringBootMain.class);

    /**
     * 
     *
     * @Title: main
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Mar 10, 2020 2:24:42 PM
     * @param args
     * @return void
     */
    public static void main(String[] args) {
        try {
            System.out.println("[^_^:20190628-1708-001]====================================================");
            System.out.println("[^_^:20190628-1708-001]=== zk Server Central  ?????? ... ... ");
            System.out.println("[^_^:20190628-1708-001]====================================================");
            run(args);
            System.out.println("[^_^:20190628-1708-001]----------------------------------------------------");

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static ConfigurableApplicationContext ctx = null;

    /**
     * 
     *
     * @Title: run
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Mar 10, 2020 2:25:08 PM
     * @param args
     * @return
     * @return ConfigurableApplicationContext
     */
    public static ConfigurableApplicationContext run(String[] args) {
        if (ctx == null || !ctx.isRunning()) {
            SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder();
            springApplicationBuilder = springApplicationBuilder.sources(ZKSerCenSpringBootMain.class);

            /*** ?????????????????????????????????????????? ***/
            // ??????????????????????????????[file:./config/, file:./, classpath:/config/, classpath:/]
            springApplicationBuilder = springApplicationBuilder.properties("spring.config.location=classpath:/");
            // ?????????????????????????????????applicaiton?????????????????????????????????????????? properties/yml ???????????????????????????????????????"," ??????
            springApplicationBuilder = springApplicationBuilder
                    .properties("spring.config.name=zk,zk.ser.cen,zk.ser.cen.env");

            SpringApplication springApplication = springApplicationBuilder.build();
            springApplication.setWebApplicationType(WebApplicationType.SERVLET);

            ctx = springApplication.run(args);
        }
        return ctx;
    }

    public static int exit() {
        if (ctx != null) {
            SpringApplication.exit(ctx);
        }
        return 0;
    }

}
