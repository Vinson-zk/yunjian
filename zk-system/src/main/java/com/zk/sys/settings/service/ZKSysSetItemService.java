/**
 * 
 */
package com.zk.sys.settings.service;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zk.base.service.ZKBaseService;
import com.zk.sys.settings.entity.ZKSysSetItem;
import com.zk.sys.settings.dao.ZKSysSetItemDao;

/**
 * ZKSysSetItemService
 * @author 
 * @version 
 */
@Service
@Transactional(readOnly = true)
public class ZKSysSetItemService extends ZKBaseService<String, ZKSysSetItem, ZKSysSetItemDao> {

	
	
}