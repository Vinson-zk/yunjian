/**
 * Copyright &copy; All rights reserved.
 */
package com.zk.sys.auth.entity;

import com.zk.core.commons.data.ZKJson;
import java.lang.String;
import com.zk.core.utils.ZKIdUtils;
import com.zk.db.commons.ZKDBQueryType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import com.zk.db.annotation.ZKTable;
import com.zk.db.annotation.ZKColumn;
import com.zk.db.commons.ZKSqlConvertDelegating;
import com.zk.db.commons.ZKSqlProvider;

import com.zk.base.entity.ZKBaseEntity;

/**
 * 定义权限
 * @author 
 * @version 
 */
@ZKTable(name = "t_sys_auth_defined", alias = "sysAuthDefined", orderBy = " c_create_date ASC ")
public class ZKSysAuthDefined extends ZKBaseEntity<String, ZKSysAuthDefined> {
	
	static ZKSqlProvider sqlProvider;
	
    @Override 
    public ZKSqlProvider getSqlProvider() {
        return initSqlProvider();
    }
    
    public static ZKSqlProvider initSqlProvider() {
        if(sqlProvider == null) {
            sqlProvider = new ZKSqlProvider(new ZKSqlConvertDelegating(), new ZKSysAuthDefined());
        }
        return sqlProvider;
    }
    
    private static final long serialVersionUID = 1L;
	
	/**
	 * 权限名称
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@NotEmpty(message = "{zk.core.data.validation.notNull}")
	@ZKColumn(name = "c_name", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	ZKJson name;	
	/**
	 * 权限代码；应用服务项目下唯一；
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_code", isInsert = true, isUpdate = true, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	String code;	
	/**
	 * 应用系统代码
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_system_code", isInsert = true, isUpdate = true, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String systemCode;	
	/**
	 *  简介
	 */
	@ZKColumn(name = "c_short_desc", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = false)
	ZKJson shortDesc;	
	
	public ZKSysAuthDefined() {
		super();
	}

	public ZKSysAuthDefined(String pkId){
		super(pkId);
	}
	
	/**
	 * 权限名称	
	 */	
	public ZKJson getName() {
		return name;
	}
	
	/**
	 * 权限名称
	 */	
	public void setName(ZKJson name) {
		this.name = name;
	}
	/**
	 * 权限代码；应用服务项目下唯一；	
	 */	
	public String getCode() {
		return code;
	}
	
	/**
	 * 权限代码；应用服务项目下唯一；
	 */	
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 应用系统代码	
	 */	
	public String getSystemCode() {
		return systemCode;
	}
	
	/**
	 * 应用系统代码
	 */	
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	/**
	 *  简介	
	 */	
	public ZKJson getShortDesc() {
		return shortDesc;
	}
	
	/**
	 *  简介
	 */	
	public void setShortDesc(ZKJson shortDesc) {
		this.shortDesc = shortDesc;
	}
	
	/**
	 * 根据主键类型，重写主键生成；
	 */
	@Override
	protected String genId() {
        return ZKIdUtils.genLongStringId();
    }
	
}