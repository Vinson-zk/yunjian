/**
 * 
 */
package com.zk.sys.org.service;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zk.base.service.ZKBaseService;
import com.zk.sys.org.entity.ZKSysOrgRole;
import com.zk.sys.org.dao.ZKSysOrgRoleDao;

/**
 * ZKSysOrgRoleService
 * @author 
 * @version 
 */
@Service
@Transactional(readOnly = true)
public class ZKSysOrgRoleService extends ZKBaseService<String, ZKSysOrgRole, ZKSysOrgRoleDao> {

	
	
}