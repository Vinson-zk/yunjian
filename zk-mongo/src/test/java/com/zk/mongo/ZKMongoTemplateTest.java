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
 * @Title: ZKMongoTemplateTest.java 
 * @author Vinson 
 * @Package com.zk.mongo 
 * @Description: TODO(simple description this file what to do.) 
 * @date Dec 18, 2019 6:09:27 PM 
 * @version V1.0   
*/
package com.zk.mongo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.alibaba.fastjson.JSON;
import com.zk.core.utils.ZKObjectUtils;
import com.zk.mongo.helper.ZKMongoTestConfig;
import com.zk.mongo.helper.ZKObjectToSave;
import com.zk.mongo.helper.ZKTestObject;
import com.zk.mongo.utils.ZKMongoUtils;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import junit.framework.TestCase;

/** 
* @ClassName: ZKMongoTemplateTest 
* @Description: TODO(simple description this class what to do.) 
* @author Vinson 
* @version 1.0 
*/
public class ZKMongoTemplateTest {

    public static final String config_path = "classpath:mongo/test_spring_context_mongo_2.0.xml";

    public static FileSystemXmlApplicationContext ctx;

    public static MongoTemplate mongoTemplate;

    static {
        try {
            ctx = ZKMongoTestConfig.getCtx();
            TestCase.assertNotNull(ctx);
            mongoTemplate = ctx.getBean(MongoTemplate.class);
            TestCase.assertNotNull(mongoTemplate);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static final String collectionAttributeName_id = "key";

    /**
     * ????????????
     */
    @Test
    public void testFind() {
        String idPrefix = "test_query_";
        int count = 39;
        List<String> ids = new ArrayList<>();
        String id = null;
        try {
            Query query = null;

            ZKObjectToSave ots = null;
            for (int i = 1; i <= count; ++i) {
                ots = new ZKObjectToSave();
                ids.add(idPrefix + i);
                ots.setKey(idPrefix + i);
                ots.setValue("?????? ???????????? mongoTemplate " + i);
                ots.setInt(i);
                mongoTemplate.save(ots);
            }
            query = Query.query(Criteria.where(collectionAttributeName_id).in(ids));
            List<ZKObjectToSave> otsList = mongoTemplate.find(query, ZKObjectToSave.class);
            TestCase.assertEquals(count, otsList.size());

            // ????????????
            id = idPrefix + 101;
            query = Query.query(Criteria.where(collectionAttributeName_id).is(id));
            ots = null;
            ots = mongoTemplate.findOne(query, ZKObjectToSave.class);
            TestCase.assertNull(ots);

            id = idPrefix + 10;
            query = Query.query(Criteria.where(collectionAttributeName_id).is(id));
            ots = null;
            ots = mongoTemplate.findOne(query, ZKObjectToSave.class);
            TestCase.assertNotNull(ots);

            // ????????????,?????????

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        } finally {
            Query query = Query.query(Criteria.where(collectionAttributeName_id).in(ids));
            mongoTemplate.remove(query, ZKObjectToSave.class);
        }
    }

    /**
     * ???????????????????????????remove
     */
    @Test
    public void testRremove() {
        String id = "test_remove_01";
        try {
            Query query = null;
            ZKObjectToSave ots = new ZKObjectToSave();
            ots.setKey(id);
            ots.setValue("?????? ???????????? mongoTemplate");
            mongoTemplate.save(ots);

            query = Query.query(Criteria.where(collectionAttributeName_id).is(id));
            ots = null;
            ots = mongoTemplate.findOne(query, ZKObjectToSave.class);
            TestCase.assertNotNull(ots);

            DeleteResult dRes = mongoTemplate.remove(ots);
            if (dRes.wasAcknowledged()) {
                System.out.println("[^_^:20180519-1809-001] ????????????");
            }
            else {
                System.out.println("[^_^:20180519-1809-002] ??????????????????");
                TestCase.assertTrue(false);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

    /**
     * ??????
     */
    @Test
    public void testInsert() {
        String id = "test_insert_01";
        String value = "?????? mongoTemplate ??????";
        try {
            Query query = null;

            ZKObjectToSave ots = new ZKObjectToSave();
            ots.setKey(id);
            ots.setValue(value);

            DeleteResult dRes = mongoTemplate.remove(ots);
            if (dRes.wasAcknowledged()) {
                System.out.println("[^_^:20180519-1755-001] ????????????");
            }
            else {
                System.out.println("[^_^:20180519-1755-002] ??????????????????");
            }

            // ????????????????????? insert
            mongoTemplate.insert(ots);
            query = Query.query(Criteria.where(collectionAttributeName_id).is(id));
            ots = null;
            ots = mongoTemplate.findOne(query, ZKObjectToSave.class);
            TestCase.assertNotNull(ots);
            TestCase.assertEquals(value, ots.getValue());

            // ?????????????????? insert????????????
            try {
                mongoTemplate.insert(ots);
                TestCase.assertTrue(false);
            }
            catch(Exception e) {

                if (e instanceof DuplicateKeyException) {
                    DuplicateKeyException dke = (DuplicateKeyException) e;
                    TestCase.assertEquals("E11000 d", dke.getMessage().substring(0, 8));
                }
                else {
                    e.printStackTrace();
                    TestCase.assertTrue(false);
                }
            }

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
//      }finally {
//          ZKObjectToSave ots = new ZKObjectToSave();
//          ots.setKey(id);
//          mongoTemplate.remove(ots);
        }
    }

    /**
     * ??????
     */
    @Test
    public void testSave() {
        String id = "test_save_01";
        String value = "?????? mongoTemplate ??????";
        try {
            Query query = null;
            Update update = null;
            UpdateResult ur = null;

            String jsonCommand;
            Document doc;
            String t;

            ZKObjectToSave ots = new ZKObjectToSave();
            ots.setKey(id);
            ots.setValue(value);

            DeleteResult dRes = mongoTemplate.remove(ots);
            if (dRes.wasAcknowledged()) {
                System.out.println("[^_^:20180519-1937-001] ????????????");
            }
            else {
                System.out.println("[^_^:20180519-1937-002] ??????????????????");
            }

            // ????????????????????? save
            mongoTemplate.save(ots);
            query = Query.query(Criteria.where(collectionAttributeName_id).is(id));
            ots = null;
            ots = mongoTemplate.findOne(query, ZKObjectToSave.class);
            TestCase.assertNotNull(ots);
            TestCase.assertEquals(value, ots.getValue());

            // ?????????????????? save?????????????????????????????????
            value = value + "??????????????????";
            ots.setValue(value);
            mongoTemplate.save(ots);
            query = Query.query(Criteria.where(collectionAttributeName_id).is(id));
            ots = null;
            ots = mongoTemplate.findOne(query, ZKObjectToSave.class);
            TestCase.assertNotNull(ots);
            TestCase.assertEquals(value, ots.getValue());

            /*** ???????????????????????? mongoTemplate.save ??????????????????,???????????? ***/
            String attributeKeyName = "testAttr";
            String attributeKeyValue = "testAttr ???????????????";
            jsonCommand = String.format("{find:'%s', filter:{%s:'%s'}, projection:{%s:1}}",
                    ZKObjectToSave.collectionName, "_id", id, attributeKeyName);
            System.out.println("[^_^:20180529-0655-001] jsonCommand:" + jsonCommand);
            // ??? mongoTemplate.upsert(query, update,
            // ZKObjectToSave.collectionName); ?????????????????????
            query = Query.query(Criteria.where(collectionAttributeName_id).is(id));
            update = new Update();
            update.set(attributeKeyName, attributeKeyValue);
            ur = mongoTemplate.upsert(query, update, ZKObjectToSave.class);
            TestCase.assertTrue(ur.isModifiedCountAvailable());
            query = Query.query(Criteria.where(collectionAttributeName_id).is(id));
            // ?????????????????????
            doc = mongoTemplate.executeCommand(jsonCommand);
            System.out.println("[^_^:20180529-0655-002] doc:" + JSON.toJSONString(doc));
            doc = ZKMongoUtils.getOneResult(doc);
            t = (String) doc.get(attributeKeyName);
            TestCase.assertEquals(attributeKeyValue, t);
            // mongoTemplate.save(ots);
            value = "???????????????????????? mongoTemplate.save ??????????????????";
            ots.setValue(value);
            mongoTemplate.save(ots);
            query = Query.query(Criteria.where(collectionAttributeName_id).is(id));
            ots = null;
            ots = mongoTemplate.findOne(query, ZKObjectToSave.class);
            TestCase.assertNotNull(ots);
            TestCase.assertEquals(value, ots.getValue());
            // ?????????????????????
            doc = mongoTemplate.executeCommand(jsonCommand);
            System.out.println("[^_^:20180529-0655-003] doc:" + JSON.toJSONString(doc));
            doc = ZKMongoUtils.getOneResult(doc);
            t = (String) doc.get(attributeKeyName);
            TestCase.assertEquals(null, t);

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
//      }finally {
//          ZKObjectToSave ots = new ZKObjectToSave();
//          ots.setKey(id);
//          mongoTemplate.remove(ots);
        }
    }

    /**
     * ????????????????????????????????????????????????????????????????????????????????????
     * 
     * mongoTemplate.upsert ??????????????????????????????????????????????????????????????????????????????
     * 
     * mongoTemplate.findAndModify ??????????????????????????????????????????????????????
     * 
     * update.setOnInsert ???????????????????????? update.set ??????????????????????????????????????????
     * 
     */
    @Test
    public void testDocUpdateAttribute() {
        String id = "test_doc_update_attribute_01";
        String value = "?????? mongoTemplate ??????????????????????????????????????????????????? ";

        String attributeKeyName = "testAttr";
        String attributeKeyValue = "";
        try {
            Query query = null;
            Update update = null;
            FindAndModifyOptions options = null;
            DeleteResult dRes = null;
            ZKObjectToSave tempOTS = null;
            UpdateResult ur = null;

            ZKObjectToSave ots = new ZKObjectToSave();
            ots.setKey(id);
            ots.setValue(value);

            // ??????????????????????????????????????????????????????
            dRes = mongoTemplate.remove(ots);
            if (dRes.wasAcknowledged()) {
                System.out.println("[^_^:20180520-0827-001] ????????????");
            }
            else {
                System.out.println("[^_^:20180520-0827-002] ??????????????????");
            }
            query = Query.query(Criteria.where(collectionAttributeName_id).is(id));
            update = new Update();
            attributeKeyValue = "mongoTemplate.upsert";
            update.set(attributeKeyName, attributeKeyValue);
            ur = mongoTemplate.upsert(query, update, ZKObjectToSave.class);
            TestCase.assertTrue(ur.isModifiedCountAvailable());

            // ???????????????????????????,???????????????????????????
            dRes = mongoTemplate.remove(ots);
            if (dRes.wasAcknowledged()) {
                System.out.println("[^_^:20180520-0827-003] ????????????");
            }
            else {
                TestCase.assertTrue(false);
            }
            query = Query.query(Criteria.where(collectionAttributeName_id).is(id));
            update = new Update();
            attributeKeyValue = "mongoTemplate.findAndModify.upsert.true";
            update.set(attributeKeyName, attributeKeyValue);
            options = FindAndModifyOptions.options();
            options.upsert(true);
            // ???????????????
            options.returnNew(true);
            tempOTS = mongoTemplate.findAndModify(query, update, options, ZKObjectToSave.class);
            TestCase.assertNotNull(tempOTS);
            System.out.println("[^_^:20180520-0827-003] " + JSON.toJSONString(tempOTS));

            // ????????????????????????????????????
            dRes = mongoTemplate.remove(ots);
            if (dRes.wasAcknowledged()) {
                System.out.println("[^_^:20180520-0827-004] ????????????");
            }
            else {
                TestCase.assertTrue(false);
            }
            query = Query.query(Criteria.where(collectionAttributeName_id).is(id));
            update = new Update();
            attributeKeyValue = "mongoTemplate.findAndModify.upsert.false";
            update.set(attributeKeyName, attributeKeyValue);
            options = FindAndModifyOptions.options();
            options.upsert(false);
            options.returnNew(true);
            tempOTS = mongoTemplate.findAndModify(query, update, options, ZKObjectToSave.class);
            TestCase.assertNull(tempOTS);

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

    /**
     * ??????????????????????????????????????????????????????????????? ??????
     */
    @Test
    public void testAttributeUpdate() {
        String id = "test_update_attribute_01";
        String value = "?????? mongoTemplate ??????????????????????????????????????? ";

        String attributeKeyName = "testAttrValue";
        Object attributeKeyValue = 2;
        try {
            // ???????????? doc
            Query query = null;
            Update update = null;
            FindAndModifyOptions options = null;
            DeleteResult dRes = null;
            ZKObjectToSave tempOTS = null;
            UpdateResult ur = null;
            Map<String, ?> resDoc;
            String jsonCommand;
            Document doc = null;
            String t = null;

            ZKObjectToSave ots = new ZKObjectToSave();
            ots.setKey(id);
            ots.setValue(value);
            dRes = mongoTemplate.remove(ots);
            if (dRes.wasAcknowledged()) {
                System.out.println("[^_^:20180520-0910-001] ????????????");
            }
            else {
                System.out.println("[^_^:20180520-0910-002] ??????????????????");
            }
            mongoTemplate.save(ots);
            query = Query.query(Criteria.where(collectionAttributeName_id).is(id));
            tempOTS = mongoTemplate.findOne(query, ZKObjectToSave.class);
            TestCase.assertNotNull(tempOTS);

            // ????????????????????????
            attributeKeyValue = "????????????????????????";
            query = Query.query(Criteria.where(collectionAttributeName_id).is(id));
            update = new Update();
            update.set(attributeKeyName, attributeKeyValue);
            options = FindAndModifyOptions.options();
            options.upsert(true);
            ur = mongoTemplate.upsert(query, update, ZKObjectToSave.class);
            TestCase.assertTrue(ur.isModifiedCountAvailable());
            // ???????????? cResult
            jsonCommand = String.format("{find:'%s', filter:{%s:'%s'}, projection:{%s:1}}",
                    ZKObjectToSave.collectionName, "_id", id, attributeKeyName);
            doc = mongoTemplate.executeCommand(jsonCommand);
            resDoc = ZKMongoUtils.getOneResult(doc);
            t = (String) resDoc.get(attributeKeyName);
            TestCase.assertEquals(attributeKeyValue, t);

            // ?????????????????????
            attributeKeyValue = "?????????????????????";
            query = Query.query(Criteria.where(collectionAttributeName_id).is(id));
            update = new Update();
            update.set(attributeKeyName, attributeKeyValue);
            options = FindAndModifyOptions.options();
            options.upsert(true);
            ur = mongoTemplate.upsert(query, update, ZKObjectToSave.class);
            TestCase.assertTrue(ur.isModifiedCountAvailable());

            // ?????????????????? ,?????????

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

    /**
     * ????????????
     */
    @Test
    public void testFindAttribute() {

        String id = "test_find_attribute_01";
        String value = "?????? mongoTemplate ???????????? ";

        String attributeKeyName = "testAttrValue";
        String attributeKeyValue = "attributeKeyValue";

        try {
            // ????????????
            Query query = null;
            Update update = null;
            FindAndModifyOptions options = null;
            DeleteResult dRes = null;
            ZKObjectToSave tempOTS = null;
            UpdateResult ur = null;
            Document doc = null;
            String t = null;

            ZKObjectToSave ots = new ZKObjectToSave();
            ots.setKey(id);
            ots.setValue(value);
            dRes = mongoTemplate.remove(ots);
            if (dRes.wasAcknowledged()) {
                System.out.println("[^_^:20180520-1010-001] ????????????");
            }
            else {
                System.out.println("[^_^:20180520-1010-002] ??????????????????");
            }
            mongoTemplate.save(ots);
            query = Query.query(Criteria.where(collectionAttributeName_id).is(id));
            tempOTS = mongoTemplate.findOne(query, ZKObjectToSave.class);
            TestCase.assertNotNull(tempOTS);

            // ?????????????????????????????????
            attributeKeyValue = "?????????????????????????????????";
            query = Query.query(Criteria.where(collectionAttributeName_id).is(id));
            update = new Update();
            update.set(attributeKeyName, attributeKeyValue);
            options = FindAndModifyOptions.options();
            options.upsert(true);
            ur = mongoTemplate.upsert(query, update, ZKObjectToSave.class);
            TestCase.assertTrue(ur.isModifiedCountAvailable());

            // ???????????? cResult
            String jsonCommand = String.format("{find:'%s', filter:{%s:'%s'}, projection:{%s:1}}",
                    ZKObjectToSave.collectionName, "_id", id, attributeKeyName);
            System.out.println("[^_^:20180520-1011-001] jsonCommand:" + jsonCommand);
            doc = mongoTemplate.executeCommand(jsonCommand);
            System.out.println("[^_^:20180520-1651-002] doc:" + JSON.toJSONString(doc));
            Map<String, ?> resDoc = ZKMongoUtils.getOneResult(doc);
            System.out.println("[^_^:20180520-1651-003] doc:" + JSON.toJSONString(resDoc));
            t = (String) resDoc.get(attributeKeyName);
            TestCase.assertEquals(attributeKeyValue, t);

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

    /**
     * ????????????????????????
     */
    @Test
    public void testAttributeType() {
        String id = "test_attribute_type_01";
        String value = "?????? mongoTemplate ?????????????????????????????? ";

        String attrStringKeyName = "attrStringKeyName";
        String attrIntKeyName = "attrIntKeyName";
        String attrDoubleKeyName = "attrFloatKeyName";
        String attrDateKeyName = "attrDateKeyName";
        String attrBytesKeyName = "attrBytesKeyName";

        String attrObjectKeyName = "attrObjectKeyName";

//      String attrIntsKeyName = "attrIntsKeyName";
//      String attrObjectsKeyName = "attrObjectsKeyName";
//      String attrListKeyName = "attrListKeyName";
//      String attrMapKeyName = "attrMapKeyName";

        String attrStringKeyValue = "String ??????";
        int attrIntKeyValue = 6;
        double attrDoubleKeyValue = 6.6f;
        Date attrDateKeyValue = new Date();
        byte[] attrBytesKeyValue = "??????????????????".getBytes();

        ZKTestObject attrObjectKeyValue = new ZKTestObject();

        try {
            // ???????????? doc
            Query query = null;
            Update update = null;
            FindAndModifyOptions options = null;
            DeleteResult dRes = null;
            ZKObjectToSave tempOTS = null;
            UpdateResult ur = null;
//          Map<String, ?> resDoc;
            String jsonCommand;
            Document doc = null;

            ZKObjectToSave ots = new ZKObjectToSave();
            ots.setKey(id);
            ots.setValue(value);
            dRes = mongoTemplate.remove(ots);
            if (dRes.wasAcknowledged()) {
                System.out.println("[^_^:20180520-0910-001] ????????????");
            }
            else {
                System.out.println("[^_^:20180520-0910-002] ??????????????????");
            }
            mongoTemplate.save(ots);
            query = Query.query(Criteria.where(collectionAttributeName_id).is(id));
            tempOTS = mongoTemplate.findOne(query, ZKObjectToSave.class);
            TestCase.assertNotNull(tempOTS);

            query = Query.query(Criteria.where(collectionAttributeName_id).is(id));
            update = new Update();
            options = FindAndModifyOptions.options();
            options.upsert(true);
            ur = mongoTemplate.upsert(query, update, ZKObjectToSave.class);
            TestCase.assertTrue(ur.isModifiedCountAvailable());

            jsonCommand = String.format("{find:'%s', filter:{%s:'%s'}}", ZKObjectToSave.collectionName, "_id", id);
            doc = mongoTemplate.executeCommand(jsonCommand);
            // String
            String resStr = ZKMongoUtils.getStringByKey(doc, attrStringKeyName);
            TestCase.assertNull(resStr);
            // Integer
            Integer resInt = ZKMongoUtils.getIntegerByKey(doc, attrIntKeyName);
            TestCase.assertNull(resInt);
            // Double
            Double resDouble = ZKMongoUtils.getDoubleByKey(doc, attrDoubleKeyName);
            TestCase.assertNull(resDouble);
            // Date
            Date resDate = ZKMongoUtils.getDateByKey(doc, attrDateKeyName);
            TestCase.assertNull(resDate);
            // byte[]
            byte[] resBytes = ZKMongoUtils.getByteByKey(doc, attrBytesKeyName);
            TestCase.assertNull(resBytes);
            // ZKTestObject
            Object obj = ZKMongoUtils.getObjectByKey(doc, attrObjectKeyName);
            TestCase.assertNull(obj);

            query = Query.query(Criteria.where(collectionAttributeName_id).is(id));
            update = new Update();
            update.set(attrStringKeyName, attrStringKeyValue);
            update.set(attrIntKeyName, attrIntKeyValue);
            update.set(attrDoubleKeyName, attrDoubleKeyValue);
            update.set(attrDateKeyName, attrDateKeyValue);
            update.set(attrBytesKeyName, attrBytesKeyValue);
            update.set(attrObjectKeyName, ZKObjectUtils.serialize(attrObjectKeyValue));
            options = FindAndModifyOptions.options();
            options.upsert(true);
            ur = mongoTemplate.upsert(query, update, ZKObjectToSave.class);
            TestCase.assertTrue(ur.isModifiedCountAvailable());

            // ???????????? Document
            jsonCommand = String.format("{find:'%s', filter:{%s:'%s'}}", ZKObjectToSave.collectionName, "_id", id);
            doc = mongoTemplate.executeCommand(jsonCommand);

            // String
            resStr = ZKMongoUtils.getStringByKey(doc, attrStringKeyName);
            TestCase.assertEquals(attrStringKeyValue, resStr);
            // Integer
            resInt = ZKMongoUtils.getIntegerByKey(doc, attrIntKeyName);
            TestCase.assertEquals(attrIntKeyValue, resInt.intValue());
            // Double
            resDouble = ZKMongoUtils.getDoubleByKey(doc, attrDoubleKeyName);
            TestCase.assertTrue(attrDoubleKeyValue - resDouble.doubleValue() < 0.000001);
            TestCase.assertTrue(attrDoubleKeyValue - resDouble.doubleValue() > -0.000001);
            // Date
            resDate = ZKMongoUtils.getDateByKey(doc, attrDateKeyName);
            TestCase.assertEquals(attrDateKeyValue.getTime(), resDate.getTime());
            // byte[]
            resBytes = ZKMongoUtils.getByteByKey(doc, attrBytesKeyName);
            TestCase.assertEquals(new String(attrBytesKeyValue), new String(resBytes));
            // ZKTestObject
            resBytes = ZKMongoUtils.getByteByKey(doc, attrObjectKeyName);
            ZKTestObject to = (ZKTestObject) ZKObjectUtils.unserialize(resBytes);
            TestCase.assertEquals(9, to.getIntValue());

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

    @Test
    public void test() {
        try {
            // ????????????

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

}
