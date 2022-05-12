/**
 * Copyright &copy; All rights reserved.
 */
package com.zk.sys.auth.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.zk.base.entity.ZKBaseEntity;
import com.zk.core.utils.ZKIdUtils;
import com.zk.db.annotation.ZKColumn;
import com.zk.db.annotation.ZKTable;
import com.zk.db.commons.ZKDBQueryType;
import com.zk.db.commons.ZKSqlConvertDelegating;
import com.zk.db.mybatis.commons.ZKSqlProvider;

/**
 * 权限-导航栏目表
 * @author 
 * @version 
 */
@ZKTable(name = "t_sys_auth_nav", alias = "sysAuthNav", orderBy = " c_create_date ASC ")
public class ZKSysAuthNav extends ZKBaseEntity<String, ZKSysAuthNav> {
	
	static ZKSqlProvider sqlProvider;
	
    @Override 
    public ZKSqlProvider getSqlProvider() {
        return initSqlProvider();
    }
    
    public static ZKSqlProvider initSqlProvider() {
        if(sqlProvider == null) {
            sqlProvider = new ZKSqlProvider(new ZKSqlConvertDelegating(), new ZKSysAuthNav());
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
	 * 导航栏目
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_nav_id", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String navId;	
	/**
	 * 导航栏目代码
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_nav_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String navCode;	
	
	public ZKSysAuthNav() {
		super();
	}

	public ZKSysAuthNav(String pkId){
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
	 * 导航栏目	
	 */	
	public String getNavId() {
		return navId;
	}
	
	/**
	 * 导航栏目
	 */	
	public void setNavId(String navId) {
		this.navId = navId;
	}
	/**
	 * 导航栏目代码	
	 */	
	public String getNavCode() {
		return navCode;
	}
	
	/**
	 * 导航栏目代码
	 */	
	public void setNavCode(String navCode) {
		this.navCode = navCode;
	}
	
	/**
	 * 根据主键类型，重写主键生成；
	 */
	@Override
	protected String genId() {
        return ZKIdUtils.genLongStringId();
    }
	
}