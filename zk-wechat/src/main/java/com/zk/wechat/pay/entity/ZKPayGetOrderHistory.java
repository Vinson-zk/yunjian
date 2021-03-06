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
* @Title: ZKPayGetOrderHistory.java 
* @author Vinson 
* @Package com.zk.wechat.pay.entity 
* @Description: TODO(simple description this file what to do. ) 
* @date Feb 22, 2021 9:38:33 AM 
* @version V1.0 
*/
package com.zk.wechat.pay.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zk.base.entity.ZKBaseEntity;
import com.zk.db.annotation.ZKColumn;
import com.zk.db.annotation.ZKTable;
import com.zk.db.commons.ZKSqlConvertDelegating;
import com.zk.db.mybatis.commons.ZKSqlProvider;
import com.zk.wechat.pay.enumType.ZKPayGetChannel;
import com.zk.wechat.pay.enumType.ZKPayStatus;

/** 
* @ClassName: ZKPayGetOrderHistory 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
@ZKTable(name = "t_wx_pay_get_order_history", alias = "wxPayGetOrderHistory")
public class ZKPayGetOrderHistory extends ZKBaseEntity<String, ZKPayGetOrderHistory> {

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
            sqlProvider = new ZKSqlProvider(new ZKSqlConvertDelegating(), new ZKPayGetOrderHistory());
        }
        return sqlProvider;
    }

    /**
     * @Fields serialVersionUID : TODO(simple description what to do.)
     */
    private static final long serialVersionUID = 1L;

    public ZKPayGetOrderHistory() {
        super();
    }

    public ZKPayGetOrderHistory(String pkId) {
        super(pkId);
    }

    public static ZKPayGetOrderHistory as(ZKPayGetOrder payGetOrder) {
        ZKPayGetOrderHistory e = new ZKPayGetOrderHistory();
        // ?????? payGetOrder ???????????????????????????
        e.setPayGetOrderId(payGetOrder.getPkId());
        e.setAppid(payGetOrder.getAppid());
        e.setAttach(payGetOrder.getAttach());
        e.setBusinessCode(payGetOrder.getBusinessCode());
        e.setBusinessNo(payGetOrder.getBusinessNo());
        e.setDelFlag(payGetOrder.getDelFlag());
        e.setDescriptionRename(payGetOrder.getDescriptionRename());
        e.setGoodsTag(payGetOrder.getGoodsTag());
        e.setMchid(payGetOrder.getMchid());
        e.setNotifyUrl(payGetOrder.getNotifyUrl());
        e.setPayGroupCode(payGetOrder.getPayGroupCode());
        e.setpDesc(payGetOrder.getpDesc());
        e.setPrepayId(payGetOrder.getPrepayId());
        e.setPrepayIdDate(payGetOrder.getPrepayIdDate());
        e.setRemarks(payGetOrder.getRemarks());
        e.setSpare1(payGetOrder.getSpare1());
        e.setSpare2(payGetOrder.getSpare2());
        e.setSpare3(payGetOrder.getSpare3());
        e.setSpareJson(payGetOrder.getSpareJson());
        e.setPayStatus(payGetOrder.getPayStatus());
        e.setTimeExpire(payGetOrder.getTimeExpire());
        e.setVersion(payGetOrder.getVersion());
        e.setWxChannel(payGetOrder.getWxChannel());
        e.setWxResStatusCode(payGetOrder.getWxResStatusCode());
        e.setPaySignDate(payGetOrder.getPaySignDate());
        e.setHistoryDate(payGetOrder.getCreateDate());
        return e;
    }

    // ?????????????????????????????????????????????????????????????????????
    @Length(min = 1, max = 20, message = "{zk.core.data.validation.length}")
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @ZKColumn(name = "c_pay_get_order_id")
    protected String payGetOrderId;

    // ??????ID appid string[1,32] ??? body
    // ?????????????????????????????????????????????appid???????????????wxd678efh567hg6787
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 32, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_wx_appid")
    String appid;

    // ??????????????? mchid string[1,32] ??? body ???????????????????????????????????????????????????????????? ????????????1230000109
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 32, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_wx_mchid")
    String mchid;

    // ???????????? description string[1,127] ??? body ????????????; ????????????Image?????????-????????????-QQ??????
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 127, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_wx_description")
    String descriptionRename; // ????????????????????????????????????????????????

    // ?????????????????? time_expire string[1,64] ??? body
    // ???????????????????????????rfc3339????????????????????????YYYY-MM-DDTHH:mm:ss+TIMEZONE???YYYY-MM-DD??????????????????T??????????????????????????????time??????????????????HH:mm:ss??????????????????TIMEZONE???????????????+08:00??????????????????????????????UTC
    // 8???????????????????????????????????????2015-05-20T13:29:35+08:00?????????????????????2015???5???20???
    // 13???29???35??????????????????2018-06-08T10:34:56+08:00
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @ZKColumn(name = "c_wx_time_expire")
    Date timeExpire;

    // ???????????? attach string[1,128] ??? body ????????????????????????API???????????????????????????????????????????????????????????????;
    // ???????????????????????????
    @Length(min = 1, max = 128, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_wx_attach")
    String attach;

    // ???????????? notify_url string[1,256] ??? body
    // ??????URL???????????????????????????URL?????????????????????????????????????????????https?????????
    // ?????????URL???????????????https://www.weixin.qq.com/wxpay/pay.php
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 256, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_wx_notify_url")
    String notifyUrl;

    // ?????????????????? goods_tag string[1,32] ??? body ??????????????????; ????????????WXG
    @Length(min = 1, max = 32, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_wx_goods_tag")
    String goodsTag;

    // ?????????????????????????????????????????????
    @Length(min = 1, max = 64, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_wx_res_status_code")
    String wxResStatusCode;

    // ???????????????????????????????????????????????????????????????????????????????????????????????????2?????????????????????wx201410272009395522657a690389285100
    @Length(min = 1, max = 64, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_wx_prepay_id")
    String prepayId;

    // ??????????????????????????????????????????
    @ZKColumn(name = "c_wx_prepay_id_date")
    Date prepayIdDate;

    // ?????????????????????????????????
    @ZKColumn(name = "c_pay_sign_date")
    String paySignDate;

    // +???????????? amount object ??? body ??????????????????
    @Transient
    ZKPayGetAmount payGetAmount;

    // +????????? payer object ??? body ???????????????
    @Transient
    ZKPayGetPayer payGetPayer;

    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////

    // ????????????
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @ZKColumn(name = "c_pay_status")
    ZKPayStatus payStatus;

    // ??????????????????-???????????????JSAPI, H5, APP, NATIVE, MINIPROGRAM;
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @ZKColumn(name = "c_wx_channel")
    ZKPayGetChannel wxChannel;

    // ???????????????????????????????????????????????? mchid ??? ??????ID appid
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 32, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_pay_group_code")
    String payGroupCode;

    // ???????????????????????????????????????????????????????????????0-?????????????????????
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 16, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_business_code")
    String businessCode;

    // ???????????????
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 64, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_business_no")
    String businessNo;

    // ????????????????????????
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @ZKColumn(name = "c_history_date")
    Date historyDate;

    /**
     * @return payGetOrderId sa
     */
    public String getPayGetOrderId() {
        return payGetOrderId;
    }

    /**
     * @param payGetOrderId
     *            the payGetOrderId to set
     */
    public void setPayGetOrderId(String payGetOrderId) {
        this.payGetOrderId = payGetOrderId;
    }

    /**
     * @return appid sa
     */
    public String getAppid() {
        return appid;
    }

    /**
     * @param appid
     *            the appid to set
     */
    public void setAppid(String appid) {
        this.appid = appid;
    }

    /**
     * @return mchid sa
     */
    public String getMchid() {
        return mchid;
    }

    /**
     * @param mchid
     *            the mchid to set
     */
    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    /**
     * @return descriptionRename sa
     */
    public String getDescriptionRename() {
        return descriptionRename;
    }

    /**
     * @param descriptionRename
     *            the descriptionRename to set
     */
    public void setDescriptionRename(String descriptionRename) {
        this.descriptionRename = descriptionRename;
    }

    /**
     * @return timeExpire sa
     */
    public Date getTimeExpire() {
        return timeExpire;
    }

    /**
     * @param timeExpire
     *            the timeExpire to set
     */
    public void setTimeExpire(Date timeExpire) {
        this.timeExpire = timeExpire;
    }

    /**
     * @return attach sa
     */
    public String getAttach() {
        return attach;
    }

    /**
     * @param attach
     *            the attach to set
     */
    public void setAttach(String attach) {
        this.attach = attach;
    }

    /**
     * 
     * @return notifyUrl sa
     */
    public String getNotifyUrl() {
        return notifyUrl;
    }

    /**
     * @param notifyUrl
     *            the notifyUrl to set
     */
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    /**
     * @return goodsTag sa
     */
    public String getGoodsTag() {
        return goodsTag;
    }

    /**
     * @param goodsTag
     *            the goodsTag to set
     */
    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    /**
     * @return payGetAmount sa
     */
    public ZKPayGetAmount getPayGetAmount() {
        return payGetAmount;
    }

    /**
     * @param payGetAmount
     *            the payGetAmount to set
     */
    public void setPayGetAmount(ZKPayGetAmount payGetAmount) {
        this.payGetAmount = payGetAmount;
    }

    /**
     * @return payGetPayer sa
     */
    public ZKPayGetPayer getPayGetPayer() {
        return payGetPayer;
    }

    /**
     * @param payGetPayer
     *            the payGetPayer to set
     */
    public void setPayGetPayer(ZKPayGetPayer payGetPayer) {
        this.payGetPayer = payGetPayer;
    }

    /**
     * @return payStatus sa
     */
    public ZKPayStatus getPayStatus() {
        return payStatus;
    }

    /**
     * @param payStatus
     *            the payStatus to set
     */
    public void setPayStatus(ZKPayStatus payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * @return businessNo sa
     */
    public String getBusinessNo() {
        return businessNo;
    }

    /**
     * @param businessNo
     *            the businessNo to set
     */
    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    /**
     * @return prepayId sa
     */
    public String getPrepayId() {
        return prepayId;
    }

    /**
     * @param prepayId
     *            the prepayId to set
     */
    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    /**
     * @return prepayIdDate sa
     */
    public Date getPrepayIdDate() {
        return prepayIdDate;
    }

    /**
     * @param prepayIdDate
     *            the prepayIdDate to set
     */
    public void setPrepayIdDate(Date prepayIdDate) {
        this.prepayIdDate = prepayIdDate;
    }

    /**
     * @return wxResStatusCode sa
     */
    public String getWxResStatusCode() {
        return wxResStatusCode;
    }

    /**
     * @param wxResStatusCode
     *            the wxResStatusCode to set
     */
    public void setWxResStatusCode(String wxResStatusCode) {
        this.wxResStatusCode = wxResStatusCode;
    }

    /**
     * @return businessCode sa
     */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
     * @param businessCode
     *            the businessCode to set
     */
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    /**
     * @return wxChannel sa
     */
    public ZKPayGetChannel getWxChannel() {
        return wxChannel;
    }

    /**
     * @param wxChannel
     *            the wxChannel to set
     */
    public void setWxChannel(ZKPayGetChannel wxChannel) {
        this.wxChannel = wxChannel;
    }

    /**
     * @return payGroupCode sa
     */
    public String getPayGroupCode() {
        return payGroupCode;
    }

    /**
     * @param payGroupCode
     *            the payGroupCode to set
     */
    public void setPayGroupCode(String payGroupCode) {
        this.payGroupCode = payGroupCode;
    }

    /**
     * @return paySignDate sa
     */
    public String getPaySignDate() {
        return paySignDate;
    }

    /**
     * @param paySignDate
     *            the paySignDate to set
     */
    public void setPaySignDate(String paySignDate) {
        this.paySignDate = paySignDate;
    }

    /**
     * @return historyDate sa
     */
    public Date getHistoryDate() {
        return historyDate;
    }

    /**
     * @param historyDate
     *            the historyDate to set
     */
    public void setHistoryDate(Date historyDate) {
        this.historyDate = historyDate;
    }

}
