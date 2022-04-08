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

import com.zk.sys.org.entity.ZKSysOrgUser;
import com.zk.sys.org.service.ZKSysOrgUserService;       

/**
 * ZKSysOrgUserController
 * @author 
 * @version 
 */
@RestController
@RequestMapping(value = "${zk.path.admin}/${zk.path.sys}/org/sysOrgUser")
public class ZKSysOrgUserController extends ZKBaseController {

	@Autowired
	private ZKSysOrgUserService sysOrgUserService;
	
	// 编辑
	@RequestMapping(value="sysOrgUser", method = RequestMethod.POST)
	public ZKMsgRes sysOrgUserPost(@RequestBody ZKSysOrgUser sysOrgUser){
		this.sysOrgUserService.save(sysOrgUser);
        return ZKMsgRes.asOk(sysOrgUser);
	}
	
	// 查询详情
	@RequestMapping(value="sysOrgUser", method = RequestMethod.GET)
	public ZKMsgRes sysOrgUserGet(@RequestParam("pkId") String pkId){
		ZKSysOrgUser sysOrgUser = this.sysOrgUserService.get(new ZKSysOrgUser(pkId));
        return ZKMsgRes.asOk(sysOrgUser);
	}
	
	// 分页查询 
	@RequestMapping(value="sysOrgUsersPage", method = RequestMethod.GET)
	public ZKMsgRes sysOrgUsersPageGet(ZKSysOrgUser sysOrgUser, HttpServletRequest hReq, HttpServletResponse hRes){
		ZKPage<ZKSysOrgUser> resPage = ZKPage.asPage(hReq);
        resPage = this.sysOrgUserService.findPage(resPage, sysOrgUser);
        return ZKMsgRes.asOk(resPage);
	}
	
	// 批量删除
	@RequestMapping(value="sysOrgUser", method = RequestMethod.DELETE)
	@ResponseBody
	public ZKMsgRes sysOrgUserDel(@RequestParam("pkId[]") String[] pkIds){
		 int count = 0;
        if (pkIds != null && pkIds.length > 0) {
            for (String pkId : pkIds) {
                count += this.sysOrgUserService.del(new ZKSysOrgUser(pkId));
            }
        }
        return ZKMsgRes.asOk(count);
	}

}