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
* @Title: ZKSecUserPrincipal.java 
* @author Vinson 
* @Package com.zk.security.principal 
* @Description: TODO(simple description this file what to do. ) 
* @date Jul 14, 2021 7:02:11 PM 
* @version V1.0 
*/
package com.zk.security.principal;
/** 
* @ClassName: ZKSecUserPrincipal 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public interface ZKSecUserPrincipal<ID> extends ZKSecPrincipal<ID> {

    /**
     * 登录名
     * 
     * @return
     */
    public String getUsername();

    /**
     * 姓名
     * 
     * @return
     */
    public String getName();

}
