/**
 * 
 */
package com.zk.sys.auth.service;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zk.base.service.ZKBaseService;
import com.zk.sys.auth.entity.ZKSysAuthUserType;
import com.zk.sys.auth.dao.ZKSysAuthUserTypeDao;

/**
 * ZKSysAuthUserTypeService
 * @author 
 * @version 
 */
@Service
@Transactional(readOnly = true)
public class ZKSysAuthUserTypeService extends ZKBaseService<String, ZKSysAuthUserType, ZKSysAuthUserTypeDao> {

	
	
}