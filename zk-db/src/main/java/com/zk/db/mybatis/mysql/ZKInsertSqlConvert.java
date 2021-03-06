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
* @Title: ZKInsertSqlConvert.java 
* @author Vinson 
* @Package com.zk.db.annotation.support.mysql 
* @Description: TODO(simple description this file what to do. ) 
* @date Sep 11, 2020 10:04:35 AM 
* @version V1.0 
*/
package com.zk.db.mybatis.mysql;

import java.beans.PropertyDescriptor;
import java.util.Map.Entry;

import com.zk.core.commons.data.ZKJson;
import com.zk.db.annotation.ZKColumn;
import com.zk.db.annotation.ZKDBAnnotationProvider;

/** 
* @ClassName: ZKInsertSqlConvert 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKInsertSqlConvert {

    // insert sql
    public static String convertSqlInsert(ZKDBAnnotationProvider annotationProvider) {
        StringBuffer sb = new StringBuffer();
        StringBuffer sbColumn = new StringBuffer();
        StringBuffer sbValue = new StringBuffer();

        sb.append("<script>INSERT INTO ");
        sb.append(annotationProvider.getTable().name());
        sb.append("(");
        boolean insertComma = false;
        for (Entry<PropertyDescriptor, ZKColumn> e : annotationProvider.getColumnInfo().entrySet()) {
            if (e.getValue().isInsert()) {
                if (insertComma) {
                    sbColumn.append(", ");
                    sbValue.append(", ");
                }
                sbColumn.append(e.getValue().name());

                if (e.getValue().javaType() == ZKJson.class) {
                    // ZKJson 格式数据为 null 时，默认插入 {}
                    sbValue.append("<choose>");
                    sbValue.append("<when test = \"");
                    sbValue.append(e.getKey().getName());
                    sbValue.append(" == null");
                    sbValue.append("\" >");
                    sbValue.append("'{}'");
                    sbValue.append("</when>");
                    sbValue.append("<otherwise>");
                    sbValue.append("#{");
                    sbValue.append(e.getKey().getName());
                    sbValue.append("}");
                    sbValue.append("</otherwise>");
                    sbValue.append("</choose>");
                }
                else {
                    sbValue.append("#{");
                    sbValue.append(e.getKey().getName());
                    sbValue.append("}");
                }
                insertComma = true;
            }
        }
        if (sbColumn.length() > 0) {
            sb.append(sbColumn.toString());
        }
        sb.append(") VALUES (");
        if (sbValue.length() > 0) {
            sb.append(sbValue.toString());
        }
        sb.append(")</script>");
        return sb.toString();
    }

}
