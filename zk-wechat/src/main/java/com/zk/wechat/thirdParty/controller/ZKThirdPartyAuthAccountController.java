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
import com.zk.wechat.thirdParty.entity.ZKThirdPartyAuthAccount;
import com.zk.wechat.thirdParty.service.ZKThirdPartyAuthAccountService;       

/**
 * ZKThirdPartyAuthAccountController
 * @author 
 * @version 
 */
@RestController
@RequestMapping(value = "${zk.path.admin}/${zk.path.wechat}/${zk.wechat.version}/${zk.path.wechat.thirdParty}/thirdPartyAuthAccount")
public class ZKThirdPartyAuthAccountController extends ZKBaseController {

	@Autowired
	private ZKThirdPartyAuthAccountService thirdPartyAuthAccountService;
	
//	// 编辑
//	@RequestMapping(value="thirdPartyAuthAccount", method = RequestMethod.POST)
//	public ZKMsgRes thirdPartyAuthAccountPost(@RequestBody ZKThirdPartyAuthAccount thirdPartyAuthAccount){
//		this.thirdPartyAuthAccountService.save(thirdPartyAuthAccount);
//        return ZKMsgRes.as("zk.0", null, thirdPartyAuthAccount);
//	}
	
	// 查询详情
	@RequestMapping(value="thirdPartyAuthAccount", method = RequestMethod.GET)
	public ZKMsgRes thirdPartyAuthAccountGet(@RequestParam("pkId") String pkId){
		ZKThirdPartyAuthAccount thirdPartyAuthAccount = this.thirdPartyAuthAccountService.get(new ZKThirdPartyAuthAccount(pkId));
        return ZKMsgRes.as("zk.0", null, thirdPartyAuthAccount);
	}
	
	// 分页查询 
	@RequestMapping(value="thirdPartyAuthAccountsPage", method = RequestMethod.GET)
	public ZKMsgRes thirdPartyAuthAccountsPageGet(ZKThirdPartyAuthAccount thirdPartyAuthAccount, HttpServletRequest hReq, HttpServletResponse hRes){
		ZKPage<ZKThirdPartyAuthAccount> resPage = ZKPage.asPage(hReq);
        resPage = this.thirdPartyAuthAccountService.findPage(resPage, thirdPartyAuthAccount);
        return ZKMsgRes.as("zk.0", null, resPage);
	}
	
//	// 批量删除
//	@RequestMapping(value="thirdPartyAuthAccount", method = RequestMethod.DELETE)
//	@ResponseBody
//	public ZKMsgRes thirdPartyAuthAccountDel(@RequestParam("pkId[]") String[] pkIds){
//		 int count = 0;
//        if (pkIds != null && pkIds.length > 0) {
//            for (String pkId : pkIds) {
//                count += this.thirdPartyAuthAccountService.del(new ZKThirdPartyAuthAccount(pkId));
//            }
//        }
//        return ZKMsgRes.as("zk.0", null, count);
//	}

}