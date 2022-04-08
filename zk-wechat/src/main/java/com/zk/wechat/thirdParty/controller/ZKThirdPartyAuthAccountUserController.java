/**
 * 
 */
package com.zk.wechat.thirdParty.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zk.base.controller.ZKBaseController;
import com.zk.core.commons.data.ZKPage;
import com.zk.core.web.ZKMsgRes;
import com.zk.wechat.thirdParty.entity.ZKThirdPartyAuthAccountUser;
import com.zk.wechat.thirdParty.service.ZKThirdPartyAuthAccountUserService;       

/**
 * ZKThirdPartyAuthAccountUserController
 * @author 
 * @version 
 */
@RestController
@RequestMapping(value = "${zk.path.admin}/${zk.path.wechat}/${zk.wechat.version}/${zk.path.wechat.thirdParty}/thirdPartyAuthAccountUser")
public class ZKThirdPartyAuthAccountUserController extends ZKBaseController {

	@Autowired
	private ZKThirdPartyAuthAccountUserService thirdPartyAuthAccountUserService;
	
//	// 编辑
//	@RequestMapping(value="thirdPartyAuthAccountUser", method = RequestMethod.POST)
//	public ZKMsgRes thirdPartyAuthAccountUserPost(@RequestBody ZKThirdPartyAuthAccountUser thirdPartyAuthAccountUser){
//		this.thirdPartyAuthAccountUserService.save(thirdPartyAuthAccountUser);
//        return ZKMsgRes.as("zk.0", null, thirdPartyAuthAccountUser);
//	}
	
	// 查询详情
	@RequestMapping(value="thirdPartyAuthAccountUser", method = RequestMethod.GET)
	public ZKMsgRes thirdPartyAuthAccountUserGet(@RequestParam("pkId") String pkId){
		ZKThirdPartyAuthAccountUser thirdPartyAuthAccountUser = this.thirdPartyAuthAccountUserService.get(new ZKThirdPartyAuthAccountUser(pkId));
        return ZKMsgRes.as("zk.0", null, thirdPartyAuthAccountUser);
	}
	
	// 分页查询 
	@RequestMapping(value="thirdPartyAuthAccountUsersPage", method = RequestMethod.GET)
	public ZKMsgRes thirdPartyAuthAccountUsersPageGet(ZKThirdPartyAuthAccountUser thirdPartyAuthAccountUser, HttpServletRequest hReq, HttpServletResponse hRes){
		ZKPage<ZKThirdPartyAuthAccountUser> resPage = ZKPage.asPage(hReq);
        resPage = this.thirdPartyAuthAccountUserService.findPage(resPage, thirdPartyAuthAccountUser);
        return ZKMsgRes.as("zk.0", null, resPage);
	}
	
//	// 批量删除
//	@RequestMapping(value="thirdPartyAuthAccountUser", method = RequestMethod.DELETE)
//	@ResponseBody
//	public ZKMsgRes thirdPartyAuthAccountUserDel(@RequestParam("pkId[]") String[] pkIds){
//		 int count = 0;
//        if (pkIds != null && pkIds.length > 0) {
//            for (String pkId : pkIds) {
//                count += this.thirdPartyAuthAccountUserService.del(new ZKThirdPartyAuthAccountUser(pkId));
//            }
//        }
//        return ZKMsgRes.as("zk.0", null, count);
//	}

}