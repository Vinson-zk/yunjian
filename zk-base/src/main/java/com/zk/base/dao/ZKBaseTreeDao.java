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
* @Title: ZKBaseTreeDao.java 
* @author Vinson 
* @Package com.zk.base.dao 
* @Description: TODO(simple description this file what to do. ) 
* @date Sep 23, 2020 4:46:23 PM 
* @version V1.0 
*/
package com.zk.base.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

import com.zk.base.entity.ZKBaseTreeEntity;
import com.zk.base.myBaits.provider.ZKMyBatisTreeSqlProvider;

/** 
* @ClassName: ZKBaseTreeDao 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public interface ZKBaseTreeDao<ID extends Serializable, E extends ZKBaseTreeEntity<ID, E>> extends ZKBaseDao<ID, E> {
    /**
     * 不分层级查询; 此方法还未达到预期效果
     *
     * @Title: findTreeNoLevel
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Jan 6, 2021 10:08:27 PM
     * @param entity
     * @return List<T>
     */
//    @Select({"SELECT ${blockSqlSelColumns} FROM ${blockSqlTableName} ${blockSqlTableAlias} ${blockSqlWhereTreeNoLevel}" })
    @SelectProvider(type = ZKMyBatisTreeSqlProvider.class, method = "selectTreeNoLevel")
    List<E> findTreeNoLevel(E entity);

}
