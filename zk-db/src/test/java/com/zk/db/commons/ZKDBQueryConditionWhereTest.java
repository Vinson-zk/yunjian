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
* @Title: ZKDBQueryConditionWhereTest.java 
* @author Vinson 
* @Package com.zk.db.commons 
* @Description: TODO(simple description this file what to do. ) 
* @date Apr 15, 2021 11:49:10 AM 
* @version V1.0 
*/
package com.zk.db.commons;

import java.util.Date;

import org.junit.Test;

import com.zk.db.mybatis.commons.ZKDBQueryConditionCol;
import com.zk.db.mybatis.commons.ZKDBQueryConditionWhereTrim;
import com.zk.db.mybatis.mysql.ZKMysqlSqlConvert;

import junit.framework.TestCase;

/** 
* @ClassName: ZKDBQueryConditionWhereTest 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKDBQueryConditionWhereTest {

    @Test
    public void test() {

        try {

            ZKMysqlSqlConvert convert = new ZKMysqlSqlConvert();
            ZKDBQueryConditionWhere where = null;
            String expectStr, resStr;
            StringBuffer sb = null;

            where = ZKDBQueryConditionWhere.asAnd(
                    ZKDBQueryConditionCol.as(ZKDBQueryType.EQ, "c_column", "attrName", String.class, null, true),
                    ZKDBQueryConditionCol.as(ZKDBQueryType.EQ, "c_column", "attrName", String.class, null, true));

            where.put(ZKDBQueryConditionWhere.asOr("(", ")",
                    ZKDBQueryConditionCol.as(ZKDBQueryType.EQ, "c_column", "attrName", Date.class, null, true),
                    ZKDBQueryConditionCol.as(ZKDBQueryType.EQ, "c_column", "attrName", Date.class, null, true)));

            sb = new StringBuffer();
            where.toQueryCondition(convert, sb, "t_Alias.");
            resStr = sb.toString();
            System.out.println(resStr);
            expectStr = "t_Alias.c_column = #{attrName} AND t_Alias.c_column = #{attrName} AND (t_Alias.c_column = #{attrName} OR t_Alias.c_column = #{attrName})";
            TestCase.assertEquals(expectStr, resStr);

            where = ZKDBQueryConditionWhereTrim.asAnd(
                    ZKDBQueryConditionCol.as(ZKDBQueryType.EQ, "c_column", "attrName", String.class, null, true),
                    ZKDBQueryConditionCol.as(ZKDBQueryType.EQ, "c_column", "attrName", String.class, null, true));

            where.put(ZKDBQueryConditionWhereTrim.asOr("(", ")",
                    ZKDBQueryConditionCol.as(ZKDBQueryType.EQ, "c_column", "attrName", Date.class, null, true),
                    ZKDBQueryConditionCol.as(ZKDBQueryType.EQ, "c_column", "attrName", Date.class, null, true)));

            sb = new StringBuffer();
            where.toQueryCondition(convert, sb, "t_Alias.");
            resStr = sb.toString();
            System.out.println(resStr);
            expectStr = "<trim prefixOverrides=\"and|or\">t_Alias.c_column = #{attrName} AND t_Alias.c_column = #{attrName} AND (<trim prefixOverrides=\"and|or\">t_Alias.c_column = #{attrName} OR t_Alias.c_column = #{attrName}</trim>)</trim>";
            TestCase.assertEquals(expectStr, resStr);

            where = ZKDBQueryConditionWhereTrim.asAnd(
                    ZKDBQueryConditionCol.as(ZKDBQueryType.EQ, "c_column", "attrName", String.class, null, false),
                    ZKDBQueryConditionCol.as(ZKDBQueryType.LIKE, "c_column", "attrName", String.class, null, true),
                    ZKDBQueryConditionCol.as(ZKDBQueryType.GT, "c_column", "attrName", Date.class,
                            new String[] { "sqlF" }, true));

            where.put(ZKDBQueryConditionWhere.asOr(
                    ZKDBQueryConditionCol.as(ZKDBQueryType.EQ, "c_column", "attrName", Date.class, null, true),
                    ZKDBQueryConditionCol.as(ZKDBQueryType.LIKE, "c_column", "attrName", String.class, null, false),
                    ZKDBQueryConditionCol.as(ZKDBQueryType.GT, "c_column", "attrName", Date.class,
                            new String[] { "", null }, true)));

            sb = new StringBuffer();
            where.toQueryCondition(convert, sb, "t_Alias.");
            resStr = sb.toString();
            System.out.println(resStr);
            expectStr = "<trim prefixOverrides=\"and|or\">UPPER(t_Alias.c_column) = UPPER(#{attrName}) AND t_Alias.c_column like CONCAT('%', REPLACE(REPLACE(REPLACE(#{attrName}, '\\\\\\\\', '\\\\\\\\\\\\\\\\'), '_', '\\\\_'), '%', '\\\\%'), '%') AND DATE_FORMAT(t_Alias.c_column, \"sqlF\") &gt; DATE_FORMAT(#{attrName}, \"sqlF\") AND t_Alias.c_column = #{attrName} OR UPPER(t_Alias.c_column) like CONCAT('%', REPLACE(REPLACE(REPLACE(UPPER(#{attrName}), '\\\\\\\\', '\\\\\\\\\\\\\\\\'), '_', '\\\\_'), '%', '\\\\%'), '%') OR DATE_FORMAT(t_Alias.c_column, \"\") &gt; DATE_FORMAT(#{attrName}, \"\")</trim>";
            TestCase.assertEquals(expectStr, resStr);
        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }

    }

}
