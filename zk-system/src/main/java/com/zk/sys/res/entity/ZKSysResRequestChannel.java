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
 * 请求渠道
 * @author 
 * @version 
 */
@ZKTable(name = "t_sys_res_request_channel", alias = "sysResRequestChannel", orderBy = " c_create_date ASC ")
public class ZKSysResRequestChannel extends ZKBaseEntity<String, ZKSysResRequestChannel> {
	
	static ZKSqlProvider sqlProvider;
	
    @Override 
    public ZKSqlProvider getSqlProvider() {
        return initSqlProvider();
    }
    
    public static ZKSqlProvider initSqlProvider() {
        if(sqlProvider == null) {
            sqlProvider = new ZKSqlProvider(new ZKSqlConvertDelegating(), new ZKSysResRequestChannel());
        }
        return sqlProvider;
    }
    
    private static final long serialVersionUID = 1L;
	
	/**
	 * 渠道名称
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@NotEmpty(message = "{zk.core.data.validation.notNull}")
	@ZKColumn(name = "c_name", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	ZKJson name;	
	/**
	 * 渠道代码，唯一
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	String code;	
	/**
	 * 渠道说明
	 */
	@ZKColumn(name = "c_channel_desc", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = false)
	ZKJson channelDesc;	
	
	public ZKSysResRequestChannel() {
		super();
	}

	public ZKSysResRequestChannel(String pkId){
		super(pkId);
	}
	
	/**
	 * 渠道名称	
	 */	
	public ZKJson getName() {
		return name;
	}
	
	/**
	 * 渠道名称
	 */	
	public void setName(ZKJson name) {
		this.name = name;
	}
	/**
	 * 渠道代码，唯一	
	 */	
	public String getCode() {
		return code;
	}
	
	/**
	 * 渠道代码，唯一
	 */	
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 渠道说明	
	 */	
	public ZKJson getChannelDesc() {
		return channelDesc;
	}
	
	/**
	 * 渠道说明
	 */	
	public void setChannelDesc(ZKJson channelDesc) {
		this.channelDesc = channelDesc;
	}
	
	/**
	 * 根据主键类型，重写主键生成；
	 */
	@Override
	protected String genId() {
        return ZKIdUtils.genLongStringId();
    }
	
}