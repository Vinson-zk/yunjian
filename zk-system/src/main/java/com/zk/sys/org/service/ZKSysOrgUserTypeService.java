/**
 * 
 */
package com.zk.sys.org.service;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zk.base.service.ZKBaseService;
import com.zk.sys.org.entity.ZKSysOrgUserType;
import com.zk.sys.org.dao.ZKSysOrgUserTypeDao;

/**
 * ZKSysOrgUserTypeService
 * @author 
 * @version 
 */
@Service
@Transactional(readOnly = true)
public class ZKSysOrgUserTypeService extends ZKBaseService<String, ZKSysOrgUserType, ZKSysOrgUserTypeDao> {

	
	
}