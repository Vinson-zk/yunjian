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
* @Title: ZKFreeMarkersTest.java 
* @author Vinson 
* @Package com.zk.code.generate.gen.utils 
* @Description: TODO(simple description this file what to do.) 
* @date Mar 18, 2020 12:06:08 AM 
* @version V1.0 
*/
package com.zk.devleopment.tool.gen.action;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.zk.devleopment.tool.gen.entity.ZKColInfo;
import com.zk.devleopment.tool.gen.entity.ZKTableInfo;

import junit.framework.TestCase;

/** 
* @ClassName: ZKFreeMarkersTest 
* @Description: TODO(simple description this class what to do.) 
* @author Vinson 
* @version 1.0 
*/
public class ZKFreeMarkersTest {

    @Test
    public void testRenderString() {
        try {
            String templateString = "123123123${tamps}wqreqw";
            String targetString = "123123123okwqreqw";
            String transferString = null;
            Map<String, Object> modelMap = new HashMap<String, Object>();
            modelMap.put("tamps", "ok");

            transferString = ZKFreeMarkers.renderString(templateString, modelMap);

            System.out.println("[^_^ 20170525-001] " + transferString);
            TestCase.assertEquals(targetString, transferString);

            templateString = "asdf23${ti.subModuleName}wqr${ti.cols[0].attrName}eqw";
            targetString = "asdf23[^_^:ok]wqr[^_^:ok]eqw";
            transferString = null;
            ZKTableInfo ti = new ZKTableInfo();
            ti.setSubModuleName("[^_^:ok]");
            ZKColInfo ci = new ZKColInfo();
            ci.setAttrName("[^_^:ok]");
            ti.setCols(Arrays.asList(ci));

            modelMap.put("ti", ti);

            transferString = ZKFreeMarkers.renderString(templateString, modelMap);

            System.out.println("[^_^ 20170525-001] " + transferString);
            TestCase.assertEquals(targetString, transferString);

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

    @Test
    public void testRenderList() {
        try {
            String templateString = "";
            String expecteStr = "";
            String transferString = null;
            List<Object> targetList = null;
            Map<String, Object> modelMap = new HashMap<String, Object>();

            // 1 ====
            targetList = Arrays.asList("a", "b");
            modelMap.put("list", targetList);
            templateString = "<#list list as item>index:${item?index}, value: ${item}; </#list>";
            expecteStr = "index:0, value: a; index:1, value: b; ";
            transferString = ZKFreeMarkers.renderString(templateString, modelMap);
            System.out.println("[^_^: 20220127-0826-001] " + transferString);
            TestCase.assertEquals(expecteStr, transferString);

            // 2 ====
            targetList = Arrays.asList("a", "b", "c", "d");
            modelMap.put("list", targetList);
            templateString = "<#list list as item>index:${item?index},value:${item},<#if (item?index)%2 == 0>双数位<#else>单数位</#if>-<#if item?has_next>有下一个<#else>无下一个</#if>; </#list>";
            transferString = ZKFreeMarkers.renderString(templateString, modelMap);
            System.out.println("[^_^: 20220127-0826-002] " + transferString);
            expecteStr = "index:0,value:a,双数位-有下一个; index:1,value:b,单数位-有下一个; index:2,value:c,双数位-有下一个; index:3,value:d,单数位-无下一个; ";
            TestCase.assertEquals(expecteStr, transferString);

            // 3 ====
            targetList = Arrays.asList("a", "b", "c", "d");
            modelMap.put("list", targetList);
            templateString = "<#list list as item>index:${item?index},value:${item},<#if (item?index)%2 == 0>双数位<#else>单数位</#if>-<#if item?has_next == false>无下一个<#else>有下一个</#if>; </#list>";
            transferString = ZKFreeMarkers.renderString(templateString, modelMap);
            System.out.println("[^_^: 20220127-0826-002] " + transferString);
            expecteStr = "index:0,value:a,双数位-有下一个; index:1,value:b,单数位-有下一个; index:2,value:c,双数位-有下一个; index:3,value:d,单数位-无下一个; ";
            TestCase.assertEquals(expecteStr, transferString);

            // 4 ====
            targetList = Arrays.asList("a", "b", "c");
            modelMap.put("list", targetList);
            templateString = "<#assign col=''><#list list as item>${list[item?index]};<#if col == \"\">col:不存在; </#if><#assign col=\"${item}\"></#list>col:${col}; ";
            transferString = ZKFreeMarkers.renderString(templateString, modelMap);
            System.out.println("[^_^: 20220127-0826-002] " + transferString);
            expecteStr = "index:0,value:a,双数位-有下一个; index:1,value:b,单数位-有下一个; index:2,value:c,双数位-有下一个; index:3,value:d,单数位-无下一个; ";
            TestCase.assertEquals(expecteStr, transferString);

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

}
