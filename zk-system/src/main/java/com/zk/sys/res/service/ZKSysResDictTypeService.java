/**
 * 
 */
package com.zk.sys.res.service;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zk.base.service.ZKBaseService;
import com.zk.sys.res.dao.ZKSysResDictTypeDao;
import com.zk.sys.res.entity.ZKSysResDictType;

/**
 * ZKSysResDictTypeService
 * @author 
 * @version 
 */
@Service
@Transactional(readOnly = true)
public class ZKSysResDictTypeService extends ZKBaseService<String, ZKSysResDictType, ZKSysResDictTypeDao> {

    public ZKSysResDictType getByTypeCode(String typeCode) {
        return this.dao.getByTypeCode(ZKSysResDictType.initSqlProvider().getTableName(),
                ZKSysResDictType.initSqlProvider().getTableAlias(),
                ZKSysResDictType.initSqlProvider().getSqlBlockSelCols(), typeCode);
    }

	
}