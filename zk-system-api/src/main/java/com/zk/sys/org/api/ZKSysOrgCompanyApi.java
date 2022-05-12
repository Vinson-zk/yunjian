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
* @Title: ZKSysOrgCompanyApi.java 
* @author Vinson 
* @Package com.zk.sys.org.api 
* @Description: TODO(simple description this file what to do. ) 
* @date May 12, 2022 2:35:45 PM 
* @version V1.0 
*/
package com.zk.sys.org.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zk.core.web.ZKMsgRes;

/** 
* @ClassName: ZKSysOrgCompanyApi 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
@FeignClient(name = "${spring.application.name}")
public interface ZKSysOrgCompanyApi {

    // 根据公司代码查公司详情
    @RequestMapping(path = "${zk.path.admin}/${zk.path.sys}/${zk.sys.version}/org/sysOrgCompany/sysOrgCompanyByCode", method = RequestMethod.GET)
    ZKMsgRes getCompanyByCode(@RequestParam("companyCode") String companyCode);

}
