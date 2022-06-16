/**
 * Copyright &copy; All rights reserved.
 */
package com.zk.wechat.platformBusiness.entity;

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
 * 微信平台，功能 key 配置；网页授权成功后，会根据这个 key 重定向到业务功能；
 * @author 
 * @version 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@ZKTable(name = "t_wx_pb_func_key_config", alias = "funcKeyConfig", orderBy = " c_create_date ASC ")
public class ZKFuncKeyConfig extends ZKBaseEntity<String, ZKFuncKeyConfig> {
	
	static ZKSqlProvider sqlProvider;
	
    @Override 
    public ZKSqlProvider getSqlProvider() {
        return initSqlProvider();
    }
    
    public static ZKSqlProvider initSqlProvider() {
        if(sqlProvider == null) {
            sqlProvider = new ZKSqlProvider(new ZKSqlConvertDelegating(), new ZKFuncKeyConfig());
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
	 * 功能类型id；
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_func_type_id", isInsert = true, isUpdate = false, javaType = String.class, isQuery = false)
	String funcTypeId;	
	/**
	 * 功能类型代码
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_func_type_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String funcTypeCode;	

    /**
     * 功能名称
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @NotEmpty(message = "{zk.core.data.validation.notNull}")
    @ZKColumn(name = "c_func_name", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
    ZKJson funcName;

	/**
	 * 功能标识代码 key；全表唯一
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_func_key", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
    String funcKey;

    /**
     * 状态；0-启用；1-禁用；
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Range(min = 0, max = 9, message = "{zk.core.data.validation.rang.int}")
    @ZKColumn(name = "c_status", isInsert = true, isUpdate = true, javaType = Integer.class, isQuery = true, queryType = ZKDBQueryType.EQ)
    Integer status;

	/**
	 * 重写向，源真实地址；从域名根路径下开始写
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 256, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_redirect_original_url", isInsert = true, isUpdate = true, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	String redirectOriginalUrl;	
	/**
	 * 重写向，真实地址；从域名根路径下开始写
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 256, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_redirect_proxy_url", isInsert = true, isUpdate = true, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	String redirectProxyUrl;	
	/**
	 * 功能说明
	 */
	@ZKColumn(name = "c_func_desc", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = false)
	ZKJson funcDesc;	
	
	public ZKFuncKeyConfig() {
		super();
	}

	public ZKFuncKeyConfig(String pkId){
		super(pkId);
	}
	
	/**
	 * 功能类型id；	
	 */	
	public String getFuncTypeId() {
		return funcTypeId;
	}
	
	/**
	 * 功能类型id；
	 */	
	public void setFuncTypeId(String funcTypeId) {
		this.funcTypeId = funcTypeId;
	}
	/**
	 * 功能类型代码	
	 */	
	public String getFuncTypeCode() {
		return funcTypeCode;
	}
	
	/**
	 * 功能类型代码
	 */	
	public void setFuncTypeCode(String funcTypeCode) {
		this.funcTypeCode = funcTypeCode;
	}

    /**
     * @return funcName sa
     */
    public ZKJson getFuncName() {
        return funcName;
    }

    /**
     * @param funcName
     *            the funcName to set
     */
    public void setFuncName(ZKJson funcName) {
        this.funcName = funcName;
    }

    /**
     * 功能标识代码 key；全表唯一
     */	
	public String getFuncKey() {
        return this.funcKey;
	}
	
    /**
     * @return status sa
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 功能标识代码 key；全表唯一
     */	
    public void setFuncKey(String funcKey) {
        this.funcKey = funcKey;
	}
	/**
	 * 重写向，源真实地址；从域名根路径下开始写	
	 */	
	public String getRedirectOriginalUrl() {
		return redirectOriginalUrl;
	}
	
	/**
	 * 重写向，源真实地址；从域名根路径下开始写
	 */	
	public void setRedirectOriginalUrl(String redirectOriginalUrl) {
		this.redirectOriginalUrl = redirectOriginalUrl;
	}
	/**
	 * 重写向，真实地址；从域名根路径下开始写	
	 */	
	public String getRedirectProxyUrl() {
		return redirectProxyUrl;
	}
	
	/**
	 * 重写向，真实地址；从域名根路径下开始写
	 */	
	public void setRedirectProxyUrl(String redirectProxyUrl) {
		this.redirectProxyUrl = redirectProxyUrl;
	}
	/**
	 * 功能说明	
	 */	
	public ZKJson getFuncDesc() {
		return funcDesc;
	}
	
	/**
	 * 功能说明
	 */	
	public void setFuncDesc(ZKJson funcDesc) {
		this.funcDesc = funcDesc;
	}
	
	/**
	 * 根据主键类型，重写主键生成；
	 */
	@Override
	protected String genId() {
        return ZKIdUtils.genLongStringId();
    }
	
}