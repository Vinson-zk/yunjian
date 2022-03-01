/**   
 * Copyright (c) 2004-2014 Vinson Technologies, Inc.
 * address: 
 * All rights reserved. 
 * 
 * This software is the confidential and proprietary information of 
 * Vinson Technologies, Inc. ("Confidential Information").  You shall not 
 * disclose such Confidential Information and shall use it only in 
 * accordance with the terms of the license agreement you entered into 
 * with Vinson. 
 *
 * @Title: ZKDBEntity.java 
 * @author Vinson 
 * @Package com.zk.db.helper 
 * @Description: TODO(simple description this file what to do.) 
 * @date Dec 19, 2019 11:10:41 AM 
 * @version V1.0   
*/
package com.zk.db.helper.entity;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zk.core.commons.data.ZKJson;
import com.zk.core.utils.ZKDateUtils;
import com.zk.db.annotation.ZKColumn;
import com.zk.db.annotation.ZKTable;
import com.zk.db.commons.ZKDBQueryType;
import com.zk.db.commons.ZKSqlConvertDelegating;
import com.zk.db.commons.ZKSqlProvider;

/**
 * @ClassName: ZKDBEntity
 * @Description: TODO(simple description this class what to do.)
 * @author Vinson
 * @version 1.0
 */
@ZKTable(name = "t_zk_db_test")
public class ZKDBEntity extends ZKDBTestBaseEntity<ZKDBEntity> {

    /**
     * t_test(c_id, c_type, c_value, c_remarks, c_json)
     */
    private static final long serialVersionUID = 1L;
    
    protected static ZKSqlProvider sqlProvider;

    @Override
    public ZKSqlProvider getSqlProvider() {
        if (sqlProvider == null) {
            sqlProvider = new ZKSqlProvider(new ZKSqlConvertDelegating(), this);
        }
        return sqlProvider;
    }

    public ZKDBEntity() {

    }

    public ZKDBEntity(String id) {
        this.id = id;
    }

    // @ZKColumn(name = "c_id")
    private String id;

    @ZKColumn(name = "c_type_m")
    private Long type;

    @ZKColumn(name = "c_remarks", isQuery = true, isUpdate = true, isCaseSensitive = false, queryType = ZKDBQueryType.LIKE)
    private String remarks;

    @ZKColumn(name = "c_json", isQuery = true, isUpdate = true, javaType = ZKJson.class, queryType = ZKDBQueryType.LIKE)
    private ZKJson json;

    @ZKColumn(name = "c_date", isQuery = false, isUpdate = true, javaType = Date.class)
    private Date mDate;

    @ZKColumn(name = "c_boolean", isQuery = true, isUpdate = true, javaType = Boolean.class)
    private Boolean mBoolean;

    /********* 查询字段 ***************/

    @Transient
    @JsonIgnore
    @XmlTransient
    @ZKColumn(name = "c_type", isResult = false, isQuery = true, isInsert = false, isUpdate = false, javaType = Object[].class, queryType = ZKDBQueryType.IN)
    private Long[] types;

    @Transient
    @JsonIgnore
    @XmlTransient
    @ZKColumn(name = "c_date", formats = { "%Y-%m-%d",
            ZKDateUtils.DF_yyyy_MM_dd }, isResult = false, isQuery = true, isInsert = false, isUpdate = false, javaType = Date.class, queryType = ZKDBQueryType.GTE)
    public Date getStartDate() {
        return this.getParamByName("startDate");
    }

    @Transient
    @JsonIgnore
    @XmlTransient
    @ZKColumn(name = "c_date", formats = { "%Y-%m-%d %H:%i",
            ZKDateUtils.DF_yyyy_MM_dd_HH_mm }, isResult = false, isQuery = true, isInsert = false, isUpdate = false, javaType = Date.class, queryType = ZKDBQueryType.LTE)
    public Date getEndDate() {
        return this.getParamByName("endDate");
    }

    @Transient
    @JsonIgnore
    @XmlTransient
    @ZKColumn(name = "c_type", isResult = false, isQuery = true, isInsert = false, isUpdate = false, javaType = List.class, queryType = ZKDBQueryType.IN, isCaseSensitive = false)
    public List<String> getTypeStrs() {
        return this.getParamByName("typeStrs");
    }

    /******************************** */

    @ZKColumn(name = "c_id", isPk = true, isUpdate = false, isQuery = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ZKColumn(name = "c_type", isQuery = true, isUpdate = true, javaType = Long.class)
    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public ZKJson getJson() {
        return json;
    }

    public void setJson(ZKJson json) {
        this.json = json;
    }

    /**
     * @return types
     */
    public Long[] getTypes() {
        return types;
    }

    /**
     * @param types
     *            the types to set
     */
    public void setTypes(Long[] types) {
        this.types = types;
    }

    /**
     * @return mDate sa
     */
    public Date getmDate() {
        return mDate;
    }

    /**
     * @param mDate
     *            the mDate to set
     */
    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    /**
     * @return mBoolean sa
     */
    public Boolean getmBoolean() {
        return mBoolean;
    }

    /**
     * @param mBoolean
     *            the mBoolean to set
     */
    public void setmBoolean(Boolean mBoolean) {
        this.mBoolean = mBoolean;
    }

}
