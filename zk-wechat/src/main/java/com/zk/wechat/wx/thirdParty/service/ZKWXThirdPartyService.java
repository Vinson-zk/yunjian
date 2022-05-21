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
* @Title: ZKWXThirdPartyService.java 
* @author Vinson 
* @Package com.zk.wechat.wx.thirdParty.service 
* @Description: TODO(simple description this file what to do. ) 
* @date May 19, 2022 2:35:18 PM 
* @version V1.0 
*/
package com.zk.wechat.wx.thirdParty.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zk.core.exception.ZKCodeException;
import com.zk.core.utils.ZKStringUtils;
import com.zk.wechat.common.ZKWechatCacheUtils;
import com.zk.wechat.officialAccounts.entity.ZKOfficialAccounts;
import com.zk.wechat.officialAccounts.entity.ZKOfficialAccountsUser;
import com.zk.wechat.officialAccounts.service.ZKOfficialAccountsService;
import com.zk.wechat.officialAccounts.service.ZKOfficialAccountsUserService;
import com.zk.wechat.thirdParty.entity.ZKThirdParty;
import com.zk.wechat.thirdParty.service.ZKThirdPartyService;
import com.zk.wechat.wx.officialAccounts.ZKWXOfficialAccountsConstants;
import com.zk.wechat.wx.officialAccounts.msgBean.ZKWXAccountAuthAccessToken;
import com.zk.wechat.wx.officialAccounts.msgBean.ZKWXUserAuthAccessToken;
import com.zk.wechat.wx.officialAccounts.service.ZKWXOfficialAccountsUserService;
import com.zk.wechat.wx.thirdParty.msgBean.ZKWXTPComponentAccessToken;

/** 
* @ClassName: ZKWXThirdPartyService 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
@Service
public class ZKWXThirdPartyService {

    /**
     * 日志对象
     */
    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    ZKWXApiThirdPartyService wxApiThirdPartyService;

    @Autowired
    ZKWXOfficialAccountsUserService wxOfficialAccountsUserService;

    @Autowired
    ZKThirdPartyService thirdPartyService;

    @Autowired
    ZKOfficialAccountsService thirdPartyAuthAccountService;

    @Autowired
    ZKOfficialAccountsUserService officialAccountsUserService;

    /**
     * 请求微信平台获取第三方平台component_access_token
     *
     * @Title: getComponentAccessToken
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Nov 5, 2021 5:14:19 PM
     * @param thirdPartyAppid
     * @return
     * @return ZKWXTPComponentAccessToken
     */
    public ZKWXTPComponentAccessToken getComponentAccessToken(String thirdPartyAppid) {
        ZKThirdParty thirdParty = thirdPartyService.get(thirdPartyAppid);

        if (thirdParty == null) {
            log.error("[>_<_^:20211104-1758-001] 第三方平台账号未录入系统，无法获取 ComponentAccessToken");
            throw new ZKCodeException("zk.wechat.010006", "第三方平台账号未录入系统，无法获取 ComponentAccessToken");
        }

        /** 1、从缓存中取 第三方平台 componentAccessToken 并判断是否存在或过期 */
        ZKWXTPComponentAccessToken componentAccessToken = ZKWechatCacheUtils
                .getWXTPComponentAccessToken(thirdPartyAppid);

        /** 1、判断第三方平台 componentAccessToken 是否过期，未过期，直接返回 */
        if (componentAccessToken != null && !componentAccessToken.isExpires()) {
            return componentAccessToken;
        }

        /** 2、判断第三方平台 令牌是否存在，存在继续 */
        String componentVerifyTicket = thirdParty.getWxTicket();
        if (ZKStringUtils.isEmpty(componentVerifyTicket)) {
            log.error("[>_<_^:20211104-1758-002] 第三方平台账号令牌不存在，无法获取 ComponentAccessToken");
            throw new ZKCodeException("zk.wechat.010007", "第三方平台账号令牌不存在，无法获取 ComponentAccessToken");
        }

        /** 3、取 token 并保存 */
        if (componentAccessToken == null) {
            componentAccessToken = wxApiThirdPartyService.api_component_token(thirdParty.getPkId(),
                    thirdParty.getWxAppSecret(), thirdParty.getWxTicket());

        }
        if (componentAccessToken == null) {
            log.error("[>_<_^:20220520-0059-001] 取第三方平台 AccessToken 失败");
            throw new ZKCodeException("zk.wechat.010016", "取第三方平台 AccessToken 失败");
        }
        else {
            // 保存 第三方平台 AccessToken 到缓存
            ZKWechatCacheUtils.putWXTPComponentAccessToken(componentAccessToken);
        }
        return componentAccessToken;
    }

    /**
     * 取 授权账号的 wxAccountAuthAccessToken
     *
     * @Title: getAccountAuthAccessToken
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date May 20, 2022 12:49:00 AM
     * @param thirdPartyAppid
     * @param accountAppid
     * @return
     * @return ZKWXAccountAuthAccessToken
     */
    public ZKWXAccountAuthAccessToken getAccountAuthAccessToken(String thirdPartyAppid, String accountAppid) {

        ZKOfficialAccounts thirdPartyAuthAccount = thirdPartyAuthAccountService.getByAccountAppid(thirdPartyAppid,
                accountAppid);

        if (thirdPartyAuthAccount == null) {
            log.error(
                    "[>_<_^:20211108-1848-001] 目标授权方未向第三方平台授权，无法获取 AuthAccountAccessToken; thirdPartyAppid：{}，accountAppid：{}",
                    thirdPartyAppid, accountAppid);
            throw new ZKCodeException("zk.wechat.010011", "目标授权方未向第三方平台授权，无法获取 AuthAccountAccessToken");
        }

        /** 1、从缓存中取 目标授权方 wxAccountAuthAccessToken 并判断是否存在或过期 */
        ZKWXAccountAuthAccessToken wxAccountAuthAccessToken = ZKWechatCacheUtils.getWXAccountAuthAccessToken(
                ZKWXAccountAuthAccessToken.makeIdentification(thirdPartyAppid, accountAppid));

        /** 1、判断 目标授权方 wxAccountAuthAccessToken 是否过期，未过期，直接返回 */
        if (wxAccountAuthAccessToken != null && !wxAccountAuthAccessToken.isExpires()) {
            return wxAccountAuthAccessToken;
        }

        /** 2、判断 目标授权方刷新 AuthorizerRefreshToken 是否存在，存在继续 */
        if (ZKStringUtils.isEmpty(thirdPartyAuthAccount.getWxAuthorizerRefreshToken())) {
            log.error(
                    "[>_<_^:20211108-1848-002] 目标授权方刷新 AuthorizerRefreshToken 不存在，无法获取 AuthAccountAccessToken; thirdPartyAppid：{}，accountAppid：{}",
                    thirdPartyAppid, accountAppid);
            throw new ZKCodeException("zk.wechat.010012", "目标授权方刷新 AuthorizerRefreshToken 不存在");
        }

        /** 3、刷新 AuthorizerRefreshToken 并保存 */
        if (wxAccountAuthAccessToken == null) {
            wxAccountAuthAccessToken = wxApiThirdPartyService.api_authorizer_token(thirdPartyAppid,
                    this.getComponentAccessToken(thirdPartyAppid).getAccessToken(), accountAppid,
                    thirdPartyAuthAccount.getWxAuthorizerRefreshToken());
        }
        if (wxAccountAuthAccessToken == null) {
            log.error("[>_<_^:20220520-0046-001] 目标授权方刷新 AuthorizerRefreshToken 失败; thirdPartyAppid：{}，accountAppid：{}",
                    thirdPartyAppid, accountAppid);
            throw new ZKCodeException("zk.wechat.010017", "取授权方 AccessToken 失败");
        }
        else {
            // 保存目标授权账号 accessToken 到 缓存
            ZKWechatCacheUtils.putWXAccountAuthAccessToken(wxAccountAuthAccessToken);
        }
        return wxAccountAuthAccessToken;
    }

    /**
     * 取 授权用户的 AccessToken
     *
     * @Title: getZKWXUserAuthAccessToken
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date May 20, 2022 12:49:06 AM
     * @param thirdPartyAppid
     * @param accountAppid
     * @param openid
     * @return
     * @return ZKWXUserAuthAccessToken
     */
    public ZKWXUserAuthAccessToken getZKWXUserAuthAccessToken(String thirdPartyAppid, String accountAppid,
            String openid) {
        
        /** 1、从缓存中取 用户 userAuthAccessToken 并判断是否存在或过期 */
        ZKWXUserAuthAccessToken userAuthAccessToken = ZKWechatCacheUtils.getWXUserAccessToken(
                ZKWXUserAuthAccessToken.makeIdentification(thirdPartyAppid, accountAppid, openid));
        if (userAuthAccessToken != null && !userAuthAccessToken.isExpires()) {
            return userAuthAccessToken;
        }
        /** 2、用户 userAuthAccessToken 在缓存中不存在，从用户中取刷新来刷新用户 userAuthAccessToken */
        ZKOfficialAccountsUser officialAccountsUser = officialAccountsUserService.getByOpenId(thirdPartyAppid,
                accountAppid, openid);
        if (officialAccountsUser == null) {
            log.error(
                    "[>_<_^:20220520-0031-001] 用户不存在，无法获取 userAuthAccessToken; thirdPartyAppid：{}，accountAppid：{}，openid：{}",
                    thirdPartyAppid, accountAppid, openid);
            throw new ZKCodeException("zk.wechat.010015", "用户不存在或未授权");
        }
        userAuthAccessToken = officialAccountsUser.getAccessToken();
        if (userAuthAccessToken == null || ZKStringUtils.isEmpty(userAuthAccessToken.getRefreshToken())) {
            log.error(
                    "[>_<_^:20220520-0031-002] 用户刷新 Token 不存在，需要重新授权; thirdPartyAppid：{}，accountAppid：{}，openid：{}",
                    thirdPartyAppid, accountAppid, openid);
            throw new ZKCodeException("zk.wechat.010018", "取用户 AccessToken 失败");
        }
        /** 3、使用用户 刷新 Token 刷新用户 userAuthAccessToken */
        userAuthAccessToken = this.wxApiThirdPartyService.api_user_auth_refresh_token(thirdPartyAppid,
                this.getComponentAccessToken(thirdPartyAppid).getAccessToken(), accountAppid,
                userAuthAccessToken.getRefreshToken());
        if(userAuthAccessToken == null) {
            log.error(
                    "[>_<_^:20220520-0031-003] 刷新 用户 Token 失败; thirdPartyAppid：{}，accountAppid：{}，openid：{}",
                    thirdPartyAppid, accountAppid, openid);
            throw new ZKCodeException("zk.wechat.010018", "取用户 AccessToken 失败");
        }
        else {
            // 保存用户 accessToken 到 缓存
            ZKWechatCacheUtils.putWXUserAccessToken(userAuthAccessToken);
        }
        return userAuthAccessToken;
    }

    // 根据用户授权代码取用户 accessToken 并取用户用户信息保存用户
    public void getUserAccessTokenByAuthCode(String thirdPartyAppId, String funcKey, String code, String state,
            String appId) {
        ZKWXUserAuthAccessToken userAuthAccessToken = this.wxApiThirdPartyService.api_user_auth_access_token(
                thirdPartyAppId, this.getComponentAccessToken(thirdPartyAppId).getAccessToken(), appId, code);
        if (userAuthAccessToken == null) {
            log.error("[>_<_^:20220520-1104-003] 刷新 用户 Token 失败; thirdPartyAppid：{}，accountAppid：{}，code：{}",
                    thirdPartyAppId, appId, code);
            throw new ZKCodeException("zk.wechat.010018", "取用户 AccessToken 失败");
        }
        else {
            // 保存用户 accessToken 到 缓存
            ZKWechatCacheUtils.putWXUserAccessToken(userAuthAccessToken);
        }

        // 取用户信息，并创建保存用户
        ZKOfficialAccountsUser officialAccountsUser;
        if(!ZKStringUtils.isEmpty(userAuthAccessToken.getScope()) && (userAuthAccessToken.getScope().indexOf(ZKWXOfficialAccountsConstants.KeyAuthUserScope.snsapi_userinfo) > -1)) {
            // 可以获取用户基本信息
            officialAccountsUser = this.wxOfficialAccountsUserService.getUserInfo(thirdPartyAppId, appId,
                    userAuthAccessToken.getOpenid(),
                    userAuthAccessToken.getAccessToken());
        }
        else {
            // 取用户 UnionID
            officialAccountsUser = this.wxOfficialAccountsUserService.getUserUnionID(thirdPartyAppId, appId,
                    userAuthAccessToken.getOpenid(),
                    userAuthAccessToken.getAccessToken());
        }
        officialAccountsUser.setWxChannel(ZKOfficialAccountsUser.KeyWxCannel.webAuth);
        officialAccountsUser.setWxSubscribeStatus(ZKOfficialAccountsUser.KeyWxSubscribeStatus.unknown);
        this.officialAccountsUserService.save(officialAccountsUser);

    }

}
