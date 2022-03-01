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
* @Title: ZKInsertSqlConvertTest.java 
* @author Vinson 
* @Package com.zk.db.annotation.support.mysql 
* @Description: TODO(simple description this file what to do. ) 
* @date Sep 11, 2020 10:07:45 AM 
* @version V1.0 
*/
package com.zk.db.dialect.support.mysql;

import org.junit.Test;

import com.zk.db.annotation.ZKDBAnnotationProvider;
import com.zk.db.helper.entity.ZKDBEntity;
import com.zk.db.mybatis.mysql.ZKInsertSqlConvert;

import junit.framework.TestCase;

/** 
* @ClassName: ZKInsertSqlConvertTest 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKInsertSqlConvertTest {

    @Test
    public void testGetSql() {
        try {
            ZKDBAnnotationProvider zkAp = new ZKDBAnnotationProvider(ZKDBEntity.class);
            System.out.println("[^_^:20200910-1639-001] insert sql: \n" + ZKInsertSqlConvert.convertSqlInsert(zkAp));

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

}
