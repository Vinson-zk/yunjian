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
 * @Title: ZKUpdateTest.java 
 * @author Vinson 
 * @Package com.zk.mongo.command.queryAndWriteOperation 
 * @Description: TODO(simple description this file what to do.) 
 * @date Dec 18, 2019 6:14:28 PM 
 * @version V1.0   
*/
package com.zk.mongo.command.queryAndWriteOperation;

import java.util.Date;

import org.bson.Document;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.zk.mongo.ZKMongoTemplateTest;
import com.zk.mongo.command.administration.ZKCreate;
import com.zk.mongo.command.administration.ZKCreateIndexes;
import com.zk.mongo.command.administration.ZKDrop;
import com.zk.mongo.element.ZKIndexElement;
import com.zk.mongo.element.ZKUpdateElement;
import com.zk.mongo.helper.ZKMongoTestConfig;
import com.zk.mongo.operator.ZKQueryOpt;
import com.zk.mongo.operator.ZKUpdateOpt;
import com.zk.mongo.utils.ZKMongoUtils;

import junit.framework.TestCase;

/** 
* @ClassName: ZKUpdateTest 
* @Description: TODO(simple description this class what to do.) 
* @author Vinson 
* @version 1.0 
*/
public class ZKUpdateTest {

    public static FileSystemXmlApplicationContext ctx;

    public static MongoTemplate mongoTemplate;

    static {
        try {
            ctx = ZKMongoTemplateTest.ctx;
            TestCase.assertNotNull(ctx);

            mongoTemplate = ctx.getBean(MongoTemplate.class);
            TestCase.assertNotNull(mongoTemplate);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCommandZKUpdate() {

        String id1 = "ZKUpdate_id_1";
        String id2 = "ZKUpdate_id_2";
        String id3 = "ZKUpdate_id_3";

        String attrName1 = "a1";
        String attrValue1 = "v1";
        String attrName2 = "a2";
        String attrValue2 = "v2";
        String attrName3 = "a3";
        int attrValue3 = 3;

        try {
            // ??????????????????
            if (ZKMongoUtils.isExist(mongoTemplate, ZKMongoTestConfig.colleationName_QueryAndWriteOperation)) {
                ZKDrop drop = new ZKDrop(ZKMongoTestConfig.colleationName_QueryAndWriteOperation);
                mongoTemplate.executeCommand(drop);
            }
            ZKCreate create = new ZKCreate(ZKMongoTestConfig.colleationName_QueryAndWriteOperation);
            mongoTemplate.executeCommand(create);
            // ??????????????????
            ZKIndexElement expireIndex = ZKCreateIndexes.IndexElement(ZKMongoTestConfig.indexExpireTimeName,
                    ZKMongoTestConfig.attrExpireTimeName);
            expireIndex.setExpireAfterSeconds(0);
            ZKCreateIndexes createIndexes = new ZKCreateIndexes(ZKMongoTestConfig.colleationName_QueryAndWriteOperation,
                    expireIndex);
            mongoTemplate.executeCommand(createIndexes);

            /*** ZKUpdate ??????????????? ***/
            // ????????????
            ZKUpdate update = new ZKUpdate(ZKMongoTestConfig.colleationName_QueryAndWriteOperation);
            ZKUpdateElement updateElement = new ZKUpdateElement();
            // ??????????????????
            updateElement.setUpsert(true);
            // ????????????????????????????????? true?????????????????????????????????false???????????? false
            updateElement.setMulti(true);
            ZKUpdateOpt updateOpt = new ZKUpdateOpt();
            updateOpt.set(ZKMongoTestConfig.attrExpireTimeName, new Date(System.currentTimeMillis() + 30000));
            updateOpt.set(attrName1, id1 + attrValue1);
            updateOpt.setOnInsert(attrName2, id1 + attrValue2);
            updateOpt.inc(attrName3, attrValue3);
            updateElement.setUpdate(updateOpt);
            ZKQueryOpt query = ZKQueryOpt.where(ZKMongoUtils.autoIndexIdName).eq(id1);
            updateElement.setQuery(query);
            updateElement.setQuery(query);
            update.addUpdates(updateElement);

            updateElement = new ZKUpdateElement();
            // ??????????????????
            updateElement.setUpsert(true);
            updateElement.setMulti(true);
            updateOpt = new ZKUpdateOpt();
            updateOpt.set(ZKMongoTestConfig.attrExpireTimeName, new Date(System.currentTimeMillis() + 30000));
            updateOpt.set(attrName1, id2 + attrValue1);
            updateOpt.setOnInsert(attrName2, id2 + attrValue2);
            updateOpt.inc(attrName3, attrValue3);
            updateElement.setUpdate(updateOpt);
            query = ZKQueryOpt.where(ZKMongoUtils.autoIndexIdName).eq(id2);
            updateElement.setQuery(query);
            updateElement.setQuery(query);
            update.addUpdates(updateElement);

            updateElement = new ZKUpdateElement();
            // ?????????????????????
            updateElement.setUpsert(false);
            updateElement.setMulti(true);
            updateOpt = new ZKUpdateOpt();
            updateOpt.set(ZKMongoTestConfig.attrExpireTimeName, new Date(System.currentTimeMillis() + 30000));
            updateOpt.set(attrName1, id3 + attrValue1);
            updateOpt.setOnInsert(attrName2, id3 + attrValue2);
            updateOpt.inc(attrName3, attrValue3);
            updateElement.setUpdate(updateOpt);
            query = ZKQueryOpt.where(ZKMongoUtils.autoIndexIdName).eq(id3);
            updateElement.setQuery(query);
            updateElement.setQuery(query);
            update.addUpdates(updateElement);

            Document resDoc = mongoTemplate.executeCommand(update);
            TestCase.assertEquals(1, resDoc.getDouble("ok").intValue());
            TestCase.assertEquals(2, resDoc.getInteger("n").intValue());

            // ??????????????????
            Document documentCommand = new Document();
            documentCommand.put("find", ZKMongoTestConfig.colleationName_QueryAndWriteOperation);
            // ??????????????????
            Document filterDoc = new Document();
            Document compareDoc = new Document();
            compareDoc.put("$eq", id1);
            filterDoc.put(ZKMongoUtils.autoIndexIdName, compareDoc);
            documentCommand.put("filter", filterDoc);
            resDoc = mongoTemplate.executeCommand(documentCommand);
            TestCase.assertEquals(1, resDoc.getDouble("ok").intValue());
            resDoc = ZKMongoUtils.getOneResult(resDoc);
            TestCase.assertEquals(attrValue3, resDoc.getInteger(attrName3).intValue());
            TestCase.assertEquals(id1, resDoc.getString(ZKMongoUtils.autoIndexIdName));
            // id3 ???????????????????????????????????? id3???????????????
            documentCommand = new Document();
            documentCommand.put("find", ZKMongoTestConfig.colleationName_QueryAndWriteOperation);
            // ??????????????????
            filterDoc = new Document();
            compareDoc = new Document();
            compareDoc.put("$eq", id3);
            filterDoc.put(ZKMongoUtils.autoIndexIdName, compareDoc);
            documentCommand.put("filter", filterDoc);
            resDoc = mongoTemplate.executeCommand(documentCommand);
            TestCase.assertEquals(1, resDoc.getDouble("ok").intValue());
            resDoc = ZKMongoUtils.getOneResult(resDoc);
            TestCase.assertNull(resDoc);

            /*** ZKUpdate ???????????? ***/
            update = new ZKUpdate(ZKMongoTestConfig.colleationName_QueryAndWriteOperation);
            updateElement = new ZKUpdateElement();
            // ??????????????????
            updateElement.setUpsert(false);
            updateElement.setMulti(true);
            updateOpt = new ZKUpdateOpt();
            updateOpt.set(attrName1, attrValue1);
            updateOpt.setOnInsert(attrName2, attrValue2);
            updateOpt.inc(attrName3, attrValue3);
            updateElement.setUpdate(updateOpt);
            query = ZKQueryOpt.where(ZKMongoUtils.autoIndexIdName).eq(id2);
            updateElement.setQuery(query);
            updateElement.setQuery(query);
            update.addUpdates(updateElement);
            resDoc = mongoTemplate.executeCommand(update);
            TestCase.assertEquals(1, resDoc.getDouble("ok").intValue());
            TestCase.assertEquals(1, resDoc.getInteger("n").intValue());
            // ??????????????????
            documentCommand = new Document();
            documentCommand.put("find", ZKMongoTestConfig.colleationName_QueryAndWriteOperation);
            // ??????????????????
            filterDoc = new Document();
            compareDoc = new Document();
            compareDoc.put("$eq", id2);
            filterDoc.put(ZKMongoUtils.autoIndexIdName, compareDoc);
            documentCommand.put("filter", filterDoc);
            resDoc = mongoTemplate.executeCommand(documentCommand);
            TestCase.assertEquals(1, resDoc.getDouble("ok").intValue());
            resDoc = ZKMongoUtils.getOneResult(resDoc);
            TestCase.assertEquals(attrValue3 + attrValue3, resDoc.getInteger(attrName3).intValue());
            TestCase.assertEquals(id2, resDoc.getString(ZKMongoUtils.autoIndexIdName));
            TestCase.assertEquals(attrValue1, resDoc.getString(attrName1));
            TestCase.assertEquals(id2 + attrValue2, resDoc.getString(attrName2));

            /*** ZKUpdate ???????????? ???????????? ***/
            update = new ZKUpdate(ZKMongoTestConfig.colleationName_QueryAndWriteOperation);
            updateElement = new ZKUpdateElement();
            // ??????????????????
            updateElement.setUpsert(false);
            updateElement.setMulti(true);
            updateOpt = new ZKUpdateOpt();
            updateOpt.unset(attrName2, attrName3);
            updateElement.setUpdate(updateOpt);
            query = ZKQueryOpt.where(ZKMongoUtils.autoIndexIdName).eq(id2);
            updateElement.setQuery(query);
            updateElement.setQuery(query);
            update.addUpdates(updateElement);
            resDoc = mongoTemplate.executeCommand(update);
            TestCase.assertEquals(1, resDoc.getDouble("ok").intValue());
            TestCase.assertEquals(1, resDoc.getInteger("n").intValue());
            // ??????????????????
            documentCommand = new Document();
            documentCommand.put("find", ZKMongoTestConfig.colleationName_QueryAndWriteOperation);
            // ??????????????????
            filterDoc = new Document();
            compareDoc = new Document();
            compareDoc.put("$eq", id2);
            filterDoc.put(ZKMongoUtils.autoIndexIdName, compareDoc);
            documentCommand.put("filter", filterDoc);
            resDoc = mongoTemplate.executeCommand(documentCommand);
            TestCase.assertEquals(1, resDoc.getDouble("ok").intValue());
            resDoc = ZKMongoUtils.getOneResult(resDoc);
            TestCase.assertEquals(null, resDoc.getInteger(attrName3));
            TestCase.assertEquals(id2, resDoc.getString(ZKMongoUtils.autoIndexIdName));
            TestCase.assertEquals(attrValue1, resDoc.getString(attrName1));
            TestCase.assertEquals(null, resDoc.getString(attrName2));

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

}
