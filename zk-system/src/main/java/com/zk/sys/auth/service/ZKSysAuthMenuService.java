/**
 * 
 */
package com.zk.sys.auth.service;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zk.base.service.ZKBaseService;
import com.zk.sys.auth.entity.ZKSysAuthMenu;
import com.zk.sys.auth.dao.ZKSysAuthMenuDao;

/**
 * ZKSysAuthMenuService
 * @author 
 * @version 
 */
@Service
@Transactional(readOnly = true)
public class ZKSysAuthMenuService extends ZKBaseService<String, ZKSysAuthMenu, ZKSysAuthMenuDao> {

	
	
}