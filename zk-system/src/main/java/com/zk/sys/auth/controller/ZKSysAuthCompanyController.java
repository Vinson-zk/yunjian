/**
 * 
 */
package com.zk.sys.auth.controller;

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

import com.zk.sys.auth.entity.ZKSysAuthCompany;
import com.zk.sys.auth.service.ZKSysAuthCompanyService;       

/**
 * ZKSysAuthCompanyController
 * @author 
 * @version 
 */
@RestController
@RequestMapping(value = "${zk.path.admin}/${zk.path.sys}/auth/sysAuthCompany")
public class ZKSysAuthCompanyController extends ZKBaseController {

	@Autowired
	private ZKSysAuthCompanyService sysAuthCompanyService;
	
	// 编辑
	@RequestMapping(value="sysAuthCompany", method = RequestMethod.POST)
	public ZKMsgRes sysAuthCompanyPost(@RequestBody ZKSysAuthCompany sysAuthCompany){
		this.sysAuthCompanyService.save(sysAuthCompany);
        return ZKMsgRes.asOk(sysAuthCompany);
	}
	
	// 查询详情
	@RequestMapping(value="sysAuthCompany", method = RequestMethod.GET)
	public ZKMsgRes sysAuthCompanyGet(@RequestParam("pkId") String pkId){
		ZKSysAuthCompany sysAuthCompany = this.sysAuthCompanyService.get(new ZKSysAuthCompany(pkId));
        return ZKMsgRes.asOk(sysAuthCompany);
	}
	
	// 分页查询 
	@RequestMapping(value="sysAuthCompanysPage", method = RequestMethod.GET)
	public ZKMsgRes sysAuthCompanysPageGet(ZKSysAuthCompany sysAuthCompany, HttpServletRequest hReq, HttpServletResponse hRes){
		ZKPage<ZKSysAuthCompany> resPage = ZKPage.asPage(hReq);
        resPage = this.sysAuthCompanyService.findPage(resPage, sysAuthCompany);
        return ZKMsgRes.asOk(resPage);
	}
	
	// 批量删除
	@RequestMapping(value="sysAuthCompany", method = RequestMethod.DELETE)
	@ResponseBody
	public ZKMsgRes sysAuthCompanyDel(@RequestParam("pkId[]") String[] pkIds){
		 int count = 0;
        if (pkIds != null && pkIds.length > 0) {
            for (String pkId : pkIds) {
                count += this.sysAuthCompanyService.del(new ZKSysAuthCompany(pkId));
            }
        }
        return ZKMsgRes.asOk(count);
	}

}