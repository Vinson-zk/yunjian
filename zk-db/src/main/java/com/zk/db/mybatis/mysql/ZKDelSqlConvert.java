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
* @Title: ZKDelSqlConvert.java 
* @author Vinson 
* @Package com.zk.db.annotation.support.mysql 
* @Description: TODO(simple description this file what to do. ) 
* @date Sep 11, 2020 10:04:50 AM 
* @version V1.0 
*/
package com.zk.db.mybatis.mysql;

import com.zk.db.annotation.ZKDBAnnotationProvider;

/** 
* @ClassName: ZKDelSqlConvert 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKDelSqlConvert {

    // 逻辑删除，逻辑删除语句，实体中定义
    public static String convertSqlDel(ZKDBAnnotationProvider annotationProvider, String delSetSql) {
        StringBuffer sb = new StringBuffer();

        sb.append("UPDATE ");
        sb.append(annotationProvider.getTable().name());
        sb.append(" SET ");
        sb.append(delSetSql);
//        sb.append("c_del_flag = #{delFlag}, ");
//        sb.append("c_update_user_id = #{updateUserId}, ");
//        sb.append("c_update_date = #{updateDate} ");
//        sb.append("WHERE c_pk_id = #{pkId} ");
        sb.append(" ");
        sb.append(ZKSelectSqlConvert.convertSqlPkWhere(annotationProvider, ""));

        return sb.toString();
    }

    // 物理删除
    public static String convertSqlDiskDel(ZKDBAnnotationProvider annotationProvider) {
        StringBuffer sb = new StringBuffer();

        sb.append("DELETE FROM ");
        sb.append(annotationProvider.getTable().name());
        sb.append(" ");
        sb.append(ZKSelectSqlConvert.convertSqlPkWhere(annotationProvider, ""));

        return sb.toString();
    }

}
