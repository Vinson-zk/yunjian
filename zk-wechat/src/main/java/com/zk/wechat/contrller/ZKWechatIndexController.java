/** 
* Copyright (c) 2004-2020 ZK-Vinson Technologies, Inc.
* address: 
* All rights reserved. 
* 
* This software is the confidential and proprietary information of 
* ZK-Vinson Technologies, Inc. ("Confidential Information"). You shall not 
* disclose such Confidential Information and shall use it only in 
* accordance with the terms of the license agreement you entered into 
* with ZK-Vinson. 
*
* @Title: ZKWechatIndexController.java 
* @author Vinson 
* @Package com.zk.wechat.contrller 
* @Description: TODO(simple description this file what to do. ) 
* @date Feb 23, 2021 4:10:26 PM 
* @version V1.0 
*/
package com.zk.wechat.contrller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zk.core.web.ZKMsgRes;

/** 
* @ClassName: ZKWechatIndexController 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
@RestController
//https://gfwechat.zhgxfz.com/zk/wechat/wx/pay/jsApi
@RequestMapping("${zk.path.admin}/${zk.path.wechat}/${zk.wechat.version}")
public class ZKWechatIndexController {

    @RequestMapping({ "", "index" })
    public ZKMsgRes index() {
        return ZKMsgRes.asOk(null);
    }

}
