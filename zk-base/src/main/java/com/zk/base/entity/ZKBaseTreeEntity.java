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
* @Title: ZKBaseTreeEntity.java 
* @author Vinson 
* @Package com.zk.base.entity 
* @Description: TODO(simple description this file what to do. ) 
* @date Aug 20, 2020 11:29:05 AM 
* @version V1.0 
*/
package com.zk.base.entity;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zk.base.commons.ZKTreeSqlProvider;
import com.zk.db.annotation.ZKColumn;
import com.zk.db.annotation.ZKDBAnnotationProvider;
import com.zk.db.commons.ZKDBQueryCondition;
import com.zk.db.commons.ZKDBQueryConditionWhere;
import com.zk.db.commons.ZKDBQueryType;
import com.zk.db.commons.ZKSqlConvert;
import com.zk.db.mybatis.commons.ZKDBQueryConditionIf;
import com.zk.db.mybatis.commons.ZKDBQueryConditionIfByClass;
import com.zk.db.mybatis.commons.ZKDBQueryConditionSql;
import com.zk.db.mybatis.commons.ZKSqlProvider;

/** 
* @ClassName: ZKBaseTreeEntity 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
public abstract class ZKBaseTreeEntity<ID extends Serializable, E extends ZKBaseTreeEntity<ID, E>> extends ZKBaseEntity<ID, E> {

    @Transient
    @XmlTransient
    @JsonIgnore
    public ZKSqlProvider getSqlProvider() {
        return this.getTreeSqlProvider();
    }

    @Transient
    @XmlTransient
    @JsonIgnore
    public abstract ZKTreeSqlProvider getTreeSqlProvider();

    /**
     * @Fields serialVersionUID : TODO(simple description what to do.)
     */
    private static final long serialVersionUID = 1L;
    
    public ZKBaseTreeEntity() {
        super();
    }

    public ZKBaseTreeEntity(ID pkId) {
        super(pkId);
    }

    /**
     * ??????(??????)??????????????? id???????????????????????????????????? 
     */
    @ZKColumn(name = "c_parent_id", isUpdate = false, isQuery = true)
    protected ID parentId;

    @XmlTransient
    @Transient
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    protected E parent;

    /**
     * ?????????????????????
     * 
     * ??? parentId ???null??????????????? parentIdIsEmpty ??? true?????????????????? c_parent_id ??? null ?????????????????????; parentIdIsEmpty ??? false, c_parent_id ????????????????????????
     * 
     */
    @XmlTransient
    @Transient
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    protected boolean parentIdIsEmpty = false;

    /**
     * ?????????(?????????)?????????
     */
    @XmlTransient
    @Transient
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    protected List<E> children;

    @XmlTransient
    @Transient
    protected Integer depth;

    /**
     * @return parentIdIsEmpty sa
     */
    public boolean isParentIdIsEmpty() {
        return parentIdIsEmpty;
    }

    /**
     * @param parentIdIsEmpty
     *            the parentIdIsEmpty to set
     */
    public void setParentIdIsEmpty(boolean parentIdIsEmpty) {
        this.parentIdIsEmpty = parentIdIsEmpty;
    }

    /**
     * @return parentId sa
     */
    public ID getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     *            the parentId to set
     */
    public void setParentId(ID parentId) {
        this.parentId = parentId;
    }

    /**
     * @return parent sa
     */
    public E getParent() {
        return parent;
    }

    /**
     * @param parent
     *            the parent to set
     */
    public void setParent(E parent) {
        this.parent = parent;
    }

    /**
     * @return children sa
     */
    public List<E> getChildren() {
        return children;
    }

    /**
     * @param children
     *            the children to set
     */
    public void setChildren(List<E> children) {
        this.children = children;
    }

    /**
     * @return depth sa
     */
    public Integer getDepth() {
        return depth;
    }

    /**
     * @param depth
     *            the depth to set
     */
    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    /**
     * ???????????????????????????????????? null ?????????????????????????????????
     *
     * @Title: isLeaf
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Apr 18, 2022 11:40:24 PM
     * @return boolean
     */
    public boolean getIsLeaf() {
        return this.getChildren() == null || this.getChildren().isEmpty();
    }

    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

    @Transient
    @JsonIgnore
    @XmlTransient
    public ZKDBQueryConditionWhere getZKDbWhere(ZKSqlConvert sqlConvert, ZKDBAnnotationProvider annotationProvider) {
        ZKDBQueryConditionWhere where = super.getZKDbWhere(sqlConvert, annotationProvider);
        // ?????? c_parent_id ??? null ???????????? ??????????????????????????????
        ZKDBQueryCondition parentCondition = this.getParentCondition(this.getPkIDClass());
        // ??? parentId ???null?????????, ??? parentIdIsEmpty ??? true?????????????????? c_parent_id ??? null ?????????????????????;
        // ??????????????????????????????????????????; 1.????????????????????????; 2.????????????????????????????????????
        where.put(ZKDBQueryConditionIfByClass.as(
                ZKDBQueryConditionIf.asAnd(parentCondition, "parentIdIsEmpty", false, true), "parentId", String.class,
                true));
        return where;
    }

    // ??? parentId ???null?????????, ??? parentIdIsEmpty ??? true?????????????????? c_parent_id ??? null ?????????????????????
    //
    @JsonIgnore
    @XmlTransient
    @Transient
    private ZKDBQueryCondition getParentCondition(Class<?> idClass) {
        if (idClass == String.class) {
//          return ZKDBQueryConditionWhere.asOr("(", ")",
//                  ZKDBQueryConditionCol.as(ZKDBQueryType.EQ, "c_parent_id", "parentId", idClass, null, true),
//                  ZKDBQueryConditionCol.as(ZKDBQueryType.ISNULL, "c_parent_id", "parentId", idClass, null, true));
            return ZKDBQueryConditionWhere.asOr("(", ")",
                    ZKDBQueryConditionSql.as(ZKDBQueryType.ISNULL, "c_parent_id", ""),
                    ZKDBQueryConditionSql.as(ZKDBQueryType.EQ, "c_parent_id", "\"\""));
        }
        else if (idClass == Long.class || idClass == Integer.class) {
//            return ZKDBQueryConditionWhere.asOr("(", ")",
//                    ZKDBQueryConditionCol.as(ZKDBQueryType.EQ, "c_parent_id", "parentId", idClass, null, true),
//                    ZKDBQueryConditionCol.as(ZKDBQueryType.ISNULL, "c_parent_id", "parentId", idClass, null, true));
            return ZKDBQueryConditionWhere.asOr("(", ")",
                    ZKDBQueryConditionSql.as(ZKDBQueryType.ISNULL, "c_parent_id", ""),
                    ZKDBQueryConditionSql.as(ZKDBQueryType.EQ, "c_parent_id", "0"));
        }
        else {
//            return ZKDBQueryConditionCol.as(ZKDBQueryType.ISNULL, "c_parent_id", "parentId", idClass, null, true);
            return ZKDBQueryConditionSql.as(ZKDBQueryType.ISNULL, "c_parent_id", "");
        }
    }

    // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
    @JsonIgnore
    @XmlTransient
    @Transient
    public ZKDBQueryConditionWhere getZKDbWhereTreeNoLevel(ZKSqlConvert sqlConvert,
            ZKDBAnnotationProvider annotationProvider) {
        
        StringBuffer sb = new StringBuffer();
        
        ZKDBQueryConditionWhere where = ZKDBQueryConditionWhere.asOr();
        // ?????? c_parent_id ??? null ???????????? ??????????????????????????????
        ZKDBQueryCondition parentCondition = this.getParentCondition(this.getPkIDClass());
        where.put(ZKDBQueryConditionIfByClass.as(parentCondition, "parentId", String.class, true));

        String childStr = sqlConvert.convertSqlWhere(super.getZKDbWhere(sqlConvert, annotationProvider), "_t");
        where.put(ZKDBQueryConditionSql.as(ZKDBQueryType.NIN, "c_parent_id",
                sb.append("(SELECT _t.c_pk_id FROM ").append(annotationProvider.getTable().name()).append(" _t ")
                        .append(childStr).append(")").toString()));
        return where;
    }

}
