/** 
 e* Copyright (c) 2004-2020 ZK-Vinson Technologies, Inc.
* address: 
* All rights reserved. 
* 
* This software is the confidential and proprietary information of 
* ZK-Vinson Technologies, Inc. ("Confidential Information"). You shall not 
* disclose such Confidential Information and shall use it only in 
* accordance with the terms of the license agreement you entered into 
* with ZK-Vinson. 
*
* @Title: ZKSqlMybatisConvert.java 
* @author Vinson 
* @Package com.zk.db.mybatis.commons 
* @Description: TODO(simple description this file what to do. ) 
* @date Apr 15, 2021 10:45:58 AM 
* @version V1.0 
*/
package com.zk.db.mybatis.commons;

import com.zk.db.commons.ZKDBQueryCondition.ZKDBConditionLogicDispose;
import com.zk.db.commons.ZKDBQueryLogic;
import com.zk.db.commons.ZKDBQueryType;
import com.zk.db.commons.ZKSqlConvert;

/** 
* @ClassName: ZKSqlMybatisConvert 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public interface ZKSqlMybatisConvert extends ZKSqlConvert {

    /**
     * 
     *
     * @Title: appendScriptQueryCondition
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Apr 15, 2021 10:51:11 AM
     * @param sb
     * @param tableAlias
     *            表的别名；这里需要加上 “.”；不会自动添加
     * @param columnName
     * @param attrName
     * @param queryLogic
     * @param queryType
     * @param javaClassz
     * @param formats
     * @param isCaseSensitive
     * @return void
     */
    void appendScriptQueryCondition(StringBuffer sb, String tableAlias, String columnName, String attrName,
            ZKDBQueryLogic queryLogic, ZKDBQueryType queryType, Class<?> javaClassz, String[] formats,
            boolean isCaseSensitive);

    void appendScriptQueryConditionIfByClass(StringBuffer sb, String attrName, Class<?> javaClassz,
            boolean isEmpty, ZKDBConditionLogicDispose funcQueryLogicDispose);

    void appendScriptQueryConditionIf(ZKDBQueryLogic logic, ZKDBConditionLogicDispose funcQueryLogicDispose,
            StringBuffer sb, String attrName, Boolean isNull, Boolean isTrue, Boolean isEmpty, Boolean lengthIs0,
            Boolean valueIs0);

}
