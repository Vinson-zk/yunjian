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
 * @Title: ZKUserUtils.java 
 * @author Vinson 
 * @Package com.zk.base.commons 
 * @Description: TODO(simple description this file what to do.) 
 * @date Dec 19, 2019 2:15:11 PM 
 * @version V1.0   
*/
package com.zk.base.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* @ClassName: ZKUserUtils 
* @Description: TODO(simple description this class what to do.) 
* @author Vinson 
* @version 1.0 
*/
public class ZKUserUtils {

    /**
     * 日志对象
     */
    protected static Logger log = LoggerFactory.getLogger(ZKUserUtils.class);

    /**
     * 返回当前登录用户ID
     * 
     * @return
     */
//  public static Principal getPrincipal(){
//      return null;
//  }

    public static <ID> ID getPrincipalId() {
//      if(getPrincipal() != null){
//          return getPrincipal().getPkId();
//      }
        return null;
    }

}
