/**
 * Copyright &copy; All rights reserved.
 */
package com.zk.sys.res.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.zk.base.entity.ZKBaseEntity;
import com.zk.core.commons.data.ZKJson;
import com.zk.core.utils.ZKIdUtils;
import com.zk.db.annotation.ZKColumn;
import com.zk.db.annotation.ZKTable;
import com.zk.db.commons.ZKDBQueryType;
import com.zk.db.commons.ZKSqlConvertDelegating;
import com.zk.db.commons.ZKSqlProvider;

/**
 * 应用系统
 * @author 
 * @version 
 */
@ZKTable(name = "t_sys_res_application_system", alias = "sysResApplicationSystem", orderBy = " c_create_date ASC ")
public class ZKSysResApplicationSystem extends ZKBaseEntity<String, ZKSysResApplicationSystem> {
	
	static ZKSqlProvider sqlProvider;
	
    @Override 
    public ZKSqlProvider getSqlProvider() {
        return initSqlProvider();
    }
    
    public static ZKSqlProvider initSqlProvider() {
        if(sqlProvider == null) {
            sqlProvider = new ZKSqlProvider(new ZKSqlConvertDelegating(), new ZKSysResApplicationSystem());
        }
        return sqlProvider;
    }
    
    private static final long serialVersionUID = 1L;
	
	/**
	 * 应用系统名称
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@NotEmpty(message = "{zk.core.data.validation.notNull}")
	@ZKColumn(name = "c_name", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	ZKJson name;	
	/**
	 * 应用系统代码，唯一
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	String code;	
	/**
	 * 应用系统简称
	 */
	@ZKColumn(name = "c_short_name", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = false)
	ZKJson shortName;	
	
	public ZKSysResApplicationSystem() {
		super();
	}

	public ZKSysResApplicationSystem(String pkId){
		super(pkId);
	}
	
	/**
	 * 应用系统名称	
	 */	
	public ZKJson getName() {
		return name;
	}
	
	/**
	 * 应用系统名称
	 */	
	public void setName(ZKJson name) {
		this.name = name;
	}
	/**
	 * 应用系统代码，唯一	
	 */	
	public String getCode() {
		return code;
	}
	
	/**
	 * 应用系统代码，唯一
	 */	
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 应用系统简称	
	 */	
	public ZKJson getShortName() {
		return shortName;
	}
	
	/**
	 * 应用系统简称
	 */	
	public void setShortName(ZKJson shortName) {
		this.shortName = shortName;
	}
	
	/**
	 * 根据主键类型，重写主键生成；
	 */
	@Override
	protected String genId() {
        return ZKIdUtils.genLongStringId();
    }
	
}