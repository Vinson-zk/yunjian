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
* @Title: ZKSqlConvertDelegating.java 
* @author Vinson 
* @Package com.zk.db.annotation 
* @Description: TODO(simple description this file what to do. ) 
* @date Sep 11, 2020 9:58:16 AM 
* @version V1.0 
*/
package com.zk.db.commons;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.core.commons.data.ZKOrder;
import com.zk.core.utils.ZKEnvironmentUtils;
import com.zk.core.utils.ZKStringUtils;
import com.zk.db.annotation.ZKDBAnnotationProvider;
import com.zk.db.mybatis.ZKMybatisSqlHelper.envKey;
import com.zk.db.mybatis.mysql.ZKMysqlSqlConvert;

/** 
* @ClassName: ZKSqlConvertDelegating 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKSqlConvertDelegating implements ZKSqlConvert {

    protected Logger log = LoggerFactory.getLogger(getClass());

    private ZKSqlConvert sqlConvert;

    public ZKSqlConvertDelegating() {
        this(null);
    }

    public ZKSqlConvertDelegating(String dbType) {
        if (ZKStringUtils.isEmpty(dbType)) {
            dbType = ZKEnvironmentUtils.getString(envKey.jdbcType, ZKDBConstants.defaultJdbcType);
        }
        if ("mysql".equals(dbType)) {
            sqlConvert = new ZKMysqlSqlConvert();
        }

        if (sqlConvert == null) {
            log.error("[>_<:20200911-0915-001] sqlConvert 初始化失败，不支持的数据库：{};", dbType);
        }
    }

    protected static Logger logger = LoggerFactory.getLogger(ZKMysqlSqlConvert.class);

    /********************************************************/
    /*** 一些 完整的 sql 语句；一般只要生成一次即可；所以就保存下来 **/
    /********************************************************/
    @Override
    public String convertSqlInsert(ZKDBAnnotationProvider annotationProvider) {
        return sqlConvert.convertSqlInsert(annotationProvider);
    }

    @Override
    public String convertSqlUpdate(ZKDBAnnotationProvider annotationProvider) {
        return sqlConvert.convertSqlUpdate(annotationProvider);
    }

    @Override
    public String convertSqlDel(ZKDBAnnotationProvider annotationProvider, String delSetSql) {
        return sqlConvert.convertSqlDel(annotationProvider, delSetSql);
    }

    @Override
    public String convertSqlDiskDel(ZKDBAnnotationProvider annotationProvider) {
        return sqlConvert.convertSqlDiskDel(annotationProvider);
    }

    /********************************************************/
    /***  **/
    /********************************************************/

//    // 查询目标，带有 FROM
//    public String convertSqlFrom(ZKDBAnnotationProvider annotationProvider, String tableAlias) {
//        return sqlConvert.convertSqlFrom(annotationProvider, tableAlias);
//    }

    // 查询目标，不带有 FROM
    @Override
    public String convertSqlSelTable(ZKDBAnnotationProvider annotationProvider, String tableAlias) {
        return sqlConvert.convertSqlSelTable(annotationProvider, tableAlias);
    }

    // 查询结果映射
    @Override
    public String convertSqlSelCols(ZKDBAnnotationProvider annotationProvider, String tableAlias) {
        return sqlConvert.convertSqlSelCols(annotationProvider, tableAlias);
    }

    // 查询主键条件; 带有 WHERE
    @Override
    public String convertSqlPkWhere(ZKDBAnnotationProvider annotationProvider, String tableAlias) {
        return sqlConvert.convertSqlPkWhere(annotationProvider, tableAlias);
    }

    // 生成 查询条件 sql; 带有 WHERE
    @Override
    public String convertSqlWhere(ZKDBQueryConditionWhere where, String tableAlias) {
        return sqlConvert.convertSqlWhere(where, tableAlias);
    }

    // 这里将根据字段名映射到数据库字段；所以这里 ZKOrder 中填写的是 JAVA 实体字段名； 带有 ORDER BY
    @Override
    public String convertSqlOrderBy(ZKDBAnnotationProvider annotationProvider, Collection<ZKOrder> sorts,
            String tableAlias, boolean isDefault) {
        return sqlConvert.convertSqlOrderBy(annotationProvider, sorts, tableAlias, isDefault);
    }

    /********************************************************/
    /***  **/
    /********************************************************/

    @Override
    public ZKDBQueryConditionWhere getWhere(ZKDBAnnotationProvider annotationProvider) {
        return sqlConvert.getWhere(annotationProvider);
    }

}
