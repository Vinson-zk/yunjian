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
* @Title: ZKSecSimpleAuthorizationInfo.java 
* @author Vinson 
* @Package com.zk.security.authz 
* @Description: TODO(simple description this file what to do. ) 
* @date Jul 13, 2021 2:50:04 PM 
* @version V1.0 
*/
package com.zk.security.authz;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/** 
* @ClassName: ZKSecSimpleAuthorizationInfo 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKSecSimpleAuthorizationInfo implements ZKSecAuthorizationInfo {

    /**
     * @Fields serialVersionUID : TODO(simple description what to do.)
     */
    private static final long serialVersionUID = 2649735532521497365L;

    private Set<String> personalApiCodes;

    private Set<String> userTypeApiCodes;

    private Set<String> userGradeApiCodes;

    private Set<String> companyApiCodes;

    private Set<String> positionApiCodes;

    private Set<String> departmentApiCodes;

    private Set<String> userGroupApiCodes;

    /**
     * 添加个人用户拥有的 apiCode
     * 
     * @param apiCodes
     * @see com.zk.security.authz.ZKSecAuthorizationInfo#addPersonalApiCode(java.lang.String[])
     */
    @Override
    public void addPersonalApiCode(String apiCode) {
        if (this.personalApiCodes == null) {
            this.personalApiCodes = new HashSet<String>();
        }
        this.personalApiCodes.add(apiCode);
    }

    /**
     * (not Javadoc)
     * <p>
     * Title: addUserTypeApiCode
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param apiCodes
     * @see com.zk.security.authz.ZKSecAuthorizationInfo#addUserTypeApiCode(java.lang.String[])
     */
    @Override
    public void addUserTypeApiCode(String apiCode) {
        if (this.userTypeApiCodes == null) {
            this.userTypeApiCodes = new HashSet<String>();
        }
        this.userTypeApiCodes.add(apiCode);
    }

    @Override
    public void addUserGradeApiCode(String apiCode) {
        if (this.userGradeApiCodes == null) {
            this.userGradeApiCodes = new HashSet<String>();
        }
        this.userGradeApiCodes.add(apiCode);
    }

    @Override
    public void addCompanyApiCode(String apiCode) {
        if (this.companyApiCodes == null) {
            this.companyApiCodes = new HashSet<String>();
        }
        this.companyApiCodes.add(apiCode);
    }

    @Override
    public void addPositionApiCode(String apiCode) {
        if (this.positionApiCodes == null) {
            this.positionApiCodes = new HashSet<String>();
        }
        this.positionApiCodes.add(apiCode);
    }

    @Override
    public void addDepartmentApiCode(String apiCode) {
        if (this.departmentApiCodes == null) {
            this.departmentApiCodes = new HashSet<String>();
        }
        this.departmentApiCodes.add(apiCode);
    }

    @Override
    public void addUserGroupApiCode(String apiCode) {
        if (this.userGroupApiCodes == null) {
            this.userGroupApiCodes = new HashSet<String>();
        }
        this.userGroupApiCodes.add(apiCode);
    }

    @Override
    public Collection<String> getPersonalApiCodes() {
        return personalApiCodes;
    }

    @Override
    public Collection<String> getUserTypeApiCodes() {
        return userTypeApiCodes;
    }

    @Override
    public Collection<String> getUserGradeApiCodes() {
        return userGradeApiCodes;
    }

    @Override
    public Collection<String> getCompanyApiCodes() {
        return companyApiCodes;
    }

    @Override
    public Collection<String> getPositionApiCodes() {
        return positionApiCodes;
    }

    @Override
    public Collection<String> getDepartmentApiCodes() {
        return departmentApiCodes;
    }

    @Override
    public Collection<String> getUserGroupApiCodes() {
        return userGroupApiCodes;
    }

}
