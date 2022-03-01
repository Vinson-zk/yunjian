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
 * @Title: ZKOrder.java 
 * @author Vinson 
 * @Package com.zk.core.commons 
 * @Description: TODO(simple description this file what to do.) 
 * @date Dec 18, 2019 6:16:38 PM 
 * @version V1.0   
*/
package com.zk.core.commons.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.core.utils.ZKStringUtils;

/** 
* @ClassName: ZKOrder 
* @Description: TODO(simple description this class what to do.) 
* @author Vinson 
* @version 1.0 
*/
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZKOrder implements Serializable {

    /**
     * @Fields serialVersionUID : TODO(描述变量)
     */
    private static final long serialVersionUID = 3705768353395390489L;

    /**
     * sql 的字段名中是否包含非法字符；字段名由下划线，大小写字母、数字组成；只能以下划线或字母开头。
     */
    private static final Pattern isColumnNamePattern = Pattern.compile("^([A-Za-z_][A-Za-z_0-9.]*)$");

    /**
     * 表中字段名
     */
    private String columnName;

    private ZKSortMode sortMode;

    public ZKOrder() {
    }

    public ZKOrder(String columnName, ZKSortMode sortMode) {
        if(!isColumnNamePattern.matcher(columnName).matches()){
            throw new RuntimeException(
                    "ColumnName contains illegal characters.ColumnName is underlined, uppercase and lowercase letters and point, and Numbers; You can only start with an underscore or a letter. ");
        }
        this.columnName = columnName;
        this.sortMode = sortMode;
    }

    public String getColumnName() {
        // 需要判断是否有非法字符
        return columnName;
    }

    public ZKSortMode getSortMode() {
        return sortMode;
    }

    @JsonIgnore
    public String getValue() {
        return sortMode.getValue();
    }

    @Override
    public String toString() {
        return this.toString("");
    }

    /**
     * 
     *
     * @Title: toString
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Sep 17, 2020 7:07:36 PM
     * @param tableAlias
     *            表的别名；这里需要加上 “.”；不会自动添加
     * @return
     * @return String
     */
    public String toString(String tableAlias) {
        return tableAlias + columnName + " " + sortMode.getValue();
    }

    /****************************************************************/
    public static interface Param_Name {
        /**
         * 排序字段
         */
        public static final String column = "page.sort.col";

        /**
         * 排序方式
         */
        public static final String mode = "page.sort.mode";
    }

    /**
     * 生成一个排序对象；默认为 DESC 升序；
     * 
     * @param columnName
     * @param sort
     * @return
     * @throws CoreException
     */
    public static ZKOrder asOrder(String columnName, String sort) {
        ZKSortMode st = ZKSortMode.parseKey(sort);
        return new ZKOrder(columnName, ((st == null) ? ZKSortMode.DESC : st));
    }

    public static ZKOrder asOrder(String columnName, ZKSortMode sort) {
        return new ZKOrder(columnName, ((sort == null) ? ZKSortMode.DESC : sort));
    }

    public static List<ZKOrder> asOrder(HttpServletRequest hReq) {

        String[] cols = ServletRequestUtils.getStringParameters(hReq, Param_Name.column);
        String[] modes = ServletRequestUtils.getStringParameters(hReq, Param_Name.mode);

        if (cols.length > 0) {
            List<ZKOrder> orders = new ArrayList<ZKOrder>();
            for (int i = 0; i < cols.length; ++i) {
                if (i < modes.length) {
                    orders.add(asOrder(cols[i], modes[i]));
                }
                else {
                    // 如果未指定排序模式，默认按升序处理；排序模式与字段按顺序对应
                    orders.add(asOrder(cols[i], ZKSortMode.ASC));
                }
            }
            return orders;
        }
        return null;
    }

    /**
     * 转换为排序的 sql 语句；这里的转换没有根据字段名映射到数据库字段；需要直接填写数据库表的字段名；
     *
     * @Title: toOrderBySql
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Sep 17, 2020 7:09:54 PM
     * @param tableAlias
     *            表的别名；这里需要加上 “.”；不会自动添加
     * @param orders
     * @return
     * @return String
     */
    public static String toOrderBySql(String tableAlias, Collection<ZKOrder> orders) {
        String str = "";
        for (ZKOrder order : orders) {
            str += "," + order.toString(tableAlias);
        }
        return ZKStringUtils.isEmpty(str) ? null : str.substring(1);
    }

    /**
     * 转换为排序的 sql 语句；这里的转换没有根据字段名映射到数据库字段；需要直接填写数据库表的字段名；
     * 
     * @return
     */
    public static String toOrderBySql(Collection<ZKOrder> orders) {
        return toOrderBySql("", orders);
    }

}
