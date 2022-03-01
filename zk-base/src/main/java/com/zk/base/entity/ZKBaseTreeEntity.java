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
import com.zk.db.commons.ZKDBQueryConditionSql;
import com.zk.db.commons.ZKDBQueryConditionWhere;
import com.zk.db.commons.ZKDBQueryType;
import com.zk.db.commons.ZKSqlConvert;
import com.zk.db.commons.ZKSqlProvider;
import com.zk.db.mybatis.commons.ZKDBQueryConditionIf;

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
     * 菜单(路由)的上级结点 id，制作树形结构关键属性； 
     */
    @ZKColumn(name = "c_parent_id", isUpdate = true, isQuery = true)
    protected ID parentId;

    @XmlTransient
    @Transient
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    protected E parent;

    /**
     * 子菜单(子路由)数组；
     */
    @XmlTransient
    @Transient
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    protected List<E> children;

    @XmlTransient
    @Transient
    protected Integer depth;

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

    // 递归查询树形节点; 1.不支持子节点过虑; 2.仅支持根节点过滤与分页；
    @JsonIgnore
    @XmlTransient
    @Transient
    public ZKDBQueryConditionWhere getZKDbWhereTree(ZKSqlConvert sqlConvert,
            ZKDBAnnotationProvider annotationProvider) {
        ZKDBQueryConditionWhere where = super.getZKDbWhere(sqlConvert, annotationProvider);
        this.putWhereParent(where, this.getPkIDClass());
        return where;
    }

    @JsonIgnore
    @XmlTransient
    @Transient
    private void putWhereParent(ZKDBQueryConditionWhere where, Class<?> idClass) {
        if (idClass == String.class) {
//            ZKDBQueryConditionWhere pWhere = ZKDBQueryConditionWhere.asOr("(", ")",
//                    ZKDBQueryConditionCol.as(ZKDBQueryType.EQ, "c_parent_id", "parentId", idClass, null, true),
//                    ZKDBQueryConditionCol.as(ZKDBQueryType.ISNULL, "c_parent_id", "parentId", idClass, null, true));
//            where.put(ZKDBQueryConditionIf.as(pWhere, "parentId", String.class, true));

            ZKDBQueryConditionWhere pWhere = ZKDBQueryConditionWhere.asOr("(", ")",
                    ZKDBQueryConditionSql.as(ZKDBQueryType.ISNULL, "c_parent_id", ""),
                    ZKDBQueryConditionSql.as(ZKDBQueryType.EQ, "c_parent_id", "\"\""));
            where.put(ZKDBQueryConditionIf.as(pWhere, "parentId", String.class, true));
        }
        else if (idClass == Long.class || idClass == Integer.class) {
//            ZKDBQueryConditionWhere pWhere = ZKDBQueryConditionWhere.asOr("(", ")",
//                    ZKDBQueryConditionCol.as(ZKDBQueryType.EQ, "c_parent_id", "parentId", idClass, null, true),
//                    ZKDBQueryConditionCol.as(ZKDBQueryType.ISNULL, "c_parent_id", "parentId", idClass, null, true));
//            where.put(ZKDBQueryConditionIf.as(pWhere, "parentId", idClass, true));

            ZKDBQueryConditionWhere pWhere = ZKDBQueryConditionWhere.asOr("(", ")",
                    ZKDBQueryConditionSql.as(ZKDBQueryType.ISNULL, "c_parent_id", ""),
                    ZKDBQueryConditionSql.as(ZKDBQueryType.EQ, "c_parent_id", "0"));
            where.put(ZKDBQueryConditionIf.as(pWhere, "parentId", String.class, true));
        }
        else {
//            where.put(ZKDBQueryConditionCol.as(ZKDBQueryType.ISNULL, "c_parent_id", "parentId", idClass, null, true));
            where.put(ZKDBQueryConditionIf.as(ZKDBQueryConditionSql.as(ZKDBQueryType.ISNULL, "c_parent_id", ""),
                    "parentId", String.class, true));
        }
    }

    // 不分层级按条件过滤，过滤结果中不是根结点时，如果父节点不在过滤结果中，升级为结果中的根节点；
    @JsonIgnore
    @XmlTransient
    @Transient
    public ZKDBQueryConditionWhere getZKDbWhereTreeNoLevel(ZKSqlConvert sqlConvert,
            ZKDBAnnotationProvider annotationProvider) {
        
        StringBuffer sb = new StringBuffer();
        
        ZKDBQueryConditionWhere where = ZKDBQueryConditionWhere.asOr();
        this.putWhereParent(where, this.getPkIDClass());

        String childStr = sqlConvert.convertSqlWhere(super.getZKDbWhere(sqlConvert, annotationProvider), "_t");
        where.put(ZKDBQueryConditionSql.as(ZKDBQueryType.NIN, "c_parent_id",
                sb.append("(SELECT _t.c_pk_id FROM ").append(annotationProvider.getTable().name()).append(" _t ")
                        .append(childStr).append(")").toString()));
        return where;
    }

//    /********************************************************/
//    /*** 供 dao 使用的 sql 片段 ***/
//    /********************************************************/
//
//    // 不分层级按条件过滤，过滤结果中不是根结点时，如果父节点不在过滤结果中，升级为结果中的根节点；
//    @JsonIgnore
//    @XmlTransient
//    @Transient
//    public String getSqlBlockWhereTreeNoLevel() {
//        return this.getTreeSqlProvider().getSqlBlockWhereTreeNoLevel();
//    }
//
//    // 递归查询树形节点; 1.不支持子节点过虑; 2.仅支持根节点过滤与分页；
//    @JsonIgnore
//    @XmlTransient
//    @Transient
//    public String getSqlBlockWhereTree() {
//        return this.getTreeSqlProvider().getSqlBlockWhereTree();
//    }

}
