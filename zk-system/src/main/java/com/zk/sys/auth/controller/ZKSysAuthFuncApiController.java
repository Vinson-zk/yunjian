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

import com.zk.sys.auth.entity.ZKSysAuthFuncApi;
import com.zk.sys.auth.service.ZKSysAuthFuncApiService;       

/**
 * ZKSysAuthFuncApiController
 * @author 
 * @version 
 */
@RestController
@RequestMapping(value = "${zk.path.admin}/${zk.path.sys}/auth/sysAuthFuncApi")
public class ZKSysAuthFuncApiController extends ZKBaseController {

	@Autowired
	private ZKSysAuthFuncApiService sysAuthFuncApiService;
	
	// 编辑
	@RequestMapping(value="sysAuthFuncApi", method = RequestMethod.POST)
	public ZKMsgRes sysAuthFuncApiPost(@RequestBody ZKSysAuthFuncApi sysAuthFuncApi){
		this.sysAuthFuncApiService.save(sysAuthFuncApi);
        return ZKMsgRes.asOk(sysAuthFuncApi);
	}
	
	// 查询详情
	@RequestMapping(value="sysAuthFuncApi", method = RequestMethod.GET)
	public ZKMsgRes sysAuthFuncApiGet(@RequestParam("pkId") String pkId){
		ZKSysAuthFuncApi sysAuthFuncApi = this.sysAuthFuncApiService.get(new ZKSysAuthFuncApi(pkId));
        return ZKMsgRes.asOk(sysAuthFuncApi);
	}
	
	// 分页查询 
	@RequestMapping(value="sysAuthFuncApisPage", method = RequestMethod.GET)
	public ZKMsgRes sysAuthFuncApisPageGet(ZKSysAuthFuncApi sysAuthFuncApi, HttpServletRequest hReq, HttpServletResponse hRes){
		ZKPage<ZKSysAuthFuncApi> resPage = ZKPage.asPage(hReq);
        resPage = this.sysAuthFuncApiService.findPage(resPage, sysAuthFuncApi);
        return ZKMsgRes.asOk(resPage);
	}
	
	// 批量删除
	@RequestMapping(value="sysAuthFuncApi", method = RequestMethod.DELETE)
	@ResponseBody
	public ZKMsgRes sysAuthFuncApiDel(@RequestParam("pkId[]") String[] pkIds){
		 int count = 0;
        if (pkIds != null && pkIds.length > 0) {
            for (String pkId : pkIds) {
                count += this.sysAuthFuncApiService.del(new ZKSysAuthFuncApi(pkId));
            }
        }
        return ZKMsgRes.asOk(count);
	}

}