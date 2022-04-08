/**
 * Copyright &copy; All rights reserved.
 */
package com.zk.sys.res.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Transient;

import com.zk.base.commons.ZKTreeSqlProvider;
import com.zk.base.entity.ZKBaseTreeEntity;
import com.zk.core.commons.data.ZKJson;
import com.zk.core.utils.ZKIdUtils;
import com.zk.db.annotation.ZKColumn;
import com.zk.db.annotation.ZKTable;
import com.zk.db.commons.ZKDBQueryType;
import com.zk.db.commons.ZKSqlConvertDelegating;

/**
 * 字典表
 * @author 
 * @version 
 */
@ZKTable(name = "t_sys_res_dict", alias = "sysResDict", orderBy = " c_type_code ASC, c_create_date ASC ")
public class ZKSysResDict extends ZKBaseTreeEntity<String, ZKSysResDict> {
	
    static ZKTreeSqlProvider sqlProvider;

    @Override
    public ZKTreeSqlProvider getTreeSqlProvider() {
        return initSqlProvider();
    }

    public static ZKTreeSqlProvider initSqlProvider() {
        if (sqlProvider == null) {
            sqlProvider = new ZKTreeSqlProvider(new ZKSqlConvertDelegating(), new ZKSysResDict());
        }
        return sqlProvider;
    }

    private static final long serialVersionUID = 1L;
	
	/**
	 * 类型代码
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_type_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String typeCode;	
	/**
	 * 字典代码
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_dict_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	String dictCode;	
	/**
	 * 字典名称
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@NotEmpty(message = "{zk.core.data.validation.notNull}")
	@ZKColumn(name = "c_dict_name", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
    ZKJson dictName;

	/**
	 * 字典说明
	 */
	@ZKColumn(name = "c_dict_desc", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = false)
    ZKJson dictDesc;
	
    /**
     * 字典类型；查询明细时，会级联查询出来，service 中 get 时，不会级联查询
     */
    @Transient
    ZKSysResDictType dictType;

	public ZKSysResDict() {
		super();
	}

	public ZKSysResDict(String pkId){
		super(pkId);
	}
	
	/**
	 * 类型代码	
	 */	
	public String getTypeCode() {
		return typeCode;
	}
	
	/**
	 * 类型代码
	 */	
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	/**
	 * 字典代码	
	 */	
	public String getDictCode() {
		return dictCode;
	}
	
	/**
	 * 字典代码
	 */	
	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}
	/**
	 * 字典名称	
	 */	
    public ZKJson getDictName() {
		return dictName;
	}
	
	/**
	 * 字典名称
	 */	
    public void setDictName(ZKJson dictName) {
		this.dictName = dictName;
	}
	/**
	 * 字典说明	
	 */	
    public ZKJson getDictDesc() {
		return dictDesc;
	}
	
	/**
	 * 字典说明
	 */	
    public void setDictDesc(ZKJson dictDesc) {
		this.dictDesc = dictDesc;
	}
	
	/**
	 * 根据主键类型，重写主键生成；
	 */
	@Override
	protected String genId() {
        return ZKIdUtils.genLongStringId();
    }

    /**
     * @return dictType sa
     */
    public ZKSysResDictType getDictType() {
        return dictType;
    }

    /**
     * @param dictType
     *            the dictType to set
     */
    public void setDictType(ZKSysResDictType dictType) {
        this.dictType = dictType;
    }

}
