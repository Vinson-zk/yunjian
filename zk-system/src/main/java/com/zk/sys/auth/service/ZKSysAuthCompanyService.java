/**
 * 
 */
package com.zk.sys.auth.service;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zk.base.service.ZKBaseService;
import com.zk.sys.auth.entity.ZKSysAuthCompany;
import com.zk.sys.auth.dao.ZKSysAuthCompanyDao;

/**
 * ZKSysAuthCompanyService
 * @author 
 * @version 
 */
@Service
@Transactional(readOnly = true)
public class ZKSysAuthCompanyService extends ZKBaseService<String, ZKSysAuthCompany, ZKSysAuthCompanyDao> {

	
	
}