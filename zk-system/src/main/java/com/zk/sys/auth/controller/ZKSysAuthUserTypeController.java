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

import com.zk.sys.auth.entity.ZKSysAuthUserType;
import com.zk.sys.auth.service.ZKSysAuthUserTypeService;       

/**
 * ZKSysAuthUserTypeController
 * @author 
 * @version 
 */
@RestController
@RequestMapping(value = "${zk.path.admin}/${zk.path.sys}/auth/sysAuthUserType")
public class ZKSysAuthUserTypeController extends ZKBaseController {

	@Autowired
	private ZKSysAuthUserTypeService sysAuthUserTypeService;
	
	// 编辑
	@RequestMapping(value="sysAuthUserType", method = RequestMethod.POST)
	public ZKMsgRes sysAuthUserTypePost(@RequestBody ZKSysAuthUserType sysAuthUserType){
		this.sysAuthUserTypeService.save(sysAuthUserType);
        return ZKMsgRes.asOk(sysAuthUserType);
	}
	
	// 查询详情
	@RequestMapping(value="sysAuthUserType", method = RequestMethod.GET)
	public ZKMsgRes sysAuthUserTypeGet(@RequestParam("pkId") String pkId){
		ZKSysAuthUserType sysAuthUserType = this.sysAuthUserTypeService.get(new ZKSysAuthUserType(pkId));
        return ZKMsgRes.asOk(sysAuthUserType);
	}
	
	// 分页查询 
	@RequestMapping(value="sysAuthUserTypesPage", method = RequestMethod.GET)
	public ZKMsgRes sysAuthUserTypesPageGet(ZKSysAuthUserType sysAuthUserType, HttpServletRequest hReq, HttpServletResponse hRes){
		ZKPage<ZKSysAuthUserType> resPage = ZKPage.asPage(hReq);
        resPage = this.sysAuthUserTypeService.findPage(resPage, sysAuthUserType);
        return ZKMsgRes.asOk(resPage);
	}
	
	// 批量删除
	@RequestMapping(value="sysAuthUserType", method = RequestMethod.DELETE)
	@ResponseBody
	public ZKMsgRes sysAuthUserTypeDel(@RequestParam("pkId[]") String[] pkIds){
		 int count = 0;
        if (pkIds != null && pkIds.length > 0) {
            for (String pkId : pkIds) {
                count += this.sysAuthUserTypeService.del(new ZKSysAuthUserType(pkId));
            }
        }
        return ZKMsgRes.asOk(count);
	}

}