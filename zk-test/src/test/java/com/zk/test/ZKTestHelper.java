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
* @Title: ZKTestHelper.java 
* @author Vinson 
* @Package com.zk.test 
* @Description: TODO(simple description this file what to do. ) 
* @date Jan 24, 2022 2:49:58 PM 
* @version V1.0 
*/
package com.zk.test;

import java.util.Random;

import org.junit.Test;

import com.zk.core.utils.ZKDateUtils;

/** 
* @ClassName: ZKTestHelper 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKTestHelper {

    @Test
    public void genPwd() {
        String sourceStr = "1234567890qwertyuiopasdfghjklzxcvbnm!@#$%*()_+?.][QWERTYUIOPASDFGHJKLZXCVBNM";

        int length = 6;
        int maxLen = sourceStr.length();

        String pwd = "";

        Random r = new Random(ZKDateUtils.getToday().getTime());
        for (int i = 0; i < length; i++) {
            int index = r.nextInt(maxLen);
            pwd += sourceStr.toCharArray()[index];
        }
        System.out.println("[^_^:20220124-1452-001] 生成的 password : " + pwd);

    }

}
