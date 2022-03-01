/** 
* Copyright (c) 2004-2020 ZK-Vinson Technologies, Inc.
* address: 
* All rights reserved. 
* 
* This software is the confidential and proprietary information of 
* ZK-Vinson Technologies, Inc. ("Confidential Information"). You shall not 
* disclose such Confidential Information and shall use it only in 
* accordance with the terms of the license agreement you entered into 
* with ZK-Vinson. 
*
* @Title: ZKDBBaseEntity.java 
* @author Vinson 
* @Package com.zk.db.commons 
* @Description: TODO(simple description this file what to do. ) 
* @date Sep 10, 2020 3:45:28 PM 
* @version V1.0 
*/
package com.zk.db.commons;

import java.io.Serializable;
import java.util.Map;

import javax.xml.bind.annotation.XmlTransient;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Maps;
import com.zk.core.commons.data.ZKPage;
import com.zk.core.utils.ZKEnvironmentUtils;
import com.zk.db.annotation.ZKDBAnnotationProvider;
import com.zk.db.annotation.ZKTable;

/** 
* @ClassName: ZKDBBaseEntity 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public abstract class ZKDBBaseEntity<E extends ZKDBBaseEntity<E>> implements Serializable {

    /**
     * @Fields serialVersionUID : TODO(simple description what to do.)
     */
    private static final long serialVersionUID = 1L;

    // 额外参数集合; 用于传输额外的查询参数，自定义SQL（SQL标识，SQL内容）或返回额外的关联参数；
    @Transient
    @XmlTransient
    @JsonIgnore
    protected Map<String, Object> extraParams;

    @Transient
    protected ZKPage<E> page;

    /**
     * @return page
     */
    @JsonIgnore
    @XmlTransient
    public ZKPage<E> getPage() {
        return page;
    }

    /**
     * @param page
     *            the page to set
     */
    public void setPage(ZKPage<E> page) {
        this.page = page;
    }

    /**
     * 额外参数集合
     * 
     * @return extraParams
     */
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    public Map<String, Object> getExtraParams() {
        if (extraParams == null) {
            extraParams = Maps.newHashMap();
        }
        return extraParams;
    }

    @SuppressWarnings("unchecked")
    @JsonIgnore
    @XmlTransient
    @Transient
    public <T> T getParamByName(String paramName) {
        return (T) this.getExtraParams().get(paramName);
    }

//    /**
//     * 额外参数集合
//     * 
//     * @param extraParams
//     *            the extraParams to set
//     */
//    public void setExtraParams(Map<String, Object> extraParams) {
//        this.extraParams = extraParams;
//    }

    /**
     * 获取数据库名称
     */
//    @JsonIgnore
//    public String getDbName() {
//        return ZKEnvironmentUtils.getString("jdbc.type", "mysql");
//    }

    @Transient
    @XmlTransient
    @JsonIgnore
    public String getJdbcType() {
        return ZKEnvironmentUtils.getString("jdbc.type", "mysql");
    }

    /********************************************************/
    /*** 一些 sql 片段，以便定制 ***/
    /********************************************************/

    /**
     * 取自定义 逻辑删除语句 中 set 内容；抽象方法；为方便定制
     * 
     * 示例：
     * 
     * "c_del_flag = #{delFlag}, c_update_user_id = #{updateUserId},
     * c_update_date = #{updateDate}";
     *
     * @Title: getDelSetSql
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Sep 14, 2020 6:50:21 PM
     * @return
     * @return String
     */
    @Transient
    @JsonIgnore
    @XmlTransient
    public abstract String getZKDbDelSetSql();

    // 取 where 条件；实体定义可以定制；在 生成的 sql；注意：末尾加空格
    @Transient
    @JsonIgnore
    @XmlTransient
    protected ZKDBQueryConditionWhere getZKDbWhere(ZKSqlConvert sqlConvert, ZKDBAnnotationProvider annotationProvider) {
        return sqlConvert.getWhere(annotationProvider);
    }

    // 注意：末尾加空格
    @Transient
    @JsonIgnore
    @XmlTransient
    protected String getZKDbStrSelCols(ZKSqlConvert sqlConvert, ZKDBAnnotationProvider annotationProvider) {
        // 示例：alia1.c1 AS 'a1c1', alia2.c1 AS 'a2c1'
        return sqlConvert.convertSqlSelCols(annotationProvider, annotationProvider.getTable().alias());
    }

    // 注意 末尾加空格
    @Transient
    @JsonIgnore
    @XmlTransient
    protected String getZKDbStrSelTable(ZKSqlConvert sqlConvert, ZKDBAnnotationProvider annotationProvider) {
        // 示例：table1 alia1 或 table1 alia1 left join table2 alia2
        return sqlConvert.convertSqlSelTable(annotationProvider, annotationProvider.getTable().alias());
    }

    // 定制默认的排序 sql 片段；带有 ORDER BY
    @JsonIgnore
    @XmlTransient
    protected String getZKDbStrSelOrderBy(ZKSqlConvert sqlConvert, ZKDBAnnotationProvider annotationProvider) {
        // 示列：ORDER BY alia1.c1 ASC
        return sqlConvert.convertSqlOrderBy(annotationProvider, null, annotationProvider.getTable().alias(), true);
    }

    /********************************************************/
    /*** 转换实体到持久层信息的一些实体 **/
    /********************************************************/
    @Transient
    @XmlTransient
    @JsonIgnore
    public abstract ZKSqlProvider getSqlProvider();

    @Transient
    @JsonIgnore
    @XmlTransient
    protected final ZKSqlConvert getSqlConvert() {
        return this.getSqlProvider().getSqlConvert();
    }

    @Transient
    @JsonIgnore
    @XmlTransient
    protected final ZKDBAnnotationProvider getAnnotationProvider() {
        return this.getSqlProvider().getAnnotationProvider();
    }

    @Transient
    @JsonIgnore
    @XmlTransient
    protected final ZKTable getTable() {
        return this.getSqlProvider().getAnnotationProvider().getTable();
    }

    /********************************************************/
    /*** 供 dao 使用的 sql 片段 ***/
    /********************************************************/

    @Transient
    @JsonIgnore
    @XmlTransient
    public String getSqlBlockSelCols() {
        return this.getSqlProvider().getSqlBlockSelCols();
    }

    // 取表名
    @Transient
    @JsonIgnore
    @XmlTransient
    public String getSqlBlockTableName() {
        return this.getTable().name() + " ";
    }

    // 取表 别名
    @Transient
    @JsonIgnore
    @XmlTransient
    public String getSqlBlockTableAlias() {
        return this.getTable().alias() + " ";
    }

    @Transient
    @JsonIgnore
    @XmlTransient
    public String getSqlBlockOrderBy() {
        return this.getSqlProvider().convertOrderBySql(this);
    }

//    @Transient
//    @JsonIgnore
//    @XmlTransient
//    public String getSqlBlockWhere() {
//        return this.getSqlProvider().getSqlBlockWhere();
//    }
//
//    @Transient
//    @JsonIgnore
//    @XmlTransient
//    public String getSqlBlockPkWhere() {
//        return this.getSqlProvider().getSqlBlockPkWhere();
//    }

}
