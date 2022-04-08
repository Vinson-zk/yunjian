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

import com.zk.sys.auth.entity.ZKSysAuthMenu;
import com.zk.sys.auth.service.ZKSysAuthMenuService;       

/**
 * ZKSysAuthMenuController
 * @author 
 * @version 
 */
@RestController
@RequestMapping(value = "${zk.path.admin}/${zk.path.sys}/auth/sysAuthMenu")
public class ZKSysAuthMenuController extends ZKBaseController {

	@Autowired
	private ZKSysAuthMenuService sysAuthMenuService;
	
	// 编辑
	@RequestMapping(value="sysAuthMenu", method = RequestMethod.POST)
	public ZKMsgRes sysAuthMenuPost(@RequestBody ZKSysAuthMenu sysAuthMenu){
		this.sysAuthMenuService.save(sysAuthMenu);
        return ZKMsgRes.asOk(sysAuthMenu);
	}
	
	// 查询详情
	@RequestMapping(value="sysAuthMenu", method = RequestMethod.GET)
	public ZKMsgRes sysAuthMenuGet(@RequestParam("pkId") String pkId){
		ZKSysAuthMenu sysAuthMenu = this.sysAuthMenuService.get(new ZKSysAuthMenu(pkId));
        return ZKMsgRes.asOk(sysAuthMenu);
	}
	
	// 分页查询 
	@RequestMapping(value="sysAuthMenusPage", method = RequestMethod.GET)
	public ZKMsgRes sysAuthMenusPageGet(ZKSysAuthMenu sysAuthMenu, HttpServletRequest hReq, HttpServletResponse hRes){
		ZKPage<ZKSysAuthMenu> resPage = ZKPage.asPage(hReq);
        resPage = this.sysAuthMenuService.findPage(resPage, sysAuthMenu);
        return ZKMsgRes.asOk(resPage);
	}
	
	// 批量删除
	@RequestMapping(value="sysAuthMenu", method = RequestMethod.DELETE)
	@ResponseBody
	public ZKMsgRes sysAuthMenuDel(@RequestParam("pkId[]") String[] pkIds){
		 int count = 0;
        if (pkIds != null && pkIds.length > 0) {
            for (String pkId : pkIds) {
                count += this.sysAuthMenuService.del(new ZKSysAuthMenu(pkId));
            }
        }
        return ZKMsgRes.asOk(count);
	}

}