/**
 * 
 */
package com.zk.sys.auth.service;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zk.base.service.ZKBaseService;
import com.zk.sys.auth.entity.ZKSysAuthDept;
import com.zk.sys.auth.dao.ZKSysAuthDeptDao;

/**
 * ZKSysAuthDeptService
 * @author 
 * @version 
 */
@Service
@Transactional(readOnly = true)
public class ZKSysAuthDeptService extends ZKBaseService<String, ZKSysAuthDept, ZKSysAuthDeptDao> {

	
	
}