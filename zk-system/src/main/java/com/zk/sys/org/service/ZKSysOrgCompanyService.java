/**
 * 
 */
package com.zk.sys.org.service;
 
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zk.base.service.ZKBaseTreeService;
import com.zk.core.commons.data.ZKPage;
import com.zk.sys.org.entity.ZKSysOrgCompany;
import com.zk.sys.org.dao.ZKSysOrgCompanyDao;

/**
 * ZKSysOrgCompanyService
 * @author 
 * @version 
 */
@Service
@Transactional(readOnly = true)
public class ZKSysOrgCompanyService extends ZKBaseTreeService<String, ZKSysOrgCompany, ZKSysOrgCompanyDao> {

	/**
     * 树形查询； 不分页
     */
    public List<ZKSysOrgCompany> findTree(ZKSysOrgCompany sysOrgCompany) {
        return this.dao.findTree(sysOrgCompany);
    }

	/**
     * 树形查询；分页
     */
    public ZKPage<ZKSysOrgCompany> findTree(ZKPage<ZKSysOrgCompany> page, ZKSysOrgCompany sysOrgCompany) {
        sysOrgCompany.setPage(page);
        page.setResult(dao.findTree(sysOrgCompany));
        return page;
    }

	/**
	 * 查询详情，包含父节点
	 */
    public ZKSysOrgCompany getDetail(ZKSysOrgCompany sysOrgCompany) {
        return this.dao.getDetail(sysOrgCompany);
    }
	
}