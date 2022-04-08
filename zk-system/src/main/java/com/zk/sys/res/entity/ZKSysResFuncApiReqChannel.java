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
* @Title: ZKSysResFuncApiReqChannel.java 
* @author Vinson 
* @Package com.zk.sys.res.entity 
* @Description: TODO(simple description this file what to do. ) 
* @date Nov 30, 2021 10:39:17 AM 
* @version V1.0 
*/
package com.zk.sys.res.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.zk.base.entity.ZKBaseEntity;
import com.zk.db.annotation.ZKColumn;
import com.zk.db.annotation.ZKTable;
import com.zk.db.commons.ZKSqlConvertDelegating;
import com.zk.db.commons.ZKSqlProvider;

/** 
* @ClassName: ZKSysResFuncApiReqChannel 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
@ZKTable(name = "t_sys_res_func_api_req_channel", alias = "sysResFuncApiReqChannel", orderBy = " c_create_date ASC ")
public class ZKSysResFuncApiReqChannel extends ZKBaseEntity<String, ZKSysResFuncApiReqChannel> {

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
            sqlProvider = new ZKSqlProvider(new ZKSqlConvertDelegating(), new ZKSysResFuncApiReqChannel());
        }
        return sqlProvider;
    }

    /**
     * 渠道ID
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_channel_id", isInsert = true, isQuery = true)
    String channelId;

    /**
     * 功能API ID
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_func_api_id", isInsert = true, isQuery = true)
    String funcApiId;

    /**
     * 渠道代码，沉余字段，方便查询；
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_channel_code", isInsert = true, isQuery = true)
    String channelCode;

    /**
     * 接口所在的应用系统代码，沉余字段，方便查询；
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_system_code", isInsert = true, isQuery = true)
    String systemCode;

    /**
     * 接口代码，沉余字段，方便查询；
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_func_api_code", isInsert = true, isQuery = true)
    String funcApiCode;

    public ZKSysResFuncApiReqChannel() {
        super();
    }

    public ZKSysResFuncApiReqChannel(String channelId, String funcApiId) {
        super();
        this.channelId = channelId;
        this.funcApiId = funcApiId;
    }

    /**
     * @return channelId sa
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * @return funcApiId sa
     */
    public String getFuncApiId() {
        return funcApiId;
    }

    /**
     * @return channelCode sa
     */
    public String getChannelCode() {
        return channelCode;
    }

    /**
     * @return systemCode sa
     */
    public String getSystemCode() {
        return systemCode;
    }

    /**
     * @return funcApiCode sa
     */
    public String getFuncApiCode() {
        return funcApiCode;
    }

    /**
     * @param channelId
     *            the channelId to set
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    /**
     * @param funcApiId
     *            the funcApiId to set
     */
    public void setFuncApiId(String funcApiId) {
        this.funcApiId = funcApiId;
    }

    /**
     * @param channelCode
     *            the channelCode to set
     */
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    /**
     * @param systemCode
     *            the systemCode to set
     */
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    /**
     * @param funcApiCode
     *            the funcApiCode to set
     */
    public void setFuncApiCode(String funcApiCode) {
        this.funcApiCode = funcApiCode;
    }

}
