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
* @Title: ZKThirdParty.java 
* @author Vinson 
* @Package com.zk.wechat.thirdParty.entity 
* @Description: TODO(simple description this file what to do. ) 
* @date Feb 18, 2021 10:39:20 AM 
* @version V1.0 
*/
package com.zk.wechat.thirdParty.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zk.base.entity.ZKBaseEntity;
import com.zk.core.utils.ZKDateUtils;
import com.zk.db.annotation.ZKColumn;
import com.zk.db.annotation.ZKTable;
import com.zk.db.commons.ZKDBQueryType;
import com.zk.db.commons.ZKSqlConvert;
import com.zk.db.commons.ZKSqlConvertDelegating;
import com.zk.db.mybatis.commons.ZKSqlProvider;

/** 
* @ClassName: ZKThirdParty 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
@ZKTable(name = "t_wx_third_party", alias = "wxThirdParty")
public class ZKThirdParty extends ZKBaseEntity<String, ZKThirdParty> {

    static ZKSqlProvider sqlProvider;

    @Transient
    @XmlTransient
    @JsonIgnore
    @Override
    public ZKSqlProvider getSqlProvider() {
        return sqlProvider();
    }

    public static ZKSqlProvider sqlProvider() {
        if (sqlProvider == null) {
            sqlProvider = new ZKSqlProvider(new ZKSqlConvertDelegating(), new ZKThirdParty());
        }
        return sqlProvider;
    }
    /**
     * @Fields serialVersionUID : TODO(simple description what to do.)
     */
    private static final long serialVersionUID = 1L;

    protected static ZKSqlConvert sqlConvert;

    public ZKThirdParty() {
        super();
    }

    public ZKThirdParty(String appId) {
        super(appId);
    }

//  @ZKColumn(name = "c_name", isUpdate = true, isQuery = false, javaType = ZKJson.class, isCaseSensitive = false, queryType = ZKDBQueryType.LIKE)

    // ?????????????????????????????? app secret
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_wx_app_secret", isUpdate = false, isQuery = false)
    private String wxAppSecret;

    // ????????????????????????????????? token
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_wx_token", isUpdate = false, isQuery = false)
    private String wxToken;

    // ????????????????????? key
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_wx_aes_key", isUpdate = false, isQuery = false)
    private String wxAesKey;

    // ?????????????????????
    @ZKColumn(name = "c_wx_ticket", isUpdate = false, isQuery = false)
    @Length(max = 64, message = "{zk.core.data.validation.length.max}")
    private String wxTicket;

    // ?????????????????????????????????
    @ZKColumn(name = "c_wx_ticket_update_date", javaType = Date.class)
    protected Date wxTicketUpdateDate;

    /**
     * ????????????
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_group_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
    String groupCode;

    /**
     * ??????ID
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_company_id", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
    String companyId;

    /**
     * ????????????
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_company_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
    String companyCode;

    /**
     * @return wxAppSecret sa
     */
    public String getWxAppSecret() {
        return wxAppSecret;
    }

    /**
     * @param wxAppSecret
     *            the wxAppSecret to set
     */
    public void setWxAppSecret(String wxAppSecret) {
        this.wxAppSecret = wxAppSecret;
    }

    /**
     * @return wxToken sa
     */
    public String getWxToken() {
        return wxToken;
    }

    /**
     * @param wxToken
     *            the wxToken to set
     */
    public void setWxToken(String wxToken) {
        this.wxToken = wxToken;
    }

    /**
     * @return wxAesKey sa
     */
    public String getWxAesKey() {
        return wxAesKey;
    }

    /**
     * @param wxAesKey
     *            the wxAesKey to set
     */
    public void setWxAesKey(String wxAesKey) {
        this.wxAesKey = wxAesKey;
    }

    /**
     * @return wxTicket sa
     */
    public String getWxTicket() {
        return wxTicket;
    }

    /**
     * @param wxTicket
     *            the wxTicket to set
     */
    public void setWxTicket(String wxTicket) {
        this.wxTicket = wxTicket;
    }

    /**
     * @return wxTicketUpdateDate sa
     */
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    @JsonFormat(pattern = ZKDateUtils.DF_yyyy_MM_dd_HH_mm_ss, timezone = timezone)
    public Date getWxTicketUpdateDate() {
        return wxTicketUpdateDate;
    }

    /**
     * @param wxTicketUpdateDate
     *            the wxTicketUpdateDate to set
     */
    public void setWxTicketUpdateDate(Date wxTicketUpdateDate) {
        this.wxTicketUpdateDate = wxTicketUpdateDate;
    }

    /**
     * ?????????????????????????????????????????????????????????????????????????????????????????????;
     *
     * @Title: getOffsetExpiresTime
     * @Description: TODO(simple description this method what to do.)
     * @author bs
     * @date 2018???9???13??? ??????12:32:56
     * @return
     * @return int
     */
    protected int getOffsetExpiresTime() {
        return 60;
    }

    /**
     * ????????????
     * 
     * @return groupCode sa
     */
    public String getGroupCode() {
        return groupCode;
    }

    /**
     * ????????????
     * 
     * @param groupCode
     *            the groupCode to set
     */
    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    /**
     * ??????ID
     * 
     * @return companyId sa
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * ??????ID
     * 
     * @param companyId
     *            the companyId to set
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * ????????????
     * 
     * @return companyCode sa
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     * ????????????
     * 
     * @param companyCode
     *            the companyCode to set
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

}