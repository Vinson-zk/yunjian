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
* @Title: ZKDBQueryConditionSql.java 
* @author Vinson 
* @Package com.zk.db.commons 
* @Description: TODO(simple description this file what to do. ) 
* @date Apr 15, 2021 8:36:58 AM 
* @version V1.0 
*/
package com.zk.db.commons;

/** 
* @ClassName: ZKDBQueryConditionSql 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKDBQueryConditionSql implements ZKDBQueryCondition {

    @Override
    public void toQueryCondition(ZKSqlConvert convert, StringBuffer sb, String tableAlias,
            ZKDBConditionLogicDispose funcQueryLogicDispose) {
        funcQueryLogicDispose.run();
        sb.append(tableAlias);
        sb.append(columnName);
        sb.append(queryType.getEsc());
        sb.append(conditionSql);
    }

    public static ZKDBQueryConditionSql as(ZKDBQueryType queryType, String columnName, String conditionSql) {
        return new ZKDBQueryConditionSql(queryType, columnName, conditionSql);
    }

    public ZKDBQueryConditionSql(ZKDBQueryType queryType, String columnName, String conditionSql) {
        this.queryType = queryType;
        this.columnName = columnName;
        this.conditionSql = conditionSql;
    }

    // 条件字段名
    private String columnName;

    // 查询方式
    private ZKDBQueryType queryType;

    // 条件语句
    private String conditionSql;




}
