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
 * @Title: ZKMongoAutoConfigurationTest.java 
 * @author Vinson 
 * @Package com.zk.mongo.helper.configuration 
 * @Description: TODO(simple description this file what to do.) 
 * @date Dec 18, 2019 6:11:50 PM 
 * @version V1.0   
*/
package com.zk.mongo.configuration;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;

import junit.framework.TestCase;

/** 
* @ClassName: ZKMongoAutoConfigurationTest 
* @Description: TODO(simple description this class what to do.) 
* @author Vinson 
* @version 1.0 
*/
@SpringBootApplication
@ImportAutoConfiguration(classes = { ZKMongoAutoConfiguration.class })
@AutoConfigureBefore(value = { ZKMongoAutoConfiguration.class })
public class ZKMongoAutoConfigurationTest {

    protected static Logger log = LoggerFactory.getLogger(ZKMongoAutoConfigurationTest.class);

    public static void main(String[] args) {
        log.info("[^_^:20190822-1554-001]============================================");
        log.info("[^_^:20190822-1554-001]=== Spring Boot zk-mongo ZKMongoConfigurationTest DoMain  ?????? ... ... ");
        log.info("[^_^:20190822-1554-001]============================================");
        run(args);
        log.info("[^_^:20190822-1554-001]--------------------------------------------");
    }

    public static ConfigurableApplicationContext run(String[] args) {

        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(
                ZKMongoAutoConfigurationTest.class);
//        springApplicationBuilder = springApplicationBuilder.sources(ZKMongoConfigurationTest.class);
        // ?????????????????????????????????
//        springApplicationBuilder = springApplicationBuilder.profiles("springApplicationBuilder");
//        springApplicationBuilder = springApplicationBuilder.properties("spring.profiles.active=springApplicationBuilder");

        /*** ?????????????????????????????????????????? ***/
        // ??????????????????????????????[file:./config/, file:./, classpath:/config/, classpath:/]
//        springApplicationBuilder = springApplicationBuilder.properties("spring.config.location=classpath:/");
        // ?????????????????????????????????applicaiton?????????????????????????????????????????? properties/yml ???????????????????????????????????????","
        // ??????
//        springApplicationBuilder = springApplicationBuilder.properties("spring.config.name=application,other");

        springApplicationBuilder = springApplicationBuilder.properties("spring.config.location=classpath:/");
        springApplicationBuilder = springApplicationBuilder.properties("spring.config.name=test_mongodb");

        SpringApplication springApplication = springApplicationBuilder.build();
        springApplication.setWebApplicationType(WebApplicationType.NONE);

        return springApplication.run(args);
    }

    @Bean
    @ConfigurationProperties(prefix = "zk.mongodb")
    public ZKMongoProperties mongoProperties() {
        return new ZKMongoProperties();
    }

    @Test
    public void test() {
        try {
            ConfigurableApplicationContext ctx = ZKMongoAutoConfigurationTest.run(new String[0]);
            TestCase.assertNotNull(ctx);

            MongoTemplate mongoTemplate = ctx.getBean(MongoTemplate.class);
            TestCase.assertNotNull(mongoTemplate);

            MongoClient mongoClient = ctx.getBean(MongoClient.class);

            TestCase.assertEquals("10.211.55.10", mongoClient.getAddress().getHost());

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

}
