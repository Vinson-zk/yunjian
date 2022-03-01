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
* @Title: ZKMybatisScriptSqlParse.java 
* @author Vinson 
* @Package com.zk.db.mybatis 
* @Description: TODO(simple description this file what to do. ) 
* @date Apr 15, 2021 11:50:25 AM 
* @version V1.0 
*/
package com.zk.db.mybatis;

import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.scripting.xmltags.XMLScriptBuilder;
import org.apache.ibatis.session.Configuration;
import org.junit.Test;

import com.zk.db.ZKMybatisSessionFactory;
import com.zk.db.helper.ZKDBTestConfig;
import com.zk.db.helper.entity.ZKDBEntity;

import junit.framework.TestCase;

/**
 * @ClassName: ZKMybatisScriptSqlParseTest
 * @Description: TODO(simple description this class what to do. )
 * @author Vinson
 * @version 1.0
 */
public class ZKMybatisScriptSqlParseTest {

    @Test
    public void testScriptSqlParse() {
        try {

            ZKMybatisSessionFactory mybatisSessionFactory = ZKDBTestConfig.getZKMybatisSessionFactory();
            Configuration configuration = mybatisSessionFactory.getSqlSessionFactory().getConfiguration();
            XPathParser parser = null;
            XMLScriptBuilder builder = null;
            String script = "";

            ZKDBEntity entity = new ZKDBEntity();
            entity.setId(null);
            entity.setType(1L);

            System.out.println("===============================================");
            script = "";
            script += "<script>";
            script += "SELECT ";
            script += "ta.* ";
            script += "FROM ta ta <where> AND ";
            script += "(<trim prefix=\"\" prefixOverrides=\"and|or\">";
            script += "<if test=\"id!=null\">AND id=#{id}</if>";
            script += "<if test=\"type!=null\">AND type=#{type}</if>";
            script += " AND (";
            script += "<trim prefixOverrides=\"AND|OR\">";
            script += "OR id=#{id} OR type=#{type}";
            script += "</trim>)";
            script += "</trim>)";
            script += "</where></script>";
            System.out.println("[^_^:20210414-1920-001]: select get sql: " + script);
            parser = new XPathParser(script, false, configuration.getVariables(), new XMLMapperEntityResolver());
            builder = new XMLScriptBuilder(configuration, parser.evalNode("/script"), entity.getClass());
            script = builder.parseScriptNode().getBoundSql(entity).getSql();
            System.out.println("[^_^:20210414-1920-001]: select get sql.builder:" + script);

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

}
