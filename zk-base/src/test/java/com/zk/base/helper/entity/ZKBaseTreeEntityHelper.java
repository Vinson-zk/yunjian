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
* @Title: ZKBaseTreeEntityHelper.java 
* @author Vinson 
* @Package com.zk.base.helper.entity 
* @Description: TODO(simple description this file what to do. ) 
* @date Apr 18, 2022 5:07:17 PM 
* @version V1.0 
*/
package com.zk.base.helper.entity;

import com.zk.base.commons.ZKTreeSqlProvider;
import com.zk.base.entity.ZKBaseTreeEntity;
import com.zk.db.annotation.ZKColumn;
import com.zk.db.annotation.ZKTable;
import com.zk.db.commons.ZKSqlConvertDelegating;

/** 
* @ClassName: ZKBaseTreeEntityHelper 
* @Description: TODO(simple description this class what to do. ) 
* @author Vinson 
* @version 1.0 
*/
@ZKTable(name = "t_test", alias = "a")
public class ZKBaseTreeEntityHelper extends ZKBaseTreeEntity<String, ZKBaseTreeEntityHelper> {

    /**
     * @Fields serialVersionUID : TODO(simple description what to do.)
     */
    private static final long serialVersionUID = 1L;

    /**
     * (not Javadoc)
     * <p>
     * Title: getTreeSqlProvider
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @return
     * @see com.zk.base.entity.ZKBaseTreeEntity#getTreeSqlProvider()
     */
    @Override
    public ZKTreeSqlProvider getTreeSqlProvider() {
        // TODO Auto-generated method stub return null;
        return initSqlProvider();
    }

    static ZKTreeSqlProvider sqlProvider;

    public static ZKTreeSqlProvider initSqlProvider() {
        if (sqlProvider == null) {
            sqlProvider = new ZKTreeSqlProvider(new ZKSqlConvertDelegating(), new ZKBaseTreeEntityHelper());
        }
        return sqlProvider;
    }

    @ZKColumn(name = "zk_col", isQuery = true)
    private String str;

}
