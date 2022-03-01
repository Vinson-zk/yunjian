/**   
 * Copyright (c) 2004-2014 Vinson Technologies, Inc.
 * address: 
 * All rights reserved. 
 * 
 * This software is the confidential and proprietary information of 
 * Vinson Technologies, Inc. ("Confidential Information").  You shall not 
 * disclose such Confidential Information and shall use it only in 
 * accordance with the terms of the license agreement you entered into 
 * with Vinson. 
 *
 * @Title: ZKDBDao.java 
 * @author Vinson 
 * @Package com.zk.db.helper 
 * @Description: TODO(simple description this file what to do.) 
 * @date Dec 19, 2019 11:09:57 AM 
 * @version V1.0   
*/
package com.zk.db.helper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zk.db.annotation.ZKMyBatisDao;
import com.zk.db.helper.ZKDBColumnsSqlHelper;
import com.zk.db.helper.entity.ZKDBEntity;

/**
 * @ClassName: ZKDBDao
 * @Description: TODO(simple description this class what to do.)
 * @author Vinson
 * @version 1.0
 */
@ZKMyBatisDao
public interface ZKDBDao {

    @Insert("INSERT INTO t_zk_db_test(c_id, c_type, c_value, c_remarks, c_json, c_date, c_boolean) "
            + "VALUES (#{id}, #{type}, #{value}, #{remarks}, #{json}, #{mDate}, #{mBoolean})")
    int insert(ZKDBEntity te);

    @Update({ "<script>",
            "UPDATE t_zk_db_test SET c_type=#{type}, c_value=#{value}, c_remarks=#{remarks}, c_date=#{mDate}, c_boolean=#{mBoolean} ",
            "<if test='json != null' >",
//      ", c_json=JSON_SET(c_json, #{json})",
            ", c_json=JSON_MERGE_PATCH(c_json, #{json})", "</if>", "WHERE c_id=#{id}", "</script>" })
    int update(ZKDBEntity te);

    @Delete("<script>DELETE FROM t_zk_db_test <where><if test='id != null and id != \"\"'>c_id = #{id}</if></where></script>")
    int del(@Param("id") String id);

    @Select({"<script>",
        "SELECT ",
            ZKDBColumnsSqlHelper.testColumns,
            "FROM t_zk_db_test t",
        "<where>",
            "<if test='id != null and id!=\"\"'> AND t.c_id = #{id} </if>",
//            "<if test='type != null'> AND t.c_type = #{type} </if>",
            "<if test='types != null and types.size() > 0'> t.c_type in",
                "<foreach item=\"type\" index=\"index\" collection=\"types\" open=\"(\" separator=\",\" close=\")\">" ,
                    "#{type}",
                "</foreach>",
            "</if>",
            "<if test='value != null and value != \"\"'> AND t.c_value like CONCAT('%', REPLACE(REPLACE(REPLACE(#{value}, '\\\\', '\\\\\\\\'), '_', '\\_'), '%', '\\%'), '%')</if>",
            "<if test='remarks != null and remarks != \"\"'> AND t.c_remarks = #{remarks} </if>",
            "<if test='mBoolean != null '> AND t.c_boolean = #{mBoolean} </if>",
            "<if test='json != null and json.getKeyValues() != null'> AND ",
                "<foreach item=\"_v\" index=\"_k\" collection=\"json.getKeyValues()\" open=\"\" separator=\"AND \" close=\"\">" ,
            "JSON_UNQUOTE(JSON_EXTRACT(t.c_json, #{_k})) like CONCAT('%', REPLACE(REPLACE(REPLACE(#{_v}, '\\\\', '\\\\\\\\'), '_', '\\_'), '%', '\\%'), '%') ",
                "</foreach>",
            "</if>",
        "</where>",
        "<choose>",
            "<when test='page != null and page.sorters != null'> ORDER BY ",
            "<foreach item=\"sort_item\" index=\"sort_index\" collection=\"page.sorters\" open=\"\" separator=\",\" close=\"\">",
                    "t.${sort_item.columnName} ${sort_item.value}",
                "</foreach>",
            "</when>",
            "<otherwise>",
                "ORDER BY t.c_id ASC",
            "</otherwise>",
        "</choose>",
        "</script>"})
    List<ZKDBEntity> find(ZKDBEntity te);

    // 这类 mapper sql 取不到原始 sql？待测试
    @Select({ "<script>", "SELECT ", ZKDBColumnsSqlHelper.testColumns, "FROM t_zk_db_test ",
            "where c_remarks like CONCAT('%', REPLACE(REPLACE(REPLACE(#{remarks}, '\\\\', '\\\\\\\\'), '_', '\\_'), '%', '\\%'), '%') ",
            "</script>" })
    List<ZKDBEntity> findLike(@Param("remarks") String remarks);

}
