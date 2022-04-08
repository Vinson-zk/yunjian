/**
 * Copyright &copy; All rights reserved.
 */
package com.zk.sys.auth.dao;

import com.zk.base.dao.ZKBaseDao;
import com.zk.db.annotation.ZKMyBatisDao;
import com.zk.sys.auth.entity.ZKSysAuthUserType;

/**
 * ZKSysAuthUserTypeDAO接口
 * @author 
 * @version  1.0
 */
@ZKMyBatisDao
public interface ZKSysAuthUserTypeDao extends ZKBaseDao<String, ZKSysAuthUserType> {
	
}