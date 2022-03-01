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
* @Title: ZKSysMenuDao.java 
* @author Vinson 
* @Package com.zk.sys.dao 
* @Description: TODO(simple description this file what to do.) 
* @date Aug 4, 2020 5:42:20 PM 
* @version V1.0 
*/
package com.zk.sys.res.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.mapping.FetchType;

import com.zk.base.dao.ZKBaseTreeDao;
import com.zk.base.myBaits.provider.ZKMyBatisTreeSqlProvider;
import com.zk.db.annotation.ZKMyBatisDao;
import com.zk.sys.res.entity.ZKSysMenu;

/** 
* @ClassName: ZKSysMenuDao 
* @Description: TODO(simple description this class what to do.) 
* @author Vinson 
* @version 1.0 
*/
@ZKMyBatisDao
public interface ZKSysMenuDao extends ZKBaseTreeDao<String, ZKSysMenu> {

    /**
     * 树形查询菜单 fetchType = FetchType.EEAGER
     * 不能改为懒加截，不然会报错：com.linde.wms.dto.BinGroupInfoDto_$$_jvst38e_0["handler"])
     * 虽然可以用 @JsonIgnoreProperties(value = "handler") 解决报错，但查出的数据还是错的；
     */
    @SelectProvider(type = ZKMyBatisTreeSqlProvider.class, method = "selectTree")
    @Results(id = "treeResult", value = {
            @Result(column = "{parentId=pkId}", property = "children", javaType = List.class, many = @Many(select = "com.zk.sys.res.dao.ZKSysMenuDao.findTreeChild", fetchType = FetchType.EAGER)) })
    List<ZKSysMenu> findTree(ZKSysMenu sysMenu);

    /**
     * 树形查询的子节点查询
     *
     * @Title: findTreeChild
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Jan 6, 2021 10:09:10 PM
     * @param sysMenu
     * @return List<T>
     */
    @SelectProvider(type = ZKMyBatisTreeSqlProvider.class, method = "selectTree")
    @ResultMap("treeResult")
    List<ZKSysMenu> findTreeChild(ZKSysMenu sysMenu);

    /**
     * 查询菜单详情，包含父节点 fetchType = FetchType.EEAGER
     */
    @SelectProvider(type = ZKMyBatisTreeSqlProvider.class, method = "selectTreeDetail")
    @Results(value = {
            @Result(column = "{pkId=parentId}", property = "parent", javaType = ZKSysMenu.class, one = @One(select = "com.zk.sys.res.dao.ZKSysMenuDao.getDetail", fetchType = FetchType.EAGER)) })
    ZKSysMenu getDetail(ZKSysMenu sysMenu);

    /**
     * 按 code 查询, code 需要做唯一键
     *
     * @Title: getByCode
     * @Description:
     * @author Vinson
     * @date Aug 4, 2020 10:14:01 PM
     * @param code
     * @return
     * @return ZKSysMenu
     */
    @Select(value = { "SELECT ${sCols} FROM ${tn} WHERE c_code = #{code}" })
    ZKSysMenu getByCode(@Param("tn")String tn, @Param("sCols")String sCols, @Param("code") String code);

}
