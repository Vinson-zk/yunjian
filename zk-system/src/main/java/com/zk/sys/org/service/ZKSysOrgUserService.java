/**
 * 
 */
package com.zk.sys.org.service;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zk.base.service.ZKBaseService;
import com.zk.sys.org.entity.ZKSysOrgUser;
import com.zk.sys.org.dao.ZKSysOrgUserDao;

/**
 * ZKSysOrgUserService
 * @author 
 * @version 
 */
@Service
@Transactional(readOnly = true)
public class ZKSysOrgUserService extends ZKBaseService<String, ZKSysOrgUser, ZKSysOrgUserDao> {

	
	
}