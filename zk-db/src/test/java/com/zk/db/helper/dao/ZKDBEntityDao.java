/** 
* Copyright (c) 2004-2020 ZK-Vinson Technologies, Inc.
* address: 
* All rights reserved. 
* 
* This software is the confidential and proprietary information of 
* ZK-Vinson Technologies, Inc. ("Confidential Information"). You shall not 
* disclose such Confidential Information and shall use it only in 
* accordance with the terms of the license agreement you entered into 
* with ZK-Vinson. 
*
* @Title: ZKDBEntityDao.java 
* @author Vinson 
* @Package com.zk.db.helper.dao 
* @Description: TODO(simple description this file what to do. ) 
* @date Sep 14, 2020 3:33:16 PM 
* @version V1.0 
*/
package com.zk.db.helper.dao;
/** 
* @ClassName: ZKDBEntityDao 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/

import com.zk.db.annotation.ZKMyBatisDao;
import com.zk.db.helper.entity.ZKDBEntity;
import com.zk.db.mybatis.dao.ZKDBBaseDao;

@ZKMyBatisDao
public interface ZKDBEntityDao extends ZKDBBaseDao<ZKDBEntity> {

}
