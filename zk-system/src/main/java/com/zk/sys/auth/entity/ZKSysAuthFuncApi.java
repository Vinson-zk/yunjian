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
 * 权限-功能API接口表
 * @author 
 * @version 
 */
@ZKTable(name = "t_sys_auth_func_api", alias = "sysAuthFuncApi", orderBy = " c_create_date ASC ")
public class ZKSysAuthFuncApi extends ZKBaseEntity<String, ZKSysAuthFuncApi> {
	
	static ZKSqlProvider sqlProvider;
	
    @Override 
    public ZKSqlProvider getSqlProvider() {
        return initSqlProvider();
    }
    
    public static ZKSqlProvider initSqlProvider() {
        if(sqlProvider == null) {
            sqlProvider = new ZKSqlProvider(new ZKSqlConvertDelegating(), new ZKSysAuthFuncApi());
        }
        return sqlProvider;
    }
    
    private static final long serialVersionUID = 1L;
	
	/**
	 * 权限 
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_auth_id", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String authId;	
	/**
	 * 功能API接口ID 
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_func_api_id", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String funcApiId;	
	/**
	 * 功能API接口 代码
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_func_api_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	String funcApiCode;	
	
	public ZKSysAuthFuncApi() {
		super();
	}

	public ZKSysAuthFuncApi(String pkId){
		super(pkId);
	}
	
	/**
	 * 权限 	
	 */	
	public String getAuthId() {
		return authId;
	}
	
	/**
	 * 权限 
	 */	
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	/**
	 * 功能API接口ID 	
	 */	
	public String getFuncApiId() {
		return funcApiId;
	}
	
	/**
	 * 功能API接口ID 
	 */	
	public void setFuncApiId(String funcApiId) {
		this.funcApiId = funcApiId;
	}
	/**
	 * 功能API接口 代码	
	 */	
	public String getFuncApiCode() {
		return funcApiCode;
	}
	
	/**
	 * 功能API接口 代码
	 */	
	public void setFuncApiCode(String funcApiCode) {
		this.funcApiCode = funcApiCode;
	}
	
	/**
	 * 根据主键类型，重写主键生成；
	 */
	@Override
	protected String genId() {
        return ZKIdUtils.genLongStringId();
    }
	
}