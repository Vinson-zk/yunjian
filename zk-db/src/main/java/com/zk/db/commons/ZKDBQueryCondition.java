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
* @Title: ZKDBQueryCondition.java 
* @author Vinson 
* @Package com.zk.db.commons 
* @Description: TODO(simple description this file what to do. ) 
* @date Sep 16, 2020 8:44:54 AM 
* @version V1.0 
*/
package com.zk.db.commons;

/** 
* @ClassName: ZKDBQueryCondition 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public interface ZKDBQueryCondition {

    // 函数方法，条件处理中的函数方法，一般在处理中需要提前处理或一穿插处理时使用；
    @FunctionalInterface
    public static interface ZKDBConditionLogicDispose {
        public abstract void doing();
    }

    /**
     * 
     *
     * @Title: toQueryCondition
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Apr 19, 2022 3:11:03 PM
     * @param convert
     * @param sb
     * @param tableAlias
     *            表的别名；这里需要加上 “.”；不会自动添加
     * @param queryLogic
     *            查询关系逻辑，要查询条件中生效；
     * @param isInserQueryLogic
     *            是否需要插入 查询逻辑关键字
     * @return void
     */
    void toQueryCondition(ZKSqlConvert convert, StringBuffer sb, String tableAlias, ZKDBQueryLogic queryLogic,
            boolean isInserQueryLogic);



}
