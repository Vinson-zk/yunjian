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

import com.zk.sys.org.entity.ZKSysOrgDept;
import com.zk.sys.org.service.ZKSysOrgDeptService;       

/**
 * ZKSysOrgDeptController
 * @author 
 * @version 
 */
@RestController
@RequestMapping(value = "${zk.path.admin}/${zk.path.sys}/org/sysOrgDept")
public class ZKSysOrgDeptController extends ZKBaseController {

	@Autowired
	private ZKSysOrgDeptService sysOrgDeptService;
	
	// 编辑
	@RequestMapping(value="sysOrgDept", method = RequestMethod.POST)
	public ZKMsgRes sysOrgDeptPost(@RequestBody ZKSysOrgDept sysOrgDept){
		this.sysOrgDeptService.save(sysOrgDept);
        return ZKMsgRes.asOk(sysOrgDept);
	}
	
	// 查询详情
	@RequestMapping(value="sysOrgDept", method = RequestMethod.GET)
	public ZKMsgRes sysOrgDeptGet(@RequestParam("pkId") String pkId){
		ZKSysOrgDept sysOrgDept = this.sysOrgDeptService.getDetail(new ZKSysOrgDept(pkId));
        return ZKMsgRes.asOk(sysOrgDept);
	}
	
	// 分页查询 
	@RequestMapping(value="sysOrgDeptsTree", method = RequestMethod.GET)
	public ZKMsgRes sysOrgDeptsTree(ZKSysOrgDept sysOrgDept, HttpServletRequest hReq, HttpServletResponse hRes){
		ZKPage<ZKSysOrgDept> resPage = ZKPage.asPage(hReq);
        resPage = this.sysOrgDeptService.findTree(resPage, sysOrgDept);
        return ZKMsgRes.asOk(resPage);
	}
	
	// 批量删除
	@RequestMapping(value="sysOrgDept", method = RequestMethod.DELETE)
	@ResponseBody
	public ZKMsgRes sysOrgDeptDel(@RequestParam("pkId[]") String[] pkIds){
		 int count = 0;
        if (pkIds != null && pkIds.length > 0) {
            for (String pkId : pkIds) {
                count += this.sysOrgDeptService.del(new ZKSysOrgDept(pkId));
            }
        }
        return ZKMsgRes.asOk(count);
	}

}