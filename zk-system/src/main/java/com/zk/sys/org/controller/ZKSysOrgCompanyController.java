/**
 * 
 */
package com.zk.sys.org.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zk.base.controller.ZKBaseController;
import com.zk.core.commons.data.ZKPage;
import com.zk.core.web.ZKMsgRes;

import com.zk.sys.org.entity.ZKSysOrgCompany;
import com.zk.sys.org.service.ZKSysOrgCompanyService;       

/**
 * ZKSysOrgCompanyController
 * @author 
 * @version 
 */
@RestController
@RequestMapping(value = "${zk.path.admin}/${zk.path.sys}/org/sysOrgCompany")
public class ZKSysOrgCompanyController extends ZKBaseController {

	@Autowired
	private ZKSysOrgCompanyService sysOrgCompanyService;
	
	// 编辑
	@RequestMapping(value="sysOrgCompany", method = RequestMethod.POST)
	public ZKMsgRes sysOrgCompanyPost(@RequestBody ZKSysOrgCompany sysOrgCompany){
		this.sysOrgCompanyService.save(sysOrgCompany);
        return ZKMsgRes.asOk(sysOrgCompany);
	}
	
	// 查询详情
	@RequestMapping(value="sysOrgCompany", method = RequestMethod.GET)
	public ZKMsgRes sysOrgCompanyGet(@RequestParam("pkId") String pkId){
		ZKSysOrgCompany sysOrgCompany = this.sysOrgCompanyService.getDetail(new ZKSysOrgCompany(pkId));
        return ZKMsgRes.asOk(sysOrgCompany);
	}
	
	// 分页查询 
	@RequestMapping(value="sysOrgCompanysTree", method = RequestMethod.GET)
	public ZKMsgRes sysOrgCompanysTree(ZKSysOrgCompany sysOrgCompany, HttpServletRequest hReq, HttpServletResponse hRes){
		ZKPage<ZKSysOrgCompany> resPage = ZKPage.asPage(hReq);
        resPage = this.sysOrgCompanyService.findTree(resPage, sysOrgCompany);
        return ZKMsgRes.asOk(resPage);
	}
	
	// 批量删除
	@RequestMapping(value="sysOrgCompany", method = RequestMethod.DELETE)
	@ResponseBody
	public ZKMsgRes sysOrgCompanyDel(@RequestParam("pkId[]") String[] pkIds){
		 int count = 0;
        if (pkIds != null && pkIds.length > 0) {
            for (String pkId : pkIds) {
                count += this.sysOrgCompanyService.del(new ZKSysOrgCompany(pkId));
            }
        }
        return ZKMsgRes.asOk(count);
	}

}