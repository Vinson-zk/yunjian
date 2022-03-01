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
* @Title: ZKDBAnnotationProvider.java 
* @author Vinson 
* @Package com.zk.db.commons 
* @Description: TODO(simple description this file what to do. ) 
* @date Sep 11, 2020 9:44:20 AM 
* @version V1.0 
*/
package com.zk.db.annotation;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.zk.core.utils.ZKClassUtils;
import com.zk.core.utils.ZKStringUtils;

/** 
* @ClassName: ZKDBAnnotationProvider 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKDBAnnotationProvider {

    private Class<?> classz;

    public ZKDBAnnotationProvider(Class<?> classz) {
        this.classz = classz;
        this.initSettingTable();

        try {
            this.initSettingColumns();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 类上注解的表名
     */
    private ZKTable table;

    /**
     * 字段或方法上注解的 column 映射；以 属性名为 key
     * 
     * 注意: 这里在 get 方法上注解的优先级大于在字段上注解的优先级；
     */
    private Map<PropertyDescriptor, ZKColumn> columnInfo;

//    private Map<String, PropertyDescriptor> columnInfo;

    private void initSettingColumns() throws IntrospectionException {

        this.columnInfo = new HashMap<PropertyDescriptor, ZKColumn>();

        PropertyDescriptor[] propertyDescriptors = ZKClassUtils.getAllProperty(this.classz);
        List<Field> fields = ZKClassUtils.getAllField(this.classz);
        Method m = null;
        Field filed = null;
        ZKColumn zkc = null;
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            m = null;
            filed = null;
            zkc = null;
            m = propertyDescriptor.getReadMethod();
            // 优先从 bean 的 getter 方法中取；
            if (m != null) {
                zkc = m.getAnnotation(ZKColumn.class);
            }
            if (zkc == null) {
                // 再从类定义的字段上取；如果已在 bean 的 getter 方法中取到注解，则不在从定义的字段上取；
                filed = ZKClassUtils.getField(fields, propertyDescriptor.getName());
                if (filed != null) {
                    zkc = filed.getAnnotation(ZKColumn.class);
                }
            }

            this.put(propertyDescriptor, zkc);
        }

    }

    private void put(PropertyDescriptor propertyDescriptor, ZKColumn column) {
        // 如果字段注解的名称为空，不处理，丢弃；
        if (column != null && !ZKStringUtils.isEmpty(column.name())) {
            if (!this.isExist(propertyDescriptor)) {
                this.columnInfo.put(propertyDescriptor, column);
            }
        }
    }

    private boolean isExist(PropertyDescriptor propertyDescriptor) {
        return this.columnInfo.containsKey(propertyDescriptor);
    }

    private void initSettingTable() {
        this.table = this.classz.getAnnotation(ZKTable.class);
    }

    public ZKTable getTable() {
        return this.table;
    }

    /**
     * @return classz sa
     */
    public Class<?> getClassz() {
        return classz;
    }

    /**
     * @return columnInfo sa
     */
    public Map<PropertyDescriptor, ZKColumn> getColumnInfo() {
        if (columnInfo == null) {
            this.columnInfo = new HashMap<>();
        }
        return columnInfo;
    }

    public ZKColumn getColumn(String attrName) {
        for (Entry<PropertyDescriptor, ZKColumn> e : this.columnInfo.entrySet()) {
            if (e.getKey().getName().equals(attrName)) {
                return e.getValue();
            }
        }
        return null;
    }

}
