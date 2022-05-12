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
* @Title: ZKDBQueryConditionCol.java 
* @author Vinson 
* @Package com.zk.db.commons 
* @Description: TODO(simple description this file what to do. ) 
* @date Apr 15, 2021 8:15:26 AM 
* @version V1.0 
*/
package com.zk.db.mybatis.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.db.commons.ZKDBQueryCondition;
import com.zk.db.commons.ZKDBQueryLogic;
import com.zk.db.commons.ZKDBQueryType;
import com.zk.db.commons.ZKSqlConvert;

/** 
* @ClassName: ZKDBQueryConditionCol 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public class ZKDBQueryConditionCol implements ZKDBQueryCondition {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void toQueryCondition(ZKSqlConvert convert, StringBuffer sb, String tableAlias, ZKDBQueryLogic queryLogic,
            boolean isInserQueryLogic) {
        if (convert instanceof ZKSqlMybatisConvert) {
            if (queryLogic != null && isInserQueryLogic) {
                sb.append(queryLogic.getEsc());
            }
            ((ZKSqlMybatisConvert) convert).appendScriptQueryCondition(sb, tableAlias, this.getColumnName(),
                    this.getAttrName(), queryLogic, this.getQueryType(), this.getJavaClassz(), this.getFormats(),
                    this.isCaseSensitive());
        }
        else {
            log.error("[>_<:20210415-1059-001] 转换器不支持此类型的查询条件转换；查询条件类型：ZKDBQueryConditionCol; 转换器类型：{} ",
                    convert.getClass());
        }
    }

    /**
     * 
     *
     * @Title: as
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Apr 15, 2022 11:48:38 AM
     * @param queryType
     * @param columnName
     *            表 字段名；包含了表的别名；
     * @param attrName
     *            java 属性名; 这里会添加 #{}
     * @param javaClassz
     *            查询字段的 java 数据类型
     * @param formats
     *            日期格式化参数；不为 null 时，格式化数据库字段；不为 null 且属性 java 数据类型为 Date 时，同时也用他格式化参数；
     * @param isCaseSensitive
     *            是否区分大小，注，此参数仅在 javaClassz 为 String.class 类型下有效；true-区分大小；false-不区分大小写；
     * @return
     * @return ZKDBQueryConditionCol
     */
    public static ZKDBQueryConditionCol as(ZKDBQueryType queryType, String columnName, String attrName,
            Class<?> javaClassz, String[] formats, boolean isCaseSensitive) {
        return new ZKDBQueryConditionCol(queryType, columnName, attrName, javaClassz, formats, isCaseSensitive);
    }

    /**
     * 
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param queryType
     * @param columnName
     * @param attrName
     * @param javaClassz
     * @param formats
     * @param isCaseSensitive
     */
    public ZKDBQueryConditionCol(ZKDBQueryType queryType, String columnName, String attrName, Class<?> javaClassz,
            String[] formats, boolean isCaseSensitive) {
        this.columnName = columnName;
        this.attrName = attrName;
        this.javaClassz = javaClassz;
        this.isCaseSensitive = isCaseSensitive;
        this.queryType = queryType;
        this.formats = formats;
    }

    // 查询方式
    private ZKDBQueryType queryType;

    // 条件字段名
    private String columnName;

    // 条件属性名
    private String attrName;

    // 条件值 javaType
    private Class<?> javaClassz;

    // 格式化参数
    private String[] formats;

    // 是否区分大小写; 字段属性为 String/ZKJson 时，生效；true-区分大小写；false-不区分大小写；
    private boolean isCaseSensitive;

    /**
     * @return queryType sa
     */
    public ZKDBQueryType getQueryType() {
        return queryType;
    }

    /**
     * @return columnName sa
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @return attrName sa
     */
    public String getAttrName() {
        return attrName;
    }

    /**
     * @return javaClassz sa
     */
    public Class<?> getJavaClassz() {
        return javaClassz;
    }

    /**
     * @return formats sa
     */
    public String[] getFormats() {
        return formats;
    }

    /**
     * @return isCaseSensitive sa
     */
    public boolean isCaseSensitive() {
        return isCaseSensitive;
    }

}
