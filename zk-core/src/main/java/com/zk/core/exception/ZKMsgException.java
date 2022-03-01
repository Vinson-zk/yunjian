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
 * @Title: ZKMsgException.java 
 * @author Vinson 
 * @Package com.zk.core.exception 
 * @Description: TODO(simple description this file what to do.) 
 * @date Dec 18, 2019 2:46:15 PM 
 * @version V1.0   
*/
package com.zk.core.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zk.core.utils.ZKJsonUtils;

/**
 * 带有消息的异常，消息自行国际化，会在异常处理中直接根据消息，代码，数据做成响应数据
 * 
 * @ClassName: ZKMsgException
 * @Description: TODO(simple description this class what to do.)
 * @author Vinson
 * @version 1.0
 */
public class ZKMsgException extends ZKUnknownException {

    /**
     * @Fields serialVersionUID : TODO(simple description what to do.)
     */
    private static final long serialVersionUID = 1L;

    /**
     * 异常代码
     */
    private String code;

    /**
     * 异常时，返回的数据。
     */
    private Object data;

    public ZKMsgException(String code) {
        this(code, null);
    }

    public ZKMsgException(String code, String msg) {
        this(code, msg, null);
    }

    public ZKMsgException(String code, String msg, Object data) {
        this(code, msg, data, null);
    }

    public ZKMsgException(String code, String msg, Object data, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.data = data;
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return data
     */
    public Object getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(Object data) {
        this.data = data;
    }

    @JsonIgnore
    public String getDataStr() {
        if (this.data != null) {
            if (this.data instanceof String) {
                return (String) this.data;
            }
            else {
                return ZKJsonUtils.writeObjectJson(this.data);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("{code:%s, msg:%s, data:%s}", this.getCode(), super.getMessage(), this.getDataStr());
    }

    @Override
    public String getMessage() {
        return toString();
    }

}
