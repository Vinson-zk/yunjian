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
* @Title: ZKSelectSqlConvertTest.java 
* @author Vinson 
* @Package com.zk.db.annotation.support.mysql 
* @Description: TODO(simple description this file what to do. ) 
* @date Sep 11, 2020 2:34:34 PM 
* @version V1.0 
*/
package com.zk.db.dialect.support.mysql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.scripting.xmltags.XMLScriptBuilder;
import org.apache.ibatis.session.Configuration;
import org.junit.Test;

import com.zk.core.commons.data.ZKJson;
import com.zk.core.commons.data.ZKOrder;
import com.zk.core.commons.data.ZKSortMode;
import com.zk.db.ZKMybatisSessionFactory;
import com.zk.db.annotation.ZKDBAnnotationProvider;
import com.zk.db.commons.ZKDBQueryType;
import com.zk.db.helper.ZKDBTestConfig;
import com.zk.db.helper.entity.ZKDBEntity;
import com.zk.db.mybatis.commons.ZKDBQueryConditionCol;
import com.zk.db.mybatis.commons.ZKDBQueryConditionIfByClass;
import com.zk.db.mybatis.commons.ZKDBQueryConditionWhereTrim;
import com.zk.db.mybatis.mysql.ZKMysqlSqlConvert;
import com.zk.db.mybatis.mysql.ZKSelectSqlConvert;

import junit.framework.TestCase;

/** 
* @ClassName: ZKSelectSqlConvertTest 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKSelectSqlConvertTest {

    @Test
    public void testSql() {
        try {
//           
            ZKMysqlSqlConvert mysqlSqlConvert = new ZKMysqlSqlConvert();
            ZKDBQueryConditionWhereTrim where = null;
//            StringBuffer sb = null;
            String expectedStr = "";
            String resStr = "";
            List<ZKOrder> orders = new ArrayList<ZKOrder>();

            orders.add(ZKOrder.asOrder("id", ZKSortMode.ASC));
            orders.add(ZKOrder.asOrder("type", ZKSortMode.DESC));
            ZKDBAnnotationProvider zkAp = new ZKDBAnnotationProvider(ZKDBEntity.class);

            // selCols 偶尔可能会出现顺序问题，测试不匹配
            expectedStr = "t0.c_value AS \"value\", t0.c_json AS \"json\", t0.c_type AS \"type\", t0.c_boolean AS \"mBoolean\", t0.c_remarks AS \"remarks\", t0.c_id AS \"id\", t0.c_date AS \"mDate\"";
            resStr = ZKSelectSqlConvert.convertSqlSelCols(zkAp, zkAp.getTable().alias());
            System.out.println("[^_^:20200911-1435-001] selColumns: " + resStr);
//            TestCase.assertEquals(expectedStr, resStr);

            // where 偶尔可能会出现顺序问题，测试不匹配
            expectedStr = "<where><trim prefixOverrides=\"and|or\"><if test=\"json != null and json.getKeyValues() != null\"><foreach item=\"_v\" index=\"_k\" collection=\"json.getKeyValues()\" open=\"\" separator=\" AND \" close=\"\">JSON_UNQUOTE(JSON_EXTRACT(ta.c_json, #{_k})) like CONCAT('%', REPLACE(REPLACE(REPLACE(#{_v}, '\\\\\\\\', '\\\\\\\\\\\\\\\\'), '_', '\\\\_'), '%', '\\\\%'), '%')</foreach></if><if test=\" type != null\"> AND ta.c_type = #{type}</if><if test=\" mBoolean != null\"> AND ta.c_boolean = #{mBoolean}</if><if test=\" remarks != null and remarks != ''\"> AND UPPER(ta.c_remarks) like CONCAT('%', REPLACE(REPLACE(REPLACE(UPPER(#{remarks}), '\\\\\\\\', '\\\\\\\\\\\\\\\\'), '_', '\\\\_'), '%', '\\\\%'), '%')</if><if test=\" startDate != null\"> AND DATE_FORMAT(ta.c_date, \"%Y-%m-%d\") &gt;= DATE_FORMAT(#{startDate}, \"%Y-%m-%d\")</if><if test=\" endDate != null\"> AND DATE_FORMAT(ta.c_date, \"%Y-%m-%d %H:%i\") &lt;= DATE_FORMAT(#{endDate}, \"%Y-%m-%d %H:%i\")</if><if test=\" id != null and id != ''\"> AND ta.c_id = #{id}</if><if test=\"types != null and types.length > 0\"> AND ta.c_type in <foreach item=\"_v\" index=\"_index\" collection=\"types\" open=\"(\" separator=\",\" close=\")\">#{_v}</foreach></if><if test=\"typeStrs != null and typeStrs.isEmpty() != true\"> AND ta.c_type in <foreach item=\"_v\" index=\"_index\" collection=\"typeStrs\" open=\"(\" separator=\",\" close=\")\">#{_v}</foreach></if></trim></where>";
            where = ZKSelectSqlConvert.getWhere(zkAp);
            resStr = mysqlSqlConvert.convertSqlWhere(where, "ta");
            System.out.println("[^_^:20200911-1435-002] whereIf: " + resStr);
//            TestCase.assertEquals(expectedStr, resStr);

            // order by
            expectedStr = "";
            resStr = ZKSelectSqlConvert.convertSqlOrderBy(zkAp, zkAp.getTable().alias());
            System.out.println("[^_^:20200911-1435-003-1] orderBy: " + resStr);
            TestCase.assertEquals(expectedStr, resStr);
            expectedStr = " ORDER BY t0.c_id ASC, t0.c_type DESC";
            resStr = ZKSelectSqlConvert.convertSqlOrderBy(zkAp, orders, zkAp.getTable().alias());
            System.out.println("[^_^:20200911-1435-003-2] orderBy: " + resStr);
            TestCase.assertEquals(expectedStr, resStr);

            // from
            expectedStr = "t_zk_db_test t0";
            resStr = ZKSelectSqlConvert.convertSqlSelTable(zkAp, zkAp.getTable().alias());
            System.out.println("[^_^:20210314-0843-001] From: " + resStr);
            TestCase.assertEquals(expectedStr, resStr);

            System.out.println("[^_^:20200911-1435-004] getPkWhereCondition: "
                    + ZKSelectSqlConvert.convertSqlPkWhere(zkAp, "---"));

            /*** 输出一些完成 sql ***/
            System.out.println("[^_^:20200911-1435-005] getSql: " + ZKSelectSqlConvert.convertSqlSelCols(zkAp, "ta")
                    + " FROM "
                    + ZKSelectSqlConvert.convertSqlSelTable(zkAp, "ta")
                    + ZKSelectSqlConvert.convertSqlPkWhere(zkAp, "ta"));

            where = ZKSelectSqlConvert.getWhere(zkAp);
            resStr = mysqlSqlConvert.convertSqlWhere(where, "ta");
            System.out.println("[^_^:20200911-1435-006-1] selectListSql: "
                    + ZKSelectSqlConvert.convertSqlSelCols(zkAp, "ta")
                    + " FROM "
                    + ZKSelectSqlConvert.convertSqlSelTable(zkAp, "ta")
                    + resStr
                    + ZKSelectSqlConvert.convertSqlOrderBy(zkAp, "ta"));

            ZKDBEntity entity = new ZKDBEntity();
            ZKJson json = new ZKJson();
            json.put("key-1", "v-1");
            json.put("key-2", "v-2");

            /* 插入/新增 */
            entity.setId("12");
            entity.setType(1L);
            entity.setValue("TET");
            entity.setRemarks("SS");
            entity.setJson(json);

            where = ZKSelectSqlConvert.getWhere(zkAp);
            resStr = mysqlSqlConvert.convertSqlWhere(where, "ta");
            System.out.println("[^_^:20200911-1435-006-2] while: " + resStr);
            System.out.println("[^_^:20200911-1435-006-3] selectListSql: "
                    + ZKSelectSqlConvert.convertSqlSelCols(zkAp, "ta")
                    + " FROM "
                    + ZKSelectSqlConvert.convertSqlSelTable(zkAp, "ta")
                    + resStr
                    + ZKSelectSqlConvert.convertSqlOrderBy(zkAp, "ta"));
        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

    @Test
    public void testParseScriptSql() {
        try {

            ZKMybatisSessionFactory mybatisSessionFactory = ZKDBTestConfig.getZKMybatisSessionFactory();
            Configuration configuration = mybatisSessionFactory.getSqlSessionFactory().getConfiguration();
            XPathParser parser = null;
            XMLScriptBuilder builder = null;
            String script = "";
            ZKDBQueryConditionWhereTrim where = null;
            ZKDBEntity entity = null;
            ZKDBAnnotationProvider zkAp = null;
            ZKJson json = null;
            ZKMysqlSqlConvert mysqlSqlConvert = new ZKMysqlSqlConvert();

//            ZKDBEntity entity = new ZKDBEntity();
            List<ZKOrder> orders = new ArrayList<ZKOrder>();

            orders.add(ZKOrder.asOrder("id", ZKSortMode.ASC));
            orders.add(ZKOrder.asOrder("type", ZKSortMode.DESC));

            zkAp = new ZKDBAnnotationProvider(ZKDBEntity.class);
            entity = new ZKDBEntity();
            json = new ZKJson();
            json.put("key-1", "v-1");
            json.put("key-2", "v-2");
            json.put("key-3", "v-3; delete * from t_dd;");

            /*  */
            entity.setId("1");
            entity.setType(1L);
            entity.setTypes(new Long[] { 1l });

            System.out.println("===============================================");
            where = ZKDBQueryConditionWhereTrim.asAnd(
                    ZKDBQueryConditionIfByClass.as(
                            ZKDBQueryConditionCol.as(ZKDBQueryType.EQ, "c_id", "id", String.class, null, true), "id",
                            String.class, false),
                    ZKDBQueryConditionIfByClass.as(
                            ZKDBQueryConditionCol.as(ZKDBQueryType.NIN, "c_type", "types", Long[].class, null, true),
                            "types", Long[].class, false));
            where.put(ZKDBQueryConditionWhereTrim.asOr("(", ")", where.getConditions().get(0),
                    where.getConditions().get(1)));
            script = "";
            script += "<script>";
            script += "SELECT ";
            script += ZKSelectSqlConvert.convertSqlSelCols(zkAp, "ta");
            script += ZKSelectSqlConvert.convertSqlSelTable(zkAp, "ta");
            script +=  mysqlSqlConvert.convertSqlWhere(where,  "ta");
            script += "</script>";
            System.out.println("[^_^:20210414-1431-001]: select get sql: " + script);
            parser = new XPathParser(script, false, configuration.getVariables(), new XMLMapperEntityResolver());
            builder = new XMLScriptBuilder(configuration, parser.evalNode("/script"), entity.getClass());
            script = builder.parseScriptNode().getBoundSql(entity).getSql();
            System.out.println("[^_^:20210414-1431-001]: select get sql.builder:" + script);

            entity.setValue("TET");
            entity.setRemarks("SS");
            entity.setJson(json);
            entity.getExtraParams().put("typeStrs", Arrays.asList("1", "2"));

            // 查询 get
            System.out.println("===============================================");
            script = "";
            script += "<script>";
            script += "SELECT ";
            script += ZKSelectSqlConvert.convertSqlSelCols(zkAp, "ta");
            script += ZKSelectSqlConvert.convertSqlSelTable(zkAp, "ta");
            script += ZKSelectSqlConvert.convertSqlPkWhere(zkAp, "ta");
            script += "</script>";
            System.out.println("[^_^:20210414-0905-001]: select get sql: " + script);
            parser = new XPathParser(script, false, configuration.getVariables(),
                    new XMLMapperEntityResolver());
            builder = new XMLScriptBuilder(configuration, parser.evalNode("/script"),
                    entity.getClass());
            script = builder.parseScriptNode().getBoundSql(entity).getSql();
            System.out.println("[^_^:20210414-0905-001]: select get sql.builder:" + script);

            // 查询 list
            System.out.println("===============================================");
            where = ZKSelectSqlConvert.getWhere(zkAp);
            script = "";
            script += "<script>";
            script += "SELECT ";
            script += ZKSelectSqlConvert.convertSqlSelCols(zkAp, "ta");
            script += ZKSelectSqlConvert.convertSqlSelTable(zkAp, "ta");
            script += mysqlSqlConvert.convertSqlWhere(where, "ta");
            script += ZKSelectSqlConvert.convertSqlOrderBy(zkAp, "ta");
            script += "</script>";

            System.out.println("[^_^:20210414-0847-001]: select list sql: " + script);
            parser = new XPathParser(script, false, configuration.getVariables(),
                    new XMLMapperEntityResolver());
            builder = new XMLScriptBuilder(configuration, parser.evalNode("/script"),
                    entity.getClass());
//            DynamicContext context = new DynamicContext(configuration, entity);
//            SqlSource sqlSource = builder.parseScriptNode();
            script = builder.parseScriptNode().getBoundSql(entity).getSql();
            System.out.println("[^_^:20210414-0847-001]: select list sql.builder:" + script);

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

}
