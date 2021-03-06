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
* @Title: ZKRedisTestHelperConfiguration.java 
* @author Vinson 
* @Package com.zk.test.redis 
* @Description: TODO(simple description this file what to do. ) 
* @date Aug 9, 2021 11:55:26 AM 
* @version V1.0 
*/
package com.zk.test.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zk.core.utils.ZKJsonUtils;
import com.zk.core.web.ZKJsonObjectMapper;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/** 
* @ClassName: ZKRedisTestHelperConfiguration 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
@PropertySources(value = {
        @PropertySource(value = { "classpath:redis/test_redis.properties" }, encoding = "UTF-8") })
public class ZKRedisTestHelperConfiguration {

    private static Logger logger = LoggerFactory.getLogger(ZKRedisTestHelperConfiguration.class);

    @Value("${zk.test.redis.host:127.0.0.1}")
    String host;

    @Value("${zk.test.redis.port:6379}")
    int port;

    @Value("${zk.test.redis.password}")
    String password;

    @Value("${zk.test.redis.timeout:3000}")
    int timeout;

    @Bean
    @ConfigurationProperties(prefix = "zk.test.redis.pool")
    public JedisPoolConfig jedisPoolConfig() {
        return new JedisPoolConfig();
    }

    /*** jedis ?????? ?????? ***/
    /** ???????????????????????? */
    @Bean
    @ConditionalOnBean(name = "jedisPoolConfig")
    public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig) {
        logger.info("[^_^:20210809-1255-001] hosr:{}, port:{}, password:{}", host, port, password);
        logger.info("[^_^:20210809-1255-001] JedisPoolConfig:{}", ZKJsonUtils.writeObjectJson(jedisPoolConfig));

        /** ???????????? */
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
        return jedisPool;
    }

//    /** ?????????????????????????????? redis ?????????????????? */
//    @Bean
//    @ConditionalOnBean(name = "jedisPoolConfig")
//    public JedisSentinelPool jedisPool(JedisPoolConfig jedisPoolConfig) {
//        logger.info("[^_^:20210809-1255-001] hosr:{}, port:{}, password:{}", host, port, password);
//        logger.info("[^_^:20210809-1255-001] JedisPoolConfig:{}", ZKJsonUtils.writeObjectJson(jedisPoolConfig));
//        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("zk_test",
//                ZKCollectionUtils.asSet(String.format("%s:%s", host, port)));
//        return jedisSentinelPool;
//    }

    /*******************************************************************************************************************************/
    /*** spring-data-redis ?????? ?????? ***/

//    /* redis ???????????? ?????? JedisConnectionFactory */
//    public RedisClusterConfiguration redisClusterConfiguration() {
//        Collection<String> clusterNodes = ZKCollectionUtils.asList(String.format("%s:%s", host, port));
//        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(clusterNodes);
//        redisClusterConfiguration.setPassword(password);
//        return redisClusterConfiguration;
//    }
////    @Bean
////    @ConditionalOnBean(name = { "jedisPoolConfig", "redisClusterConfiguration" })
////    public JedisConnectionFactory jedisConnectionFactory(RedisClusterConfiguration redisClusterConfiguration,
////            JedisPoolConfig jedisPoolConfig) {
////        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration,
////                jedisPoolConfig);
////        return jedisConnectionFactory;
////    }
//
//    /* redis ??????????????????????????? redis ?????????????????? */
//    @Bean
//    public RedisSentinelConfiguration redisSentinelConfiguration() {
//        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
////        redisSentinelConfiguration.sentinel(host, port);
//        RedisNode rn = new RedisNode(host, port);
////        redisSentinelConfiguration.sentinel(rn);
//        redisSentinelConfiguration.setSentinels(ZKCollectionUtils.asList(rn));
//        redisSentinelConfiguration.setDatabase(2);
//        redisSentinelConfiguration.setPassword(password);
//        return redisSentinelConfiguration;
//    }
//
//    @Bean
//    @ConditionalOnBean(name = { "jedisPoolConfig", "redisSentinelConfiguration" })
//    public JedisConnectionFactory jedisConnectionFactory(RedisSentinelConfiguration redisSentinelConfiguration,
//            JedisPoolConfig jedisPoolConfig) {
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration,
//                jedisPoolConfig);
//        return jedisConnectionFactory;
//    }
//
    /* redis ???????????? ?????? */
    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setDatabase(2);
        redisStandaloneConfiguration.setPassword(password);
        return redisStandaloneConfiguration;
    }

    @Bean
    @ConditionalOnBean(name = { "jedisPoolConfig", "redisStandaloneConfiguration" })
    public JedisConnectionFactory jedisConnectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration,
            JedisPoolConfig jedisPoolConfig) {
        // ?????????????????????????????????(?????????????????????????????????????????????????????????????????????)
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration
                .builder();
        // ??????jedisPoolConifig???????????????????????????????????????????????????????????????????????????
        jpcb.poolConfig(jedisPoolConfig);
        // ????????????????????????jedis???????????????
        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration);
        return jedisConnectionFactory;
    }

    @Bean
    @Primary
    @ConditionalOnBean(name = { "jedisConnectionFactory" })
    public RedisTemplate<String, ?> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, ?> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setEnableTransactionSupport(true);

        // ??????StringRedisSerializer???????????????????????????redis???key???
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // ??????Jackson2JsonRedisSerializer???????????????????????????redis???value??????????????????JDK?????????????????????
        Jackson2JsonRedisSerializer<Object> jacksonSeial = new Jackson2JsonRedisSerializer<Object>(Object.class);
//        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

        ZKJsonObjectMapper om = new ZKJsonObjectMapper();
        // ???????????????????????????field,get???set,????????????????????????ANY???????????????private???public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // ????????????????????????????????????????????????final????????????final?????????????????????String,Integer??????????????????
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSeial.setObjectMapper(om);

        // ?????? key ???value???????????????
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jacksonSeial);

        // ??????hash key ???value???????????????
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jacksonSeial);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Bean
    @ConfigurationProperties(prefix = "zk.test.redis.pool2")
    public JedisPoolConfig jedisPoolConfig2() {
        return new JedisPoolConfig();
    }

    @Bean
    @ConditionalOnBean(name = "jedisPoolConfig2")
    public JedisPool jedisPool2(JedisPoolConfig jedisPoolConfig2) {
        logger.info("[^_^:20210809-1255-001] hosr:{}, port:{}, password:{}", host, port, password);
        logger.info("[^_^:20210809-1255-001] JedisPoolConfig:{}", ZKJsonUtils.writeObjectJson(jedisPoolConfig2));

        /** ???????????? */
        JedisPool jedisPool = new JedisPool(jedisPoolConfig2, host, port, timeout, password);
        return jedisPool;
    }

    @Bean
    @ConditionalOnBean(name = { "jedisPoolConfig2", "redisStandaloneConfiguration" })
    public JedisConnectionFactory jedisConnectionFactory2(RedisStandaloneConfiguration redisStandaloneConfiguration,
            JedisPoolConfig jedisPoolConfig2) {
        // ?????????????????????????????????(?????????????????????????????????????????????????????????????????????)
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration
                .builder();
        // ??????jedisPoolConifig???????????????????????????????????????????????????????????????????????????
        jpcb.poolConfig(jedisPoolConfig2);
        // ????????????????????????jedis???????????????
        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration);
        return jedisConnectionFactory;
    }

    @Bean
    @ConditionalOnBean(name = { "jedisConnectionFactory2" })
    public RedisTemplate<String, ?> redisTemplate2(JedisConnectionFactory jedisConnectionFactory2) {
        RedisTemplate<String, ?> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory2);
        redisTemplate.setEnableTransactionSupport(true);

        // ??????StringRedisSerializer???????????????????????????redis???key???
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // ??????Jackson2JsonRedisSerializer???????????????????????????redis???value??????????????????JDK?????????????????????
        Jackson2JsonRedisSerializer<Object> jacksonSeial = new Jackson2JsonRedisSerializer<Object>(Object.class);
//        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

        ZKJsonObjectMapper om = new ZKJsonObjectMapper();
        // ???????????????????????????field,get???set,????????????????????????ANY???????????????private???public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // ????????????????????????????????????????????????final????????????final?????????????????????String,Integer??????????????????
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSeial.setObjectMapper(om);

        // ?????? key ???value???????????????
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jacksonSeial);

        // ??????hash key ???value???????????????
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jacksonSeial);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

}
