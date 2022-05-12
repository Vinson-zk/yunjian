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
import com.zk.db.mybatis.commons.ZKSqlProvider;

/**
 * 字典类型表
 * @author 
 * @version 
 */
@ZKTable(name = "t_sys_res_dict_type", alias = "sysResDictType", orderBy = " c_create_date ASC ")
public class ZKSysResDictType extends ZKBaseEntity<String, ZKSysResDictType> {
	
	static ZKSqlProvider sqlProvider;
	
    @Override 
    public ZKSqlProvider getSqlProvider() {
        return initSqlProvider();
    }
    
    public static ZKSqlProvider initSqlProvider() {
        if(sqlProvider == null) {
            sqlProvider = new ZKSqlProvider(new ZKSqlConvertDelegating(), new ZKSysResDictType());
        }
        return sqlProvider;
    }
    
    private static final long serialVersionUID = 1L;
	
    /**
     * 字典类型代码；全表唯一
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_type_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
    String typeCode;

	/**
	 * 字典类型名称
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@NotEmpty(message = "{zk.core.data.validation.notNull}")
	@ZKColumn(name = "c_type_name", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
    ZKJson typeName;

	/**
	 * 字典类型说明
	 */
	@ZKColumn(name = "c_type_desc", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = false)
    ZKJson typeDesc;
	
	public ZKSysResDictType() {
		super();
	}

	public ZKSysResDictType(String pkId){
		super(pkId);
	}
	
	/**
     * 字典类型代码；全表唯一
     */	
	public String getTypeCode() {
		return typeCode;
	}
	
	/**
     * 字典类型代码；全表唯一
     */	
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	/**
	 * 字典类型名称	
	 */	
    public ZKJson getTypeName() {
		return typeName;
	}
	
	/**
	 * 字典类型名称
	 */	
    public void setTypeName(ZKJson typeName) {
		this.typeName = typeName;
	}

	/**
	 * 字典类型说明	
	 */	
    public ZKJson getTypeDesc() {
		return typeDesc;
	}
	
	/**
	 * 字典类型说明
	 */	
    public void setTypeDesc(ZKJson typeDesc) {
		this.typeDesc = typeDesc;
	}
	
	/**
	 * 根据主键类型，重写主键生成；
	 */
	@Override
	protected String genId() {
        return ZKIdUtils.genLongStringId();
    }
	
}