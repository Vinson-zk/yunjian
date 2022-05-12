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
* @Title: ZKSqlConvert.java 
* @author Vinson 
* @Package com.zk.db.commons 
* @Description: TODO(simple description this file what to do. ) 
* @date Sep 11, 2020 9:45:49 AM 
* @version V1.0 
*/
package com.zk.db.commons;

import java.util.Collection;

import com.zk.core.commons.data.ZKOrder;
import com.zk.db.annotation.ZKDBAnnotationProvider;

/** 
* @ClassName: ZKSqlConvert 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public interface ZKSqlConvert {

    /********************************************************/
    /*** 一些 完整的 sql 语句；一般只要生成一次即可；所以就保存下来 **/
    /********************************************************/
    // insert sql
    public String convertSqlInsert(ZKDBAnnotationProvider annotationProvider);

    // update sql
    public String convertSqlUpdate(ZKDBAnnotationProvider annotationProvider);

    // 逻辑删除 sql，逻辑删除语句，实体中定义
    public String convertSqlDel(ZKDBAnnotationProvider annotationProvider, String delSetSql);

    // 物理删除 sql
    public String convertSqlDiskDel(ZKDBAnnotationProvider annotationProvider);

    /********************************************************/
    /***  **/
    /********************************************************/

//    // 查询目标，带有 FROM
//    public String convertSqlSelFrom(ZKDBAnnotationProvider annotationProvider, String tableAlias);

    // 查询目标，不带有 FROM
    public String convertSqlSelTable(ZKDBAnnotationProvider annotationProvider, String tableAlias);
    
    // 查询结果映射
    public String convertSqlSelCols(ZKDBAnnotationProvider annotationProvider, String tableAlias);

    // 查询主键条件; 带有 WHERE
    public String convertSqlPkWhere(ZKDBAnnotationProvider annotationProvider, String tableAlias);

    // 生成 查询条件 sql; 带有 WHERE
    public String convertSqlWhere(ZKDBQueryConditionWhere where, String tableAlias);

    // 这里将根据字段名映射到数据库字段；所以这里 ZKOrder 中填写的是 JAVA 实体字段名； 带有 ORDER BY
    public String convertSqlOrderBy(ZKDBAnnotationProvider annotationProvider, Collection<ZKOrder> sorts,
            String tableAlias, boolean isDefault);

    /********************************************************/
    /***  **/
    /********************************************************/

    // 生成 查询 where
    public ZKDBQueryConditionWhere getWhere(ZKDBAnnotationProvider annotationProvider);


}
