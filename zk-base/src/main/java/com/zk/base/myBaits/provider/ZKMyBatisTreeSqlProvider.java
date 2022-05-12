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
* @Title: ZKMyBatisTreeSqlProvider.java 
* @author Vinson 
* @Package com.zk.base.myBaits.provider 
* @Description: TODO(simple description this file what to do. ) 
* @date Apr 28, 2021 10:29:50 AM 
* @version V1.0 
*/
package com.zk.base.myBaits.provider;

import com.zk.base.entity.ZKBaseTreeEntity;

/** 
* @ClassName: ZKMyBatisTreeSqlProvider 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKMyBatisTreeSqlProvider {

    public String selectTree(ZKBaseTreeEntity<?, ?> entity) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script>SELECT ");
        sb.append(entity.getSqlProvider().getSqlBlockSelCols());
        sb.append(" FROM ");
        sb.append(entity.getSqlProvider().getTableName());
        sb.append(" ");
        sb.append(entity.getSqlProvider().getTableAlias());
        sb.append(" ");
        sb.append(entity.getTreeSqlProvider().getSqlBlockWhereTree());
        sb.append(entity.getSqlProvider().convertOrderBySql(entity));
        sb.append("</script>");
        return sb.toString();
    }

    public String selectTreeNoLevel(ZKBaseTreeEntity<?, ?> entity) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script>SELECT ");
        sb.append(entity.getSqlProvider().getSqlBlockSelCols());
        sb.append(" FROM ");
        sb.append(entity.getSqlProvider().getTableName());
        sb.append(" ");
        sb.append(entity.getSqlProvider().getTableAlias());
        sb.append(" ");
        sb.append(entity.getTreeSqlProvider().getSqlBlockWhereTreeNoLevel()).append(" ");
        sb.append(entity.getSqlProvider().convertOrderBySql(entity));
        sb.append("</script>");
        return sb.toString();
    }

    public String selectTreeDetail(ZKBaseTreeEntity<?, ?> entity) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        sb.append(entity.getSqlProvider().getSqlBlockSelCols());
        sb.append(" FROM ");
        sb.append(entity.getSqlProvider().getTableName());
        sb.append(" ");
        sb.append(entity.getSqlProvider().getTableAlias());
        sb.append(" ");
        sb.append(entity.getSqlProvider().getSqlBlockPkWhere());
        return sb.toString();
    }

}
