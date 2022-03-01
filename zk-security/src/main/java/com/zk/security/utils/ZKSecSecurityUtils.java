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
* @Title: ZKSecSecurityUtils.java 
* @author Vinson 
* @Package com.zk.security.utils 
* @Description: TODO(simple description this file what to do. ) 
* @date Jul 26, 2021 6:42:11 PM 
* @version V1.0 
*/
package com.zk.security.utils;

import com.zk.security.exception.ZKSecUnknownException;
import com.zk.security.mgt.ZKSecSecurityManager;
import com.zk.security.principal.ZKSecDevPrincipal;
import com.zk.security.principal.ZKSecPrincipal;
import com.zk.security.principal.ZKSecUserPrincipal;
import com.zk.security.principal.pc.ZKSecPrincipalCollection;
import com.zk.security.subject.ZKSecSubject;
import com.zk.security.thread.ZKSecThreadContext;
import com.zk.security.ticket.ZKSecTicket;

/** 
* @ClassName: ZKSecSecurityUtils 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKSecSecurityUtils {

    private static ZKSecSecurityManager securityManager;

    /**
     * 取当前登录用户所有身份
     */
    public static ZKSecPrincipalCollection getPrincipalCollection() {
        if (getSubject() != null) {
            return getSubject().getPrincipalCollection();
        }
        return null;
    }

    /**
     * 取当前登录用户身份
     * 
     * @return
     */
    public static ZKSecUserPrincipal<?> getUserPrincipal() {
        return getUserPrincipal(getTikcet());
    }

    public static ZKSecUserPrincipal<?> getUserPrincipal(ZKSecTicket tk) {
        if (tk != null) {
            return (ZKSecUserPrincipal<?>) getPrincipalByType(tk, ZKSecPrincipal.TYPE.User);
        }
        return null;
    }

    /**
     * 取当前 开发者 身份
     * 
     * @return
     */
    public static ZKSecDevPrincipal<?> getAppPrincipal() {
        return getDevPrincipal(getTikcet());
    }

    public static ZKSecDevPrincipal<?> getDevPrincipal(ZKSecTicket tk) {
        if (tk != null) {
            return (ZKSecDevPrincipal<?>) getPrincipalByType(tk, ZKSecPrincipal.TYPE.Developer);
        }
        return null;
    }

    /**
     * 取指定类型的身份
     * 
     * @param type
     * @return
     */
    protected static ZKSecPrincipal<?> getPrincipalByType(ZKSecTicket tk, int type) {
        if (tk != null) {
            ZKSecPrincipalCollection pc = tk.getPrincipalCollection();
            if (pc != null && !pc.isEmpty()) {
                for (ZKSecPrincipal<?> p : pc.asSet()) {
                    if (p.getType() == type) {
                        return p;
                    }
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <ID> ID getUserId() {
        ZKSecTicket tk = getTikcet();
        if (tk != null) {
            ZKSecPrincipal<?> userP = tk.getPrincipalCollection().getPrimaryPrincipal();
            if (userP != null) {
                return (ID) userP.getPkId();
            }
        }
        return null;
    }

    // 取当前令牌
    public static ZKSecTicket getTikcet() {
        if (getSubject() != null) {
            return getSubject().getTicket(false);
        }
        return null;
    }

    public static ZKSecSubject getSubject() {
        ZKSecSubject subject = ZKSecThreadContext.getSubject();
        if (subject == null) {
            subject = getSecurityManager().createSubject();
            ZKSecThreadContext.bind(subject);
        }
        return subject;
    }

    public static void setSecurityManager(ZKSecSecurityManager securityManager) {
        ZKSecSecurityUtils.securityManager = securityManager;
    }

    public static ZKSecSecurityManager getSecurityManager() {
        ZKSecSecurityManager secManager = ZKSecThreadContext.getSecurityManager();
        if (secManager == null) {
            secManager = ZKSecSecurityUtils.securityManager;
        }
        if (secManager == null) {
            String msg = "No SecManager accessible to the calling code, either bound to the "
                    + ZKSecThreadContext.class.getName()
                    + " or as a vm static singleton.  This is an invalid application "
                    + "configuration.";
            throw new ZKSecUnknownException(msg);
        }
        return secManager;
    }

}
