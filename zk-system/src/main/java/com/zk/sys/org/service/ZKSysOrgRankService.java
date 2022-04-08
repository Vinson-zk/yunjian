/**
 * 
 */
package com.zk.sys.org.service;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zk.base.service.ZKBaseService;
import com.zk.sys.org.entity.ZKSysOrgRank;
import com.zk.sys.org.dao.ZKSysOrgRankDao;

/**
 * ZKSysOrgRankService
 * @author 
 * @version 
 */
@Service
@Transactional(readOnly = true)
public class ZKSysOrgRankService extends ZKBaseService<String, ZKSysOrgRank, ZKSysOrgRankDao> {

	
	
}