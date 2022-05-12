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
* @Title: ZKDBQueryConditionWhere.java 
* @author Vinson 
* @Package com.zk.db.commons 
* @Description: TODO(simple description this file what to do. ) 
* @date Apr 15, 2021 8:39:46 AM 
* @version V1.0 
*/
package com.zk.db.commons;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** 
* @ClassName: ZKDBQueryConditionWhere 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKDBQueryConditionWhere implements ZKDBQueryCondition {

    public String toQueryCondition(ZKSqlConvert convert, String tableAlias) {
        StringBuffer sb = new StringBuffer();
        toQueryCondition(convert, sb, tableAlias);
        return sb.toString();
    }
    public void toQueryCondition(ZKSqlConvert convert, StringBuffer sb, String tableAlias) {
        toQueryCondition(convert, sb, tableAlias, null, false);
    }

    @Override
    public void toQueryCondition(ZKSqlConvert convert, StringBuffer sb, String tableAlias, ZKDBQueryLogic queryLogic,
            boolean isInserQueryLogic) {
        if (!this.getConditions().isEmpty()) {
            if (isInserQueryLogic && queryLogic != null) {
                sb.append(queryLogic.getEsc());
            }
            sb.append(this.getPrefix());
            this.toQueryConditionValue(convert, sb, tableAlias);
            sb.append(this.getSuffix());
        }
    }

    protected void toQueryConditionValue(ZKSqlConvert convert, StringBuffer sb, String tableAlias) {
        if (!this.getConditions().isEmpty()) {
            Iterator<ZKDBQueryCondition> iterator = conditions.iterator();
            ZKDBQueryCondition condition = iterator.next();
            condition.toQueryCondition(convert, sb, tableAlias, this.getQueryLogic(), false);
            while (iterator.hasNext()) {
                condition = iterator.next();
                condition.toQueryCondition(convert, sb, tableAlias, this.getQueryLogic(), true);
            }
        }
    }

    public static ZKDBQueryConditionWhere asAnd(ZKDBQueryCondition... conditions) {
        return new ZKDBQueryConditionWhere(ZKDBQueryLogic.AND, "", "", conditions);
    }

    public static ZKDBQueryConditionWhere asOr(ZKDBQueryCondition... conditions) {
        return new ZKDBQueryConditionWhere(ZKDBQueryLogic.OR, "", "", conditions);
    }

    public static ZKDBQueryConditionWhere asAnd(String prefix, String suffix, ZKDBQueryCondition... conditions) {
        return new ZKDBQueryConditionWhere(ZKDBQueryLogic.AND, prefix, suffix, conditions);
    }

    public static ZKDBQueryConditionWhere asOr(String prefix, String suffix, ZKDBQueryCondition... conditions) {
        return new ZKDBQueryConditionWhere(ZKDBQueryLogic.OR, prefix, suffix, conditions);
    }

    public ZKDBQueryConditionWhere(ZKDBQueryLogic queryLogic, String prefix, String suffix,
            ZKDBQueryCondition... conditions) {
        this.queryLogic = queryLogic;
        this.prefix = prefix;
        this.suffix = suffix;
        if (conditions != null) {
            this.put(conditions);
        }
    }

    // 添加条件；方法名定义，不想用 add ；怕与 and 混淆
    public void put(ZKDBQueryCondition... queryConditions) {
        for (ZKDBQueryCondition queryCondition : queryConditions) {
            this.put(queryCondition, -1);
        }
    }

    public void put(ZKDBQueryCondition queryCondition, int point) {
        if (queryCondition != null) {
//            queryCondition.setQueryLogic(queryLogic);
            if (point < 0) {
                this.getConditions().add(queryCondition);
            }
            else {
                this.getConditions().add(point, queryCondition);
            }
        }
    }

    public boolean isEmpty() {
        return this.getConditions().isEmpty();
    }

    // 条件逻辑 and or
    ZKDBQueryLogic queryLogic;

    List<ZKDBQueryCondition> conditions = new ArrayList<>();

    String prefix;

    String suffix;

    /**
     * @return queryLogic sa
     */
    public ZKDBQueryLogic getQueryLogic() {
        return queryLogic;
    }

    /**
     * @return conditions sa
     */
    public List<ZKDBQueryCondition> getConditions() {
        return conditions;
    }

    /**
     * @return prefix sa
     */
    public String getPrefix() {
        return prefix == null ? "" : prefix;
    }

    /**
     * @return suffix sa
     */
    public String getSuffix() {
        return suffix == null ? "" : suffix;
    }

}
