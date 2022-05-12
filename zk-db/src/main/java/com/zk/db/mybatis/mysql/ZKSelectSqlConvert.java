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
* @Title: ZKSelectSqlConvert.java 
* @author Vinson 
* @Package com.zk.db.annotation.support.mysql 
* @Description: TODO(simple description this file what to do. ) 
* @date Sep 11, 2020 11:06:53 AM 
* @version V1.0 
*/
package com.zk.db.mybatis.mysql;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.zk.core.commons.data.ZKJson;
import com.zk.core.commons.data.ZKOrder;
import com.zk.core.utils.ZKStringUtils;
import com.zk.db.annotation.ZKColumn;
import com.zk.db.annotation.ZKDBAnnotationProvider;
import com.zk.db.commons.ZKDBQueryCondition;
import com.zk.db.commons.ZKDBQueryCondition.ZKDBConditionLogicDispose;
import com.zk.db.commons.ZKDBQueryLogic;
import com.zk.db.commons.ZKDBQueryType;
import com.zk.db.mybatis.commons.ZKDBQueryConditionCol;
import com.zk.db.mybatis.commons.ZKDBQueryConditionIfByClass;
import com.zk.db.mybatis.commons.ZKDBQueryConditionWhereTrim;

/** 
* @ClassName: ZKSelectSqlConvert 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKSelectSqlConvert {

//    // 查询目标，带有 FROM
//    public static String convertSqlSelFrom(ZKDBAnnotationProvider annotationProvider, String tableAlias) {
//        StringBuffer sb = new StringBuffer();
//        String alias = "";
//        if (!ZKStringUtils.isEmpty(tableAlias)) {
//            alias = tableAlias;
//        }
//        sb.append("FROM ");
//        sb.append(annotationProvider.getTableName());
//        sb.append(" ");
//        sb.append(alias);
//        sb.append(" ");
//        return sb.toString();
//    }

    // 查询目标，不带有 FROM
    public static String convertSqlSelTable(ZKDBAnnotationProvider annotationProvider, String tableAlias) {
        StringBuffer sb = new StringBuffer();
        String alias = "";
        if (!ZKStringUtils.isEmpty(tableAlias)) {
            alias = tableAlias;
        }
//        sb.append(" FROM ");
        sb.append(annotationProvider.getTableName());
        sb.append(" ");
        sb.append(alias);
        return sb.toString();
    }

    // 查询结果映射
    public static String convertSqlSelCols(ZKDBAnnotationProvider annotationProvider, String tableAlias) {
        StringBuffer sb = new StringBuffer();
        String alias = "";
        if (!ZKStringUtils.isEmpty(tableAlias)) {
            alias = tableAlias + ".";
        }
        for (Entry<PropertyDescriptor, ZKColumn> e : annotationProvider.getColumnInfo().entrySet()) {
            // 是老婆呢做结果映射
            if (e.getValue().isResult()) {
                sb.append(", ");
                sb.append(alias);
                sb.append(e.getValue().name());
                sb.append(" AS ");
                sb.append("\"");
                sb.append(e.getKey().getName());
                sb.append("\"");
            }
        }
        return (sb.length() > 1 ? sb.substring(2) : sb.toString());
    }

    // 查询主键条件; 带有 WHERE
    public static String convertSqlPkWhere(ZKDBAnnotationProvider annotationProvider, String tableAlias) {
        String alias = "";
        if (!ZKStringUtils.isEmpty(tableAlias)) {
            alias = tableAlias + ".";
        }

        StringBuffer sbWere = new StringBuffer();
        ZKColumn column = null;
        for (Entry<PropertyDescriptor, ZKColumn> e : annotationProvider.getColumnInfo().entrySet()) {
            column = e.getValue();
            if (column.isPk()) {
                if (sbWere.length() < 1) {
                    sbWere.append("WHERE ");
                }
                else {
                    sbWere.append(" AND ");
                }
                sbWere.append(alias);
                sbWere.append(column.name());
                sbWere.append(" = ");
                sbWere.append("#{");
                sbWere.append(e.getKey().getName());
                sbWere.append("}");
            }
        }
        return sbWere.toString();
    }

    /**
     * 取查询条件 生成 查询条件 script if sql; 不带 WHERE
     *
     * @Title: convertSqlQueryConditionIf
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Apr 15, 2021 7:14:55 AM
     * @param annotationProvider
     * @param tableAlias
     *            表的别名；
     * @return void
     */
    public static String convertSqlQueryConditionIf(ZKDBAnnotationProvider annotationProvider, String tableAlias) {
        StringBuffer sb = new StringBuffer();
        for (Entry<PropertyDescriptor, ZKColumn> e : annotationProvider.getColumnInfo().entrySet()) {
            if (e.getValue().isQuery()) {
                appendScriptQueryConditionIfByClass(sb, e.getKey().getName(), e.getValue().javaType(), false, () -> {
                    String alias = "";
                    if (!ZKStringUtils.isEmpty(tableAlias)) {
                        alias = tableAlias + ".";
                    }
                    sb.append(ZKDBQueryLogic.AND.getEsc());
                    convertSqlQueryCondition(sb, alias, e.getKey().getName(), e.getValue(), ZKDBQueryLogic.AND);
                });
            }
        }
        return sb.toString();
    }

    // 这里将根据字段名映射到数据库字段；所以这里 ZKOrder 中填写的是 JAVA 实体字段名； 带有 ORDER BY
    public static String convertSqlOrderBy(ZKDBAnnotationProvider annotationProvider, Collection<ZKOrder> sorts,
            String tableAlias) {
        String alias = "";
        if (!ZKStringUtils.isEmpty(tableAlias)) {
            alias = tableAlias + ".";
        }
        StringBuffer sb = new StringBuffer();
        if (sorts != null && !sorts.isEmpty()) {
            for (ZKOrder item : sorts) {
                if (annotationProvider.getColumn(item.getColumnName()) != null) {
                    sb.append(", ");
                    sb.append(alias);
                    sb.append(annotationProvider.getColumn(item.getColumnName()).name());
                    sb.append(" ");
                    sb.append(item.getSortMode().getValue());
                }
            }
        }
        if (sb.length() > 0) {
            return " ORDER BY " + sb.substring(2);
        }
        return "";
    }

    // 从注解 table 中取默认的排序字段，在注解中直接定义 排序映射，不需要再解析属性与字段的映射； 带有 ORDER BY
    public static String convertSqlOrderBy(ZKDBAnnotationProvider annotationProvider, String tableAlias) {
        String alias = "";
        if (!ZKStringUtils.isEmpty(tableAlias)) {
            alias = tableAlias + ".";
        }
        StringBuffer sb = new StringBuffer();
        if (annotationProvider.getTable().orderBy().length > 0) {
            for (String col : annotationProvider.getTable().orderBy()) {
                col = col.trim();
                sb.append(", ");
                sb.append(alias);
                sb.append(col);
            }
        }
        if (sb.length() > 0) {
            return " ORDER BY " + sb.substring(2);
        }
        return "";
    }

    /********************************************************/
    /***  **/
    /********************************************************/

    // 生成 查询 where
    public static ZKDBQueryConditionWhereTrim getWhere(ZKDBAnnotationProvider annotationProvider) {
        Map<PropertyDescriptor, ZKColumn> columnInfo = annotationProvider.getColumnInfo();
        ZKDBQueryConditionWhereTrim where = ZKDBQueryConditionWhereTrim.asAnd((ZKDBQueryCondition) null);
        ZKColumn col = null;
        for (Entry<PropertyDescriptor, ZKColumn> e : columnInfo.entrySet()) {
            col = e.getValue();
            if (col.isQuery()) {
                where.put(convertCondition(e.getKey(), col));
            }
        }
        return where;
    }

    // 根据字段与ZKColumn 生成查询条件
    private static ZKDBQueryCondition convertCondition(PropertyDescriptor pd, ZKColumn col) {
        ZKDBQueryConditionCol conditionCol = ZKDBQueryConditionCol.as(col.queryType(), col.name(), pd.getName(),
                col.javaType(), col.formats(), col.isCaseSensitive());
        if (col.isForce()) {
            return conditionCol;
        }
        else {
            return ZKDBQueryConditionIfByClass.as(conditionCol, pd.getName(), col.javaType(), false);
        }

    }

    /********************************************************/
    /***  **/
    /********************************************************/
    
    /**
     * 包裹 <if>
     *
     * @Title: appendScriptQueryConditionIf
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Apr 15, 2021 10:11:33 AM
     * @param sb
     * @param attrName
     * @param javaClassz
     * @param isEmpty
     * @param funcAppendScriptQueryCondition
     * @return void
     */
    public static void appendScriptQueryConditionIfByClass(StringBuffer sb, String attrName, Class<?> javaClassz,
            boolean isEmpty, ZKDBConditionLogicDispose funcQueryLogicDispose) {
        if (isEmpty) {
            appendScriptQueryConditionIfByClassEmpty(sb, attrName, javaClassz);
        }
        else {
            appendScriptQueryConditionIfByClassNotEmpty(sb, attrName, javaClassz);
        }
        funcQueryLogicDispose.doing();
        sb.append("</if>");
    }

    /**
     * 添加 if 包裹脚本；
     *
     * @Title: appendScriptQueryConditionIf
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Apr 19, 2022 4:15:55 PM
     * @param logic
     *            if 逻辑字符 and or 同 sql 逻辑查询字符
     * @param funcQueryLogicDispose
     * @param sb
     * @param attrName
     * @param isNull
     *            为null时不做为条件判断；true-添加为 null 判断；false-添加不为 null 判断；
     * @param isTrue
     *            为null时不做为条件判断；true-添加为 true 判断；false-添加不为 ture 判断；
     * @param isEmpty
     *            为null时不做为条件判断；true-添加为 空 判断；false-添加不为 空 判断；
     * @param lengthIs0
     *            为null时不做为条件判断；true-添加为 长度为 0 判断；false-添加不为 长度不为 0 判断；
     * @param valueIs0
     *            为null时不做为条件判断；true-添加为 值为0 判断；false-添加不为 值不为 0 判断；
     * @return void
     */
    public static void appendScriptQueryConditionIf(ZKDBQueryLogic logic,
            ZKDBConditionLogicDispose funcQueryLogicDispose, StringBuffer sb, String attrName, Boolean isNull,
            Boolean isTrue, Boolean isEmpty, Boolean lengthIs0, Boolean valueIs0) {
        boolean isInsertLogic = false;
        sb.append("<if test=\"");
        if (isNull != null) {
            if (isNull) {
                sb.append(attrName);
                sb.append(" == null ");
            }
            else {
                sb.append(attrName);
                sb.append(" != null ");
            }
            isInsertLogic = true;
        }
        if (isTrue != null) {
            if (isInsertLogic == true) {
                sb.append(logic.getEsc().toLowerCase());
                sb.append(" ");
            }
            else {
                isInsertLogic = true;
            }
            if (isTrue) {
                sb.append(attrName);
                sb.append(" == true ");
            }
            else {
                sb.append(attrName);
                sb.append(" == false ");
            }
        }
        if (isEmpty != null) {
            if (isInsertLogic == true) {
                sb.append(logic.getEsc().toLowerCase());
                sb.append(" ");
            }
            else {
                isInsertLogic = true;
            }
            if (isEmpty) {
                sb.append(attrName);
                sb.append(".isEmpty() == true ");
            }
            else {
                sb.append(attrName);
                sb.append(".isEmpty() == false ");
            }
        }
        if (lengthIs0 != null) {
            if (isInsertLogic == true) {
                sb.append(logic.getEsc().toLowerCase());
                sb.append(" ");
            }
            else {
                isInsertLogic = true;
            }
            if (lengthIs0) {
                sb.append(attrName);
                sb.append(".length == 0 ");
            }
            else {
                sb.append(attrName);
                sb.append(".length != 0 ");
            }
        }
        if (valueIs0 != null) {
            if (isInsertLogic == true) {
                sb.append(logic.getEsc().toLowerCase());
                sb.append(" ");
            }
            else {
                isInsertLogic = true;
            }
            if (valueIs0) {
                sb.append(attrName);
                sb.append(" == 0 ");
            }
            else {
                sb.append(attrName);
                sb.append(" != 0 ");
            }
        }
        sb.append("\">");
        funcQueryLogicDispose.doing();
        sb.append("</if>");
    }

    // 包裹 <if> 为 null 或 为空
    private static void appendScriptQueryConditionIfByClassEmpty(StringBuffer sb, String attrName,
            Class<?> javaClassz) {
        // 添加查询条件，判断参数 为 null 或 为空；数字类型时 为 null 或为 0 时
        if (javaClassz == String.class) {
            // 字符串
            sb.append("<if test=\"");
            sb.append(attrName);
            sb.append(" == null or ");
            sb.append(attrName);
            sb.append(" == ''\">");
        }
        else if (javaClassz == List.class) {
            // 数组或 List
            sb.append("<if test=\"");
            sb.append(attrName);
            sb.append(" == null or ");
            sb.append(attrName);
            sb.append(".isEmpty() == true\">");
        }
        else if (javaClassz.isArray()) {
            // 数组
            sb.append("<if test=\"");
            sb.append(attrName);
            sb.append(" == null or ");
            sb.append(attrName);
            sb.append(".length == 0\">");
        }
        else if (javaClassz == Integer.class || javaClassz == Long.class) {
            // 数组
            sb.append("<if test=\"");
            sb.append(attrName);
            sb.append(" == null or ");
            sb.append(attrName);
            sb.append(" == 0\">");
        }
        else {
            // 其他类型
            sb.append("<if test=\"");
            sb.append(attrName);
            sb.append(" == null\">");
        }
    }

    // 包裹 <if> 不为空
    private static void appendScriptQueryConditionIfByClassNotEmpty(StringBuffer sb, String attrName,
            Class<?> javaClassz) {
        // 添加查询条件，判断参数 不为 null 且不为空
        if (javaClassz == String.class) {
            // 字符串
            sb.append("<if test=\" ");
            sb.append(attrName);
            sb.append(" != null and ");
            sb.append(attrName);
            sb.append(" != ''\">");
        }
        else if (javaClassz == ZKJson.class) {
            // Json 类型
            sb.append("<if test=\"");
            sb.append(attrName);
            sb.append(" != null and ");
            sb.append(attrName);
            sb.append(".getKeyValues() != null\">");
        }
        else if (javaClassz == List.class) {
            // 数组或 List
            sb.append("<if test=\"");
            sb.append(attrName);
            sb.append(" != null and ");
            sb.append(attrName);
            sb.append(".isEmpty() != true\">");
        }
        else if (javaClassz.isArray()) {
            // 数组
            sb.append("<if test=\"");
            sb.append(attrName);
            sb.append(" != null and ");
            sb.append(attrName);
            sb.append(".length > 0\">");
        }
        else {
            // 其他类型
            sb.append("<if test=\" ");
            sb.append(attrName);
            sb.append(" != null\">");
        }
    }

    /**
     * 根据 ZKColumn 生成查询条件；没有查询的逻辑关系，即没 AND OR
     *
     * @Title: convertSqlQueryCondition
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Apr 15, 2021 10:08:55 AM
     * @param sb
     * @param tableAlias
     *            表的别名；这里需要加上 “.”；不会自动添加
     * @param attrName
     * @param col
     * @param queryLogic
     * @return void
     */
    private static void convertSqlQueryCondition(StringBuffer sb, String tableAlias, String attrName, ZKColumn col,
            ZKDBQueryLogic queryLogic) {
        appendScriptQueryCondition(sb, tableAlias, col.name(), attrName, queryLogic, col.queryType(), col.javaType(),
                col.formats(), col.isCaseSensitive());
    }

    /**
     * 添加查询条件; 没有查询的逻辑关系，即没 AND OR
     *
     * @Title: convertScriptQueryCondition
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Apr 14, 2021 8:05:17 PM
     * @param sb
     * @param tableAlias
     *            表的别名；这里需要加上 “.”；不会自动添加
     * @param columnName
     * @param attrName
     * @param queryLogic
     * @param queryType
     * @param javaClassz
     * @param formats
     * @param isCaseSensitive
     * @return String
     */
    public static void appendScriptQueryCondition(StringBuffer sb, String tableAlias, String columnName,
            String attrName, ZKDBQueryLogic queryLogic, ZKDBQueryType queryType, Class<?> javaClassz, String[] formats,
            boolean isCaseSensitive) {
        String dateFormatPattern = null;
        if (formats != null && formats.length > 0) {
            dateFormatPattern = formats[0];
        }
        if (javaClassz == ZKJson.class) {
            // JSON 属性条件细分
            sb.append("<foreach item=\"_v\" index=\"_k\" collection=\"");
            sb.append(attrName);
            sb.append(".getKeyValues()\" open=\"\" separator=\"");
            sb.append(queryLogic.getEsc());
            sb.append("\" close=\"\">");
            appendScriptQueryCondition(sb, "JSON_UNQUOTE(JSON_EXTRACT(" + tableAlias + columnName + ", #{_k}))",
                    "_v", queryType, String.class, dateFormatPattern, isCaseSensitive);
            sb.append("</foreach>");
        }
        else {
            appendScriptQueryCondition(sb, tableAlias + columnName, attrName, queryType, javaClassz,
                    dateFormatPattern, isCaseSensitive);
        }
    }

    /**
     * 添加查询条件值; 没有查询的逻辑关系，即没 AND OR
     *
     * @Title: convertScriptQueryCondition
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Apr 14, 2021 8:06:10 PM
     * @param sb
     * @param columnName
     *            表 字段名；包含了表的别名；
     * @param attrName
     *            java 属性名; 这里会添加 #{}
     * @param queryType
     *            查询类型
     * @param javaClassz
     *            查询字段的 java 数据类型
     * @param dateFormatPattern
     *            日期格式化参数；不为 null 时，格式化数据库字段；不为 null 且属性 java 数据类型为 Date
     *            时，同时也用他格式化参数；
     * @param isCaseSensitive
     *            是否区分大小，注，此参数仅在 javaClassz 为 String.class
     *            类型下有效；true-区分大小；false-不区分大小写；
     * @return void
     */
    public static void appendScriptQueryCondition(StringBuffer sb, String columnName, String attrName,
            ZKDBQueryType queryType, Class<?> javaClassz, String dateFormatPattern, boolean isCaseSensitive) {
        switch (queryType) {
            case ISNULL:
            case ISNOTNULL:
                sb.append(columnName);
                sb.append(queryType.getEsc());
                break;
            case IN:
            case NIN: // 为 IN, NIN 时，Java 数据类型 要为数组或 List
                sb.append(columnName);
                sb.append(queryType.getEsc());
                sb.append("<foreach item=\"_v\" index=\"_index\" collection=\"");
                sb.append(attrName);
                sb.append("\" open=\"(\" separator=\",\"");
                sb.append(" close=\")\">");
                sb.append(
                        appendConditionFormatValue("#{_v}", queryType, javaClassz, dateFormatPattern, isCaseSensitive));
                sb.append("</foreach>");
                break;
            default:
                appendConditionFormat(sb, columnName, "#{" + attrName + "}", queryType, javaClassz, dateFormatPattern,
                        isCaseSensitive);
                break;
        }
    }

    /**
     * 
     *
     * @Title: appendConditionFormat
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Apr 14, 2021 8:12:10 PM
     * @param sb
     * @param columnName
     * @param attrName
     * @param queryType
     * @param javaClassz
     * @param dateFormatPattern
     *            日期格式化参数；不为 null 时，格式化数据库字段；不为 null 且属性 java 数据类型为 Date
     *            时，同时也用他格式化参数；
     * @param isCaseSensitive
     *            是否区分大小，注，此参数仅在 javaClassz 为 String.class
     *            类型下有效；true-区分大小；false-不区分大小写；
     * @return void
     */
    public static void appendConditionFormat(StringBuffer sb, String columnName, String attrName,
            ZKDBQueryType queryType, Class<?> javaClassz, String dateFormatPattern, boolean isCaseSensitive) {
        sb.append(appendConditionFormatColumnName(columnName, javaClassz, dateFormatPattern, isCaseSensitive));
        sb.append(queryType.getEsc());
        sb.append(appendConditionFormatValue(attrName, queryType, javaClassz, dateFormatPattern, isCaseSensitive));
        // sb.append(" ");
    }

    public static String appendConditionFormatColumnName(String columnName, Class<?> javaClassz,
            String dateFormatPattern, boolean isCaseSensitive) {

        StringBuffer tsb = null;
        // 日期格式化
        if (dateFormatPattern != null) {
            tsb = new StringBuffer();
            tsb.append("DATE_FORMAT(");
            tsb.append(columnName);
            tsb.append(", \"");
            tsb.append(dateFormatPattern);
            tsb.append("\")");
            columnName = tsb.toString();

        }
        // 字段 java 类型为字符串，且不区分大小定时
        if (javaClassz == String.class && !isCaseSensitive) {
            tsb = new StringBuffer();
            tsb.append("UPPER(");
            tsb.append(columnName);
            tsb.append(")");
            columnName = tsb.toString();
        }
        return columnName;
    }

    public static String appendConditionFormatValue(String attrName, ZKDBQueryType queryType,
            Class<?> javaClassz, String dateFormatPattern, boolean isCaseSensitive) {

        StringBuffer tsb = null;
        // 日期格式化
        if (javaClassz == Date.class && dateFormatPattern != null) {
            tsb = new StringBuffer();
            tsb.append("DATE_FORMAT(");
            tsb.append(attrName);
            tsb.append(", \"");
            tsb.append(dateFormatPattern);
            tsb.append("\")");
            attrName = tsb.toString();
        }
        // 字段 java 类型为字符串，且不区分大小定时
        if (javaClassz == String.class && !isCaseSensitive) {
            tsb = new StringBuffer();
            tsb.append("UPPER(");
            tsb.append(attrName);
            tsb.append(")");
            attrName = tsb.toString();
        }
        // like 和 not like 处理；仅 java 数据类型为 String.class 时进行处理；
        if (javaClassz == String.class && (queryType == ZKDBQueryType.LIKE || queryType == ZKDBQueryType.NLIKE)) {
            // "CONCAT('%', REPLACE(REPLACE(REPLACE(?, '\\\\\\\\', '\\\\\\\\\\\\\\\\'), '_', '\\\\_'), '%', '\\\\%'), '%')"
            // 
            // public static final String replaceString = "$1LIKE$2$3CONCAT('%', REPLACE(REPLACE(REPLACE(?, '\\\\\\\\', '\\\\\\\\\\\\\\\\'), '_', '\\\\_'), '%', '\\\\%'), '%')";
            tsb = new StringBuffer();
            tsb.append("CONCAT('%', REPLACE(REPLACE(REPLACE(");
            tsb.append(attrName);
            tsb.append(", '\\\\\\\\', '\\\\\\\\\\\\\\\\'), '_', '\\\\_'), '%', '\\\\%'), '%')");
            attrName = tsb.toString();
        }

        return attrName;
    }

}
