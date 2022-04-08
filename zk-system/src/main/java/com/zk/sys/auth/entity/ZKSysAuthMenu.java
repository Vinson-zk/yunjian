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
import com.zk.db.commons.ZKSqlProvider;

import com.zk.base.entity.ZKBaseEntity;

/**
 * 权限-菜单资源表
 * @author 
 * @version 
 */
@ZKTable(name = "t_sys_auth_menu", alias = "sysAuthMenu", orderBy = " c_create_date ASC ")
public class ZKSysAuthMenu extends ZKBaseEntity<String, ZKSysAuthMenu> {
	
	static ZKSqlProvider sqlProvider;
	
    @Override 
    public ZKSqlProvider getSqlProvider() {
        return initSqlProvider();
    }
    
    public static ZKSqlProvider initSqlProvider() {
        if(sqlProvider == null) {
            sqlProvider = new ZKSqlProvider(new ZKSqlConvertDelegating(), new ZKSysAuthMenu());
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
	 * 菜单ID 
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_menu_id", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String menuId;	
	/**
	 * 菜单代码
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_menu_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	String menuCode;	
	
	public ZKSysAuthMenu() {
		super();
	}

	public ZKSysAuthMenu(String pkId){
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
	 * 菜单ID 	
	 */	
	public String getMenuId() {
		return menuId;
	}
	
	/**
	 * 菜单ID 
	 */	
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	/**
	 * 菜单代码	
	 */	
	public String getMenuCode() {
		return menuCode;
	}
	
	/**
	 * 菜单代码
	 */	
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	
	/**
	 * 根据主键类型，重写主键生成；
	 */
	@Override
	protected String genId() {
        return ZKIdUtils.genLongStringId();
    }
	
}