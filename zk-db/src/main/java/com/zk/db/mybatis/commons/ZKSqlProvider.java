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
* @Title: ZKSqlProvider.java 
* @author Vinson 
* @Package com.zk.db.commons 
* @Description: TODO(simple description this file what to do. ) 
* @date Apr 14, 2021 9:12:54 AM 
* @version V1.0 
*/
package com.zk.db.commons;

import java.util.List;

import com.zk.core.commons.data.ZKOrder;
import com.zk.db.annotation.ZKDBAnnotationProvider;
import com.zk.db.annotation.ZKTable;

/** 
* @ClassName: ZKSqlProvider 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKSqlProvider {

    ZKDBAnnotationProvider annotationProvider;

    ZKSqlConvert sqlConvert;

    public ZKSqlProvider(ZKSqlConvert sqlConvert, ZKDBBaseEntity<?> entity) {
        this.annotationProvider = new ZKDBAnnotationProvider(entity.getClass());
        this.sqlConvert = sqlConvert;
        this.init(entity);
    }
    
    public void init(ZKDBBaseEntity<?> entity) {
        /********************************************************/
        this.where = entity.getZKDbWhere(sqlConvert, annotationProvider);

        /********************************************************/

        // 查询位置
        this.sqlBlockSelTable = entity.getZKDbStrSelTable(sqlConvert, annotationProvider) + " ";
        // 查询结果映射
        this.sqlBlockSelCols = entity.getZKDbStrSelCols(sqlConvert, annotationProvider) + " ";
        // 主键查询
        this.sqlBlockPkWhere = sqlConvert.convertSqlPkWhere(annotationProvider, annotationProvider.getTable().alias())
                + " ";
        // 查询条件
        this.sqlBlockWhere = sqlConvert.convertSqlWhere(this.where, annotationProvider.getTable().alias()) + " ";
        //
        this.sqlBlockOrderBy = entity.getZKDbStrSelOrderBy(sqlConvert, annotationProvider) + " ";
        // 列表查询
        this.sqlBlockSelList = "SELECT " + this.sqlBlockSelCols + " FROM " + this.sqlBlockSelTable + this.sqlBlockWhere;

        /********************************************************/

//        // 默认排序字段
//        this.sqlBlockOrderBy = sqlConvert.convertSqlOrderBy(annotationProvider, null,
//                annotationProvider.getTable().alias(), true);
        // 插入语句
        this.sqlInsert = sqlConvert.convertSqlInsert(annotationProvider);
        // 修改语句
        this.sqlUpdate = sqlConvert.convertSqlUpdate(annotationProvider);
        // 逻辑删除
        this.sqlDel = sqlConvert.convertSqlDel(annotationProvider, entity.getZKDbDelSetSql());
        // 物理删除
        this.sqlDiskDel = sqlConvert.convertSqlDiskDel(annotationProvider);
        // 查询
        this.sqlGet = "<script>SELECT " + this.sqlBlockSelCols + " FROM " + this.sqlBlockSelTable + " " + this.sqlBlockPkWhere + "</script>";
    }

    /*** 动态转换 *****************************/
    // 转换 排序 sql 片段
    public String convertOrderBySql(ZKDBBaseEntity<?> entity) {
        if (entity.getPage() == null || entity.getPage().getSorters() == null
                || entity.getPage().getSorters().isEmpty()) {
            return this.sqlBlockOrderBy;
        }
        else {
            return sqlConvert.convertSqlOrderBy(annotationProvider, entity.getPage().getSorters(),
                    annotationProvider.getTable().alias(), true);
        }
    }

    /********************************************************/
    /***  *****************************/
    /********************************************************/

    public ZKDBAnnotationProvider getAnnotationProvider() {
        return this.annotationProvider;
    }

    /**
     * @return sqlConvert sa
     */
    public ZKSqlConvert getSqlConvert() {
        return sqlConvert;
    }

    public ZKTable getTable() {
        return this.getAnnotationProvider().getTable();
    }

    public String getTableName() {
        return this.getAnnotationProvider().getTable().name();
    }

    public String getTableAlias() {
        return this.getAnnotationProvider().getTable().alias();
    }

    /********************************************************/
    /*** 一些 动态生成 sql 片段的方法 *****************************/
    /********************************************************/

    public String getSqlBlockSelCols(String tableAlias) {
        return this.sqlConvert.convertSqlSelCols(this.getAnnotationProvider(), tableAlias);
    }

    // 带有 WHERE
    public String getSqlBlockWhere(String tableAlias) {
        return this.sqlConvert.convertSqlWhere(this.getWhere(), tableAlias);
    }

    // 带有 ORDER BY
    public String getSqlBlockOrder(List<ZKOrder> sorts) {
        return this.getSqlBlockOrder("", sorts);
    }

    // 带有 ORDER BY
    public String getSqlBlockOrder(String tableAlias, List<ZKOrder> sorts) {
        return this.getSqlBlockOrder(tableAlias, sorts, true);
    }

    // 带有 ORDER BY
    public String getSqlBlockOrder(String tableAlias, List<ZKOrder> sorts, boolean isDefault) {
        return this.getSqlConvert().convertSqlOrderBy(annotationProvider, sorts, tableAlias, isDefault);
    }

    // 带有 WHERE
    public String getSqlBlockPkWhere(String tableAlias) {
        return this.getSqlConvert().convertSqlPkWhere(annotationProvider, tableAlias);
    }



    /*** 完成 sql *****************************/
    // 插入语句
    String sqlInsert;

    // 修改语句
    String sqlUpdate;

    // 逻辑删除
    String sqlDel;

    // 物理删除
    String sqlDiskDel;

    // 查询
    String sqlGet;

    /***   *****************************/
    // 查询条件
    ZKDBQueryConditionWhere where;

    /*** sql 片段 *****************************/
    // 查询结果映射
    String sqlBlockSelCols;

    // 查询位置
    String sqlBlockSelTable;

    // 列表查询
    String sqlBlockSelList;

    // 主键查询
    String sqlBlockPkWhere;

    // 查询条件
    String sqlBlockWhere;

    // 默认排序字段
    String sqlBlockOrderBy;

    /**
     * @return sqlInsert sa
     */
    public String getSqlInsert() {
        return sqlInsert;
    }

    /**
     * @return sqlUpdate sa
     */
    public String getSqlUpdate() {
        return sqlUpdate;
    }

    /**
     * @return sqlDel sa
     */
    public String getSqlDel() {
        return sqlDel;
    }

    /**
     * @return sqlDiskDel sa
     */
    public String getSqlDiskDel() {
        return sqlDiskDel;
    }

    /**
     * @return sqlGet sa
     */
    public String getSqlGet() {
        return sqlGet;
    }

    /**
     * @return sqlBlockSelList sa
     */
    public String getSqlBlockSelList() {
        return sqlBlockSelList;
    }

    /**
     * @return where sa
     */
    public ZKDBQueryConditionWhere getWhere() {
        return where;
    }

    /**
     * @return sqlBlockSelCols sa
     */
    public String getSqlBlockSelCols() {
        return sqlBlockSelCols;
    }

    /**
     * @return sqlBlockSelTable sa
     */
    public String getSqlBlockSelTable() {
        return sqlBlockSelTable;
    }

    /**
     * @return sqlBlockPkWhere sa
     */
    public String getSqlBlockPkWhere() {
        return sqlBlockPkWhere;
    }

    /**
     * @return sqlBlockWhere sa
     */
    public String getSqlBlockWhere() {
        return sqlBlockWhere;
    }

    /**
     * @return sqlBlockOrderBy sa
     */
    public String getSqlBlockOrderBy() {
        return sqlBlockOrderBy;
    }

}
