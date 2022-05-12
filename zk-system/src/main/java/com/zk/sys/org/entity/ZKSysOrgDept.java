/**
 * Copyright &copy; All rights reserved.
 */
package com.zk.sys.org.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zk.base.commons.ZKTreeSqlProvider;
import com.zk.base.entity.ZKBaseTreeEntity;
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

/**
 * 部门表
 * @author 
 * @version 
 */
@ZKTable(name = "t_sys_org_dept", alias = "sysOrgDept", orderBy = " c_create_date ASC ")
public class ZKSysOrgDept extends ZKBaseTreeEntity<String, ZKSysOrgDept> {
	
	static ZKTreeSqlProvider sqlProvider;

    @Override
    public ZKTreeSqlProvider getTreeSqlProvider() {
        return initSqlProvider();
    }

    public static ZKTreeSqlProvider initSqlProvider() {
        if (sqlProvider == null) {
            sqlProvider = new ZKTreeSqlProvider(new ZKSqlConvertDelegating(), new ZKSysOrgDept());
        }
        return sqlProvider;
    }
    
	private static final long serialVersionUID = 1L;
	
	/**
     * 部门状态；0-正常；1-撤销；
     */
    public static interface KeyStatus {
        /**
         * 0-正常
         */
        public static final int normal = 0;

        /**
         * 1-撤销
         */
        public static final int revoke = 1;

    }

    /**
     * 集团代码
     */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_group_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	String groupCode;	
	/**
	 * 公司ID 
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_company_id", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String companyId;	
	/**
	 * 公司代码
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_company_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String companyCode;	
	/**
	 * 部门代码；公司下唯一
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	String code;	
	/**
	 * 部门名称
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@NotEmpty(message = "{zk.core.data.validation.notNull}")
	@ZKColumn(name = "c_name", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = true, queryType = ZKDBQueryType.LIKE)
	ZKJson name;	
	/**
	 * 部门传真号
	 */
	@Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_fax_num", isInsert = true, isUpdate = true, javaType = String.class, isQuery = false)
	String faxNum;	
	/**
	 * 部门固定电话号
	 */
	@Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_tel_num", isInsert = true, isUpdate = true, javaType = String.class, isQuery = false)
	String telNum;	
	/**
	 * 部门手机号
	 */
	@Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_phone_num", isInsert = true, isUpdate = true, javaType = String.class, isQuery = false)
	String phoneNum;	
	/**
	 * 部门邮箱
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
	@Length(min = 1, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_mail", isInsert = true, isUpdate = true, javaType = String.class, isQuery = false)
	String mail;	
	/**
	 * 部门状态；0-正常；1-撤销；
	 */
	@NotNull(message = "{zk.core.data.validation.notNull}")
    @Range(min = 0, max = 9, message = "{zk.core.data.validation.rang.int}")
	@ZKColumn(name = "c_status", isInsert = true, isUpdate = true, javaType = Long.class, isQuery = true, queryType = ZKDBQueryType.EQ)
    Integer status;

	/**
	 * 部门地址
	 */
	@ZKColumn(name = "c_address", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = false)
	ZKJson address;	
	/**
	 *  部门简介
	 */
	@ZKColumn(name = "c_short_desc", isInsert = true, isUpdate = true, javaType = ZKJson.class, isQuery = false)
	ZKJson shortDesc;	
	
    /**
     * 来源代码；与来源ID标识唯一；
     */
	@Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_source_code", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String sourceCode;	
	/**
	 * 来源ID标识，与来源代码唯一
	 */
	@Length(min = 0, max = 64, message = "{zk.core.data.validation.length.max}")
	@ZKColumn(name = "c_source_id", isInsert = true, isUpdate = false, javaType = String.class, isQuery = true, queryType = ZKDBQueryType.EQ)
	String sourceId;	
	
	public ZKSysOrgDept() {
		super();
	}

	public ZKSysOrgDept(String pkId){
		super(pkId);
	}
	
    /////////////////////////////// @XmlTransient
    @Transient
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    protected ZKSysOrgCompany company;

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
	 * 公司ID 	
	 */	
	public String getCompanyId() {
		return companyId;
	}
	
	/**
	 * 公司ID 
	 */	
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	/**
	 * 公司代码	
	 */	
	public String getCompanyCode() {
		return companyCode;
	}
	
	/**
	 * 公司代码
	 */	
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * 部门代码；公司下唯一	
	 */	
	public String getCode() {
		return code;
	}
	
	/**
	 * 部门代码；公司下唯一
	 */	
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 部门名称	
	 */	
	public ZKJson getName() {
		return name;
	}
	
	/**
	 * 部门名称
	 */	
	public void setName(ZKJson name) {
		this.name = name;
	}
	/**
	 * 部门传真号	
	 */	
	public String getFaxNum() {
		return faxNum;
	}
	
	/**
	 * 部门传真号
	 */	
	public void setFaxNum(String faxNum) {
		this.faxNum = faxNum;
	}
	/**
	 * 部门固定电话号	
	 */	
	public String getTelNum() {
		return telNum;
	}
	
	/**
	 * 部门固定电话号
	 */	
	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}
	/**
	 * 部门手机号	
	 */	
	public String getPhoneNum() {
		return phoneNum;
	}
	
	/**
	 * 部门手机号
	 */	
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	/**
	 * 部门邮箱	
	 */	
	public String getMail() {
		return mail;
	}
	
	/**
	 * 部门邮箱
	 */	
	public void setMail(String mail) {
		this.mail = mail;
	}
	/**
	 * 部门状态；0-正常；1-撤销；	
	 */	
    public Integer getStatus() {
		return status;
	}
	
	/**
	 * 部门状态；0-正常；1-撤销；
	 */	
    public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 部门地址	
	 */	
	public ZKJson getAddress() {
		return address;
	}
	
	/**
	 * 部门地址
	 */	
	public void setAddress(ZKJson address) {
		this.address = address;
	}
	/**
	 *  部门简介	
	 */	
	public ZKJson getShortDesc() {
		return shortDesc;
	}
	
	/**
	 *  部门简介
	 */	
	public void setShortDesc(ZKJson shortDesc) {
		this.shortDesc = shortDesc;
	}
	/**
	 * 公司来源代码；与来源ID标识唯一；	
	 */	
	public String getSourceCode() {
		return sourceCode;
	}
	
	/**
	 * 公司来源代码；与来源ID标识唯一；
	 */	
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	/**
	 * 来源ID标识，与来源代码唯一	
	 */	
	public String getSourceId() {
		return sourceId;
	}
	
	/**
	 * 来源ID标识，与来源代码唯一
	 */	
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	/**
	 * 根据主键类型，重写主键生成；
	 */
	@Override
	protected String genId() {
        return ZKIdUtils.genLongStringId();
    }

    /**
     * @return company sa
     */
    public ZKSysOrgCompany getCompany() {
        return company;
    }

    /**
     * @param company
     *            the company to set
     */
    public void setCompany(ZKSysOrgCompany company) {
        this.company = company;
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