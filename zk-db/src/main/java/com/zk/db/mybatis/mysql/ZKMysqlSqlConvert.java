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
* @Title: ZKMysqlSqlConvert.java 
* @author Vinson 
* @Package com.zk.db.commons.support 
* @Description: TODO(simple description this file what to do. ) 
* @date Sep 11, 2020 9:51:50 AM 
* @version V1.0 
*/
package com.zk.db.mybatis.mysql;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.core.commons.data.ZKOrder;
import com.zk.core.utils.ZKStringUtils;
import com.zk.db.annotation.ZKDBAnnotationProvider;
import com.zk.db.commons.ZKDBQueryCondition.ZKDBConditionLogicDispose;
import com.zk.db.commons.ZKDBQueryConditionWhere;
import com.zk.db.commons.ZKDBQueryLogic;
import com.zk.db.commons.ZKDBQueryType;
import com.zk.db.mybatis.commons.ZKSqlMybatisConvert;

/** 
* @ClassName: ZKMysqlSqlConvert 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKMysqlSqlConvert implements ZKSqlMybatisConvert {

    protected static Logger logger = LoggerFactory.getLogger(ZKMysqlSqlConvert.class);

    /********************************************************/
    /*** 一些 完整的 sql 语句；一般只要生成一次即可；所以就保存下来 **/
    /********************************************************/
    @Override
    public String convertSqlInsert(ZKDBAnnotationProvider annotationProvider) {
        return ZKInsertSqlConvert.convertSqlInsert(annotationProvider);
    }

    @Override
    public String convertSqlUpdate(ZKDBAnnotationProvider annotationProvider) {
        return ZKUpdateSqlConvert.convertSqlUpdate(annotationProvider);
    }

    @Override
    public String convertSqlDel(ZKDBAnnotationProvider annotationProvider, String delSetSql) {
        return ZKDelSqlConvert.convertSqlDel(annotationProvider, delSetSql);
    }

    @Override
    public String convertSqlDiskDel(ZKDBAnnotationProvider annotationProvider) {
        return ZKDelSqlConvert.convertSqlDiskDel(annotationProvider);
    }

    /********************************************************/
    /***  **/
    /********************************************************/

//    // 查询目标，带有 FROM
//    @Override
//    public String convertSqlSelFrom(ZKDBAnnotationProvider annotationProvider, String tableAlias) {
//        return ZKSelectSqlConvert.convertSqlSelFrom(annotationProvider, tableAlias);
//    }

    // 查询目标，不带有 FROM
    @Override
    public String convertSqlSelTable(ZKDBAnnotationProvider annotationProvider, String tableAlias) {
        return ZKSelectSqlConvert.convertSqlSelTable(annotationProvider, tableAlias);
    }

    // 查询结果映射
    @Override
    public String convertSqlSelCols(ZKDBAnnotationProvider annotationProvider, String tableAlias) {
        return ZKSelectSqlConvert.convertSqlSelCols(annotationProvider, tableAlias);
    }

    // 查询主键条件; 带有 WHERE
    @Override
    public String convertSqlPkWhere(ZKDBAnnotationProvider annotationProvider, String tableAlias) {
        return ZKSelectSqlConvert.convertSqlPkWhere(annotationProvider, tableAlias);
    }

    // 生成 查询条件 sql; 带有 WHERE
    @Override
    public String convertSqlWhere(ZKDBQueryConditionWhere where, String tableAlias) {
        StringBuffer sb = new StringBuffer();
        String alias = "";
        if (!ZKStringUtils.isEmpty(tableAlias)) {
            alias = tableAlias + ".";
        }
        where.toQueryCondition(this, sb, alias);
        if (sb.length() > 0) {
            return "<where>" + sb.toString() + "</where>";
        }
        return "";
    }

    // 这里将根据字段名映射到数据库字段；所以这里 ZKOrder 中填写的是 JAVA 实体字段名； 带有 ORDER BY
    @Override
    public String convertSqlOrderBy(ZKDBAnnotationProvider annotationProvider, Collection<ZKOrder> sorts,
            String tableAlias, boolean isDefault) {
        if (sorts == null || sorts.isEmpty()) {
            if (isDefault) {
                return ZKSelectSqlConvert.convertSqlOrderBy(annotationProvider, tableAlias);
            }
        }
        else {
            return ZKSelectSqlConvert.convertSqlOrderBy(annotationProvider, sorts, tableAlias);
        }
        return "";
    }

    /********************************************************/
    /***  **/
    /********************************************************/

    @Override
    public ZKDBQueryConditionWhere getWhere(ZKDBAnnotationProvider annotationProvider) {
        return ZKSelectSqlConvert.getWhere(annotationProvider);
    }

    @Override
    public void appendScriptQueryCondition(StringBuffer sb, String tableAlias, String columnName, String attrName,
            ZKDBQueryLogic queryLogic, ZKDBQueryType queryType, Class<?> javaClassz, String[] formats,
            boolean isCaseSensitive) {
        ZKSelectSqlConvert.appendScriptQueryCondition(sb, tableAlias, columnName, attrName, queryLogic, queryType,
                javaClassz, formats, isCaseSensitive);
    }

    @Override
    public void appendScriptQueryConditionIf(StringBuffer sb, String attrName, Class<?> javaClassz, boolean isEmpty,
            ZKDBConditionLogicDispose funcQueryLogicDispose) {
        ZKSelectSqlConvert.appendScriptQueryConditionIf(sb, attrName, javaClassz, isEmpty, funcQueryLogicDispose);
    }

}

