/**
 * 
 */
package com.zk.sys.auth.service;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zk.base.service.ZKBaseService;
import com.zk.sys.auth.entity.ZKSysAuthDefined;
import com.zk.sys.auth.dao.ZKSysAuthDefinedDao;

/**
 * ZKSysAuthDefinedService
 * @author 
 * @version 
 */
@Service
@Transactional(readOnly = true)
public class ZKSysAuthDefinedService extends ZKBaseService<String, ZKSysAuthDefined, ZKSysAuthDefinedDao> {

	
	
}