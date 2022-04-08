/**
 * Copyright &copy; All rights reserved.
 */
package com.zk.sys.org.entity;

import java.lang.String;
import com.zk.core.commons.data.ZKJson;
import com.zk.core.utils.ZKIdUtils;
import com.zk.db.commons.ZKDBQueryType;

import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotEmpty;

import com.zk.db.annotation.ZKTable;
import com.zk.db.annotation.ZKColumn;
import com.zk.db.commons.ZKSqlConvertDelegating;
import com.zk.db.commons.ZKSqlProvider;

import com.zk.base.entity.ZKBaseEntity;

/**
 * 角色表
 * @author 
 * @version 
 */
@ZKTable(name = "t_sys_org_role", alias = "sysOrgRole", orderBy = " c_create_date ASC ")
public class ZKSysOrgRole extends ZKBaseEntity<String, ZKSysOrgRole> {
	
	static ZKSqlProvider sqlProvider;
	
    @Override 
    public ZKSqlProvider getSqlProvider() {
        return initSqlProvider();
    }
    
    public static ZKSqlProvider initSqlProvider() {
        if(sqlProvider == null) {
            sqlProvider = new ZKSqlProvider(new ZKSqlConvertDelegating(), new ZKSysOrgRole());
        }
        return sqlProvider;
    }
    
    private static final long serialVersionUID = 1L;
	
	/**
	 * 集团代码
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_group_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
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
	 * 角色代码;公司下唯一
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	String code;	
	/**
	 * 角色名称
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@NotEmpty(message = "{zk.core.data.validation.notNull}")
	@ZKColumn(name = "c_name", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	ZKJson name;	
	/**
	 * 角色类型；0-正常；1-部门角色，指定为某个部门的角色；
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Range(min = 0, max = 999999999, message = "{zk.core.data.validation.rang.int}")
	@ZKColumn(name = "c_type", isInsert = true, isUpdate = true, javaType = Long.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	Long type;	
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
	@ZKColumn(name = "c_dept_code", isInsert = true, isUpdate = true, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String deptCode;	
	/**
	 * 角色状态；0-正常；1-禁用；
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Range(min = 0, max = 999999999, message = "{zk.core.data.validation.rang.int}")
	@ZKColumn(name = "c_status", isInsert = true, isUpdate = true, javaType = Long.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	Long status;	
	/**
	 *  角色简介
	 */
	@ZKColumn(name = "c_short_desc", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = false)
	ZKJson shortDesc;	
	/**
	 * 来源代码；与来源ID标识唯一；
	 */
	@Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_source_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String sourceCode;	
	/**
	 * 来源ID标识，与来源代码唯一
	 */
	@Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_source_id", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String sourceId;	
	
	public ZKSysOrgRole() {
		super();
	}

	public ZKSysOrgRole(String pkId){
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
	 * 角色代码;公司下唯一	
	 */	
	public String getCode() {
		return code;
	}
	
	/**
	 * 角色代码;公司下唯一
	 */	
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 角色名称	
	 */	
	public ZKJson getName() {
		return name;
	}
	
	/**
	 * 角色名称
	 */	
	public void setName(ZKJson name) {
		this.name = name;
	}
	/**
	 * 角色类型；0-正常；1-部门角色，指定为某个部门的角色；	
	 */	
	public Long getType() {
		return type;
	}
	
	/**
	 * 角色类型；0-正常；1-部门角色，指定为某个部门的角色；
	 */	
	public void setType(Long type) {
		this.type = type;
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
	 * 角色状态；0-正常；1-禁用；	
	 */	
	public Long getStatus() {
		return status;
	}
	
	/**
	 * 角色状态；0-正常；1-禁用；
	 */	
	public void setStatus(Long status) {
		this.status = status;
	}
	/**
	 *  角色简介	
	 */	
	public ZKJson getShortDesc() {
		return shortDesc;
	}
	
	/**
	 *  角色简介
	 */	
	public void setShortDesc(ZKJson shortDesc) {
		this.shortDesc = shortDesc;
	}
	/**
	 * 来源代码；与来源ID标识唯一；	
	 */	
	public String getSourceCode() {
		return sourceCode;
	}
	
	/**
	 * 来源代码；与来源ID标识唯一；
	 */	
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	/**
	 * 来源ID标识，与来源代码唯一	
	 */	
	public String getSourceId() {
		return sourceId;
	}
	
	/**
	 * 来源ID标识，与来源代码唯一
	 */	
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	/**
	 * 根据主键类型，重写主键生成；
	 */
	@Override
	protected String genId() {
        return ZKIdUtils.genLongStringId();
    }
	
}