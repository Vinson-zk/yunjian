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
* @Title: ZKColumn.java 
* @author Vinson 
* @Package com.zk.db.annotation 
* @Description: TODO(simple description this file what to do. ) 
* @date Sep 10, 2020 11:29:41 AM 
* @version V1.0 
*/
package com.zk.db.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zk.db.commons.ZKDBQueryType;

/** 
* @ClassName: ZKColumn 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Documented
public @interface ZKColumn {

    /**
     * 表字段名；name 为空时，不处理；这样处理是为了方便删除父类中的注解；
     */
    String name();

    /**
     * 字段说明；默认为 空
     *
     * @Title: comment
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Sep 15, 2020 11:38:46 AM
     * @return String
     */
    String comment() default "";

    /**
     * 是否是主键；可以是复合主键，但仅支持解析一组主键；
     * 
     * 默认为 false
     *
     * @Title: isPk
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Sep 11, 2020 2:28:51 PM
     * @return boolean
     */
    boolean isPk() default false;

    /**
     * 字段是否插入；
     * 
     * 默认为 true
     *
     * @Title: isInsert
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Sep 15, 2020 11:38:41 AM
     * @return
     * @return boolean
     */
    boolean isInsert() default true;

    /**
     * 字段是否修改；
     * 
     * 默认为 false
     *
     * @Title: isUpdate
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Sep 15, 2020 11:38:38 AM
     * @return
     * @return boolean
     */
    boolean isUpdate() default false;

    /**
     * 字段是否做为结果映射，true-做为结果映射；false-不做为结果映射；不做为结果集时，可以做查询条件；
     * 
     * 默认为 true
     *
     * @Title: isResult
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Sep 15, 2020 11:38:34 AM
     * @return
     * @return boolean
     */
    boolean isResult() default true;

    /**
     * 实体属性的 java 类型；
     * 
     * 默认 String.class
     *
     * @Title: javaType
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Sep 15, 2020 11:38:32 AM
     * @return
     * @return Class<?>
     */
    Class<?> javaType() default String.class;

    /**
     * 查询方式；注意 使用 IN 或 NIN 时，只能使用集合，此时 javaType 填写集合中对象实体的类型
     * 
     * 默认为 ZKDBQueryType.EQ
     *
     * @Title: queryType
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Sep 15, 2020 11:38:29 AM
     * @return
     * @return ZKDBQueryType
     */
    ZKDBQueryType queryType() default ZKDBQueryType.EQ;

    /**
     * 是否为查询条件；
     * 
     * 默认为 false
     *
     * @Title: isQuery
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Sep 15, 2020 11:38:26 AM
     * @return
     * @return boolean
     */
    boolean isQuery() default false;

    /**
     * 是否强制做为查询条件；true-是；false-不是；false 时，当参数值为 null 时，不会做为查询条件；
     * 
     * 默认为 false
     *
     * @Title: isForce
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Sep 15, 2020 11:38:22 AM
     * @return
     * @return boolean
     */
    boolean isForce() default false;

    /**
     * 是否区分大小写; 字段属性为 String/ZKJson 时，生效；true-区分大小写；false-不区分大小写；
     * 
     * 默认为 true，区分大小写
     *
     * @Title: isCaseSensitive
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Sep 11, 2020 11:40:32 AM
     * @return
     * @return boolean
     */
    boolean isCaseSensitive() default true;
    
    /**
     * 格式化字符串，比如日期格式化; 第一位为数据库的格式，第二位为 java 中转换的格式; 注：第二位在 script sql 生成中未使用；
     * 
     * mysql 格式化字符说明：%Y-%m-%d %H:%i:%s
     * 
     * %Y：代表4位的年份； %y：代表2为的年份；
     * 
     * %m：代表月, 格式为(01……12)； %c：代表月, 格式为(1……12)；
     * 
     * %d：代表月份中的天数,格式为(00……31)； %e：代表月份中的天数, 格式为(0……31)；
     * 
     * %H：代表小时,格式为(00……23)；%k： 代表 小时,格式为(0……23)； %h： 代表小时,格式为(01……12)；
     * 
     * %I： 代表小时,格式为(01……12)； %l ：代表小时,格式为(1……12)；
     * 
     * %i： 代表分钟, 格式为(00……59)；
     * 
     * %r：代表 时间,格式为12 小时(hh:mm:ss [AP]M)； %T：代表 时间,格式为24 小时(hh:mm:ss)；
     * 
     * %S：代表 秒,格式为(00……59)； %s：代表 秒,格式为(00……59)；
     * 
     */
    String[] formats() default {};

}
