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

import com.zk.sys.auth.entity.ZKSysAuthRole;
import com.zk.sys.auth.service.ZKSysAuthRoleService;       

/**
 * ZKSysAuthRoleController
 * @author 
 * @version 
 */
@RestController
@RequestMapping(value = "${zk.path.admin}/${zk.path.sys}/auth/sysAuthRole")
public class ZKSysAuthRoleController extends ZKBaseController {

	@Autowired
	private ZKSysAuthRoleService sysAuthRoleService;
	
	// 编辑
	@RequestMapping(value="sysAuthRole", method = RequestMethod.POST)
	public ZKMsgRes sysAuthRolePost(@RequestBody ZKSysAuthRole sysAuthRole){
		this.sysAuthRoleService.save(sysAuthRole);
        return ZKMsgRes.asOk(sysAuthRole);
	}
	
	// 查询详情
	@RequestMapping(value="sysAuthRole", method = RequestMethod.GET)
	public ZKMsgRes sysAuthRoleGet(@RequestParam("pkId") String pkId){
		ZKSysAuthRole sysAuthRole = this.sysAuthRoleService.get(new ZKSysAuthRole(pkId));
        return ZKMsgRes.asOk(sysAuthRole);
	}
	
	// 分页查询 
	@RequestMapping(value="sysAuthRolesPage", method = RequestMethod.GET)
	public ZKMsgRes sysAuthRolesPageGet(ZKSysAuthRole sysAuthRole, HttpServletRequest hReq, HttpServletResponse hRes){
		ZKPage<ZKSysAuthRole> resPage = ZKPage.asPage(hReq);
        resPage = this.sysAuthRoleService.findPage(resPage, sysAuthRole);
        return ZKMsgRes.asOk(resPage);
	}
	
	// 批量删除
	@RequestMapping(value="sysAuthRole", method = RequestMethod.DELETE)
	@ResponseBody
	public ZKMsgRes sysAuthRoleDel(@RequestParam("pkId[]") String[] pkIds){
		 int count = 0;
        if (pkIds != null && pkIds.length > 0) {
            for (String pkId : pkIds) {
                count += this.sysAuthRoleService.del(new ZKSysAuthRole(pkId));
            }
        }
        return ZKMsgRes.asOk(count);
	}

}