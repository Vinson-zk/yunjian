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
* @Title: ZKDBQueryConditionIfByClass.java 
* @author Vinson 
* @Package com.zk.db.mybatis.commons 
* @Description: TODO(simple description this file what to do. ) 
* @date Apr 19, 2022 3:49:10 PM 
* @version V1.0 
*/
package com.zk.db.mybatis.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.db.commons.ZKDBQueryCondition;
import com.zk.db.commons.ZKDBQueryLogic;
import com.zk.db.commons.ZKSqlConvert;

/** 
* @ClassName: ZKDBQueryConditionIfByClass 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKDBQueryConditionIfByClass implements ZKDBQueryConditionScript {

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * (not Javadoc)
     * 
     * @param convert
     * @param sb
     * @param tableAlias
     * @param queryLogic
     * @see com.zk.db.commons.ZKDBQueryCondition#toQueryCondition(com.zk.db.commons.ZKSqlConvert, java.lang.StringBuffer, java.lang.String,
     *      com.zk.db.commons.ZKDBQueryLogic)
     */
    @Override
    public void toQueryCondition(ZKSqlConvert convert, StringBuffer sb, String tableAlias, ZKDBQueryLogic queryLogic,
            boolean isInserQueryLogic) {
        if (convert instanceof ZKSqlMybatisConvert) {
            ((ZKSqlMybatisConvert) convert).appendScriptQueryConditionIfByClass(sb, this.getAttrName(),
                    this.getJavaClassz(),
                    this.isEmpty(), () -> {
                        this.getQueryCondition().toQueryCondition(convert, sb, tableAlias, queryLogic,
                                isInserQueryLogic);
                    });
        }
        else {
            log.error("[>_<:20210415-1101-001] ????????????????????????????????????????????????????????????????????????ZKDBQueryConditionCol; ??????????????????{} ",
                    convert.getClass());
        }
    }

    public static ZKDBQueryConditionIfByClass as(ZKDBQueryCondition queryCondition, String attrName,
            Class<?> javaClassz,
            boolean isEmpty) {
        return new ZKDBQueryConditionIfByClass(queryCondition, attrName, javaClassz, isEmpty);
    }

    public ZKDBQueryConditionIfByClass(ZKDBQueryCondition queryCondition, String attrName, Class<?> javaClassz,
            boolean isEmpty) {
        this.queryCondition = queryCondition;
        this.attrName = attrName;
        this.javaClassz = javaClassz;
        this.isEmpty = isEmpty;
    }

    ZKDBQueryCondition queryCondition;

    // ???????????????
    String attrName;

    // ???????????? java ??????
    Class<?> javaClassz;

    // ??????????????? ??????????????????; true- ??? null ??? ??????????????????????????? ??? null ?????? 0 ???; false-??????null???????????????
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

