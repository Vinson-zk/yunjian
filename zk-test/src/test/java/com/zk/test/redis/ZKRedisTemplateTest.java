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
* @Title: ZKRedisTemplateTest.java 
* @author Vinson 
* @Package com.zk.test.redis 
* @Description: TODO(simple description this file what to do. ) 
* @date Aug 12, 2021 7:22:19 PM 
* @version V1.0 
*/
package com.zk.test.redis;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.zk.core.utils.ZKJsonUtils;

import junit.framework.TestCase;

/** 
* @ClassName: ZKRedisTemplateTest 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKRedisTemplateTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testRedisTemplate() {

        RedisTemplate<String, Object> redisTemplate = null;

        try {
            ConfigurableApplicationContext ctx = ZKRedisTestHelperSpringBootMain.run(new String[] {});
            TestCase.assertNotNull(ctx);
            redisTemplate = ctx.getBean(RedisTemplate.class);
            TestCase.assertNotNull(redisTemplate);

            String key = "test_key";
            String attr1 = "test_attr_1", attr2 = "test_attr_2";
            String valueStr = "test_value_2";
            int valueInt = 1;
            String resValue;
            long resIntValue;
            // ??????????????????
            int seconds = 1;
            long sleepSeconds = 1500;

            long beginTime, endTime;

            /*** RedisTemplate ??????????????? ***/

            /*** ??????String ?????????????????? ***/
            BoundValueOperations<String, Object> stringKey = redisTemplate.boundValueOps(key);
            // ?????????
            stringKey.set(valueInt);
            // ??????
            resValue = String.valueOf(stringKey.get());
            TestCase.assertEquals(String.valueOf(valueInt), resValue);
            // ????????????; ???????????????????????????????????????ERR value is not an integer or out of range
            stringKey.increment(1l);
            resValue = String.valueOf(stringKey.get());
            TestCase.assertEquals(String.valueOf(valueInt + 1), resValue);
            // ??????;
//            redisTemplate.delete(key)
            // ????????????
            stringKey.expire(seconds, TimeUnit.SECONDS);
//            redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
            beginTime = System.currentTimeMillis();
            System.out.println("[^_^:20210810-1123-001] ??????????????????: " + beginTime);
            Thread.sleep(sleepSeconds);
            endTime = System.currentTimeMillis();
            System.out.println("[^_^:20210810-1123-001] ??????????????????: " + endTime + "; ?????????" + (endTime - beginTime) + " ?????????");
            resValue = (String) stringKey.get();
            TestCase.assertNull(resValue);

            redisTemplate.delete(key);
            System.out.println("[^_^:20210810-1158-001] key:" + key + " ???????????????" + redisTemplate.hasKey(key));
            resIntValue = stringKey.increment(1l);
            System.out.println("[^_^:20210810-1158-001] key:" + key + " ???????????????" + redisTemplate.hasKey(key));
            TestCase.assertEquals(1l, resIntValue);
            resIntValue = stringKey.increment(1l);
            TestCase.assertEquals(2l, resIntValue);
            redisTemplate.delete(key);

            /*** ??????Hash ?????????????????? ***/
            BoundHashOperations<String, Object, Object> hashKey = redisTemplate.boundHashOps("HashKey");
            // ?????????
//            hashKey.putAll(m);
            hashKey.put(attr1, valueInt);
            hashKey.put(attr2, valueStr);
            // ??????
            resValue = String.valueOf(hashKey.get(attr1));
            TestCase.assertEquals(String.valueOf(valueInt), resValue);
            resValue = String.valueOf(hashKey.get(attr2));
            TestCase.assertEquals(valueStr, resValue);
            // ????????????
            hashKey.expire(seconds, TimeUnit.SECONDS);
//          redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
            beginTime = System.currentTimeMillis();
            System.out.println("[^_^:20210810-1123-001] ??????????????????: " + beginTime);
            Thread.sleep(sleepSeconds);
            endTime = System.currentTimeMillis();
            System.out.println("[^_^:20210810-1123-001] ??????????????????: " + endTime + "; ?????????" + (endTime - beginTime) + " ?????????");
            resValue = (String) hashKey.get(attr2);
            TestCase.assertNull(resValue);

            /*** ??????Set ?????????????????? ***/
            /*** ??????LIST ?????????????????? ***/
            /*** ??????Zset ????????????????????? ***/

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        } finally {

        }
    }

    // ??????????????? increment
    @SuppressWarnings("unchecked")
    @Test
    public void testRedisTemplateIncrement() {

        ConfigurableApplicationContext ctx = ZKRedisTestHelperSpringBootMain.run(new String[] {});
        TestCase.assertNotNull(ctx);
        final RedisTemplate<String, Object> redisTemplate = ctx.getBean(RedisTemplate.class);
        TestCase.assertNotNull(redisTemplate);

        try {
            final String redisTemplateKey = "test_redisTemplate_key_increment";
            // ??????????????????
            final int seconds = 5;
            final int threadCount = 800;
            final Map<String, String> resRedisTemplateMap = new HashMap<>();

            ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);
//            threadPool = Executors.newSingleThreadExecutor();
            ValueOperations<String, Object> valueOpts = redisTemplate.opsForValue();
            redisTemplate.delete(redisTemplateKey);
            for (long i = 0; i < threadCount; ++i) {
                String index = String.valueOf(i);
                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        long threadId = Thread.currentThread().getId();
                        long res = valueOpts.increment(redisTemplateKey, 1l);
                        resRedisTemplateMap.put(index + ":Thread.id=" + threadId, String.valueOf(res));
                        return;
                    }
                });
            }

            try {
                threadPool.shutdown();
                threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                System.out.println("all thread complete");
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }

//            jedisClose.expire(jedisKey, seconds);
            redisTemplate.boundValueOps(redisTemplateKey).expire(seconds, TimeUnit.SECONDS);

            System.out.println("[^_^:20210910-1522-001] resRedisTemplateMap.size:" + resRedisTemplateMap.size());
            System.out.println(
                    "[^_^:20210910-1522-001] resRedisTemplateMap:" + ZKJsonUtils.writeObjectJson(resRedisTemplateMap));
            int count1 = 0;
            for (Entry<String, String> e : resRedisTemplateMap.entrySet()) {
                if ("1".equals(e.getValue())) {
                    ++count1;
                    System.out.println("[^_^:20210910-1522-002] resRedisTemplateMap ??????????????????????????????" + e.getKey());
                }
            }
            TestCase.assertEquals(1, count1);

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

}
