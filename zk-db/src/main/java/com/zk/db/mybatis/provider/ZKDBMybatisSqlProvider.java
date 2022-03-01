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
* @Title: ZKDBMybatisSqlProvider.java 
* @author Vinson 
* @Package com.zk.db.mybatis.provider 
* @Description: TODO(simple description this file what to do. ) 
* @date Sep 16, 2020 10:43:25 AM 
* @version V1.0 
*/
package com.zk.db.mybatis.provider;

import com.zk.db.commons.ZKDBBaseEntity;

/** 
* @ClassName: ZKDBMybatisSqlProvider 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKDBMybatisSqlProvider {

    public String insert(ZKDBBaseEntity<?> entity) {
        return entity.getSqlProvider().getSqlInsert();
    }

    public String update(ZKDBBaseEntity<?> entity) {
        return entity.getSqlProvider().getSqlUpdate();
    }

    public String del(ZKDBBaseEntity<?> entity) {
        return entity.getSqlProvider().getSqlDel();
    }

    public String diskDel(ZKDBBaseEntity<?> entity) {
        return entity.getSqlProvider().getSqlDiskDel();
    }

    public String get(ZKDBBaseEntity<?> entity) {
        return entity.getSqlProvider().getSqlGet();
    }

    public String selectList(ZKDBBaseEntity<?> entity) {

//        System.out.println("======== " + ZKJsonUtils.writeObjectJson(entity));

//        return "<script>SELECT t0.c_json AS \"json\", t0.c_id AS \"id\", t0.c_type AS \"type\", t0.c_value AS \"value\", t0.c_remarks AS \"remarks\" FROM t_test t0 <where><if test=' type != null '>AND t0.c_type = #{type}</if></where> </script>";

//        return "SELECT t0.c_json AS \"json\", t0.c_id AS \"id\", t0.c_type AS \"type\", t0.c_value AS \"value\", t0.c_remarks AS \"remarks\" FROM t_test t0 WHERE t0.c_type = #{type}";
//      

//        return entity.getSqlConvert()
//                .selectList((entity.getPage() == null || entity.getPage().getSort() == null) ? Collections.emptyList()
//                        : entity.getPage().getSort());

//        /***************** */
//        if (entity.getPage() == null || entity.getPage().getSort() == null) {
//            return entity.getSqlConvert().selectList(Collections.emptyList());
//        }
//        else {
//            return entity.getSqlConvert().selectList(entity.getPage().getSort());
//        }

        /***************** */
        // 转换 list sql
        return "<script>" + entity.getSqlProvider().getSqlBlockSelList() + entity.getSqlProvider().convertOrderBySql(entity) + "</script>";
    }

}
