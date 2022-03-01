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
* @ClassName: ZKDBQueryConditionIf 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKDBQueryConditionIf implements ZKDBQueryCondition {

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
    public void toQueryCondition(ZKSqlConvert convert, StringBuffer sb, String tableAlias,
            ZKDBConditionLogicDispose funcQueryLogicDispose) {
        if (convert instanceof ZKSqlMybatisConvert) {
            ((ZKSqlMybatisConvert) convert).appendScriptQueryConditionIf(sb, this.getAttrName(), this.getJavaClassz(), this.isEmpty(), () -> {
                ZKDBQueryLogic queryLogic = funcQueryLogicDispose.run();
                this.getQueryCondition().toQueryCondition(convert, sb, tableAlias, () -> {
                    return queryLogic;
                });
                return queryLogic;
            });
        }
        else {
            log.error("[>_<:20210415-1101-001] 转换器不支持此类型的查询条件转换；查询条件类型：ZKDBQueryConditionCol; 转换器类型：{} ",
                    convert.getClass());
        }
    }

    public static ZKDBQueryConditionIf as(ZKDBQueryCondition queryCondition, String attrName, Class<?> javaClassz,
            boolean isEmpty) {
        return new ZKDBQueryConditionIf(queryCondition, attrName, javaClassz, isEmpty);
    }

    public ZKDBQueryConditionIf(ZKDBQueryCondition queryCondition, String attrName, Class<?> javaClassz,
            boolean isEmpty) {
        this.queryCondition = queryCondition;
        this.attrName = attrName;
        this.javaClassz = javaClassz;
        this.isEmpty = isEmpty;
    }

    ZKDBQueryCondition queryCondition;

    // 条件属性名
    String attrName;

    // 参数数据 java 类型
    Class<?> javaClassz;

    // 参数是否为 空，或不为空; true-不为 null，但为空; false-不为null且不为空。
    boolean isEmpty = false;

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
     * @return javaClassz sa
     */
    public Class<?> getJavaClassz() {
        return javaClassz;
    }

    /**
     * @return isEmpty sa
     */
    public boolean isEmpty() {
        return isEmpty;
    }

}
