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
* @Title: ZKDBQueryLogic.java 
* @author Vinson 
* @Package com.zk.db.commons 
* @Description: TODO(simple description this file what to do. ) 
* @date Sep 15, 2020 11:39:49 PM 
* @version V1.0 
*/
package com.zk.db.commons;
/** 
* @ClassName: ZKDBQueryLogic 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public enum ZKDBQueryLogic {
    
    AND("AND", " AND "), OR("OR", " OR ");

    // key
    private final String key;

    // 转译字符
    private final String esc;

    ZKDBQueryLogic(String key, String esc) {
        this.key = key;
        this.esc = esc;
    }

    /**
     * @return key sa
     */
    public String getKey() {
        return key;
    }

    /**
     * @return esc sa
     */
    public String getEsc() {
        return esc;
    }

}
