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

import com.zk.sys.auth.entity.ZKSysAuthUser;
import com.zk.sys.auth.service.ZKSysAuthUserService;       

/**
 * ZKSysAuthUserController
 * @author 
 * @version 
 */
@RestController
@RequestMapping(value = "${zk.path.admin}/${zk.path.sys}/auth/sysAuthUser")
public class ZKSysAuthUserController extends ZKBaseController {

	@Autowired
	private ZKSysAuthUserService sysAuthUserService;
	
	// 编辑
	@RequestMapping(value="sysAuthUser", method = RequestMethod.POST)
	public ZKMsgRes sysAuthUserPost(@RequestBody ZKSysAuthUser sysAuthUser){
		this.sysAuthUserService.save(sysAuthUser);
        return ZKMsgRes.asOk(sysAuthUser);
	}
	
	// 查询详情
	@RequestMapping(value="sysAuthUser", method = RequestMethod.GET)
	public ZKMsgRes sysAuthUserGet(@RequestParam("pkId") String pkId){
		ZKSysAuthUser sysAuthUser = this.sysAuthUserService.get(new ZKSysAuthUser(pkId));
        return ZKMsgRes.asOk(sysAuthUser);
	}
	
	// 分页查询 
	@RequestMapping(value="sysAuthUsersPage", method = RequestMethod.GET)
	public ZKMsgRes sysAuthUsersPageGet(ZKSysAuthUser sysAuthUser, HttpServletRequest hReq, HttpServletResponse hRes){
		ZKPage<ZKSysAuthUser> resPage = ZKPage.asPage(hReq);
        resPage = this.sysAuthUserService.findPage(resPage, sysAuthUser);
        return ZKMsgRes.asOk(resPage);
	}
	
	// 批量删除
	@RequestMapping(value="sysAuthUser", method = RequestMethod.DELETE)
	@ResponseBody
	public ZKMsgRes sysAuthUserDel(@RequestParam("pkId[]") String[] pkIds){
		 int count = 0;
        if (pkIds != null && pkIds.length > 0) {
            for (String pkId : pkIds) {
                count += this.sysAuthUserService.del(new ZKSysAuthUser(pkId));
            }
        }
        return ZKMsgRes.asOk(count);
	}

}