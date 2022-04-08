/**
 * 
 */
package com.zk.sys.auth.service;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zk.base.service.ZKBaseService;
import com.zk.sys.auth.entity.ZKSysAuthUserRole;
import com.zk.sys.auth.dao.ZKSysAuthUserRoleDao;

/**
 * ZKSysAuthUserRoleService
 * @author 
 * @version 
 */
@Service
@Transactional(readOnly = true)
public class ZKSysAuthUserRoleService extends ZKBaseService<String, ZKSysAuthUserRole, ZKSysAuthUserRoleDao> {

	
	
}