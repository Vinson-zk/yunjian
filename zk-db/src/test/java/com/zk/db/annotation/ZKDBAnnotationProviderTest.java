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
* @Title: ZKDBAnnotationProviderTest.java 
* @author Vinson 
* @Package com.zk.db.commons 
* @Description: TODO(simple description this file what to do. ) 
* @date Sep 11, 2020 9:44:52 AM 
* @version V1.0 
*/
package com.zk.db.annotation;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Test;

import com.zk.core.utils.ZKClassUtils;
import com.zk.db.helper.entity.ZKDBEntity;

import junit.framework.TestCase;

/** 
* @ClassName: ZKDBAnnotationProviderTest 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKDBAnnotationProviderTest {

    @Test
    public void testAnnotation() {
        try {

            ZKDBAnnotationProvider zkAp = new ZKDBAnnotationProvider(ZKDBEntity.class);

            System.out.println("[^_^:20200910-1642-001] table.name: " + zkAp.getTable().name());

            for (Entry<PropertyDescriptor, ZKColumn> e : zkAp.getColumnInfo().entrySet()) {
                System.out.println("20200910-1642-002 column.name: " + e.getKey().getName() + " -> "
                        + e.getValue().name()
                        + " -> " + e.getValue().javaType());
            }

            TestCase.assertEquals("t_zk_db_test", zkAp.getTable().name());
            TestCase.assertEquals(11, zkAp.getColumnInfo().size());
            TestCase.assertEquals("c_type", zkAp.getColumn("type").name());

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

    @Test
    public void test() {
        try {

            BeanInfo beanInfo = Introspector.getBeanInfo(ZKDBEntity.class);

            for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
                System.out.println("-------------------- 0 ");
                System.out.println("[^_^:20200917-1114-001] PropertyDescriptor.getName: " + pd.getName());
                System.out.println("[^_^:20200917-1114-001] PropertyDescriptor.getName: " + pd.getReadMethod());
//                System.out.println("[^_^:20200917-1114-001] PropertyDescriptor.getDisplayName: " + pd.getDisplayName());
//                System.out
//                        .println("[^_^:20200917-1114-001] PropertyDescriptor.getPropertyType: " + pd.getPropertyType());
//
//                Enumeration<String> es = pd.attributeNames();
//                while (es.hasMoreElements()) {
//                    System.out
//                            .println("[^_^:20200917-1114-001] PropertyDescriptor.attributeNames: " + es.nextElement());
//                }

            }

            System.out.println("==============================  ");
            System.out.println("==============================  ");
            System.out.println("==============================  ");

            List<Field> fs = ZKClassUtils.getAllField(ZKDBEntity.class);
            for (Field f : fs) {
                System.out.println("-------------------- 2 ");
                System.out.println("[^_^:20200917-1114-001] Field.getName: " + f.getName());
            }

            System.out.println("==============================  ");
            System.out.println("==============================  ");
            System.out.println("==============================  ");

            for (MethodDescriptor md : beanInfo.getMethodDescriptors()) {
                System.out.println("-------------------- 1 ");
                System.out.println("[^_^:20200917-1114-001] MethodDescriptor.getName: " + md.getName());
//                System.out.println("[^_^:20200917-1114-001] MethodDescriptor.getDisplayName: " + md.getDisplayName());
//                System.out.println(
//                        "[^_^:20200917-1114-001] MethodDescriptor.getShortDescription: " + md.getShortDescription());
//                System.out.println(
//                        "[^_^:20200917-1114-001] MethodDescriptor.getMethod().getName: " + md.getMethod().getName());
//
//                Enumeration<String> es = md.attributeNames();
//                while (es.hasMoreElements()) {
//                    System.out.println("[^_^:20200917-1114-001] MethodDescriptor.attributeNames: " + es.nextElement());
//                }

            }

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

}
