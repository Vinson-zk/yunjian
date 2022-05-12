/**
 * Copyright &copy; All rights reserved.
 */
package com.zk.sys.auth.entity;

import java.lang.String;
import com.zk.core.utils.ZKIdUtils;
import com.zk.db.commons.ZKDBQueryType;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.zk.db.annotation.ZKTable;
import com.zk.db.annotation.ZKColumn;
import com.zk.db.commons.ZKSqlConvertDelegating;
import com.zk.db.mybatis.commons.ZKSqlProvider;
import com.zk.base.entity.ZKBaseEntity;

/**
 * 部门权限
 * @author 
 * @version 
 */
@ZKTable(name = "t_sys_auth_dept", alias = "sysAuthDept", orderBy = " c_create_date ASC ")
public class ZKSysAuthDept extends ZKBaseEntity<String, ZKSysAuthDept> {
	
	static ZKSqlProvider sqlProvider;
	
    @Override 
    public ZKSqlProvider getSqlProvider() {
        return initSqlProvider();
    }
    
    public static ZKSqlProvider initSqlProvider() {
        if(sqlProvider == null) {
            sqlProvider = new ZKSqlProvider(new ZKSqlConvertDelegating(), new ZKSysAuthDept());
        }
        return sqlProvider;
    }
    
    private static final long serialVersionUID = 1L;
	
	/**
	 * 集团代码
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_group_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String groupCode;	
	/**
	 * 公司ID 
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_company_id", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String companyId;	
	/**
	 * 公司代码
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_company_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String companyCode;	
	/**
	 * 部门ID 
	 */
	@Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_dept_id", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String deptId;	
	/**
	 * 部门代码
	 */
	@Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_dept_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String deptCode;	
	/**
	 * 权限ID 
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_auth_id", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String authId;	
	/**
	 * 权限代码
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_auth_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String authCode;	
	
	public ZKSysAuthDept() {
		super();
	}

	public ZKSysAuthDept(String pkId){
		super(pkId);
	}
	
	/**
	 * 集团代码	
	 */	
	public String getGroupCode() {
		return groupCode;
	}
	
	/**
	 * 集团代码
	 */	
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	/**
	 * 公司ID 	
	 */	
	public String getCompanyId() {
		return companyId;
	}
	
	/**
	 * 公司ID 
	 */	
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	/**
	 * 公司代码	
	 */	
	public String getCompanyCode() {
		return companyCode;
	}
	
	/**
	 * 公司代码
	 */	
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * 部门ID 	
	 */	
	public String getDeptId() {
		return deptId;
	}
	
	/**
	 * 部门ID 
	 */	
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	/**
	 * 部门代码	
	 */	
	public String getDeptCode() {
		return deptCode;
	}
	
	/**
	 * 部门代码
	 */	
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	/**
	 * 权限ID 	
	 */	
	public String getAuthId() {
		return authId;
	}
	
	/**
	 * 权限ID 
	 */	
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	/**
	 * 权限代码	
	 */	
	public String getAuthCode() {
		return authCode;
	}
	
	/**
	 * 权限代码
	 */	
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	
	/**
	 * 根据主键类型，重写主键生成；
	 */
	@Override
	protected String genId() {
        return ZKIdUtils.genLongStringId();
    }
	
}