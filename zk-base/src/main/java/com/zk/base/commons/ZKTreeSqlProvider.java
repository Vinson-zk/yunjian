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
* @Title: ZKTreeSqlProvider.java 
* @author Vinson 
* @Package com.zk.base.commons 
* @Description: TODO(simple description this file what to do. ) 
* @date Apr 28, 2021 1:46:32 PM 
* @version V1.0 
*/
package com.zk.base.commons;

import com.zk.base.entity.ZKBaseTreeEntity;
import com.zk.db.commons.ZKSqlConvert;
import com.zk.db.commons.ZKSqlProvider;

/** 
* @ClassName: ZKTreeSqlProvider 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKTreeSqlProvider extends ZKSqlProvider {

    /**
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param sqlConvert
     * @param entity
     */
    public ZKTreeSqlProvider(ZKSqlConvert sqlConvert, ZKBaseTreeEntity<?, ?> entity) {
        super(sqlConvert, entity); // TODO Auto-generated constructor stub
        this.sqlBlockWhereTree = sqlConvert.convertSqlWhere(
                entity.getZKDbWhereTree(sqlConvert, this.getAnnotationProvider()),
                this.getAnnotationProvider().getTable().alias()) + " ";
        this.sqlBlockWhereTreeNoLevel = sqlConvert.convertSqlWhere(
                entity.getZKDbWhereTreeNoLevel(sqlConvert, this.getAnnotationProvider()),
                this.getAnnotationProvider().getTable().alias()) + " ";
    }


    // 递归查询树形节点; 1.不支持子节点过虑; 2.仅支持根节点过滤与分页；
    String sqlBlockWhereTree;

    // 不分层级按条件过滤，过滤结果中不是根结点时，如果父节点不在过滤结果中，升级为结果中的根节点；
    String sqlBlockWhereTreeNoLevel;

    /**
     * @return sqlBlockWhereTree sa
     */
    public String getSqlBlockWhereTree() {
        return sqlBlockWhereTree;
    }

    /**
     * @return sqlBlockWhereTreeNoLevel sa
     */
    public String getSqlBlockWhereTreeNoLevel() {
        return sqlBlockWhereTreeNoLevel;
    }

}
