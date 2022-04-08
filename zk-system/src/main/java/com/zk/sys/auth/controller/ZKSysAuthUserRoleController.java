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

import com.zk.sys.auth.entity.ZKSysAuthUserRole;
import com.zk.sys.auth.service.ZKSysAuthUserRoleService;       

/**
 * ZKSysAuthUserRoleController
 * @author 
 * @version 
 */
@RestController
@RequestMapping(value = "${zk.path.admin}/${zk.path.sys}/auth/sysAuthUserRole")
public class ZKSysAuthUserRoleController extends ZKBaseController {

	@Autowired
	private ZKSysAuthUserRoleService sysAuthUserRoleService;
	
	// 编辑
	@RequestMapping(value="sysAuthUserRole", method = RequestMethod.POST)
	public ZKMsgRes sysAuthUserRolePost(@RequestBody ZKSysAuthUserRole sysAuthUserRole){
		this.sysAuthUserRoleService.save(sysAuthUserRole);
        return ZKMsgRes.asOk(sysAuthUserRole);
	}
	
	// 查询详情
	@RequestMapping(value="sysAuthUserRole", method = RequestMethod.GET)
	public ZKMsgRes sysAuthUserRoleGet(@RequestParam("pkId") String pkId){
		ZKSysAuthUserRole sysAuthUserRole = this.sysAuthUserRoleService.get(new ZKSysAuthUserRole(pkId));
        return ZKMsgRes.asOk(sysAuthUserRole);
	}
	
	// 分页查询 
	@RequestMapping(value="sysAuthUserRolesPage", method = RequestMethod.GET)
	public ZKMsgRes sysAuthUserRolesPageGet(ZKSysAuthUserRole sysAuthUserRole, HttpServletRequest hReq, HttpServletResponse hRes){
		ZKPage<ZKSysAuthUserRole> resPage = ZKPage.asPage(hReq);
        resPage = this.sysAuthUserRoleService.findPage(resPage, sysAuthUserRole);
        return ZKMsgRes.asOk(resPage);
	}
	
	// 批量删除
	@RequestMapping(value="sysAuthUserRole", method = RequestMethod.DELETE)
	@ResponseBody
	public ZKMsgRes sysAuthUserRoleDel(@RequestParam("pkId[]") String[] pkIds){
		 int count = 0;
        if (pkIds != null && pkIds.length > 0) {
            for (String pkId : pkIds) {
                count += this.sysAuthUserRoleService.del(new ZKSysAuthUserRole(pkId));
            }
        }
        return ZKMsgRes.asOk(count);
	}

}