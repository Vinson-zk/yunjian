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
 * @Title: ZKBaseEntity.java 
 * @author Vinson 
 * @Package com.zk.base.entity 
 * @Description: TODO(simple description this file what to do.) 
 * @date Dec 19, 2019 2:12:57 PM 
 * @version V1.0   
*/
package com.zk.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zk.core.commons.data.ZKJson;
import com.zk.core.utils.ZKClassUtils;
import com.zk.core.utils.ZKDateUtils;
import com.zk.core.utils.ZKIdUtils;
import com.zk.db.annotation.ZKColumn;
import com.zk.db.commons.ZKDBBaseEntity;
import com.zk.security.utils.ZKSecSecurityUtils;

/** 
* @ClassName: ZKBaseEntity 
* @Description: TODO(simple description this class what to do.) 
* @author Vinson 
* @version 1.0 
*/
public abstract class ZKBaseEntity<ID extends Serializable, E extends ZKBaseEntity<ID, E>> extends ZKDBBaseEntity<E> {

    @Transient
    @XmlTransient
    public static final String timezone = "GMT+8";

    /**
     * 日志对象
     */
    protected Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /*
     * @JsonInclude(JsonInclude.Include.NON_NULL) 为 null 时，忽略
     * 
     * @JsonIgnore 忽略，不返回
     * 
     * @JsonProperty(value="tes") 注解属性名，一般不注解，用默认属性名
     */

    // 实体编号（唯一标识）
    @ZKColumn(name = "c_pk_id", isPk = true, isQuery = true)
    @NotNull(message = "{zk.core.data.validation.notNull}")
    protected ID pkId;

    // 创建者, ID
    @ZKColumn(name = "c_create_user_id")
    protected ID createUserId;

    // 更新者
    @ZKColumn(name = "c_update_user_id", isUpdate = true)
    protected ID updateUserId;

    // 创建日期
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @ZKColumn(name = "c_create_date", javaType = Date.class)
    protected Date createDate;

    // 更新日期
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @ZKColumn(name = "c_update_date", javaType = Date.class, isUpdate = true)
    protected Date updateDate;

    // 备注
    @ZKColumn(name = "c_remarks", isUpdate = true)
    protected String remarks;

    // 国际化说明 description 加个前缀，防关键字冲突
    @ZKColumn(name = "c_p_desc", javaType = ZKJson.class, isUpdate = true)
    protected ZKJson pDesc;

    // 备用字段 1
    @ZKColumn(name = "c_spare1", isUpdate = true)
    protected String spare1;

    // 备用字段 2
    @ZKColumn(name = "c_spare2", isUpdate = true)
    protected String spare2;

    // 备用字段 3
    @ZKColumn(name = "c_spare3", isUpdate = true)
    protected String spare3;

    // 备用字段 ZKJson
    @ZKColumn(name = "c_spare_json", javaType = ZKJson.class, isUpdate = true)
    protected ZKJson spareJson;

    // 删除标记（0：正常；1：删除;）
    @ZKColumn(name = "c_del_flag", isQuery = true, javaType = Integer.class)
    protected Integer delFlag;

    // 数据版本
    @ZKColumn(name = "c_version", javaType = Long.class, isUpdate = true)
    protected Long version;

    // 是否是新记录（默认：false），调用setNewRecord()设置新记录，使用自定义ID。设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
    @Transient
    @JsonProperty("isNewRecord")
    protected boolean isNewRecord;

    /**
     * 删除标记
     * 
     * @author zk
     *
     */
    public static interface DEL_FLAG {
        /**
         * 正常
         */
        public static final int normal = 0;

        /**
         * 删除
         */
        public static final int delete = 1;
    }

    /**
     * 是否是新记录（ 默认：false ），调用setIsNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     */
    public ZKBaseEntity() {
        super();
        this.isNewRecord = false;
        this.version = 0l;
        this.delFlag = DEL_FLAG.normal;
    }

    /**
     * 是否是新记录（ 默认：false ），调用setIsNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     */
    public ZKBaseEntity(ID pkId) {
        this();
        this.pkId = pkId;
    }

    /**
     * 实体编号（唯一标识）
     */
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    public ID getPkId() {
        return pkId;
    }

    /**
     * 实体编号（唯一标识）
     */
    public void setPkId(ID pkId) {
        this.pkId = pkId;
    }

    /**
     * 数据版本号
     * 
     * @return
     */
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    public Long getVersion() {
        return version;
    }

    /**
     * 数据版本号
     * 
     * @return
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * 创建者, ID
     * 
     * @return
     */
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    public ID getCreateUserId() {
        return createUserId;
    }

    /**
     * // 创建者, ID
     * 
     * @param createUserId
     */
    public void setCreateUserId(ID createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 创建日期
     * 
     * @return
     */
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    @JsonFormat(pattern = ZKDateUtils.DF_yyyy_MM_dd_HH_mm_ss, timezone = timezone)
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 创建日期
     * 
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 修改者ID
     * 
     * @return
     */
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    public ID getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 修改者ID
     * 
     * @param updateUserId
     */
    public void setUpdateUserId(ID updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * 修改日期
     * 
     * @return
     */
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    @JsonFormat(pattern = ZKDateUtils.DF_yyyy_MM_dd_HH_mm_ss, timezone = timezone)
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 修改日期
     * 
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 备注
     * 
     * @return
     */
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    public String getRemarks() {
        return remarks;
    }

    /**
     * 备注
     * 
     * @param remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 删除标记（0：正常；1：删除;）
     * 
     * @return
     */
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * 删除标记（0：正常；1：删除;）
     * 
     * @param delFlag
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * @return spare1
     */
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    public String getSpare1() {
        return spare1;
    }
    /**
     * @param spare1
     *            the spare1 to set
     */
    public void setSpare1(String spare1) {
        this.spare1 = spare1;
    }

    /**
     * @return spare2
     */
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    public String getSpare2() {
        return spare2;
    }
    /**
     * @param spare2
     *            the spare2 to set
     */
    public void setSpare2(String spare2) {
        this.spare2 = spare2;
    }

    /**
     * @return spare3
     */
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    public String getSpare3() {
        return spare3;
    }
    /**
     * @param spare3
     *            the spare3 to set
     */
    public void setSpare3(String spare3) {
        this.spare3 = spare3;
    }

    /**
     * @return spareJson
     */
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    public ZKJson getSpareJson() {
        return spareJson;
    }

    /**
     * @param spareJson
     *            the spareJson to set
     */
    public void setSpareJson(ZKJson spareJson) {
        this.spareJson = spareJson;
    }

    /**
     * @return pDesc sa
     */
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    public ZKJson getpDesc() {
        return pDesc;
    }

    /**
     * @param pDesc
     *            the pDesc to set
     */
    public void setpDesc(ZKJson pDesc) {
        this.pDesc = pDesc;
    }

    /********************************************************/
    /**
     * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     * 
     * @return
     */
    @Transient
    @XmlTransient
    @JsonIgnore
    public boolean isNewRecord() {
        return isNewRecord || pkId == null || "".equals(pkId.toString());
    }

    /**
     * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     * 
     * @param isNewRecord
     */
    public void setNewRecord(boolean isNewRecord) {
        this.isNewRecord = isNewRecord;
    }

    /**
     * 插入之前执行方法，子类实现
     */
    public void preInsert() {
        // 不限制ID为UUID，调用 setIsNewRecord() 使用自定义ID
        if (this.getPkId() == null || "".equals(pkId.toString())) {
            this.setPkId(this.genId());
        }
        this.createUserId = ZKSecSecurityUtils.getUserId();
        this.updateUserId = this.createUserId;
        this.createDate = new Date();
        this.updateDate = this.createDate;
        this.version = 0l;
    }

    @SuppressWarnings("unchecked")
    protected ID genId() {
        return (ID) ZKIdUtils.genId();
    }

    /**
     * 更新之前执行方法，子类实现
     */
    public void preUpdate() {
        this.updateUserId = ZKSecSecurityUtils.getUserId();
        this.updateDate = new Date();
    }

    @SuppressWarnings("unchecked")
    @Transient
    @XmlTransient
    @JsonIgnore
    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        E that = (E) obj;
        return null == this.getPkId() ? false : this.getPkId().equals(that.getPkId());
    }

    @Transient
    @XmlTransient
    @JsonIgnore
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    @Transient
    @XmlTransient
    @JsonIgnore
    public Class<ID> getPkIDClass() {
        Class<ID> cz = ZKClassUtils.getSuperclassByName(ZKBaseEntity.class, this.getClass(), "ID");
        return cz;
//        return (Class<ID>) this.getSuperclassByName("ID");
//        return (Class<ID>) (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    @Transient
    @XmlTransient
    @JsonIgnore
    @Override
    public String getZKDbDelSetSql() {
        return "c_del_flag = #{delFlag}, c_update_user_id = #{updateUserId}, c_update_date = #{updateDate}";
    }

    public void parseByOld(E entity) {
        this.pkId = entity.getPkId();
        this.createDate = entity.getCreateDate();
        this.createUserId = entity.createUserId;
    }

//    /**
//     * 根据 泛弄 类名取 Class<?>
//    *
//    * @Title: getSuperclassByName 
//    * @Description: TODO(simple description this method what to do.) 
//    * @author Vinson 
//    * @date May 4, 2022 11:44:02 AM 
//    * @param classTypeName
//    * @return
//    * @return Class<?>
//     */
//    public Class<?> getSuperclassByName(String classTypeName) {
//        TypeVariable<?>[] ts = ZKBaseEntity.class.getTypeParameters();
//        for (int i = 0; i < ts.length; ++i) {
//            if (classTypeName.equals(ts[i].getName())) {
//                ParameterizedType pt = ((ParameterizedType) this.getClass().getGenericSuperclass());
//                return (Class<?>) pt.getActualTypeArguments()[0];
//            }
//        }
//        return null;
//    }

}
