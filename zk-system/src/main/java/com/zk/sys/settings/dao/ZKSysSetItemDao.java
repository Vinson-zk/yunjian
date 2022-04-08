/**
 * Copyright &copy; All rights reserved.
 */
package com.zk.sys.settings.dao;

import com.zk.base.dao.ZKBaseDao;
import com.zk.db.annotation.ZKMyBatisDao;
import com.zk.sys.settings.entity.ZKSysSetItem;

/**
 * ZKSysSetItemDAO接口
 * @author 
 * @version  1.0
 */
@ZKMyBatisDao
public interface ZKSysSetItemDao extends ZKBaseDao<String, ZKSysSetItem> {
	
}