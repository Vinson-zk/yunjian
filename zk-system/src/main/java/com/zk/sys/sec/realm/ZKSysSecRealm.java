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
* @Title: ZKSysSecRealm.java 
* @author Vinson 
* @Package com.zk.sys.common.sec.realm 
* @Description: TODO(simple description this file what to do. ) 
* @date Apr 20, 2022 4:53:07 PM 
* @version V1.0 
*/
package com.zk.sys.sec.realm;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.core.utils.ZKEnvironmentUtils;
import com.zk.framework.security.ZKAuthPermission;
import com.zk.framework.security.utils.ZKUserCacheUtils;
import com.zk.security.authz.ZKSecAuthorizationInfo;
import com.zk.security.authz.ZKSecSimpleAuthorizationInfo;
import com.zk.security.exception.ZKSecCodeException;
import com.zk.security.principal.ZKSecDefaultUserPrincipal;
import com.zk.security.principal.ZKSecPrincipal;
import com.zk.security.principal.ZKSecUserPrincipal;
import com.zk.security.principal.pc.ZKSecDefaultPrincipalCollection;
import com.zk.security.principal.pc.ZKSecPrincipalCollection;
import com.zk.security.realm.ZKSecAbstractRealm;
import com.zk.security.realm.ZKSecSampleRealm;
import com.zk.security.ticket.ZKSecTicket;
import com.zk.security.ticket.ZKSecTicketManager;
import com.zk.security.token.ZKSecAuthcUserToken;
import com.zk.security.token.ZKSecAuthenticationToken;
import com.zk.security.utils.ZKSecSecurityUtils;
import com.zk.sys.org.entity.ZKSysOrgUser;
import com.zk.sys.sec.service.ZKSysSecAuthService;
import com.zk.sys.sec.service.ZKSysSecUserService;

/** 
* @ClassName: ZKSysSecRealm 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKSysSecRealm extends ZKSecAbstractRealm {
    
    // ????????????????????? KEY
    public static interface Key_Auth_Strategy {
        // ???????????????????????????
        public static final String name = "Auth_Strategy";

        // ??????????????? ???????????????????????????
        public static final String Auth_Dept = "Auth_Dept";

        // ??????????????? ???????????????????????????
        public static final String Auth_User = "Auth_User";

        // ??????????????? ???????????????????????????
        public static final String Auth_Role = "Auth_Role";

        // ??????????????? ???????????????????????????
        public static final String Auth_Rank = "Auth_Rank";

        // ??????????????? ?????????????????????????????????
        public static final String Auth_UserType = "Auth_UserType";
    }

    /**
     * ????????????
     */
    protected Logger log = LoggerFactory.getLogger(getClass());

    ZKSysSecUserService secUserService;

    ZKSysSecAuthService sysSecAuthService;

    ZKSecTicketManager ticketManager;

    public ZKSysSecRealm() {
        super(ZKSecSampleRealm.class.getName());
        this.setAuthorizationInfoStore(null);
    }

    public ZKSysSecRealm(String realmName) {
        super(realmName);
        this.setAuthorizationInfoStore(null);
    }

    /**
     * @return ticketManager sa
     */
    public ZKSecTicketManager getTicketManager() {
        return ticketManager;
    }

    /**
     * @param ticketManager
     *            the ticketManager to set
     */
    public void setTicketManager(ZKSecTicketManager ticketManager) {
        this.ticketManager = ticketManager;
    }

    /**
     * @return sysSetItemService sa
     */
    public ZKSysSecAuthService getSysSecAuthService() {
        if (sysSecAuthService == null) {
            sysSecAuthService = ZKEnvironmentUtils.getApplicationContext().getBean(ZKSysSecAuthService.class);
        }
        return sysSecAuthService;
    }

    /**
     * @param sysSetItemService
     *            the sysSetItemService to set
     */
    public void setSysSecAuthService(ZKSysSecAuthService sysSecAuthService) {
        this.sysSecAuthService = sysSecAuthService;
    }

    /**
     * @return secUserService sa
     */
    public ZKSysSecUserService getSecUserService() {
        if (secUserService == null) {
            secUserService = ZKEnvironmentUtils.getApplicationContext().getBean(ZKSysSecUserService.class);
        }
        return secUserService;
    }

    /**
     * @param secUserService
     *            the secUserService to set
     */
    public void setSecUserService(ZKSysSecUserService secUserService) {
        this.secUserService = secUserService;
    }

    @Override
    public boolean supports(ZKSecAuthenticationToken authcToken) {
        if (authcToken instanceof ZKSecAuthcUserToken) {
            return true;
        }
        return false;
    }

    /**
     * ???????????????
     * 
     * @param token
     * @return
     * @throws ZKSecCodeException
     * @throws Exception
     */
    @Override
    protected ZKSecPrincipalCollection doAuthentication(ZKSecAuthenticationToken authcToken) {
        ZKSecPrincipalCollection pc = new ZKSecDefaultPrincipalCollection();
        ZKSecAuthcUserToken authcUserToken = (ZKSecAuthcUserToken) authcToken;
        ZKSysOrgUser loginUser = this.getSecUserService().login(authcUserToken);
        if (loginUser != null) {
            ZKSecPrincipal<String> p = new ZKSecDefaultUserPrincipal<String>(loginUser.getPkId(),
                    loginUser.getAccount(), loginUser.getNickname(), authcUserToken.getOsType(),
                    authcUserToken.getUdid(), authcUserToken.getAppType(), authcUserToken.getAppId(),
                    loginUser.getGroupCode(), loginUser.getCompanyId(), loginUser.getCompanyCode());
            pc.add(this.getRealmName(), p);
            return pc;
        }
        else {
            log.error("[>_<:2022425-1723-001] ??????????????????????????????????????? {}-{}", authcUserToken.getCompanyCode(),
                    authcUserToken.getUsername());
            throw new ZKSecCodeException("zk.sys.020003");
        }
    }

    @Override
    public ZKSecAuthorizationInfo doGetZKSecAuthorizationInfo(ZKSecPrincipalCollection principalCollection) {
        ZKSecPrincipal<?> pp = principalCollection.getPrimaryPrincipal();
        if (ZKSecPrincipal.KeyType.Distributed_server == pp.getType()) {
            // ?????????????????????????????????????????????????????????????????????
            return new ZKSecSimpleAuthorizationInfo();
        }
        // ??????????????? API ????????????
        ZKAuthPermission p = ZKUserCacheUtils.getAuth(ZKUserCacheUtils.getUser(pp.getPkId()));
        ZKSecAuthorizationInfo authorizationInfo = this.getSysSecAuthService().paseByAuthPermission(p);
        return authorizationInfo;
    }

    /**
     * ????????????????????????; ????????????
     * 
     * @param principalCollection
     *            ??????
     * @param permissionCode
     *            ????????????
     * @return
     */
    @Override
    protected boolean doCheckPermission(ZKSecPrincipalCollection principalCollection, String permissionCode) {
        ZKSecPrincipal<?> pp = principalCollection.getPrimaryPrincipal();
        if (ZKSecPrincipal.KeyType.Distributed_server == pp.getType()) {
            // ????????????????????????????????????
            return true;
        }
        return false;
    }

    /**
     * ?????? api???????????? ?????? ??????
     * 
     * @param principalCollection
     *            ??????
     * @param apiCode
     *            api???????????? ??????
     * @return
     */
    @Override
    protected boolean doCheckApiCode(ZKSecPrincipalCollection principalCollection, String apiCode) {
        ZKSecPrincipal<?> pp = principalCollection.getPrimaryPrincipal();
        if (ZKSecPrincipal.KeyType.Distributed_server == pp.getType()) {
            // ????????????????????????????????????
            return true;
        }
        ZKSecAuthorizationInfo authorizationInfo = this.getZKSecAuthorizationInfo(principalCollection);
        boolean r = false;
        if (authorizationInfo != null) {
            // ?????????????????? admin ????????????????????????????????????
            if (!r && authorizationInfo.getAuthCodes() != null) {
                r = authorizationInfo.getAuthCodes().contains("admin");
            }
        }
        return r;
    }

    @Override
    public void doLimitPrincipalTicketCount(ZKSecPrincipalCollection principalCollection) {
        for (ZKSecPrincipal<?> p : principalCollection) {
            if (p instanceof ZKSecUserPrincipal<?>) {
                List<ZKSecTicket> tks = null;
                ZKSecTicket tk = ZKSecSecurityUtils.getTikcet();
                if (tk == null) {
                    tks = this.getTicketManager().findTickeByPrincipal(p, null);
                }
                else {
                    tks = this.getTicketManager().findTickeByPrincipal(p, Arrays.asList(tk));
                }

                if (tks != null && tks.size() > 0) {
                    if (ZKSecSecurityUtils.getSubject().isAuthenticated()) {
                        // ??????????????????????????????????????????????????????????????????????????????????????????
                        for (ZKSecTicket t : tks) {
                            if (t.isValid()) {
                                t.stop();
                                t.put(ZKSecTicket.KeyTicketInfo.stop_info_code, "zk.sec.000012"); // zk.sec.000012=????????????????????????????????????????????????
                            }
                            else {
                                log.info("[^_^:20220518-0906-001] ?????? [{}] ?????????", t.getTkId());
                            }
                        }
                    }
                    else {
                        // ????????????????????????????????????????????????
                        ZKSecSecurityUtils.getSubject().logout();
                        throw new ZKSecCodeException("zk.sec.000012"); // zk.sec.000012=????????????????????????????????????????????????
                    }
                }
            }
        }
    }

}


