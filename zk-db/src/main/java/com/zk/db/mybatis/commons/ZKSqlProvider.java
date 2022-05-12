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
package com.zk.db.mybatis.commons;

import java.util.List;

import com.zk.core.commons.data.ZKOrder;
import com.zk.db.annotation.ZKDBAnnotationProvider;
import com.zk.db.annotation.ZKTable;
import com.zk.db.commons.ZKDBBaseEntity;
import com.zk.db.commons.ZKDBQueryConditionWhere;
import com.zk.db.commons.ZKSqlConvert;

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

        // 查询位置；带表别名
        this.sqlBlockSelTable = sqlConvert.convertSqlSelTable(annotationProvider, annotationProvider.getAlias()) + " ";
        // 查询结果映射；带表别名
        this.sqlBlockSelCols = sqlConvert.convertSqlSelCols(annotationProvider, annotationProvider.getAlias()) + " ";
        // 查询结果映射；不带表别名
        this.sqlBlockSelColsNoAlias = sqlConvert.convertSqlSelCols(annotationProvider, "");
        // 主键查询
        this.sqlBlockPkWhere = sqlConvert.convertSqlPkWhere(annotationProvider, annotationProvider.getAlias())
                + " ";
        // 查询条件
        this.sqlBlockWhere = sqlConvert.convertSqlWhere(this.where, annotationProvider.getAlias()) + " ";
        //
        this.sqlBlockOrderBy = sqlConvert.convertSqlOrderBy(annotationProvider, null, annotationProvider.getAlias(),
                true) + " ";

//        // 列表查询不包含排序的片段，使用时，再添加排序片段，生成的是 mybatis 脚本 sql
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
        this.sqlGet = "<script>SELECT " + this.sqlBlockSelCols + " FROM " + this.sqlBlockSelTable + " "
                + this.sqlBlockPkWhere + "</script>";
    }

    public ZKDBAnnotationProvider getAnnotationProvider() {
        return this.annotationProvider;
    }

    /**
     * @return sqlConvert sa
     */
    public ZKSqlConvert getSqlConvert() {
        return sqlConvert;
    }

    /*
     * 表信息 完成 sql sql 片段 其他sql生成过程数据对象
     */

    /********************************************************/
    /*** 表信息 **********************************************/
    /********************************************************/

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
    /*** 完成 sql ********************************************/
    /********************************************************/
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

    /********************************************************/
    /*** sql 片段 ********************************************/
    /********************************************************/
    // 查询位置; 带表别名；示列：t_test t1
    String sqlBlockSelTable;
    // 默认排序字段，带表别名
    String sqlBlockOrderBy;
    // 查询结果映射 不带表别名
    String sqlBlockSelCols;
    // 查询结果映射 带表别名
    String sqlBlockSelColsNoAlias;
    // 主键查询，带表别名
    String sqlBlockPkWhere;
    // 列表查询，带表别名
    String sqlBlockSelList;
    // 查询条件，带表别名
    String sqlBlockWhere;

    /********************************************************/
    /*** 其他sql生成过程数据对象 *****************************/
    /********************************************************/

    // 查询条件
    ZKDBQueryConditionWhere where;

    /********************************************************/
    /*** 一些 动态生成 sql 片段的方法 *****************************/
    /********************************************************/

    /*** 动态转换 *****************************/
    // 根据实体 转换 排序 sql 片段
    public String convertOrderBySql(ZKDBBaseEntity<?> entity) {
        if (entity.getPage() == null || entity.getPage().getSorters() == null
                || entity.getPage().getSorters().isEmpty()) {
            return this.sqlBlockOrderBy;
        }
        else {
            return sqlConvert.convertSqlOrderBy(annotationProvider, entity.getPage().getSorters(),
                    annotationProvider.getAlias(), true);
        }
    }

    /**
     * 自定义查询结果的表别名
     *
     * @Title: getSqlBlockSelCols
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Apr 15, 2022 9:34:43 AM
     * @param tableAlias
     * @return
     * @return String
     */
    public String getSqlBlockSelCols(String tableAlias) {
        return this.getSqlConvert().convertSqlSelCols(this.getAnnotationProvider(), tableAlias);
    }

    /**
     * 自定定义查询条件的表别名
     * 
     * 带有 WHERE
     *
     * @Title: getSqlBlockWhere
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Apr 15, 2022 9:35:05 AM
     * @param tableAlias
     * @return
     * @return String
     */
    public String getSqlBlockWhere(String tableAlias) {
        return this.getSqlConvert().convertSqlWhere(this.getWhere(), tableAlias);
    }

    /**
     * 转换成排序 sql，不带表别名
     * 
     * sorts 为空或为 null 时，会取默认排序片段
     * 
     * 带有 ORDER BY
     *
     * @Title: getSqlBlockOrder
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Apr 15, 2022 9:35:34 AM
     * @param sorts
     * @return
     * @return String
     */
    public String getSqlBlockOrder(List<ZKOrder> sorts) {
        return this.getSqlBlockOrder("", sorts);
    }

    /**
     * 转换成排序 sql，自定义
     * 
     * sorts 为空或为 null 时，会取默认排序片段
     * 
     * 表别名 带有 ORDER BY
     *
     * @Title: getSqlBlockOrder
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Apr 15, 2022 9:36:24 AM
     * @param tableAlias
     * @param sorts
     * @return
     * @return String
     */
    public String getSqlBlockOrder(String tableAlias, List<ZKOrder> sorts) {
        return this.getSqlBlockOrder(tableAlias, sorts, true);
    }

    /**
     * 转换成排序 sql，自定义
     * 
     * 带有 ORDER BY
     *
     * @Title: getSqlBlockOrder
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Apr 15, 2022 9:39:19 AM
     * @param tableAlias
     * @param sorts
     * @param isDefault
     *            true-sorts 为空或为 null 时，会取默认排序片段; false-sorts 为空或为 null 时，返回空窜；
     * @return
     * @return String
     */
    public String getSqlBlockOrder(String tableAlias, List<ZKOrder> sorts, boolean isDefault) {
        return this.getSqlConvert().convertSqlOrderBy(annotationProvider, sorts, tableAlias, isDefault);
    }

    /**
     * 生成主键查询条件，自定义表名
     * 
     * 带有 WHERE
     *
     * @Title: getSqlBlockPkWhere
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Apr 15, 2022 9:41:08 AM
     * @param tableAlias
     * @return
     * @return String
     */
    public String getSqlBlockPkWhere(String tableAlias) {
        return this.getSqlConvert().convertSqlPkWhere(annotationProvider, tableAlias);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
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
     * @return sqlBlockSelCols sa
     */
    public String getSqlBlockSelCols() {
        return sqlBlockSelCols;
    }

    /**
     * @return sqlBlockSelColsNoAlias sa
     */
    public String getSqlBlockSelColsNoAlias() {
        return sqlBlockSelColsNoAlias;
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
     * @return sqlBlockSelList sa
     */
    public String getSqlBlockSelList() {
        return sqlBlockSelList;
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

    /**
     * @return where sa
     */
    public ZKDBQueryConditionWhere getWhere() {
        return where;
    }

}
