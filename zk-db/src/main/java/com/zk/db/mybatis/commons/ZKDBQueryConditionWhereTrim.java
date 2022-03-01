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
* @Title: ZKDBQueryConditionWhereTrim.java 
* @author Vinson 
* @Package com.zk.db.mybatis.commons 
* @Description: TODO(simple description this file what to do. ) 
* @date Apr 15, 2021 8:51:56 AM 
* @version V1.0 
*/
package com.zk.db.mybatis.commons;

import com.zk.db.commons.ZKDBQueryCondition;
import com.zk.db.commons.ZKDBQueryConditionWhere;
import com.zk.db.commons.ZKDBQueryLogic;
import com.zk.db.commons.ZKSqlConvert;

/** 
* @ClassName: ZKDBQueryConditionWhereTrim 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKDBQueryConditionWhereTrim extends ZKDBQueryConditionWhere {

    @Override
    public void toQueryCondition(ZKSqlConvert convert, StringBuffer sb, String tableAlias,
            ZKDBConditionLogicDispose funcQueryLogicDispose) {
        if (!this.getConditions().isEmpty()) {
            if (funcQueryLogicDispose != null) {
                funcQueryLogicDispose.run();
            }
            sb.append(this.getPrefix());
            // (<trim prefix=\"\" prefixOverrides=\"and|or\">
            sb.append("<trim prefixOverrides=\"and|or\">");
            super.toQueryConditionValue(convert, sb, tableAlias, funcQueryLogicDispose);
            sb.append("</trim>");
            sb.append(this.getSuffix());
        }
    }

    public static ZKDBQueryConditionWhereTrim asAnd(ZKDBQueryCondition... conditions) {
        return new ZKDBQueryConditionWhereTrim(ZKDBQueryLogic.AND, "", "", conditions);
    }

    public static ZKDBQueryConditionWhereTrim asOr(ZKDBQueryCondition... conditions) {
        return new ZKDBQueryConditionWhereTrim(ZKDBQueryLogic.OR, "", "", conditions);
    }

    public static ZKDBQueryConditionWhereTrim asAnd(String prefix, String suffix, ZKDBQueryCondition... conditions) {
        return new ZKDBQueryConditionWhereTrim(ZKDBQueryLogic.AND, prefix, suffix, conditions);
    }

    public static ZKDBQueryConditionWhereTrim asOr(String prefix, String suffix, ZKDBQueryCondition... conditions) {
        return new ZKDBQueryConditionWhereTrim(ZKDBQueryLogic.OR, prefix, suffix, conditions);
    }

    public ZKDBQueryConditionWhereTrim(ZKDBQueryLogic queryLogic, String prefix, String suffix,
            ZKDBQueryCondition... conditions) {
        super(queryLogic, prefix, suffix, conditions);
    }

}
