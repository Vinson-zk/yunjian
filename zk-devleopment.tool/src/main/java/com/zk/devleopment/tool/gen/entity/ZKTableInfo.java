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
* @Title: ZKTableInfo.java 
* @author Vinson 
* @Package com.zk.code.generate.entity 
* @Description: TODO(simple description this file what to do. ) 
* @date Mar 24, 2021 4:18:18 PM 
* @version V1.0 
*/
package com.zk.devleopment.tool.gen.entity;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zk.base.entity.ZKBaseEntity;
import com.zk.core.utils.ZKStringUtils;
import com.zk.db.annotation.ZKColumn;
import com.zk.db.annotation.ZKTable;
import com.zk.db.commons.ZKDBQueryType;
import com.zk.db.commons.ZKSqlConvertDelegating;
import com.zk.db.mybatis.commons.ZKSqlProvider;

/**
 * @ClassName: ZKTableInfo
 * @Description: TODO(simple description this class what to do. )
 * @author Vinson
 * @version 1.0
 */
@ZKTable(name = "t_dt_code_gen_table_info", alias = "tableInfo", orderBy = " c_create_date ASC ")
public class ZKTableInfo extends ZKBaseEntity<String, ZKTableInfo> {

    static ZKSqlProvider sqlProvider;

    @Transient
    @XmlTransient
    @JsonIgnore
    @Override
    public ZKSqlProvider getSqlProvider() {
        return sqlProvider();
    }

    public static ZKSqlProvider sqlProvider() {
        if (sqlProvider == null) {
            sqlProvider = new ZKSqlProvider(new ZKSqlConvertDelegating(), new ZKTableInfo());
        }
        return sqlProvider;
    }

    /**
     * @Fields serialVersionUID : TODO(simple description what to do.)
     */
    private static final long serialVersionUID = 1L;

    public ZKTableInfo() {
        super();
    }

    public ZKTableInfo(String pkId) {
        super(pkId);
    }

    /**********************************************************************************/
    /*** ????????? */
    /**********************************************************************************/
    /**
     * ??????
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 64, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_table_name", isUpdate = false, isQuery = true, queryType = ZKDBQueryType.LIKE)
    String tableName;

    /**
     * ??????????????????JAVA?????????????????????
     */
//    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 0, max = 512, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_table_comments", isUpdate = true)
    String tableComments = "";

    /**
     * ???????????????
     */
    @Length(min = 0, max = 64, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_pk_col_name", isUpdate = true)
    String pkColName;

    /**********************************************************************************/
    /***  */
    /**********************************************************************************/

    /**
     * java ??????????????????????????????
     */
    @Length(min = 0, max = 64, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_class_name", isUpdate = true)
    String className;

    /**
     * java ??????????????????, ???????????????
     */
    @Length(min = 0, max = 64, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_sub_module_name", isUpdate = true)
    String subModuleName;

    /**
     * ???????????????????????????
     */
    @Length(min = 0, max = 64, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_func_name", isUpdate = true)
    String funcName;

    /**
     * ?????????????????????; true-??????????????????false-???????????????????????????: false???
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @ZKColumn(name = "c_is_tree", isUpdate = true)
    boolean isTree = false;

    /**
     * ?????????
     */
//    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 0, max = 64, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_label", isUpdate = true, isQuery = false)
    String label;

    @Length(min = 0, max = 64, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_icon", isUpdate = true)
    String icon;

    @Length(min = 0, max = 64, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_nav_code", isUpdate = true, isQuery = true, queryType = ZKDBQueryType.LIKE)
    String navCode;

    /**********************************************************************************/
    /*** ?????? ????????????????????????????????? */
    /**********************************************************************************/

    /**
     * ?????????????????????; true-??????????????????false-???????????????????????????: false???
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 20, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_module_id", isUpdate = true, isQuery = true, queryType = ZKDBQueryType.EQ)
    String moduleId;

    /**
     * ??????????????????
     */
    ZKModule module;

    /**
     * java ????????????????????????
     */
    Collection<ZKColInfo> cols;

    /**
     * @return tableName sa
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName
     *            the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @return tableComments sa
     */
    public String getTableComments() {
        return tableComments;
    }

    /**
     * @param tableComments
     *            the tableComments to set
     */
    public void setTableComments(String tableComments) {
        this.tableComments = tableComments;
    }

    /**
     * @return pkColName sa
     */
    public String getPkColName() {
        return pkColName;
    }

    /**
     * @param pkColName
     *            the pkColName to set
     */
    public void setPkColName(String pkColName) {
        this.pkColName = pkColName;
    }

    /**
     * @return subModuleName sa
     */
    public String getSubModuleName() {
        return subModuleName == null ? "" : ZKStringUtils.capitalize(subModuleName);
    }

    /**
     * @param subModuleName
     *            the subModuleName to set
     */
    public void setSubModuleName(String subModuleName) {
        this.subModuleName = subModuleName;
    }

    /**
     * ??????????????????????????????
     * 
     * @return className sa
     */
    public String getClassName() {
        return ZKStringUtils.capitalize(className);
    }

    /**
     * @param className
     *            the className to set
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return funcName sa
     */
    public String getFuncName() {
        return ZKStringUtils.capitalize(funcName);
    }

    /**
     * @param funcName
     *            the funcName to set
     */
    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    /**
     * @return getIsTree sa
     */
    public boolean getIsTree() {
        return isTree;
    }

    /**
     * @param isTree
     *            the isTree to set
     */
    public void setIsTree(boolean isTree) {
        this.isTree = isTree;
    }

    /**
     * @return moduleId sa
     */
    public String getModuleId() {
        return moduleId;
    }

    /**
     * @param moduleId
     *            the moduleId to set
     */
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * @return module sa
     */
    public ZKModule getModule() {
        return module;
    }

    /**
     * @param module
     *            the module to set
     */
    public void setModule(ZKModule module) {
        this.module = module;
    }

    /**
     * @return cols sa
     */
    public Collection<ZKColInfo> getCols() {
        return cols == null ? Collections.emptyList() : cols;
    }

    /**
     * @param cols
     *            the cols to set
     */
    public void setCols(Collection<ZKColInfo> cols) {
        this.cols = cols;
    }

    /**
     * @return label sa
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label
     *            the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return icon sa
     */
    public String getIcon() {
        return icon == null ? "" : icon;
    }

    /**
     * @return navCode sa
     */
    public String getNavCode() {
        return navCode == null ? "" : navCode;
    }

    /**
     * @param icon
     *            the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @param navCode
     *            the navCode to set
     */
    public void setNavCode(String navCode) {
        this.navCode = navCode;
    }


    /**********************************************************************************/
    /*** ???????????? */
    /**********************************************************************************/

    /**
     * @return pkAttrType ??????????????? ???????????????
     */
    public String getPkAttrType() {
        for (ZKColInfo m : this.getCols()) {
            if (this.getPkColName().equals(m.getColName())) {
                return m.getAttrType();
            }
        }
        return "String";
    }

    public ZKColInfo getPkCol() {
        if (this.getCols() != null) {
            for (ZKColInfo col : this.getCols()) {
                if (col.colIsPK) {
                    return col;
                }
            }
        }
        return null;
    }

    /**
     * ??????????????? ???
     *
     * @Title: getImportList
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Mar 25, 2021 2:11:09 PM
     * @return
     * @return Set<String>
     */
    public Set<String> getImportList() {
        Set<String> r = new HashSet<String>();
        for (ZKColInfo m : this.getCols()) {
            if (m.getAttrIsBaseField()) {
                continue;
            }
            r.addAll(m.getImportList());
        }
        if ("String".equals(this.getPkAttrType()) || ("Long".equals(this.getPkAttrType()))) {
            r.add("com.zk.core.utils.ZKIdUtils");
        }
        
        return r;
    }

    public Set<String> getImportAnnotationsList() {
        Set<String> r = new HashSet<String>();
        for (ZKColInfo m : this.getCols()) {
            if (m.getAttrIsBaseField()) {
                continue;
            }
            r.addAll(m.getImportAnnotationsList());
        }
        return r;
    }

    /**
     * ?????????????????????????????????
     *
     * @Title: colIsExists
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Mar 25, 2021 2:11:14 PM
     * @param colName
     * @return
     * @return boolean
     */
    public boolean colIsExists(String colName) {
        for (ZKColInfo m : this.getCols()) {
            if (colName.equals(m.getColName())) {
                return true;
            }
        }
        return false;
    }

    public String getSubModuleNamePath() {
        if (ZKStringUtils.isEmpty(this.getSubModuleName())) {
            return "";
        }
        return ZKStringUtils.replaceEach(ZKStringUtils.uncapitalize(this.getSubModuleName()),
                new String[] { "//", "/", "." },
                new String[] { File.separator, File.separator, File.separator });
    }

    // ???????????????????????? ????????????
    public boolean getIsHasIconEdit() {
        for (ZKColInfo m : this.getCols()) {
            if (ZKColInfo.KeyEditStrategy.EditIcon.equals(m.getEditStrategy())) {
                return true;
            }
        }
        return false;
    }
}
