/**
 * 
 */
package com.zk.sys.auth.service;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zk.base.service.ZKBaseService;
import com.zk.sys.auth.entity.ZKSysAuthRank;
import com.zk.sys.auth.dao.ZKSysAuthRankDao;

/**
 * ZKSysAuthRankService
 * @author 
 * @version 
 */
@Service
@Transactional(readOnly = true)
public class ZKSysAuthRankService extends ZKBaseService<String, ZKSysAuthRank, ZKSysAuthRankDao> {

	
	
}