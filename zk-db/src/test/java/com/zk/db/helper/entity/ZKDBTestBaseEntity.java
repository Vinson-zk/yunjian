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
* @Title: ZKDBTestBaseEntity.java 
* @author Vinson 
* @Package com.zk.db.helper.entity 
* @Description: TODO(simple description this file what to do. ) 
* @date Sep 17, 2020 10:56:53 AM 
* @version V1.0 
*/
package com.zk.db.helper.entity;

import com.zk.db.annotation.ZKColumn;
import com.zk.db.commons.ZKDBBaseEntity;

/** 
* @ClassName: ZKDBTestBaseEntity 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public abstract class ZKDBTestBaseEntity<E extends ZKDBTestBaseEntity<E>> extends ZKDBBaseEntity<E> {

    /**
     * @Fields serialVersionUID : TODO(simple description what to do.)
     */
    private static final long serialVersionUID = 1L;

    @ZKColumn(name = "c_value", isUpdate = true)
    private String value;

    /**
     * @return value sa
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getZKDbDelSetSql() {
        return "c_del_flag = #{delFlag}, c_update_user_id = #{updateUserId}, c_update_date = #{updateDate}";
    }

}
