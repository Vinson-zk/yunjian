/**
 * Copyright &copy; All rights reserved.
 */
package com.zk.sys.org.dao;

import com.zk.base.dao.ZKBaseDao;
import com.zk.db.annotation.ZKMyBatisDao;
import com.zk.sys.org.entity.ZKSysOrgUser;

/**
 * ZKSysOrgUserDAO接口
 * @author 
 * @version  1.0
 */
@ZKMyBatisDao
public interface ZKSysOrgUserDao extends ZKBaseDao<String, ZKSysOrgUser> {
	
}