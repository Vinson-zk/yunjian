/**
 * 
 */
package com.zk.sys.auth.service;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zk.base.service.ZKBaseService;
import com.zk.sys.auth.entity.ZKSysAuthFuncApi;
import com.zk.sys.auth.dao.ZKSysAuthFuncApiDao;

/**
 * ZKSysAuthFuncApiService
 * @author 
 * @version 
 */
@Service
@Transactional(readOnly = true)
public class ZKSysAuthFuncApiService extends ZKBaseService<String, ZKSysAuthFuncApi, ZKSysAuthFuncApiDao> {

	
	
}