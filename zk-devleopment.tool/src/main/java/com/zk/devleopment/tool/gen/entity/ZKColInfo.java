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
* @Title: ZKColInfo.java 
* @author Vinson 
* @Package com.zk.code.generate.entity 
* @Description: TODO(simple description this file what to do. ) 
* @date Mar 24, 2021 4:18:10 PM 
* @version V1.0 
*/
package com.zk.devleopment.tool.gen.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zk.base.entity.ZKBaseEntity;
import com.zk.core.utils.ZKStringUtils;
import com.zk.db.annotation.ZKColumn;
import com.zk.db.annotation.ZKTable;
import com.zk.db.commons.ZKDBQueryType;
import com.zk.db.commons.ZKSqlConvertDelegating;
import com.zk.db.mybatis.commons.ZKSqlProvider;
import com.zk.devleopment.tool.gen.action.ZKConvertUtils;

/**
 * @ClassName: ZKColInfo
 * @Description: TODO(simple description this class what to do. )
 * @author Vinson
 * @version 1.0
 */
@ZKTable(name = "t_dt_code_gen_table_col_info", alias = "colInfo", orderBy = " c_col_sort ASC ")
public class ZKColInfo extends ZKBaseEntity<String, ZKColInfo> {

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
            sqlProvider = new ZKSqlProvider(new ZKSqlConvertDelegating(), new ZKColInfo());
        }
        return sqlProvider;
    }

    /**
     * @Fields serialVersionUID : TODO(simple description what to do.)
     */
    private static final long serialVersionUID = 1L;

    public ZKColInfo() {
        super();
    }

    public ZKColInfo(String pkId) {
        super(pkId);
    }

    /**********************************************************************************/
    /*** ??? ?????? ?????? */
    /**********************************************************************************/

    /**
     * ???: ?????????
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 64, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_col_name", isUpdate = false, isQuery = true, queryType = ZKDBQueryType.LIKE)
    String colName;

    /**
     * ??????JDBC?????????
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 512, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_col_jdbc_type", isUpdate = false, isQuery = false)
    String colJdbcType;

    /**
     * ??????????????????JAVA?????????????????????
     */
//    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 0, max = 512, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_col_comments", isUpdate = true, isQuery = false)
    String colComments = "";

    /**
     * ?????????????????????????????????????????????false-?????????true-??????????????? false;
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @ZKColumn(name = "c_col_is_pk", isUpdate = false, isQuery = false)
    boolean colIsPK = false;

    /**
     * ????????????????????????false-????????????true-?????????????????? false
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @ZKColumn(name = "c_col_is_null", isUpdate = false, isQuery = false)
    boolean colIsNull = false;

    /**
     * ??????????????????, ????????? 999999
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Range(min = 0, max = 999999, message = "{zk.core.data.validation.range}")
    @ZKColumn(name = "c_col_sort", isUpdate = true, isQuery = false)
    int colSort = 999999;

    /**********************************************************************************/
    /*** ???????????????????????? */
    /**********************************************************************************/

    /**
     * ???????????????
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 64, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_attr_name", isUpdate = true, isQuery = false)
    String attrName = "";

    /**
     * ??????????????????,?????? String
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 64, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_attr_type", isUpdate = true, isQuery = false)
    String attrType = "String";

    /**
     * ?????????????????????????????????; false-??????; true-???; ?????????false
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @ZKColumn(name = "c_attr_is_base", isUpdate = true, isQuery = false)
    boolean attrIsBaseField = false;

    /**
     * ???????????????UI ?????????????????????????????????
     * ""-?????????????????????EQ-?????????NEQ-????????????GT-?????????GTE-???????????????LT-?????????LTE-???????????????IN-????????????NIN-???????????????LIKE-???????????????NLIKE???ISNULL-?????????ISNOTNULL-????????????
     * ?????????null
     */
    @Length(min = 0, max = 64, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_attr_query_type", isUpdate = true, isQuery = false)
    String attrQueryType;

    /**
     * ?????????????????????; false-????????????true-????????????????????? true
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @ZKColumn(name = "c_attr_is_insert", isUpdate = true, isQuery = false)
    boolean attrIsInsert = true;

    /**
     * ?????????????????????false-?????????????????????true-????????????????????? true;
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @ZKColumn(name = "c_attr_is_update", isUpdate = true, isQuery = false)
    boolean attrIsUpdate = true;

    /**********************************************************************************/
    /*** ?????? ????????????????????????????????? */
    /**********************************************************************************/
    /**
     * ????????? id
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 1, max = 20, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_table_id", isUpdate = false, isQuery = true, queryType = ZKDBQueryType.EQ)
    String tableId;

    /**
     * ????????????
     */
//    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 0, max = 64, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_label", isUpdate = true, isQuery = false)
    String label;

    /**
     * UI ?????????????????????????????????????????????????????????;
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 0, max = 64, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_edit_strategy", isUpdate = true, isQuery = false)
    String editStrategy = ZKColInfo.KeyEditStrategy.noEdit;

    /**
     * UI ????????????????????????????????????????????????????????????
     */
    @NotNull(message = "{zk.core.data.validation.notNull}")
    @Length(min = 0, max = 64, message = "{zk.core.data.validation.length}")
    @ZKColumn(name = "c_search_strategy", isUpdate = true, isQuery = false)
    String searchStrategy = ZKColInfo.KeySearchStrategy.noSearch;

    /**********************************************************************************/
    /**********************************************************************************/
    /**
     * @return colName sa
     */
    public String getColName() {
        return colName;
    }

    /**
     * @param colName
     *            the colName to set
     */
    public void setColName(String colName) {
        this.colName = colName;
    }

    /**
     * @return colJdbcType sa
     */
    public String getColJdbcType() {
        return colJdbcType;
    }

    /**
     * @param colJdbcType
     *            the colJdbcType to set
     */
    public void setColJdbcType(String colJdbcType) {
        this.colJdbcType = colJdbcType;
    }

    /**
     * @return colComments sa
     */
    public String getColComments() {
        return colComments;
    }

    /**
     * @param colComments
     *            the colComments to set
     */
    public void setColComments(String colComments) {
        this.colComments = colComments;
    }

    /**
     * @return colIsPK sa
     */
    public boolean getColIsPK() {
        return colIsPK;
    }

    /**
     * @param colIsPK
     *            the colIsPK to set
     */
    public void setColIsPK(boolean colIsPK) {
        this.colIsPK = colIsPK;
    }

    /**
     * @return colIsNull sa
     */
    public boolean getColIsNull() {
        return colIsNull;
    }

    /**
     * @param colIsNull
     *            the colIsNull to set
     */
    public void setColIsNull(boolean colIsNull) {
        this.colIsNull = colIsNull;
    }

    /**
     * @return colSort sa
     */
    public int getColSort() {
        return colSort;
    }

    /**
     * @param colSort
     *            the colSort to set
     */
    public void setColSort(int colSort) {
        this.colSort = colSort;
    }

    /**
     * @return attrName sa
     */
    public String getAttrName() {
        return attrName;
    }

    /**
     * @param attrName
     *            the attrName to set
     */
    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    /**
     * @return attrType sa
     */
    public String getAttrType() {
        return attrType;
    }

    /**
     * @param attrType
     *            the attrType to set
     */
    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    /**
     * @return attrIsBaseField sa
     */
    public boolean getAttrIsBaseField() {
        return attrIsBaseField;
    }

    /**
     * @param attrIsBaseField
     *            the attrIsBaseField to set
     */
    public void setAttrIsBaseField(boolean attrIsBaseField) {
        this.attrIsBaseField = attrIsBaseField;
    }

    /**
     * @return attrQueryType sa
     */
    public String getAttrQueryType() {
        return attrQueryType;
    }

    /**
     * @param attrQueryType
     *            the attrQueryType to set
     */
    public void setAttrQueryType(String attrQueryType) {
        this.attrQueryType = attrQueryType;
    }

    /**
     * @return attrIsInsert sa
     */
    public boolean getAttrIsInsert() {
        return attrIsInsert;
    }

    /**
     * @param attrIsInsert
     *            the attrIsInsert to set
     */
    public void setAttrIsInsert(boolean attrIsInsert) {
        this.attrIsInsert = attrIsInsert;
    }

    /**
     * @return attrIsUpdate sa
     */
    public boolean getAttrIsUpdate() {
        return attrIsUpdate;
    }

    /**
     * @param attrIsUpdate
     *            the attrIsUpdate to set
     */
    public void setAttrIsUpdate(boolean attrIsUpdate) {
        this.attrIsUpdate = attrIsUpdate;
    }

    /**
     * @return tableName sa
     */
    public String getTableId() {
        return tableId;
    }

    /**
     * @param tableName
     *            the tableName to set
     */
    public void setTableId(String tableId) {
        this.tableId = tableId;
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

    /************************************/

    /**
     * UI ????????????
     * 
     * ?????????????????????????????????????????????Json????????????????????????????????????????????????????????????
     * 
     * @ClassName: KeyEditStrategy
     * @Description: TODO(simple description this class what to do. )
     * @author Vinson
     * @version 1.0
     */
    public static interface KeyEditStrategy {
        /**
         * ????????????????????????????????????
         */
        public static final String noEdit = "noEdit";

        /**
         * ?????????
         */
        public static final String EditString = "EditString";

        /**
         * ??????
         */
        public static final String EditInt = "EditInt";

        /**
         * ?????????
         */
        public static final String EditFloat = "EditFloat";

        /**
         * Json
         */
        public static final String EditJson = "EditJson";

        /**
         * ????????????
         */
        public static final String EditSel = "EditSel";

        /**
         * ??????
         */
        public static final String EditRadio = "EditRadio";

        /**
         * ??????
         */
        public static final String EditCheck = "EditCheck";

        /**
         * ????????????
         */
        public static final String EditIcon = "EditIcon";

        /**
         * ??????
         */
        public static final String EditDate = "EditDate";
    }

    /**
     * UI ????????????
     * 
     * ???????????????????????????json; ????????????????????????????????????????????????
     * 
     * @ClassName: KeySearchStrategy
     * @Description: TODO(simple description this class what to do. )
     * @author Vinson
     * @version 1.0
     */
    public static interface KeySearchStrategy {
        /**
         * ?????????
         */
        public static final String noSearch = "noSearch";

        /**
         * ????????????
         */
        public static final String SearchString = "SearchString";

        /**
         * json
         */
        public static final String SearchJson = "SearchJson";

        /**
         * ????????????
         */
        public static final String SearchSel = "SearchSel";

        /**
         * ??????
         */
        public static final String SearchDate = "SearchDate";

        /**
         * ????????????
         */
        public static final String SearchDateRang = "SearchDateRang";

        /**
         * ??????
         */
        public static final String SearchNum = "SearchNum";
    }

    /**
     * UI ????????????
     * 
     * @return editStrategy sa
     */
    public String getEditStrategy() {
        return editStrategy;
    }

    /**
     * @param editStrategy
     *            the editStrategy to set
     */
    public void setEditStrategy(String editStrategy) {
        this.editStrategy = editStrategy;
    }

    /**
     * UI ????????????
     * 
     * @return searchStrategy sa
     */
    public String getSearchStrategy() {
        return searchStrategy;
    }

    /**
     * @param searchStrategy
     *            the searchStrategy to set
     */
    public void setSearchStrategy(String searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    /**********************************************************************************/
    /*** ???????????? ?????? */
    /**********************************************************************************/
    
    public boolean getAttrTypeStartWith(String str) {
        return this.getAttrType().startsWith(str);
    }

    /**
     * @return colLength sa
     */
    public int[] getColLength() {
        return ZKConvertUtils.convertJdbcLength(this.getColJdbcType());
    }

    public String getMaxValue() {
        if ("String".equals(this.getAttrType())) {
            return String.valueOf(this.getColLength() == null ? 64 : (this.getColLength()[0] / 4));
        }
        else if (this.isInt()) {
            int maxV = 999999999;
            int[] cl = this.getColLength();
            if (cl != null) {
                maxV = cl[0];
                maxV = Double.valueOf(Math.pow(10, maxV)).intValue();
                maxV = maxV - 1;
            }
            return String.valueOf(maxV);
        }
        else if (this.isFloat()) {
            int maxV = 999999999;
            int[] cl = this.getColLength();
            if (cl != null) {
                maxV = cl[0];
                if (cl.length > 1) {
                    maxV = maxV - cl[1];
                }
                maxV = Double.valueOf(Math.pow(10, maxV)).intValue();
                maxV = maxV - 1;
            }
            return String.valueOf(maxV);
        }
        return "9";
    }

    public String getMinValue() {
        if ("String".equals(this.getAttrType())) {
            return this.getColIsNull() ? "0" : "1";
        }
        else if (this.isInt() || this.isFloat()) {
            return "0";
        }
        return "0";
    }

    public Set<String> getImportAnnotationsList() {
        Set<String> r = new HashSet<String>();
        // ??????????????????
        if (!this.getColIsNull()) {
            r.add("javax.validation.constraints.NotNull");
            if (this.getAttrType().startsWith("ZKJson")) {
                r.add("javax.validation.constraints.NotEmpty");
            }
        }
        // ????????????
        if (this.getColLength() != null) {
            if ("String".equals(this.getAttrType())) {
                r.add("org.hibernate.validator.constraints.Length");
            }
            else if (this.isInt() || this.isFloat()) {
                r.add("org.hibernate.validator.constraints.Range");
            }
        }
        else {
            if ("String".equals(this.getAttrType())) {
                r.add("org.hibernate.validator.constraints.Length");
            }
            else if (this.isInt() || this.isFloat()) {
                r.add("org.hibernate.validator.constraints.Range");
            }
        }
        // ????????????
        if ("Date".equals(this.getAttrType())) {
            r.add("com.fasterxml.jackson.annotation.JsonFormat");
            r.add("com.fasterxml.jackson.annotation.JsonInclude");
        }
        return r;
    }

    public Set<String> getImportList() {
        Set<String> r = new HashSet<String>();
        // ????????????
        if (!ZKStringUtils.isEmpty(this.getAttrQueryType())) {
            r.add("com.zk.db.commons.ZKDBQueryType");
        }
        // ????????????
        if ("Date".equals(this.getAttrType())) {
            r.add("java.util.Date");
            r.add("com.zk.core.utils.ZKDateUtils");
        }
        else if ("String".equals(this.getAttrType())) {
            r.add("java.lang.String");
        }
        else if (this.getAttrType().startsWith("ZKJson")) {
            r.add("com.zk.core.commons.data.ZKJson");
        }
        return r;
    }

    public List<String> getFieldAnnotations() {
        String annotStr = "";
        List<String> filedAnnotations = new ArrayList<>();
        // ??????????????????
        if (!this.getColIsNull()) {
            annotStr = "@NotNull(message = \"{zk.core.data.validation.notNull}\")";
            filedAnnotations.add(annotStr);
            if (this.getAttrType().startsWith("ZKJson")) {
                filedAnnotations.add("@NotEmpty(message = \"{zk.core.data.validation.notNull}\")");
            }
        }
        // ????????????
        if ("String".equals(this.getAttrType())) {
            annotStr = String.format("@Length(min = %s, max = %s, message = \"{zk.core.data.validation.length.max}\")",
                    this.getMinValue(), this.getMaxValue());
            filedAnnotations.add(annotStr);
        }
        else if (this.isInt()) {
            annotStr = String.format("@Range(min = %s, max = %s, message = \"{zk.core.data.validation.rang.int}\")",
                    this.getMinValue(), this.getMaxValue());
            filedAnnotations.add(annotStr);
        }
        else if (this.isFloat()) {
            annotStr = String.format("@Range(min = %s, max = %s, message = \"{zk.core.data.validation.rang}\")",
                    this.getMinValue(), this.getMaxValue());
            filedAnnotations.add(annotStr);
        }

        // ????????????????????????
        if ("Date".equals(this.getAttrType())) {
            annotStr = "@JsonInclude(value = JsonInclude.Include.NON_EMPTY)";
            filedAnnotations.add(annotStr);
            annotStr = "@JsonFormat(pattern = ZKDateUtils.DF_yyyy_MM_dd, timezone = timezone)";
            filedAnnotations.add(annotStr);
        }

        // ????????????
//        formats = "%Y-%m-%d %H:%i:%S", 
        StringBuffer sb = new StringBuffer();
        sb = sb.append("@ZKColumn(");
        sb = sb.append("name = \"").append(this.getColName()).append("\", ");
        sb = sb.append("isInsert = ").append(this.getAttrIsInsert()).append(", ");
        sb = sb.append("isUpdate = ").append(this.getAttrIsUpdate()).append(", ");
        sb = sb.append("javaType = ").append(this.getAttrType().replaceAll("<(.*)>", "")).append(".class, ");
        if ("Date".equals(this.getAttrType())) {
            sb = sb.append("formats = \"").append("%Y-%m-%d %H:%i:%S").append("\", ");
        }

        if (ZKStringUtils.isEmpty(this.getAttrQueryType())) {
            sb = sb.append("isQuery = ").append(false);
        }
        else {
            sb = sb.append("isQuery = ").append(true).append(", ");
            sb = sb.append("queryType = ZKDBQueryType.").append(this.getAttrQueryType());
        }

        sb = sb.append(")");
        annotStr = sb.toString();
//        annotStr = String.format(
//                "@ZKColumn(name = \"%s\", isInsert = %s, isUpdate = %s, javaType = %s.class, isQuery = %s%s)",
//                this.getColName(), this.getAttrIsInsert(), this.getAttrIsUpdate(),
//                this.getAttrType().replaceAll("<(.*)>", ""),
//                (ZKStringUtils.isEmpty(this.getAttrQueryType()) != true),
//                (ZKStringUtils.isEmpty(this.getAttrQueryType()) ? ""
//                        : String.format(", queryType = ZKDBQueryType.%s", this.getAttrQueryType())));

        filedAnnotations.add(annotStr);
        return filedAnnotations;
    }

    public boolean isFloat() {
        return ZKConvertUtils.isFloat(this.getAttrType());
    }

    public boolean isInt() {
        return ZKConvertUtils.isInt(this.getAttrType());
    }

}
