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
* @Title: ZKMailUtilsTestTest.java 
* @author Vinson 
* @Package com.zk.mail 
* @Description: TODO(simple description this file what to do. ) 
* @date May 26, 2022 10:26:05 PM 
* @version V1.0 
*/
package com.zk.mail.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

import com.zk.core.commons.ZKContentType;
import com.zk.core.commons.ZKStreamDataSource;
import com.zk.mail.common.ZKMailAuthenticator;

import junit.framework.TestCase;

/**
 * @ClassName: ZKMailUtilsTest
 * @Description: TODO(simple description this class what to do. )
 * @author Vinson
 * @version 1.0
 */
public class ZKMailUtilsTest {

    @Test
    public void testSendMail() {
        try {
            String host = "smtp.126.com";
            // # 发送邮件服务器端口
            String port = "465";
            // # 发送邮件方式 SSL TLS
            String type = "SSL";
            // # 发送邮件账号信息，账号、密码、名称
            String account = "binary_space@126.com";
            // # DABDSDVNUZPVLCRD qaz123wsx CJBDKRMXRZPGQESW
            String password = "DABDSDVNUZPVLCRD";
            boolean validate = true;
            
            String sendAddress = account;
            String sendName = "sendName";
            String subject = "subject";
            String content = "content";
            String recipientMailAddr = "binary_space@126.com";
            InputStream is = new ByteArrayInputStream("inputStream-Attachments".getBytes());
//            File file = new File("testFileAttachments.txt");
//            ZKFileUtils.writeFile("testFileAttachments.txt".getBytes(), file, true);

            Properties mailProperties = ZKMailUtils.getProperties(host, port, type, validate);
            ZKMailAuthenticator mailAuthenticator = ZKMailUtils.getMailAuthenticator(account, password);
            boolean res = ZKMailUtils.sendMail(recipientMailAddr, mailProperties, mailAuthenticator, sendAddress,
                    sendName, subject, content, false,
                    new ZKStreamDataSource("testAttachments.txt", ZKContentType.TEXT_PLAIN_UTF8.getContentType(),
                            is, null));

            TestCase.assertTrue(res);
        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

}
