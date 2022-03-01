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
* @Title: ZKSecUserDevFilter.java 
* @author Vinson 
* @Package com.zk.security.web.filter.authc 
* @Description: TODO(simple description this file what to do. ) 
* @date Jul 28, 2021 9:13:01 AM 
* @version V1.0 
*/
package com.zk.security.web.filter.authc;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.zk.security.subject.ZKSecSubject;
import com.zk.security.utils.ZKSecSecurityUtils;
import com.zk.security.web.filter.ZKSecBaseControlFilter;

/**
 * @ClassName: ZKSecUserDevFilter
 * @Description: TODO(simple description this class what to do. )
 * @author Vinson
 * @version 1.0
 */
public class ZKSecUserDevFilter extends ZKSecBaseControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        ZKSecSubject subject = ZKSecSecurityUtils.getSubject();
        if (subject != null) {
            return subject.isAuthcUser() && subject.isAuthcDev();
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

}
