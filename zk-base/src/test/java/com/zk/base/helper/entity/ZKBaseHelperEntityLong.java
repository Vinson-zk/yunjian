/**   
 * Copyright (c) 2004-2014 Vinson Technologies, Inc.
 * address: 
 * All rights reserved. 
 * 
 * This software is the confidential and proprietary information of 
 * Vinson Technologies, Inc. ("Confidential Information").  You shall not 
 * disclose such Confidential Information and shall use it only in 
 * accordance with the terms of the license agreement you entered into 
 * with Vinson. 
 *
 * @Title: ZKBaseHelperEntityLong.java 
 * @author Vinson 
 * @Package com.zk.base.helper.entity 
 * @Description: TODO(simple description this file what to do.) 
 * @date Dec 19, 2019 2:57:51 PM 
 * @version V1.0   
*/
package com.zk.base.helper.entity;

import com.zk.base.entity.ZKBaseEntity;
import com.zk.db.commons.ZKSqlConvertDelegating;
import com.zk.db.commons.ZKSqlProvider;

/** 
* @ClassName: ZKBaseHelperEntityLong 
* @Description: TODO(simple description this class what to do.) 
* @author Vinson 
* @version 1.0 
*/
public class ZKBaseHelperEntityLong extends ZKBaseEntity<Long, ZKBaseHelperEntityLong> {

    /**
     * @Fields serialVersionUID : TODO(simple description what to do.)
     */
    private static final long serialVersionUID = 1L;

    static ZKSqlProvider sqlProvider;

    @Override
    public ZKSqlProvider getSqlProvider() {
        return initSqlProvider();
    }
  
    public static ZKSqlProvider initSqlProvider() {
        if (sqlProvider == null) {
            sqlProvider = new ZKSqlProvider(new ZKSqlConvertDelegating(), new ZKBaseHelperEntityLong());
        }
        return sqlProvider;
    }

}
