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
 * @Title: ZKMybatisOperationTest.java 
 * @author Vinson 
 * @Package com.zk.db 
 * @Description: TODO(simple description this file what to do.) 
 * @date Dec 19, 2019 11:50:39 AM 
 * @version V1.0   
*/
package com.zk.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.zk.core.utils.ZKJsonUtils;
import com.zk.db.helper.ZKDBTestConfig;

import junit.framework.TestCase;

/** 
* @ClassName: ZKMybatisOperationTest 
* @Description: TODO(simple description this class what to do.) 
* @author Vinson 
* @version 1.0 
*/
public class ZKMybatisOperationTest {

    public static ZKMybatisSessionFactory mybatisSessionFactory = null;

    static {
        mybatisSessionFactory = ZKDBTestConfig.getZKMybatisSessionFactory();
    }

    @Test
    public void testInsert() {
        try {
            Map<String, Object> paramsMap = null;
            int count = 0;

            /*** 插入数据 ***/
            int i = 1;
            for (; i < 11; ++i) {
                paramsMap = new HashMap<>();
                paramsMap.put("id", i + "");
                // 物理删除数据，防止主键冲突
                try {
                    ZKMybatisOperation.delete(mybatisSessionFactory.openSession(),
                            "com.zk.db.helper.dao.ZKDBDao.del", paramsMap);
                }
                catch(Exception e) {

                }
                paramsMap.put("type", (i % 2) * 1l);
                paramsMap.put("value", "value" + i + "_插入测试");
                paramsMap.put("remarks", i + "");
                count = 0;
                count = ZKMybatisOperation.insert(mybatisSessionFactory.openSession(),
                        "com.zk.db.helper.dao.ZKDBDao.insert", paramsMap);
                TestCase.assertEquals(1, count);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

    @Test
    public void testUpdate() {
        try {
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("id", "1");
            paramsMap.put("value", "test_xml_update_自恨寻芳到已迟，往年曾见未开时。");
            int count = ZKMybatisOperation.update(mybatisSessionFactory.openSession(),
                    "com.zk.db.helper.dao.ZKDBDao.update", paramsMap);
            TestCase.assertEquals(1, count);
        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

    @Test
    public void testSelectList() {
        try {
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("remarks", "remar'ks_\\");
            List<?> list = ZKMybatisOperation.selectList(mybatisSessionFactory.openSession(),
                    "com.zk.db.helper.dao.ZKDBDao.find", paramsMap);
            System.out.println("[^_^:20180306-0904-001-1]" + ZKJsonUtils.writeObjectJson(list));

            paramsMap = new HashMap<>();
            paramsMap.put("remarks", "remar'ks_\\");
            list = ZKMybatisOperation.selectList(mybatisSessionFactory.openSession(),
                    "com.zk.db.helper.dao.ZKDBDao.findLike", paramsMap);
            System.out.println("[^_^:20180306-0904-001-1]" + ZKJsonUtils.writeObjectJson(list));

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

    @Test
    public void testDelete() {
        try {
            Map<String, Object> paramsMap = null;
            int count = 0;
            int i = 1;
            testInsert();
            for (; i < 11; ++i) {
                paramsMap = new HashMap<>();
                paramsMap.put("id", i + "");
                // 物理删除数据，防止主键冲突
                try {
                    count = ZKMybatisOperation.delete(mybatisSessionFactory.openSession(),
                            "com.zk.db.helper.dao.ZKDBDao.del", paramsMap);
                    TestCase.assertEquals(1, count);
                }
                catch(Exception e) {

                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

    @Test
    public void testDml() {
        try {
            Map<String, Object> paramsMap = new HashMap<>();
            int count = 0;
            int i = 1;

            paramsMap = new HashMap<>();
            paramsMap.put("id", i + "");
            // 物理删除数据，防止主键冲突
            count = ZKMybatisOperation.delete(mybatisSessionFactory.openSession(),
                    "com.zk.db.helper.dao.ZKDBDao.del", paramsMap);

            // 插入
            paramsMap.put("type", (i % 2) * 1l);
            paramsMap.put("value", "value" + i + "_插入测试");
            paramsMap.put("remarks", i + "");
            count = 0;
            count = ZKMybatisOperation.insert(mybatisSessionFactory.openSession(),
                    "com.zk.db.helper.dao.ZKDBDao.insert", paramsMap);
            TestCase.assertEquals(1, count);
            // 修改
            paramsMap = new HashMap<>();
            paramsMap.put("id", i + "");
            paramsMap.put("value", "test_xml_update_自恨寻芳到已迟，往年曾见未开时。");
            count = ZKMybatisOperation.update(mybatisSessionFactory.openSession(),
                    "com.zk.db.helper.dao.ZKDBDao.update", paramsMap);
            TestCase.assertEquals(1, count);
            // 查询
            List<?> list = ZKMybatisOperation.selectList(mybatisSessionFactory.openSession(),
                    "com.zk.db.helper.dao.ZKDBDao.find", null);
            System.out.println("[^_^:20180306-0904-001-1]" + ZKJsonUtils.writeObjectJson(list));

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

}
