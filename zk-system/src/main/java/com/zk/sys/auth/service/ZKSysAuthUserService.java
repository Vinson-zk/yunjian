/**
 * 
 */
package com.zk.sys.auth.service;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zk.base.service.ZKBaseService;
import com.zk.sys.auth.entity.ZKSysAuthUser;
import com.zk.sys.auth.dao.ZKSysAuthUserDao;

/**
 * ZKSysAuthUserService
 * @author 
 * @version 
 */
@Service
@Transactional(readOnly = true)
public class ZKSysAuthUserService extends ZKBaseService<String, ZKSysAuthUser, ZKSysAuthUserDao> {

	
	
}