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
* @Title: ZKSecAuthorizationInfo.java 
* @author Vinson 
* @Package com.zk.security.authz 
* @Description: TODO(simple description this file what to do. ) 
* @date Jul 10, 2021 8:24:32 AM 
* @version V1.0 
*/
package com.zk.security.authz;

import java.io.Serializable;
import java.util.Collection;

/** 
* @ClassName: ZKSecAuthorizationInfo 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public interface ZKSecAuthorizationInfo extends Serializable {

    /**
     * 个人权限
     */
    void addPersonalApiCode(String apiCode);

    /**
     * 个人权限
     */
    Collection<String> getPersonalApiCodes();

    /**
     * 用户类型权限
     */
    void addUserTypeApiCode(String apiCode);

    /**
     * 用户类型权限
     */
    Collection<String> getUserTypeApiCodes();

    /**
     * 用户职级权限
     */
    void addUserGradeApiCode(String apiCode);

    /**
     * 用户职级权限
     */
    Collection<String> getUserGradeApiCodes();

    /**
     * 公司权限
     */
    void addCompanyApiCode(String apiCode);

    /**
     * 公司权限
     */
    Collection<String> getCompanyApiCodes();

    /**
     * 岗位权限
     */
    void addPositionApiCode(String apiCode);

    /**
     * 岗位权限
     */
    Collection<String> getPositionApiCodes();

    /**
     * 部门权限
     */
    void addDepartmentApiCode(String apiCode);

    /**
     * 部门权限
     */
    Collection<String> getDepartmentApiCodes();

    /**
     * 用户组权限
     */
    void addUserGroupApiCode(String apiCode);

    /**
     * 用户组权限
     */
    Collection<String> getUserGroupApiCodes();

}
