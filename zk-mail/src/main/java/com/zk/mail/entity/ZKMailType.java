/**
 * Copyright &copy; All rights reserved.
 */
package com.zk.mail.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.base.entity.ZKBaseEntity;
import com.zk.core.commons.data.ZKJson;
import com.zk.core.utils.ZKIdUtils;
import com.zk.db.annotation.ZKColumn;
import com.zk.db.annotation.ZKTable;
import com.zk.db.commons.ZKDBQueryType;
import com.zk.db.commons.ZKSqlConvertDelegating;
import com.zk.db.mybatis.commons.ZKSqlProvider;

/**
 * 发送邮件的类型
 * @author 
 * @version 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@ZKTable(name = "t_mail_type", alias = "mailType", orderBy = " c_create_date ASC ")
public class ZKMailType extends ZKBaseEntity<String, ZKMailType> {
	
	static ZKSqlProvider sqlProvider;
	
    @Override 
    public ZKSqlProvider getSqlProvider() {
        return initSqlProvider();
    }
    
    public static ZKSqlProvider initSqlProvider() {
        if(sqlProvider == null) {
            sqlProvider = new ZKSqlProvider(new ZKSqlConvertDelegating(), new ZKMailType());
        }
        return sqlProvider;
    }
    
    private static final long serialVersionUID = 1L;

    /**
     * 状态；0-启用；1-禁用；
     */
    public static interface KeyStatus {
        /**
         * 0-启用
         */
        public static final int normal = 0;

        /**
         * 1-禁用
         */
        public static final int disabled = 1;
    }
	
	/**
	 * 类型代码；全表唯一
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_type_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	String typeCode;	
	/**
	 * 类型名称
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@NotEmpty(message = "{zk.core.data.validation.notNull}")
	@ZKColumn(name = "c_type_name", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	ZKJson typeName;	
	/**
	 * 状态：0-启用；1-禁用；
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
    @Range(min = 0, max = 9, message = "{zk.core.data.validation.rang.int}")
	@ZKColumn(name = "c_status", isInsert = true, isUpdate = true, javaType = Integer.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	Integer status;	
	/**
	 * 类型说明
	 */
	@ZKColumn(name = "c_type_desc", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = false)
	ZKJson typeDesc;	
	
	public ZKMailType() {
		super();
	}

	public ZKMailType(String pkId){
		super(pkId);
	}
	
	/**
	 * 类型代码；全表唯一	
	 */	
	public String getTypeCode() {
		return typeCode;
	}
	
	/**
	 * 类型代码；全表唯一
	 */	
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	/**
	 * 类型名称	
	 */	
	public ZKJson getTypeName() {
		return typeName;
	}
	
	/**
	 * 类型名称
	 */	
	public void setTypeName(ZKJson typeName) {
		this.typeName = typeName;
	}
	/**
	 * 状态：0-启用；1-禁用；	
	 */	
	public Integer getStatus() {
		return status;
	}
	
	/**
	 * 状态：0-启用；1-禁用；
	 */	
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 类型说明	
	 */	
	public ZKJson getTypeDesc() {
		return typeDesc;
	}
	
	/**
	 * 类型说明
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