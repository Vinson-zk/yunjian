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
 * 用户角色表
 * @author 
 * @version 
 */
@ZKTable(name = "t_sys_auth_user_role", alias = "sysAuthUserRole", orderBy = " c_create_date ASC ")
public class ZKSysAuthUserRole extends ZKBaseEntity<String, ZKSysAuthUserRole> {
	
	static ZKSqlProvider sqlProvider;
	
    @Override 
    public ZKSqlProvider getSqlProvider() {
        return initSqlProvider();
    }
    
    public static ZKSqlProvider initSqlProvider() {
        if(sqlProvider == null) {
            sqlProvider = new ZKSqlProvider(new ZKSqlConvertDelegating(), new ZKSysAuthUserRole());
        }
        return sqlProvider;
    }
    
    private static final long serialVersionUID = 1L;
	
	/**
	 * 集团代码
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_group_code", isInsert = true, isUpdate = true, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
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
	 * 角色ID 
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_role_id", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String roleId;	
	/**
	 * 职级代码;公司下唯一
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_role_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String roleCode;	
	/**
	 * 用户ID 
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_user_id", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String userId;	
	
	public ZKSysAuthUserRole() {
		super();
	}

	public ZKSysAuthUserRole(String pkId){
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
	 * 角色ID 	
	 */	
	public String getRoleId() {
		return roleId;
	}
	
	/**
	 * 角色ID 
	 */	
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	/**
	 * 职级代码;公司下唯一	
	 */	
	public String getRoleCode() {
		return roleCode;
	}
	
	/**
	 * 职级代码;公司下唯一
	 */	
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	/**
	 * 用户ID 	
	 */	
	public String getUserId() {
		return userId;
	}
	
	/**
	 * 用户ID 
	 */	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * 根据主键类型，重写主键生成；
	 */
	@Override
	protected String genId() {
        return ZKIdUtils.genLongStringId();
    }
	
}