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
* @Title: ZKDistributedRealm.java 
* @author Vinson 
* @Package com.zk.framework.security.realm 
* @Description: TODO(simple description this file what to do. ) 
* @date May 16, 2022 11:46:46 AM 
* @version V1.0 
*/
package com.zk.framework.security.realm;

import com.zk.core.utils.ZKEnvironmentUtils;
import com.zk.core.web.ZKMsgRes;
import com.zk.security.authz.ZKSecAuthorizationInfo;
import com.zk.security.authz.ZKSecSimpleAuthorizationInfo;
import com.zk.security.principal.ZKSecPrincipal;
import com.zk.security.principal.pc.ZKSecPrincipalCollection;
import com.zk.security.realm.ZKSecAbstractRealm;
import com.zk.security.ticket.ZKSecTicketManager;
import com.zk.security.token.ZKSecAuthenticationToken;
import com.zk.sys.api.sec.ZKSysSecAuthcApi;

/** 
* @ClassName: ZKDistributedRealm 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKDistributedRealm extends ZKSecAbstractRealm {

    private ZKSecTicketManager ticketManager;

    public ZKDistributedRealm() {
        super(ZKDistributedRealm.class.getName());
    }

    public ZKDistributedRealm(String realmName) {
        super(realmName);
    }

    protected ZKSysSecAuthcApi sysSecAuthcApi;

    public ZKSysSecAuthcApi getSysSecAuthcApi() {
        if (sysSecAuthcApi == null) {
            sysSecAuthcApi = ZKEnvironmentUtils.getApplicationContext().getBean(ZKSysSecAuthcApi.class);
        }
        return sysSecAuthcApi;
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

    @Override
    public boolean supports(ZKSecAuthenticationToken authcToken) {
//        if (authcToken instanceof ZKSecAuthcUserToken) {
//            return true;
//        }
        return false;
    }

    /**
     * ???????????????
     * 
     * @param token
     * @return
     * @throws com.zk.security.exception.ZKSecCodeException
     * @throws Exception
     */
    @Override
    protected ZKSecPrincipalCollection doAuthentication(ZKSecAuthenticationToken authcToken) {
        return null;
    }

    @Override
    public ZKSecAuthorizationInfo doGetZKSecAuthorizationInfo(ZKSecPrincipalCollection principalCollection) {
        ZKSecSimpleAuthorizationInfo authorizationInfo = null;
        ZKMsgRes res = this.getSysSecAuthcApi().getUserAuthc();
        if (res.isOk()) {
            authorizationInfo = res.getDataByClass(ZKSecSimpleAuthorizationInfo.class);
            log.info("[^_^:20220517-1148-001] ????????????????????????, msg???{}", res.getDataStr());
        }
        else {
            log.error("[>_<:20220517-1148-002] ????????????????????????, msg???{}", res.getDataStr());
//            authorizationInfo = new ZKSecSimpleAuthorizationInfo();
        }
        return authorizationInfo;
    }

    /**
     * ????????????????????????
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

    }

}
