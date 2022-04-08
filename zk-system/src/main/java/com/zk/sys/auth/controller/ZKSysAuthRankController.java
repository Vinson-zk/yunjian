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

import com.zk.sys.auth.entity.ZKSysAuthRank;
import com.zk.sys.auth.service.ZKSysAuthRankService;       

/**
 * ZKSysAuthRankController
 * @author 
 * @version 
 */
@RestController
@RequestMapping(value = "${zk.path.admin}/${zk.path.sys}/auth/sysAuthRank")
public class ZKSysAuthRankController extends ZKBaseController {

	@Autowired
	private ZKSysAuthRankService sysAuthRankService;
	
	// 编辑
	@RequestMapping(value="sysAuthRank", method = RequestMethod.POST)
	public ZKMsgRes sysAuthRankPost(@RequestBody ZKSysAuthRank sysAuthRank){
		this.sysAuthRankService.save(sysAuthRank);
        return ZKMsgRes.asOk(sysAuthRank);
	}
	
	// 查询详情
	@RequestMapping(value="sysAuthRank", method = RequestMethod.GET)
	public ZKMsgRes sysAuthRankGet(@RequestParam("pkId") String pkId){
		ZKSysAuthRank sysAuthRank = this.sysAuthRankService.get(new ZKSysAuthRank(pkId));
        return ZKMsgRes.asOk(sysAuthRank);
	}
	
	// 分页查询 
	@RequestMapping(value="sysAuthRanksPage", method = RequestMethod.GET)
	public ZKMsgRes sysAuthRanksPageGet(ZKSysAuthRank sysAuthRank, HttpServletRequest hReq, HttpServletResponse hRes){
		ZKPage<ZKSysAuthRank> resPage = ZKPage.asPage(hReq);
        resPage = this.sysAuthRankService.findPage(resPage, sysAuthRank);
        return ZKMsgRes.asOk(resPage);
	}
	
	// 批量删除
	@RequestMapping(value="sysAuthRank", method = RequestMethod.DELETE)
	@ResponseBody
	public ZKMsgRes sysAuthRankDel(@RequestParam("pkId[]") String[] pkIds){
		 int count = 0;
        if (pkIds != null && pkIds.length > 0) {
            for (String pkId : pkIds) {
                count += this.sysAuthRankService.del(new ZKSysAuthRank(pkId));
            }
        }
        return ZKMsgRes.asOk(count);
	}

}