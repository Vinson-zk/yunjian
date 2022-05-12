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
* @Title: ZKSysNav.java 
* @author Vinson 
* @Package com.zk.sys.entity 
* @Description: TODO(simple description this file what to do. ) 
* @date Aug 20, 2020 12:19:24 PM 
* @version V1.0 
*/
package com.zk.sys.res.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zk.base.entity.ZKBaseEntity;
import com.zk.core.commons.data.ZKJson;
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
* @ClassName: ZKSysNav 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
@ZKTable(name = "t_sys_res_navigation", alias = "sysNav", orderBy = " c_create_date ASC ")
public class ZKSysNav extends ZKBaseEntity<String, ZKSysNav> {

    /**
     * 0-不显示；1-显示；
     */
    public static interface KeyIsShow {
        /**
         * 0-不显示;
         */
        public static final int hide = 0;

        /**
         * 1-显示；
         */
        public static final int show = 1;
    }

    static ZKSqlProvider sqlProvider;

    @Override
    public ZKSqlProvider getSqlProvider() {
        return sqlProvider();
    }

    public static ZKSqlProvider sqlProvider() {
        if (sqlProvider == null) {
            sqlProvider = new ZKSqlProvider(new ZKSqlConvertDelegating(), new ZKSysNav());
        }
        return sqlProvider;
    }

    /**
     * @Fields serialVersionUID : TODO(simple description what to do.)
     */
    private static final long serialVersionUID = 1L;

    public ZKSysNav() {
        super();
    }

    public ZKSysNav(String pkId) {
        super(pkId);
    }

    /**
     * 不能为空；菜单(路由)的名称, 国际化json对象；
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @NotEmpty(message = "{zk.core.data.validation.notNull}")
    @ZKColumn(name = "c_name", isUpdate = true, isQuery = true, javaType = ZKJson.class, isCaseSensitive = false, queryType = ZKDBQueryType.LIKE)
    protected ZKJson name;

    /* 前端路由相关的属性 */

    /**
     * 不能为空；导航栏目代码，唯一
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_code", isUpdate = true, isQuery = true, queryType = ZKDBQueryType.LIKE)
    protected String code;

    /**
     * 不能为空；功能模块代码；也是功能模块目录;
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_func_module_code", isUpdate = true, isQuery = true, queryType = ZKDBQueryType.LIKE)
    protected String funcModuleCode;

    /**
     * 不能为空，功能名称，导航栏的首页组件名；将根据这名称查找到对应功能组件；
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_func_name", isUpdate = true, isQuery = true, queryType = ZKDBQueryType.LIKE)
    protected String funcName;

    /**
     * 访问路径；不能为空
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_path", isUpdate = true, isQuery = true, queryType = ZKDBQueryType.LIKE)
    protected String path;

    /**
     * 不能为空；排序字段
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Range(min = 0, max = 999999999, message = "{zk.core.data.validation.rang.int}")
    @ZKColumn(name = "c_sort", isUpdate = true, javaType = Integer.class)
    protected Integer sort;

    /**
     * 是否是默认栏目，0-不是；1-是；
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Range(min = 0, max = 9, message = "{zk.core.data.validation.rang.int}")
    @ZKColumn(name = "c_is_index", isUpdate = true, isQuery = true, javaType = Integer.class, queryType = ZKDBQueryType.EQ)
    protected Integer isIndex;

    /**
     * zk.core.data.validation.rang.int 是否显示；0-不显示；1-显示；
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Range(min = 0, max = 9, message = "{zk.core.data.validation.rang.int}")
    @ZKColumn(name = "c_is_show", javaType = Integer.class, isUpdate = true, isQuery = true, queryType = ZKDBQueryType.EQ)
    protected Integer isShow;

    /**
     * 图标
     */
    @Length(max = 64, message = "{zk.core.data.validation.length.max}")
    @ZKColumn(name = "c_icon", isUpdate = true)
    protected String icon;

    // 查询辅助字段
    @Transient
    @JsonIgnore
    @XmlTransient
    String searchValue;

    /**
     * @return name sa
     */
    public ZKJson getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(ZKJson name) {
        this.name = name;
    }

    @Transient
    public void putName(String key, String value) {
        if (this.name == null) {
            this.name = new ZKJson();
        }
        this.name.put(key, value);
    }

    /**
     * @return navCode sa
     */
    public String getCode() {
        return code;
    }

    /**
     * @param navCode
     *            the navCode to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return funcModuleCode sa
     */
    public String getFuncModuleCode() {
        return funcModuleCode;
    }

    /**
     * @param funcModuleCode
     *            the funcModuleCode to set
     */
    public void setFuncModuleCode(String funcModuleCode) {
        this.funcModuleCode = funcModuleCode;
    }

    /**
     * @return funcName sa
     */
    public String getFuncName() {
        return funcName;
    }

    /**
     * @param funcName
     *            the funcName to set
     */
    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    /**
     * @return path sa
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     *            the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return sort sa
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * @param sort
     *            the sort to set
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * @return isIndex sa
     */
    public Integer getIsIndex() {
        return isIndex;
    }

    /**
     * @param isIndex
     *            the isIndex to set
     */
    public void setIsIndex(Integer isIndex) {
        this.isIndex = isIndex;
    }

    /**
     * @return isShow sa
     */
    public Integer getIsShow() {
        return isShow;
    }

    /**
     * @param isShow
     *            the isShow to set
     */
    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    /**
     * @return icon sa
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon
     *            the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

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
