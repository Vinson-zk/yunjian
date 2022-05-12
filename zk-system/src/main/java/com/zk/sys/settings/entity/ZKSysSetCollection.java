/**
 * Copyright &copy; All rights reserved.
 */
package com.zk.sys.settings.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zk.base.entity.ZKBaseEntity;
import com.zk.core.commons.data.ZKJson;
import com.zk.core.utils.ZKIdUtils;
import com.zk.db.annotation.ZKColumn;
import com.zk.db.annotation.ZKDBAnnotationProvider;
import com.zk.db.annotation.ZKTable;
import com.zk.db.commons.ZKDBQueryConditionWhere;
import com.zk.db.commons.ZKDBQueryType;
import com.zk.db.commons.ZKSqlConvert;
import com.zk.db.commons.ZKSqlConvertDelegating;
import com.zk.db.mybatis.commons.ZKDBQueryConditionCol;
import com.zk.db.mybatis.commons.ZKDBQueryConditionIfByClass;
import com.zk.db.mybatis.commons.ZKSqlProvider;

/**
 * 配置项分组，集合
 * @author 
 * @version 
 */
@ZKTable(name = "t_sys_set_collection", alias = "sysSetCollection", orderBy = " c_create_date ASC ")
public class ZKSysSetCollection extends ZKBaseEntity<String, ZKSysSetCollection> {
	
	static ZKSqlProvider sqlProvider;
	
    @Override 
    public ZKSqlProvider getSqlProvider() {
        return initSqlProvider();
    }
    
    public static ZKSqlProvider initSqlProvider() {
        if(sqlProvider == null) {
            sqlProvider = new ZKSqlProvider(new ZKSqlConvertDelegating(), new ZKSysSetCollection());
        }
        return sqlProvider;
    }
    
    private static final long serialVersionUID = 1L;
	
	/**
	 * 配置项组别名称
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@NotEmpty(message = "{zk.core.data.validation.notNull}")
	@ZKColumn(name = "c_name", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	ZKJson name;	
	/**
	 * 配置项组别代码；全表唯一；
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_code", isInsert = true, isUpdate = true, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String code;	
	/**
	 * 配置项组别说明
	 */
	@ZKColumn(name = "c_set_desc", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	ZKJson setDesc;	
	/**
	 * 集团代码
	 */
	@Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_group_code", isInsert = true, isUpdate = true, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String groupCode;	
	/**
	 * 公司代码
	 */
	@Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_compamy_code", isInsert = true, isUpdate = true, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String compamyCode;	
	
	public ZKSysSetCollection() {
		super();
	}

	public ZKSysSetCollection(String pkId){
		super(pkId);
	}
	
	/**
	 * 配置项组别名称	
	 */	
	public ZKJson getName() {
		return name;
	}
	
	/**
	 * 配置项组别名称
	 */	
	public void setName(ZKJson name) {
		this.name = name;
	}
	/**
	 * 配置项组别代码；全表唯一；	
	 */	
	public String getCode() {
		return code;
	}
	
	/**
	 * 配置项组别代码；全表唯一；
	 */	
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 配置项组别说明	
	 */	
	public ZKJson getSetDesc() {
		return setDesc;
	}
	
	/**
	 * 配置项组别说明
	 */	
	public void setSetDesc(ZKJson setDesc) {
		this.setDesc = setDesc;
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
	
    // 查询辅助字段
    @Transient
    @JsonIgnore
    @XmlTransient
    String searchValue;

    /**
     * @return searchValue sa
     */
    public String getSearchValue() {
        return searchValue;
    }

    /**
     * @param searchValue
     *            the searchValue to set
     */
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    // 取 where 条件；实体定义可以定制；在 生成的 sql；注意：末尾加空格
    @Override
    @Transient
    @JsonIgnore
    @XmlTransient
    public ZKDBQueryConditionWhere getZKDbWhere(ZKSqlConvert sqlConvert, ZKDBAnnotationProvider annotationProvider) {
        ZKDBQueryConditionWhere where = super.getZKDbWhere(sqlConvert, annotationProvider);
        ZKDBQueryConditionWhere sWhere = ZKDBQueryConditionWhere.asOr("(", ")",
                ZKDBQueryConditionCol.as(ZKDBQueryType.LIKE, "c_name", "searchValue", String.class, null, false),
                ZKDBQueryConditionCol.as(ZKDBQueryType.LIKE, "c_code", "searchValue", String.class, null, false));
        where.put(ZKDBQueryConditionIfByClass.as(sWhere, "searchValue", String.class, false));
        return where;
    }

}