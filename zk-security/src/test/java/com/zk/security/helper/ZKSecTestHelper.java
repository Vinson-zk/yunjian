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
* @Title: ZKSecTestHelper.java 
* @author Vinson 
* @Package com.zk.security 
* @Description: TODO(simple description this file what to do. ) 
* @date Jul 28, 2021 2:52:07 PM 
* @version V1.0 
*/
package com.zk.security.helper;

import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.zk.security.helper.service.TestService;
import com.zk.security.mgt.ZKSecSecurityManager;
import com.zk.security.ticket.ZKSecTicketManager;
import com.zk.security.web.mgt.ZKSecWebSecurityManager;

import junit.framework.TestCase;

/** 
* @ClassName: ZKSecTestHelper 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKSecTestHelper {

    public static final String[] config_sprint_paths = new String[] { "classpath:test_spring_context.xml" };

    public static FileSystemXmlApplicationContext ctxSprint;

    public static final String[] config_sec_paths = new String[] { "classpath:sec/test_spring_context_sec.xml",
            "classpath:test_spring_context.xml" };

    public static FileSystemXmlApplicationContext ctxSec;

    public static final String[] config_sec_web_paths = new String[] { "classpath:sec/test_spring_context_sec_web.xml",
            "classpath:test_spring_context.xml" };

    public static FileSystemXmlApplicationContext ctxSecWeb;

    static {
        try {
            ctxSprint = new FileSystemXmlApplicationContext(config_sprint_paths);
            TestCase.assertNotNull(ctxSprint);

            ctxSec = new FileSystemXmlApplicationContext(config_sec_paths);
            TestCase.assertNotNull(ctxSec);

            ctxSecWeb = new FileSystemXmlApplicationContext(config_sec_web_paths);
            TestCase.assertNotNull(ctxSecWeb);

            ZKSecSecurityManager securityManager = ctxSec.getBean(ZKSecSecurityManager.class);
            TestCase.assertNotNull(securityManager);

            ZKSecWebSecurityManager webSecurityManager = ctxSecWeb.getBean(ZKSecWebSecurityManager.class);
            TestCase.assertNotNull(webSecurityManager);

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {

        try {
            ZKSecTicketManager tm = ctxSec.getBean("ticketManager", ZKSecTicketManager.class);
            TestCase.assertNotNull(tm);

            TestService ts = ctxSec.getBean(TestService.class);
            TestCase.assertNotNull(ts.getTicketManager());

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

}
