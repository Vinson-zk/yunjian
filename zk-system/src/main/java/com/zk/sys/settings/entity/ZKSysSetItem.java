/**
 * Copyright &copy; All rights reserved.
 */
package com.zk.sys.settings.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.zk.base.entity.ZKBaseEntity;
import com.zk.core.commons.data.ZKJson;
import com.zk.core.utils.ZKIdUtils;
import com.zk.db.annotation.ZKColumn;
import com.zk.db.annotation.ZKTable;
import com.zk.db.commons.ZKDBQueryType;
import com.zk.db.commons.ZKSqlConvertDelegating;
import com.zk.db.commons.ZKSqlProvider;

/**
 * 应用系统配置项条目
 * @author 
 * @version 
 */
@ZKTable(name = "t_sys_set_item", alias = "sysSetItem", orderBy = " c_create_date ASC ")
public class ZKSysSetItem extends ZKBaseEntity<String, ZKSysSetItem> {
	
	static ZKSqlProvider sqlProvider;
	
    @Override 
    public ZKSqlProvider getSqlProvider() {
        return initSqlProvider();
    }
    
    public static ZKSqlProvider initSqlProvider() {
        if(sqlProvider == null) {
            sqlProvider = new ZKSqlProvider(new ZKSqlConvertDelegating(), new ZKSysSetItem());
        }
        return sqlProvider;
    }
    
    private static final long serialVersionUID = 1L;
	
	/**
	 * 配置项类型；0-平台配置；1-通用配置；2-公司专属配置；仅在公司专属配置下，才有公司和集团信息；仅通用配置，才会有公司自定义配置值
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Range(min = 0, max = 999999999, message = "{zk.core.data.validation.rang.int}")
	@ZKColumn(name = "c_type", isInsert = true, isUpdate = true, javaType = Integer.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	Integer type;	
	/**
	 * 配置项名称
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@NotEmpty(message = "{zk.core.data.validation.notNull}")
	@ZKColumn(name = "c_name", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	ZKJson name;	
	/**
	 * 配置项代码；全表唯一；
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_code", isInsert = true, isUpdate = true, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	String code;	
	/**
	 * 配置项说明
	 */
	@ZKColumn(name = "c_set_desc", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = false)
	ZKJson setDesc;	
	/**
	 * 系统配置项条目，配置值；
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 256, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_value", isInsert = true, isUpdate = true, javaType = String.class, isQuery = false)
	String value;	
	/**
	 * 集团代码
	 */
    @Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_group_code", isInsert = true, isUpdate = true, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	String groupCode;	
	/**
	 * 公司代码
	 */
    @Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_compamy_code", isInsert = true, isUpdate = true, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	String compamyCode;	
	
	public ZKSysSetItem() {
		super();
	}

	public ZKSysSetItem(String pkId){
		super(pkId);
	}
	
	/**
	 * 配置项类型；0-平台配置；1-通用配置；2-公司专属配置；仅在公司专属配置下，才有公司和集团信息；仅通用配置，才会有公司自定义配置值	
	 */	
	public Integer getType() {
		return type;
	}
	
	/**
	 * 配置项类型；0-平台配置；1-通用配置；2-公司专属配置；仅在公司专属配置下，才有公司和集团信息；仅通用配置，才会有公司自定义配置值
	 */	
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 配置项名称	
	 */	
	public ZKJson getName() {
		return name;
	}
	
	/**
	 * 配置项名称
	 */	
	public void setName(ZKJson name) {
		this.name = name;
	}
	/**
	 * 配置项代码；全表唯一；	
	 */	
	public String getCode() {
		return code;
	}
	
	/**
	 * 配置项代码；全表唯一；
	 */	
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 配置项说明	
	 */	
	public ZKJson getSetDesc() {
		return setDesc;
	}
	
	/**
	 * 配置项说明
	 */	
	public void setSetDesc(ZKJson setDesc) {
		this.setDesc = setDesc;
	}
	/**
	 * 系统配置项条目，配置值；	
	 */	
	public String getValue() {
		return value;
	}
	
	/**
	 * 系统配置项条目，配置值；
	 */	
	public void setValue(String value) {
		this.value = value;
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
	 * 公司代码	
	 */	
	public String getCompamyCode() {
		return compamyCode;
	}
	
	/**
	 * 公司代码
	 */	
	public void setCompamyCode(String compamyCode) {
		this.compamyCode = compamyCode;
	}
	
	/**
	 * 根据主键类型，重写主键生成；
	 */
	@Override
	protected String genId() {
        return ZKIdUtils.genLongStringId();
    }
	
}