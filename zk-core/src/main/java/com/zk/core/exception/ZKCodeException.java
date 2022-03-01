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
 * @Title: ZKCodeException.java 
 * @author Vinson 
 * @Package com.zk.core.exception 
 * @Description: TODO(simple description this file what to do.) 
 * @date Dec 3, 2019 2:42:18 PM 
 * @version V1.0   
*/
package com.zk.core.exception;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zk.core.utils.ZKJsonUtils;

/**
 * 此类异常的异常消息为自定义的异常消息，没有经过国际化，消息不会做响应数据；响应数据中的消息需要根据错误码自行国际化；这一点会在异常处理器中统一处理
 * 
 * @ClassName: ZKCodeException
 * @Description: TODO(simple description this class what to do.)
 * @author Vinson
 * @version 1.0
 */
public class ZKCodeException extends ZKUnknownException {

    /**
     * @Fields serialVersionUID : TODO(simple description what to do.)
     */
    private static final long serialVersionUID = 1L;

    /**
     * 异常代码
     */
    private String code;

    /**
     * 异常消息的参数
     */
    private Object[] msgArgs;

    /**
     * 异常时，返回的数据。
     */
    private Object data;

    /**
     * 生成数据检验错误异常
     *
     * @Title: asDataValidator
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Nov 14, 2021 9:37:34 PM
     * @param validatorMsg
     * @return
     * @return ZKCodeException
     */
    public static ZKCodeException asDataValidator(Map<String, String> validatorMsg) {
        return new ZKCodeException("zk.000002", "", null, validatorMsg);
    }

    public ZKCodeException(String code) {
        this(code, null);
    }

    public ZKCodeException(String code, String msg) {
        this(code, msg, null, null);
    }

    public ZKCodeException(String code, String msg, Object... msgArgs) {
        this(code, msg, msgArgs, null);
    }

    public ZKCodeException(String code, String msg, Object[] msgArgs, Object data) {
        this(code, msg, msgArgs, data, null);
    }

    public ZKCodeException(String code, String msg, Object[] msgArgs, Object data, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msgArgs = msgArgs;
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("{code:%s, msg:%s, msgArgs:%s, data:%s}", this.getCode(), super.getMessage(),
                this.getMsgArgsStr(), this.getDataStr());
    }

    @Override
    public String getMessage() {
        return toString();
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
     * @return msgArgs
     */
    public Object[] getMsgArgs() {
        return msgArgs;
    }

    /**
     * @param msgArgs
     *            the msgArgs to set
     */
    public void setMsgArgs(Object... msgArgs) {
        this.msgArgs = msgArgs;
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

    @JsonIgnore
    public String getMsgArgsStr() {
        if (this.msgArgs != null) {
            return ZKJsonUtils.writeObjectJson(this.msgArgs);
        }
        return null;
    }

}
