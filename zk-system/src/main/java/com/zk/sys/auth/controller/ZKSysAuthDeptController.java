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

import com.zk.sys.auth.entity.ZKSysAuthDept;
import com.zk.sys.auth.service.ZKSysAuthDeptService;       

/**
 * ZKSysAuthDeptController
 * @author 
 * @version 
 */
@RestController
@RequestMapping(value = "${zk.path.admin}/${zk.path.sys}/auth/sysAuthDept")
public class ZKSysAuthDeptController extends ZKBaseController {

	@Autowired
	private ZKSysAuthDeptService sysAuthDeptService;
	
	// 编辑
	@RequestMapping(value="sysAuthDept", method = RequestMethod.POST)
	public ZKMsgRes sysAuthDeptPost(@RequestBody ZKSysAuthDept sysAuthDept){
		this.sysAuthDeptService.save(sysAuthDept);
        return ZKMsgRes.asOk(sysAuthDept);
	}
	
	// 查询详情
	@RequestMapping(value="sysAuthDept", method = RequestMethod.GET)
	public ZKMsgRes sysAuthDeptGet(@RequestParam("pkId") String pkId){
		ZKSysAuthDept sysAuthDept = this.sysAuthDeptService.get(new ZKSysAuthDept(pkId));
        return ZKMsgRes.asOk(sysAuthDept);
	}
	
	// 分页查询 
	@RequestMapping(value="sysAuthDeptsPage", method = RequestMethod.GET)
	public ZKMsgRes sysAuthDeptsPageGet(ZKSysAuthDept sysAuthDept, HttpServletRequest hReq, HttpServletResponse hRes){
		ZKPage<ZKSysAuthDept> resPage = ZKPage.asPage(hReq);
        resPage = this.sysAuthDeptService.findPage(resPage, sysAuthDept);
        return ZKMsgRes.asOk(resPage);
	}
	
	// 批量删除
	@RequestMapping(value="sysAuthDept", method = RequestMethod.DELETE)
	@ResponseBody
	public ZKMsgRes sysAuthDeptDel(@RequestParam("pkId[]") String[] pkIds){
		 int count = 0;
        if (pkIds != null && pkIds.length > 0) {
            for (String pkId : pkIds) {
                count += this.sysAuthDeptService.del(new ZKSysAuthDept(pkId));
            }
        }
        return ZKMsgRes.asOk(count);
	}

}