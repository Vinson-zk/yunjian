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
* @Title: ZKUpdateSqlConvert.java 
* @author Vinson 
* @Package com.zk.db.annotation.support.mysql 
* @Description: TODO(simple description this file what to do. ) 
* @date Sep 11, 2020 10:04:23 AM 
* @version V1.0 
*/
package com.zk.db.mybatis.mysql;

import java.beans.PropertyDescriptor;
import java.util.Map.Entry;

import com.zk.core.commons.data.ZKJson;
import com.zk.db.annotation.ZKColumn;
import com.zk.db.annotation.ZKDBAnnotationProvider;

/** 
* @ClassName: ZKUpdateSqlConvert 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKUpdateSqlConvert {

    // update
    public static String convertSqlUpdate(ZKDBAnnotationProvider annotationProvider) {
        StringBuffer sb = new StringBuffer();
        StringBuffer sbWere = new StringBuffer();

        sb.append("UPDATE ");
        sb.append(annotationProvider.getTable().name());
        sb.append(" SET ");

        ZKColumn column = null;
        for (Entry<PropertyDescriptor, ZKColumn> e : annotationProvider.getColumnInfo().entrySet()) {
            column = e.getValue();
            if (column.isPk()) {
                if (sbWere.length() < 1) {
                    sbWere.append(" WHERE ");
                    sbWere.append(column.name());
                    sbWere.append(" = ");
                    sbWere.append("#{");
                    sbWere.append(e.getKey().getName());
                    sbWere.append("}");
                }
                else {
                    sbWere.append(" AND ");
                    sbWere.append(column.name());
                    sbWere.append(" = ");
                    sbWere.append("#{");
                    sbWere.append(e.getKey().getName());
                    sbWere.append("}");
                }
            }
            if (column.isUpdate()) {
                if (column.javaType() == ZKJson.class) {
                    sb.append(column.name());
                    sb.append(" = ");
                    sb.append("JSON_MERGE_PATCH(");
                    sb.append(column.name());
                    sb.append(", ");
                    sb.append("#{");
                    sb.append(e.getKey().getName());
                    sb.append("}), ");
                }
                else {
                    sb.append(column.name());
                    sb.append(" = ");
                    sb.append("#{");
                    sb.append(e.getKey().getName());
                    sb.append("}, ");
                }
            }
        }

        return sb.lastIndexOf(",") != -1 ? (sb.substring(0, sb.lastIndexOf(",")) + sbWere.toString())
                : sb.toString() + sbWere.toString();
    }

}
