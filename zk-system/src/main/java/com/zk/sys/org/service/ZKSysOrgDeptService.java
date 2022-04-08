/**
 * 
 */
package com.zk.sys.org.service;
 
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zk.base.service.ZKBaseTreeService;
import com.zk.core.commons.data.ZKPage;
import com.zk.sys.org.entity.ZKSysOrgDept;
import com.zk.sys.org.dao.ZKSysOrgDeptDao;

/**
 * ZKSysOrgDeptService
 * @author 
 * @version 
 */
@Service
@Transactional(readOnly = true)
public class ZKSysOrgDeptService extends ZKBaseTreeService<String, ZKSysOrgDept, ZKSysOrgDeptDao> {

	/**
     * 树形查询； 不分页
     */
    public List<ZKSysOrgDept> findTree(ZKSysOrgDept sysOrgDept) {
        return this.dao.findTree(sysOrgDept);
    }

	/**
     * 树形查询；分页
     */
    public ZKPage<ZKSysOrgDept> findTree(ZKPage<ZKSysOrgDept> page, ZKSysOrgDept sysOrgDept) {
        sysOrgDept.setPage(page);
        page.setResult(dao.findTree(sysOrgDept));
        return page;
    }

	/**
	 * 查询详情，包含父节点
	 */
    public ZKSysOrgDept getDetail(ZKSysOrgDept sysOrgDept) {
        return this.dao.getDetail(sysOrgDept);
    }
	
}