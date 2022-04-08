/**
 * 
 */
package com.zk.sys.auth.service;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zk.base.service.ZKBaseService;
import com.zk.sys.auth.entity.ZKSysAuthRole;
import com.zk.sys.auth.dao.ZKSysAuthRoleDao;

/**
 * ZKSysAuthRoleService
 * @author 
 * @version 
 */
@Service
@Transactional(readOnly = true)
public class ZKSysAuthRoleService extends ZKBaseService<String, ZKSysAuthRole, ZKSysAuthRoleDao> {

	
	
}