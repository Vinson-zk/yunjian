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
* @Title: ZKOpenFeignConstant.java 
* @author Vinson 
* @Package com.zk.framwwork.common 
* @Description: TODO(simple description this file what to do. ) 
* @date May 12, 2022 11:23:28 AM 
* @version V1.0 
*/
package com.zk.framwwork.common;

/**
 * 定义 openFeign 的 FeignClient 名称; 要与对应项目配置的 spring.application.name 相同
 * 
 * @ClassName: ZKOpenFeignConstant
 * @Description: TODO(simple description this class what to do. )
 * @author Vinson
 * @version 1.0
 */
public interface ZKOpenFeignConstant {

    public static interface YunJian_App_Name {
        /**
         * zk-system
         */
        public static final String zkSys = "yunjian.zk.system";
    }

}
