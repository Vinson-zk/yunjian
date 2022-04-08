/**
 * Copyright &copy; All rights reserved.
 */
package com.zk.sys.org.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.annotations.SelectProvider;

import com.zk.base.dao.ZKBaseTreeDao;
import com.zk.db.annotation.ZKMyBatisDao;
import com.zk.base.myBaits.provider.ZKMyBatisTreeSqlProvider;
import com.zk.sys.org.entity.ZKSysOrgDept;

/**
 * ZKSysOrgDeptDAO接口
 * @author 
 * @version  1.0
 */
@ZKMyBatisDao
public interface ZKSysOrgDeptDao extends ZKBaseTreeDao<String, ZKSysOrgDept> {

	/**
     * 树形查询 fetchType = FetchType.EEAGER
     * 不能改为懒加截，不然会报错：com.linde.wms.dto.BinGroupInfoDto_$$_jvst38e_0["handler"])
     * 虽然可以用 @JsonIgnoreProperties(value = "handler") 解决报错，但查出的数据还是错的；
     */
    @SelectProvider(type = ZKMyBatisTreeSqlProvider.class, method = "selectTree")
    @Results(id = "treeResult", value = {
            @Result(column = "{parentId=pkId}", property = "children", javaType = List.class, many = @Many(select = "com.zk.sys.org.dao.ZKSysOrgDeptDao.findTreeChild", fetchType = FetchType.EAGER)) })
    List<ZKSysOrgDept> findTree(ZKSysOrgDept sysOrgDept);

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
    List<ZKSysOrgDept> findTreeChild(ZKSysOrgDept sysOrgDept);

	/**
     * 查询详情，包含父节点 fetchType = FetchType.EEAGER
     */
    @SelectProvider(type = ZKMyBatisTreeSqlProvider.class, method = "selectTreeDetail")
    @Results(value = {
            @Result(column = "{pkId=parentId}", property = "parent", javaType = ZKSysOrgDept.class, one = @One(select = "com.zk.sys.org.dao.ZKSysOrgDeptDao.getDetail", fetchType = FetchType.EAGER)) })
    ZKSysOrgDept getDetail(ZKSysOrgDept sysOrgDept);
	
}