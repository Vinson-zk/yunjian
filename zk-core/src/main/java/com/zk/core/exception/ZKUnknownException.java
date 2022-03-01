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
 * @Title: ZKUnknownException.java 
 * @author Vinson 
 * @Package com.zk.core.exception 
 * @Description: TODO(simple description this file what to do.) 
 * @date Dec 3, 2019 2:24:12 PM 
 * @version V1.0   
*/
package com.zk.core.exception;

import com.zk.core.utils.ZKStringUtils;

/** 
* @ClassName: ZKUnknownException 
* @Description: TODO(simple description this class what to do.) 
* @author Vinson 
* @version 1.0 
*/
public class ZKUnknownException extends RuntimeException {

    /**
     * @Fields serialVersionUID : TODO(描述变量)
     */
    private static final long serialVersionUID = 1L;

    private String msg;

    public ZKUnknownException(String msg) {
        this.msg = msg;
    }

    public ZKUnknownException(Throwable cause) {
        super(cause);
    }

    public ZKUnknownException(String msg, Throwable cause) {
        super(cause);
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return ZKStringUtils.isEmpty(msg) ? super.getMessage() : this.msg;
    }

    protected void setMsg(String msg) {
        this.msg = msg;
    }

    // 取异常的消息，仅带有消息
    public String getMsg() {
        return this.msg;
    }

//  @Override
//  public void printStackTrace(){
//      super.printStackTrace();
//  }

}
