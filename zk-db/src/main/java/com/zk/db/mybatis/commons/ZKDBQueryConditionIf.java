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
* @Title: ZKDBQueryConditionIf.java 
* @author Vinson 
* @Package com.zk.db.mybatis.commons 
* @Description: TODO(simple description this file what to do. ) 
* @date Apr 15, 2021 8:21:20 AM 
* @version V1.0 
*/
package com.zk.db.mybatis.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.db.commons.ZKDBQueryCondition;
import com.zk.db.commons.ZKDBQueryLogic;
import com.zk.db.commons.ZKSqlConvert;

/**
 * 定义 mybatis 的逻辑查询 script
 * 
 * @ClassName: ZKDBQueryConditionIf
 * @Description: TODO(simple description this class what to do. )
 * @author Vinson
 * @version 1.0
 */
public class ZKDBQueryConditionIf implements ZKDBQueryConditionScript {

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * (not Javadoc)
     * 
     * @param convert
     * @param sb
     * @param tableAlias
     * @param queryLogic
     * @see com.zk.db.commons.ZKDBQueryCondition#toQueryCondition(com.zk.db.commons.ZKSqlConvert,
     *      java.lang.StringBuffer, java.lang.String,
     *      com.zk.db.commons.ZKDBQueryLogic)
     */
    @Override
    public void toQueryCondition(ZKSqlConvert convert, StringBuffer sb, String tableAlias, ZKDBQueryLogic queryLogic,
            boolean isInserQueryLogic) {
        if (convert instanceof ZKSqlMybatisConvert) {
            ((ZKSqlMybatisConvert) convert).appendScriptQueryConditionIf(this.getLogic(), () -> {
                this.getQueryCondition().toQueryCondition(convert, sb, tableAlias, queryLogic, isInserQueryLogic);
            }, sb, this.getAttrName(), this.getIsNull(), this.getIsTrue(), this.getIsEmpty(), this.getLengthIs0(),
                    this.getValueIs0());
        }
        else {
            log.error("[>_<:20210415-1101-001] 转换器不支持此类型的查询条件转换；查询条件类型：ZKDBQueryConditionCol; 转换器类型：{} ",
                    convert.getClass());
        }
    }

    public static ZKDBQueryConditionIf asOr(ZKDBQueryCondition queryCondition, String attrName, Boolean isNull,
            Boolean isTrue, Boolean isEmpty, Boolean lengthIs0, Boolean valueIs0) {
        return ZKDBQueryConditionIf.as(ZKDBQueryLogic.OR, queryCondition, attrName, isNull, isTrue, isEmpty, lengthIs0,
                valueIs0);
    }

    public static ZKDBQueryConditionIf asAnd(ZKDBQueryCondition queryCondition, String attrName, Boolean isNull,
            Boolean isTrue) {
        return ZKDBQueryConditionIf.asAnd(queryCondition, attrName, isNull, isTrue, null, null, null);
    }

    public static ZKDBQueryConditionIf asAnd(ZKDBQueryCondition queryCondition, String attrName, Boolean isNull,
            Boolean isTrue, Boolean isEmpty, Boolean lengthIs0, Boolean valueIs0) {
        return ZKDBQueryConditionIf.as(ZKDBQueryLogic.AND, queryCondition, attrName, isNull, isTrue, isEmpty,
                lengthIs0, valueIs0);
    }

    public static ZKDBQueryConditionIf as(ZKDBQueryLogic logic, ZKDBQueryCondition queryCondition, String attrName,
            Boolean isNull, Boolean isTrue, Boolean isEmpty, Boolean lengthIs0, Boolean valueIs0) {
        return new ZKDBQueryConditionIf(logic, queryCondition, attrName, isNull, isTrue, isEmpty, lengthIs0, valueIs0);
    }

    public ZKDBQueryConditionIf(ZKDBQueryLogic logic, ZKDBQueryCondition queryCondition, String attrName,
            Boolean isNull, Boolean isTrue, Boolean isEmpty, Boolean lengthIs0, Boolean valueIs0) {
        this.queryCondition = queryCondition;
        this.attrName = attrName;
        this.logic = logic;
        this.isNull = isNull;
        this.isTrue = isTrue;
        this.isEmpty = isEmpty;
        this.lengthIs0 = lengthIs0;
        this.valueIs0 = valueIs0;
    }

    ZKDBQueryCondition queryCondition;

    // 条件属性名
    String attrName;

    // if 逻辑字符 and or 同 sql 逻辑查询字符
    ZKDBQueryLogic logic;

    // 为null时不做为条件判断；true-添加为 null 判断；false-添加不为 null 判断；
    Boolean isNull;

    // 为null时不做为条件判断；true-添加为 true 判断；false-添加不为 ture 判断；
    Boolean isTrue;

    // 为null时不做为条件判断；true-添加为 空 判断；false-添加不为 空 判断；
    Boolean isEmpty;

    // 为null时不做为条件判断；true-添加为 长度为 0 判断；false-添加不为 长度不为 0 判断；
    Boolean lengthIs0;

    // 为null时不做为条件判断；true-添加为 值为0 判断；false-添加不为 值不为 0 判断；
    Boolean valueIs0;

    /**
     * @return queryCondition sa
     */
    public ZKDBQueryCondition getQueryCondition() {
        return queryCondition;
    }

    /**
     * @return attrName sa
     */
    public String getAttrName() {
        return attrName;
    }

    /**
     * @return logic sa
     */
    public ZKDBQueryLogic getLogic() {
        return logic;
    }

    /**
     * @return isNull sa
     */
    public Boolean getIsNull() {
        return isNull;
    }

    /**
     * @return isTrue sa
     */
    public Boolean getIsTrue() {
        return isTrue;
    }

    /**
     * @return isEmpty sa
     */
    public Boolean getIsEmpty() {
        return isEmpty;
    }

    /**
     * @return lengthIs0 sa
     */
    public Boolean getLengthIs0() {
        return lengthIs0;
    }

    /**
     * @return valueIs0 sa
     */
    public Boolean getValueIs0() {
        return valueIs0;
    }

}
