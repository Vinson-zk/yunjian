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
 * @Title: ZKSampleRsaAesTransferCipherManagerTest.java 
 * @author Vinson 
 * @Package com.zk.core.encrypt.support 
 * @Description: TODO(simple description this file what to do.) 
 * @date Dec 18, 2019 3:39:26 PM 
 * @version V1.0   
*/
package com.zk.core.encrypt.support;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.zk.core.commons.ZKContentType;
import com.zk.core.encrypt.ZKTransferCipherManager;
import com.zk.core.encrypt.utils.ZKEncryptAesUtils;
import com.zk.core.encrypt.utils.ZKEncryptRsaUtils;
import com.zk.core.encrypt.utils.ZKEncryptUtils;
import com.zk.core.helper.configuration.ZKCoreChildConfiguration;
import com.zk.core.helper.configuration.ZKCoreParentConfiguration;
import com.zk.core.helper.controller.ZKCoreTransferCipherController;
import com.zk.core.utils.ZKEncodingUtils;
import com.zk.core.utils.ZKJsonUtils;
import com.zk.core.web.filter.ZKTransferCipherFilter;

import junit.framework.TestCase;

/** 
* @ClassName: ZKSampleRsaAesTransferCipherManagerTest 
* @Description: TODO(simple description this class what to do.) 
* @author Vinson 
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ZKCoreParentConfiguration.class,
        ZKCoreChildConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ZKSampleRsaAesTransferCipherManagerTest {

    private static Logger log = LoggerFactory.getLogger(ZKSampleRsaAesTransferCipherManagerTest.class);

    @Autowired
    WebApplicationContext wac;

//    @Autowired
    private MockMvc mvc;

    @Value("${zk.path.admin:zk}")
    String adminPath;

    @Value("${zk.path.core:c}")
    String modulePath;

    String baseUrl = "";

    ZKTransferCipherManager transferCipherManager = null;

    static interface ZKRSA_ReqHeader extends ZKSampleRsaAesTransferCipherManager.ZKRSA_ReqHeader {
    }

    @Before
    public void before() {
        try {
            this.baseUrl = "/" + adminPath + "/" + modulePath + "/tc";

//            transferCipherManager = wac.getBean(ZKTransferCipherManager.class);
//            TestCase.assertNotNull(transferCipherManager);

            transferCipherManager = new ZKSampleRsaAesTransferCipherManager();

            ZKTransferCipherFilter transferCipherFilter = new ZKTransferCipherFilter();

            transferCipherFilter.setArTransferCipherManager(transferCipherManager);
            this.mvc = MockMvcBuilders.webAppContextSetup(wac).addFilter(transferCipherFilter, this.baseUrl + "/*")
                    .build();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    static interface RequestParams extends ZKCoreTransferCipherController.RequestParams {
    }

    static interface msg extends ZKCoreTransferCipherController.msg {
    }

    @Test
    public void testTransferCipher() {
        try {

            MockHttpServletRequestBuilder mockReqBuilder = null;
            MvcResult mvcResult = null;
            MockHttpServletResponse mockRes = null;
            String resStr = null;
            byte[] resEncData = null;
            Map<String, ?> resMap = null;

            Map<String, Object> reqBodyMap = new HashMap<String, Object>();
            String encParameterStr = null;
            String encReqBodyStr = null;
            int intValue = 3;

            String url = String.format("%s/transferCipher/%s/%s", this.baseUrl, RequestParams.pn_pathVariable1,
                    RequestParams.pn_pathVariable2);

            ZKSampleRsaAesTransferCipherManager sampleRsaAesTc = (ZKSampleRsaAesTransferCipherManager) this.transferCipherManager;

            /*** ?????????????????? ***/
            mockReqBuilder = MockMvcRequestBuilders.post(url);
            // ???????????????
            mockReqBuilder.header(RequestParams.pn_req_header, msg.value + RequestParams.pn_req_header);
            // ??????????????????
            mockReqBuilder.requestAttr(RequestParams.pn_req_attr, msg.value + RequestParams.pn_req_attr);
            // ???????????????????????????
            mockReqBuilder.param(RequestParams.pn_strs, msg.value + RequestParams.pn_strs,
                    msg.value + RequestParams.pn_strs);
            mockReqBuilder.param(RequestParams.pn_int, String.valueOf(intValue));
            // ??????????????? requestBody
            reqBodyMap.put(RequestParams.pn_strs,
                    new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs });
            // ????????? ??????
            // mockReqBuilder.contentType(EnumContentType.X_FORM.getContentType());
            // application/json ??????; ?????????????????? Request Body???????????????????????? ???????????????
            mockReqBuilder.contentType(ZKContentType.JSON_UTF8.getContentType());
            mockReqBuilder.content(ZKJsonUtils.writeObjectJson(reqBodyMap));

            // ????????????
            mvcResult = this.mvc.perform(mockReqBuilder).andExpect(MockMvcResultMatchers.status().isOk())
//                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            mockRes = mvcResult.getResponse();
            resStr = mockRes.getContentAsString();
            log.info("[^_^:20190625-1121-001] " + resStr);

            resMap = ZKJsonUtils.jsonStrToMap(resStr);

            TestCase.assertEquals(msg.normal_transferCipher + RequestParams.pn_pathVariable1,
                    resMap.get(RequestParams.pn_pathVariable1));
            TestCase.assertEquals(msg.normal_transferCipher + RequestParams.pn_pathVariable2,
                    resMap.get(RequestParams.pn_pathVariable2));

            TestCase.assertEquals(msg.normal_transferCipher + msg.value + RequestParams.pn_req_header,
                    resMap.get(RequestParams.pn_req_header));
            TestCase.assertEquals(msg.normal_transferCipher + msg.value + RequestParams.pn_req_attr,
                    resMap.get(RequestParams.pn_req_attr));

            TestCase.assertEquals(
                    msg.normal_transferCipher + ZKJsonUtils.writeObjectJson(
                            new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs }),
                    resMap.get(RequestParams.pn_strs));
            TestCase.assertEquals(intValue * intValue, resMap.get(RequestParams.pn_int));
            TestCase.assertEquals(ZKJsonUtils.writeObjectJson(reqBodyMap),
                    ZKJsonUtils.writeObjectJson(resMap.get(RequestParams.RequestBody)));

            log.info("[^_^:20190625-1121-001] === ???????????????????????????????????? ------------- ");
            /*** ????????????????????? ***/
            Map<String, Object> paramMap = new HashMap<>();
            byte[] publicKey = sampleRsaAesTc.getZKRSAKeyByRsaId(null).getPublicKey();
            byte[] dataKey = "???????????????????????????".getBytes();
            byte[] salt = ZKEncryptUtils.genSalt(dataKey);

            log.info("[^_^:20190626-1632-001-0] ????????? ?????????" + ZKEncodingUtils.encodeHex(dataKey));
            String encDataKeyStr = ZKEncodingUtils.encodeHex(ZKEncryptRsaUtils.encrypt(dataKey, publicKey));
            log.info("[^_^:20190626-1632-001-1] ????????? ?????????" + encDataKeyStr);

            /*** post ***/
            mockReqBuilder = MockMvcRequestBuilders.post(url);
            // ???????????????
            mockReqBuilder.header(RequestParams.pn_req_header, msg.value + RequestParams.pn_req_header);
            // ??????????????????
            mockReqBuilder.requestAttr(RequestParams.pn_req_attr, msg.value + RequestParams.pn_req_attr);
            // ???????????????????????????
            paramMap.put(RequestParams.pn_strs,
                    new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs });
            paramMap.put(RequestParams.pn_int, intValue);
            encParameterStr = ZKJsonUtils.writeObjectJson(paramMap);
            log.info("[^_^:20190626-1632-002-0] ????????? ?????????" + encParameterStr);
            encParameterStr = ZKEncodingUtils
                    .encodeHex((ZKEncryptAesUtils.encrypt(encParameterStr.getBytes("utf-8"), dataKey, salt)));
            log.info("[^_^:20190626-1632-002-1] ????????? ?????????" + encParameterStr);
            mockReqBuilder.param(ZKRSA_ReqHeader.pn_parameter, encParameterStr);
            // ??????????????? requestBody
            reqBodyMap.put(RequestParams.pn_strs,
                    new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs });
            reqBodyMap.put(RequestParams.pn_int, intValue);
            // ????????? ??????
            // mockReqBuilder.contentType(EnumContentType.X_FORM.getContentType());
            // application/json ??????; ?????????????????? Request Body???????????????????????? ???????????????
            mockReqBuilder.contentType(ZKContentType.JSON_UTF8.getContentType());
            encReqBodyStr = ZKJsonUtils.writeObjectJson(reqBodyMap);
            log.info("[^_^:20190626-1632-003-0] ????????? reqBody???" + encReqBodyStr);
            encReqBodyStr = ZKEncodingUtils
                    .encodeHex(ZKEncryptAesUtils.encrypt(encReqBodyStr.getBytes(), dataKey, salt));
            log.info("[^_^:20190626-1632-003-1] ????????? reqBody???" + encReqBodyStr);
            mockReqBuilder.content(encReqBodyStr);
            // ?????????????????? rsa ID?????????????????????????????????????????????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_id, "KKK");
            // ?????? rsa ?????????????????? ??????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_key, encDataKeyStr);
            // ????????????
            mvcResult = this.mvc.perform(mockReqBuilder).andExpect(MockMvcResultMatchers.status().isOk())
//                  .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            mockRes = mvcResult.getResponse();
            resEncData = mockRes.getContentAsByteArray();
            log.info("[^_^:20190625-1121-002-0] ????????? ????????????:" + new String(resEncData));
            resStr = new String(
                    ZKEncryptAesUtils.decrypt(ZKEncodingUtils.decodeHex(new String(resEncData)), dataKey, salt));
            log.info("[^_^:20190625-1121-002-1] ????????? ??????????????? " + resStr);

            resMap = ZKJsonUtils.jsonStrToMap(resStr);

            TestCase.assertEquals(msg.normal_transferCipher + RequestParams.pn_pathVariable1,
                    resMap.get(RequestParams.pn_pathVariable1));
            TestCase.assertEquals(msg.normal_transferCipher + RequestParams.pn_pathVariable2,
                    resMap.get(RequestParams.pn_pathVariable2));

            TestCase.assertEquals(msg.normal_transferCipher + msg.value + RequestParams.pn_req_header,
                    resMap.get(RequestParams.pn_req_header));
            TestCase.assertEquals(msg.normal_transferCipher + msg.value + RequestParams.pn_req_attr,
                    resMap.get(RequestParams.pn_req_attr));

            TestCase.assertEquals(
                    msg.normal_transferCipher + ZKJsonUtils.writeObjectJson(
                            new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs }),
                    resMap.get(RequestParams.pn_strs));
            TestCase.assertEquals(intValue * intValue, resMap.get(RequestParams.pn_int));
            TestCase.assertEquals(ZKJsonUtils.writeObjectJson(reqBodyMap),
                    ZKJsonUtils.writeObjectJson(resMap.get(RequestParams.RequestBody)));
            log.info("[^_^:20190625-1121-001] === post ????????????????????????????????? ------------- ");

            /*** delete ***/
            mockReqBuilder = MockMvcRequestBuilders.delete(url);
            // ???????????????
            mockReqBuilder.header(RequestParams.pn_req_header, msg.value + RequestParams.pn_req_header);
            // ??????????????????
            mockReqBuilder.requestAttr(RequestParams.pn_req_attr, msg.value + RequestParams.pn_req_attr);
            // ???????????????????????????
            paramMap.put(RequestParams.pn_strs,
                    new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs });
            paramMap.put(RequestParams.pn_int, intValue);
            encParameterStr = ZKJsonUtils.writeObjectJson(paramMap);
            log.info("[^_^:20190626-1632-002-0] ????????? ?????????" + encParameterStr);
            encParameterStr = ZKEncodingUtils
                    .encodeHex((ZKEncryptAesUtils.encrypt(encParameterStr.getBytes("utf-8"), dataKey, salt)));
            log.info("[^_^:20190626-1632-002-1] ????????? ?????????" + encParameterStr);
            mockReqBuilder.param(ZKRSA_ReqHeader.pn_parameter, encParameterStr);
            // ??????????????? requestBody
            reqBodyMap.put(RequestParams.pn_strs,
                    new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs });
            reqBodyMap.put(RequestParams.pn_int, intValue);
            // ????????? ??????
            // mockReqBuilder.contentType(EnumContentType.X_FORM.getContentType());
            // application/json ??????; ?????????????????? Request Body???????????????????????? ???????????????
            mockReqBuilder.contentType(ZKContentType.JSON_UTF8.getContentType());
            encReqBodyStr = ZKJsonUtils.writeObjectJson(reqBodyMap);
            log.info("[^_^:20190626-1632-003-0] ????????? reqBody???" + encReqBodyStr);
            encReqBodyStr = ZKEncodingUtils
                    .encodeHex(ZKEncryptAesUtils.encrypt(encReqBodyStr.getBytes(), dataKey, salt));
            log.info("[^_^:20190626-1632-003-1] ????????? reqBody???" + encReqBodyStr);
            mockReqBuilder.content(encReqBodyStr);
            // ?????????????????? rsa ID?????????????????????????????????????????????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_id, "KKK");
            // ?????? rsa ?????????????????? ??????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_key, encDataKeyStr);
            // ????????????
            mvcResult = this.mvc.perform(mockReqBuilder).andExpect(MockMvcResultMatchers.status().isOk())
//                  .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            mockRes = mvcResult.getResponse();
            resEncData = mockRes.getContentAsByteArray();
            log.info("[^_^:20190625-1121-002-0] ????????? ????????????:" + new String(resEncData));
            resStr = new String(
                    ZKEncryptAesUtils.decrypt(ZKEncodingUtils.decodeHex(new String(resEncData)), dataKey, salt));
            log.info("[^_^:20190625-1121-002-1] ????????? ??????????????? " + resStr);

            resMap = ZKJsonUtils.jsonStrToMap(resStr);

            TestCase.assertEquals(msg.normal_transferCipher + RequestParams.pn_pathVariable1,
                    resMap.get(RequestParams.pn_pathVariable1));
            TestCase.assertEquals(msg.normal_transferCipher + RequestParams.pn_pathVariable2,
                    resMap.get(RequestParams.pn_pathVariable2));

            TestCase.assertEquals(msg.normal_transferCipher + msg.value + RequestParams.pn_req_header,
                    resMap.get(RequestParams.pn_req_header));
            TestCase.assertEquals(msg.normal_transferCipher + msg.value + RequestParams.pn_req_attr,
                    resMap.get(RequestParams.pn_req_attr));

            TestCase.assertEquals(
                    msg.normal_transferCipher + ZKJsonUtils.writeObjectJson(
                            new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs }),
                    resMap.get(RequestParams.pn_strs));
            TestCase.assertEquals(intValue * intValue, resMap.get(RequestParams.pn_int));
            TestCase.assertEquals(ZKJsonUtils.writeObjectJson(reqBodyMap),
                    ZKJsonUtils.writeObjectJson(resMap.get(RequestParams.RequestBody)));
            log.info("[^_^:20190625-1121-001] === delete ????????????????????????????????? ------------- ");

            /*** put ***/
            mockReqBuilder = MockMvcRequestBuilders.put(url);
            // ???????????????
            mockReqBuilder.header(RequestParams.pn_req_header, msg.value + RequestParams.pn_req_header);
            // ??????????????????
            mockReqBuilder.requestAttr(RequestParams.pn_req_attr, msg.value + RequestParams.pn_req_attr);
            // ???????????????????????????
            paramMap.put(RequestParams.pn_strs,
                    new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs });
            paramMap.put(RequestParams.pn_int, intValue);
            encParameterStr = ZKJsonUtils.writeObjectJson(paramMap);
            log.info("[^_^:20190626-1632-002-0] ????????? ?????????" + encParameterStr);
            encParameterStr = ZKEncodingUtils
                    .encodeHex((ZKEncryptAesUtils.encrypt(encParameterStr.getBytes("utf-8"), dataKey, salt)));
            log.info("[^_^:20190626-1632-002-1] ????????? ?????????" + encParameterStr);
            mockReqBuilder.param(ZKRSA_ReqHeader.pn_parameter, encParameterStr);
            // ??????????????? requestBody
            reqBodyMap.put(RequestParams.pn_strs,
                    new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs });
            reqBodyMap.put(RequestParams.pn_int, intValue);
            // ????????? ??????
            // mockReqBuilder.contentType(EnumContentType.X_FORM.getContentType());
            // application/json ??????; ?????????????????? Request Body???????????????????????? ???????????????
            mockReqBuilder.contentType(ZKContentType.JSON_UTF8.getContentType());
            encReqBodyStr = ZKJsonUtils.writeObjectJson(reqBodyMap);
            log.info("[^_^:20190626-1632-003-0] ????????? reqBody???" + encReqBodyStr);
            encReqBodyStr = ZKEncodingUtils
                    .encodeHex(ZKEncryptAesUtils.encrypt(encReqBodyStr.getBytes(), dataKey, salt));
            log.info("[^_^:20190626-1632-003-1] ????????? reqBody???" + encReqBodyStr);
            mockReqBuilder.content(encReqBodyStr);
            // ?????????????????? rsa ID?????????????????????????????????????????????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_id, "KKK");
            // ?????? rsa ?????????????????? ??????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_key, encDataKeyStr);
            // ????????????
            mvcResult = this.mvc.perform(mockReqBuilder).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print()).andReturn();
            mockRes = mvcResult.getResponse();
            resEncData = mockRes.getContentAsByteArray();
            log.info("[^_^:20190625-1121-002-0] ????????? ????????????:" + new String(resEncData));
            resStr = new String(
                    ZKEncryptAesUtils.decrypt(ZKEncodingUtils.decodeHex(new String(resEncData)), dataKey, salt));
            log.info("[^_^:20190625-1121-002-1] ????????? ??????????????? " + resStr);

            resMap = ZKJsonUtils.jsonStrToMap(resStr);

            TestCase.assertEquals(msg.normal_transferCipher + RequestParams.pn_pathVariable1,
                    resMap.get(RequestParams.pn_pathVariable1));
            TestCase.assertEquals(msg.normal_transferCipher + RequestParams.pn_pathVariable2,
                    resMap.get(RequestParams.pn_pathVariable2));

            TestCase.assertEquals(msg.normal_transferCipher + msg.value + RequestParams.pn_req_header,
                    resMap.get(RequestParams.pn_req_header));
            TestCase.assertEquals(msg.normal_transferCipher + msg.value + RequestParams.pn_req_attr,
                    resMap.get(RequestParams.pn_req_attr));

            TestCase.assertEquals(
                    msg.normal_transferCipher + ZKJsonUtils.writeObjectJson(
                            new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs }),
                    resMap.get(RequestParams.pn_strs));
            TestCase.assertEquals(intValue * intValue, resMap.get(RequestParams.pn_int));
            TestCase.assertEquals(ZKJsonUtils.writeObjectJson(reqBodyMap),
                    ZKJsonUtils.writeObjectJson(resMap.get(RequestParams.RequestBody)));
            log.info("[^_^:20190625-1121-001] === put ????????????????????????????????? ------------- ");
        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }

    }

    @Test
    public void testTransferCipherNoReqBody() {
        try {

            MockHttpServletRequestBuilder mockReqBuilder = null;
            MvcResult mvcResult = null;
            MockHttpServletResponse mockRes = null;
            String resStr = null;
            byte[] resEncData = null;
            Map<String, ?> resMap = null;

            String encParameterStr = null;
            int intValue = 3;

            String url = String.format("%s/transferCipherNoReqBody/%s/%s", this.baseUrl, RequestParams.pn_pathVariable1,
                    RequestParams.pn_pathVariable2);

            ZKSampleRsaAesTransferCipherManager sampleRsaAesTc = (ZKSampleRsaAesTransferCipherManager) this.transferCipherManager;

            /*** ?????????????????? ***/
            mockReqBuilder = MockMvcRequestBuilders.get(url);
            // ???????????????
            mockReqBuilder.header(RequestParams.pn_req_header, msg.value + RequestParams.pn_req_header);
            // ??????????????????
            mockReqBuilder.requestAttr(RequestParams.pn_req_attr, msg.value + RequestParams.pn_req_attr);
            // ???????????????????????????
            mockReqBuilder.param(RequestParams.pn_strs, msg.value + RequestParams.pn_strs,
                    msg.value + RequestParams.pn_strs);
            mockReqBuilder.param(RequestParams.pn_int, String.valueOf(intValue));

            // ????????????
            mvcResult = this.mvc.perform(mockReqBuilder).andExpect(MockMvcResultMatchers.status().isOk())
//                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            mockRes = mvcResult.getResponse();
            resStr = mockRes.getContentAsString();
            log.info("[^_^:20190625-1121-001] " + resStr);

            resMap = ZKJsonUtils.jsonStrToMap(resStr);

            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + RequestParams.pn_pathVariable1,
                    resMap.get(RequestParams.pn_pathVariable1));
            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + RequestParams.pn_pathVariable2,
                    resMap.get(RequestParams.pn_pathVariable2));

            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + msg.value + RequestParams.pn_req_header,
                    resMap.get(RequestParams.pn_req_header));
            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + msg.value + RequestParams.pn_req_attr,
                    resMap.get(RequestParams.pn_req_attr));

            TestCase.assertEquals(
                    msg.normal_transferCipherNoReqBody + ZKJsonUtils.writeObjectJson(
                            new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs }),
                    resMap.get(RequestParams.pn_strs));
            TestCase.assertEquals(intValue * intValue, resMap.get(RequestParams.pn_int));

            log.info("[^_^:20190625-1121-001] === ???????????????????????????????????? ------------- ");

            /*** ????????????????????? ***/
            Map<String, Object> paramMap = new HashMap<>();
            byte[] publicKey = sampleRsaAesTc.getZKRSAKeyByRsaId(null).getPublicKey();
            byte[] dataKey = "???????????????????????????".getBytes();
            byte[] salt = ZKEncryptUtils.genSalt(dataKey);

            log.info("[^_^:20190626-1632-001-0] ????????? ?????????" + new String(dataKey));
            String encDataKeyStr = ZKEncodingUtils.encodeHex(ZKEncryptRsaUtils.encrypt(dataKey, publicKey));
            log.info("[^_^:20190626-1632-001-1] ????????? ?????????" + encDataKeyStr);

            /*** get ***/
            mockReqBuilder = MockMvcRequestBuilders.get(url);
            // ???????????????
            mockReqBuilder.header(RequestParams.pn_req_header, msg.value + RequestParams.pn_req_header);
            // ??????????????????
            mockReqBuilder.requestAttr(RequestParams.pn_req_attr, msg.value + RequestParams.pn_req_attr);
            // ???????????????????????????
            paramMap.put(RequestParams.pn_strs,
                    new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs });
            paramMap.put(RequestParams.pn_int, intValue);
            encParameterStr = ZKJsonUtils.writeObjectJson(paramMap);
            log.info("[^_^:20190626-1632-002-0] ????????? ?????????" + encParameterStr);
            encParameterStr = ZKEncodingUtils
                    .encodeHex((ZKEncryptAesUtils.encrypt(encParameterStr.getBytes("utf-8"), dataKey, salt)));
            log.info("[^_^:20190626-1632-002-1] ????????? ?????????" + encParameterStr);
            mockReqBuilder.param(ZKRSA_ReqHeader.pn_parameter, encParameterStr);

            // ?????????????????? rsa ID?????????????????????????????????????????????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_id, "KKK");
            // ?????? rsa ?????????????????? ??????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_key, encDataKeyStr);
            // ????????????
            mvcResult = this.mvc.perform(mockReqBuilder).andExpect(MockMvcResultMatchers.status().isOk())
//                  .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            mockRes = mvcResult.getResponse();
            resEncData = mockRes.getContentAsByteArray();
            log.info("[^_^:20190625-1121-002-0] ????????? ????????????:" + new String(resEncData));
            resStr = new String(
                    ZKEncryptAesUtils.decrypt(ZKEncodingUtils.decodeHex(new String(resEncData)), dataKey, salt));
            log.info("[^_^:20190625-1121-002-1] ????????? ??????????????? " + resStr);

            resMap = ZKJsonUtils.jsonStrToMap(resStr);

            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + RequestParams.pn_pathVariable1,
                    resMap.get(RequestParams.pn_pathVariable1));
            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + RequestParams.pn_pathVariable2,
                    resMap.get(RequestParams.pn_pathVariable2));

            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + msg.value + RequestParams.pn_req_header,
                    resMap.get(RequestParams.pn_req_header));
            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + msg.value + RequestParams.pn_req_attr,
                    resMap.get(RequestParams.pn_req_attr));

            TestCase.assertEquals(
                    msg.normal_transferCipherNoReqBody + ZKJsonUtils.writeObjectJson(
                            new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs }),
                    resMap.get(RequestParams.pn_strs));
            TestCase.assertEquals(intValue * intValue, resMap.get(RequestParams.pn_int));
            log.info("[^_^:20190625-1121-001] === get ????????????????????????????????? ------------- ");

            /*** post ***/
            mockReqBuilder = MockMvcRequestBuilders.post(url);
            // ???????????????
            mockReqBuilder.header(RequestParams.pn_req_header, msg.value + RequestParams.pn_req_header);
            // ??????????????????
            mockReqBuilder.requestAttr(RequestParams.pn_req_attr, msg.value + RequestParams.pn_req_attr);
            // ???????????????????????????
            paramMap.put(RequestParams.pn_strs,
                    new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs });
            paramMap.put(RequestParams.pn_int, intValue);
            encParameterStr = ZKJsonUtils.writeObjectJson(paramMap);
            log.info("[^_^:20190626-1632-002-0] ????????? ?????????" + encParameterStr);
            encParameterStr = ZKEncodingUtils
                    .encodeHex((ZKEncryptAesUtils.encrypt(encParameterStr.getBytes("utf-8"), dataKey, salt)));
            log.info("[^_^:20190626-1632-002-1] ????????? ?????????" + encParameterStr);
            mockReqBuilder.param(ZKRSA_ReqHeader.pn_parameter, encParameterStr);

            // ?????????????????? rsa ID?????????????????????????????????????????????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_id, "KKK");
            // ?????? rsa ?????????????????? ??????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_key, encDataKeyStr);
            // ????????????
            mvcResult = this.mvc.perform(mockReqBuilder).andExpect(MockMvcResultMatchers.status().isOk())
//                  .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            mockRes = mvcResult.getResponse();
            resEncData = mockRes.getContentAsByteArray();
            log.info("[^_^:20190625-1121-002-0] ????????? ????????????:" + new String(resEncData));
            resStr = new String(
                    ZKEncryptAesUtils.decrypt(ZKEncodingUtils.decodeHex(new String(resEncData)), dataKey, salt));
            log.info("[^_^:20190625-1121-002-1] ????????? ??????????????? " + resStr);

            resMap = ZKJsonUtils.jsonStrToMap(resStr);

            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + RequestParams.pn_pathVariable1,
                    resMap.get(RequestParams.pn_pathVariable1));
            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + RequestParams.pn_pathVariable2,
                    resMap.get(RequestParams.pn_pathVariable2));

            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + msg.value + RequestParams.pn_req_header,
                    resMap.get(RequestParams.pn_req_header));
            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + msg.value + RequestParams.pn_req_attr,
                    resMap.get(RequestParams.pn_req_attr));

            TestCase.assertEquals(
                    msg.normal_transferCipherNoReqBody + ZKJsonUtils.writeObjectJson(
                            new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs }),
                    resMap.get(RequestParams.pn_strs));
            TestCase.assertEquals(intValue * intValue, resMap.get(RequestParams.pn_int));
            log.info("[^_^:20190625-1121-001] === post ????????????????????????????????? ------------- ");

            /*** delete ***/
            mockReqBuilder = MockMvcRequestBuilders.delete(url);
            // ???????????????
            mockReqBuilder.header(RequestParams.pn_req_header, msg.value + RequestParams.pn_req_header);
            // ??????????????????
            mockReqBuilder.requestAttr(RequestParams.pn_req_attr, msg.value + RequestParams.pn_req_attr);
            // ???????????????????????????
            paramMap.put(RequestParams.pn_strs,
                    new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs });
            paramMap.put(RequestParams.pn_int, intValue);
            encParameterStr = ZKJsonUtils.writeObjectJson(paramMap);
            log.info("[^_^:20190626-1632-002-0] ????????? ?????????" + encParameterStr);
            encParameterStr = ZKEncodingUtils
                    .encodeHex((sampleRsaAesTc.encryptData(dataKey, salt, encParameterStr.getBytes("utf-8"))));
            log.info("[^_^:20190626-1632-002-1] ????????? ?????????" + encParameterStr);
            mockReqBuilder.param(ZKRSA_ReqHeader.pn_parameter, encParameterStr);

            // ?????????????????? rsa ID?????????????????????????????????????????????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_id, "KKK");
            // ?????? rsa ?????????????????? ??????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_key, encDataKeyStr);
            // ????????????
            mvcResult = this.mvc.perform(mockReqBuilder).andExpect(MockMvcResultMatchers.status().isOk())
//                  .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            mockRes = mvcResult.getResponse();
            resEncData = mockRes.getContentAsByteArray();
            log.info("[^_^:20190625-1121-002-0] ????????? ????????????:" + new String(resEncData));
            resStr = new String(
                    ZKEncryptAesUtils.decrypt(ZKEncodingUtils.decodeHex(new String(resEncData)), dataKey, salt));
            log.info("[^_^:20190625-1121-002-1] ????????? ??????????????? " + resStr);

            resMap = ZKJsonUtils.jsonStrToMap(resStr);

            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + RequestParams.pn_pathVariable1,
                    resMap.get(RequestParams.pn_pathVariable1));
            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + RequestParams.pn_pathVariable2,
                    resMap.get(RequestParams.pn_pathVariable2));

            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + msg.value + RequestParams.pn_req_header,
                    resMap.get(RequestParams.pn_req_header));
            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + msg.value + RequestParams.pn_req_attr,
                    resMap.get(RequestParams.pn_req_attr));

            TestCase.assertEquals(
                    msg.normal_transferCipherNoReqBody + ZKJsonUtils.writeObjectJson(
                            new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs }),
                    resMap.get(RequestParams.pn_strs));
            TestCase.assertEquals(intValue * intValue, resMap.get(RequestParams.pn_int));
            log.info("[^_^:20190625-1121-001] === delete ????????????????????????????????? ------------- ");

            /*** put ***/
            mockReqBuilder = MockMvcRequestBuilders.put(url);
            // ???????????????
            mockReqBuilder.header(RequestParams.pn_req_header, msg.value + RequestParams.pn_req_header);
            // ??????????????????
            mockReqBuilder.requestAttr(RequestParams.pn_req_attr, msg.value + RequestParams.pn_req_attr);
            // ???????????????????????????
            paramMap.put(RequestParams.pn_strs,
                    new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs });
            paramMap.put(RequestParams.pn_int, intValue);
            encParameterStr = ZKJsonUtils.writeObjectJson(paramMap);
            log.info("[^_^:20190626-1632-002-0] ????????? ?????????" + encParameterStr);
            encParameterStr = ZKEncodingUtils
                    .encodeHex((sampleRsaAesTc.encryptData(dataKey, salt, encParameterStr.getBytes("utf-8"))));
            log.info("[^_^:20190626-1632-002-1] ????????? ?????????" + encParameterStr);
            mockReqBuilder.param(ZKRSA_ReqHeader.pn_parameter, encParameterStr);

            // ?????????????????? rsa ID?????????????????????????????????????????????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_id, "KKK");
            // ?????? rsa ?????????????????? ??????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_key, encDataKeyStr);
            // ????????????
            mvcResult = this.mvc.perform(mockReqBuilder).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print()).andReturn();
            mockRes = mvcResult.getResponse();
            resEncData = mockRes.getContentAsByteArray();
            log.info("[^_^:20190625-1121-002-0] ????????? ????????????:" + new String(resEncData));
            resStr = new String(
                    ZKEncryptAesUtils.decrypt(ZKEncodingUtils.decodeHex(new String(resEncData)), dataKey, salt));
            log.info("[^_^:20190625-1121-002-1] ????????? ??????????????? " + resStr);

            resMap = ZKJsonUtils.jsonStrToMap(resStr);

            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + RequestParams.pn_pathVariable1,
                    resMap.get(RequestParams.pn_pathVariable1));
            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + RequestParams.pn_pathVariable2,
                    resMap.get(RequestParams.pn_pathVariable2));

            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + msg.value + RequestParams.pn_req_header,
                    resMap.get(RequestParams.pn_req_header));
            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + msg.value + RequestParams.pn_req_attr,
                    resMap.get(RequestParams.pn_req_attr));

            TestCase.assertEquals(
                    msg.normal_transferCipherNoReqBody + ZKJsonUtils.writeObjectJson(
                            new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs }),
                    resMap.get(RequestParams.pn_strs));
            TestCase.assertEquals(intValue * intValue, resMap.get(RequestParams.pn_int));
            log.info("[^_^:20190625-1121-001] === put ????????????????????????????????? ------------- ");

            /*** get application/x-www-form-urlencoded ***/
            mockReqBuilder = MockMvcRequestBuilders.post(url);
            // ???????????????
            mockReqBuilder.header(RequestParams.pn_req_header, msg.value + RequestParams.pn_req_header);
            // ??????????????????
            mockReqBuilder.requestAttr(RequestParams.pn_req_attr, msg.value + RequestParams.pn_req_attr);
            // ???????????????????????????
            paramMap.put(RequestParams.pn_strs,
                    new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs });
            paramMap.put(RequestParams.pn_int, intValue);
            encParameterStr = ZKJsonUtils.writeObjectJson(paramMap);
            log.info("[^_^:20190626-1632-002-0] ????????? ?????????" + encParameterStr);
            encParameterStr = ZKEncodingUtils
                    .encodeHex((sampleRsaAesTc.encryptData(dataKey, salt, encParameterStr.getBytes("utf-8"))));
            log.info("[^_^:20190626-1632-002-1] ????????? ?????????" + encParameterStr);
            mockReqBuilder.param(ZKRSA_ReqHeader.pn_parameter, encParameterStr);

            // ?????????????????? rsa ID?????????????????????????????????????????????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_id, "KKK");
            // ?????? rsa ?????????????????? ??????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_key, encDataKeyStr);
            mockReqBuilder.contentType(ZKContentType.X_FORM.getContentType());
            // ????????????
            mvcResult = this.mvc.perform(mockReqBuilder).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print()).andReturn();
            mockRes = mvcResult.getResponse();
            resEncData = mockRes.getContentAsByteArray();
            log.info("[^_^:20190625-1121-002-0] ????????? ????????????:" + new String(resEncData));
            resStr = new String(
                    ZKEncryptAesUtils.decrypt(ZKEncodingUtils.decodeHex(new String(resEncData)), dataKey, salt));
            log.info("[^_^:20190625-1121-002-1] ????????? ??????????????? " + resStr);

            resMap = ZKJsonUtils.jsonStrToMap(resStr);

            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + RequestParams.pn_pathVariable1,
                    resMap.get(RequestParams.pn_pathVariable1));
            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + RequestParams.pn_pathVariable2,
                    resMap.get(RequestParams.pn_pathVariable2));

            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + msg.value + RequestParams.pn_req_header,
                    resMap.get(RequestParams.pn_req_header));
            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + msg.value + RequestParams.pn_req_attr,
                    resMap.get(RequestParams.pn_req_attr));

            TestCase.assertEquals(
                    msg.normal_transferCipherNoReqBody + ZKJsonUtils.writeObjectJson(
                            new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs }),
                    resMap.get(RequestParams.pn_strs));
            TestCase.assertEquals(intValue * intValue, resMap.get(RequestParams.pn_int));
            log.info("[^_^:20190625-1121-001] === put ????????????????????????????????? ------------- ");

            /*** post application/x-www-form-urlencoded ***/
            mockReqBuilder = MockMvcRequestBuilders.post(url);
            // ???????????????
            mockReqBuilder.header(RequestParams.pn_req_header, msg.value + RequestParams.pn_req_header);
            // ??????????????????
            mockReqBuilder.requestAttr(RequestParams.pn_req_attr, msg.value + RequestParams.pn_req_attr);
            // ???????????????????????????
            paramMap.put(RequestParams.pn_strs,
                    new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs });
            paramMap.put(RequestParams.pn_int, intValue);
            encParameterStr = ZKJsonUtils.writeObjectJson(paramMap);
            log.info("[^_^:20190626-1632-002-0] ????????? ?????????" + encParameterStr);
            encParameterStr = ZKEncodingUtils
                    .encodeHex((sampleRsaAesTc.encryptData(dataKey, salt, encParameterStr.getBytes())));
            log.info("[^_^:20190626-1632-002-1] ????????? ?????????" + encParameterStr);
            mockReqBuilder.param(ZKRSA_ReqHeader.pn_parameter, encParameterStr);

            // ?????????????????? rsa ID?????????????????????????????????????????????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_id, "KKK");
            // ?????? rsa ?????????????????? ??????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_key, encDataKeyStr);
            mockReqBuilder.contentType(ZKContentType.X_FORM.getContentType());
            // ????????????
            mvcResult = this.mvc.perform(mockReqBuilder).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print()).andReturn();
            mockRes = mvcResult.getResponse();
            resEncData = mockRes.getContentAsByteArray();
            log.info("[^_^:20190625-1121-002-0] ????????? ????????????:" + new String(resEncData));
            resStr = new String(
                    ZKEncryptAesUtils.decrypt(ZKEncodingUtils.decodeHex(new String(resEncData)), dataKey, salt));
            log.info("[^_^:20190625-1121-002-1] ????????? ??????????????? " + resStr);

            resMap = ZKJsonUtils.jsonStrToMap(resStr);

            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + RequestParams.pn_pathVariable1,
                    resMap.get(RequestParams.pn_pathVariable1));
            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + RequestParams.pn_pathVariable2,
                    resMap.get(RequestParams.pn_pathVariable2));

            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + msg.value + RequestParams.pn_req_header,
                    resMap.get(RequestParams.pn_req_header));
            TestCase.assertEquals(msg.normal_transferCipherNoReqBody + msg.value + RequestParams.pn_req_attr,
                    resMap.get(RequestParams.pn_req_attr));

            TestCase.assertEquals(
                    msg.normal_transferCipherNoReqBody + ZKJsonUtils.writeObjectJson(
                            new String[] { msg.value + RequestParams.pn_strs, msg.value + RequestParams.pn_strs }),
                    resMap.get(RequestParams.pn_strs));
            TestCase.assertEquals(intValue * intValue, resMap.get(RequestParams.pn_int));
            log.info("[^_^:20190625-1121-001] === post application/x-www-form-urlencoded ????????????????????????????????? ------------- ");

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }

    }

    @Test
    public void testTransferCipherNo() {
        try {

            MockHttpServletRequestBuilder mockReqBuilder = null;
            MvcResult mvcResult = null;
            MockHttpServletResponse mockRes = null;
            String resStr = null;
            byte[] resEncData = null;

            String url = String.format("%s/transferCipherNo", this.baseUrl);

            ZKSampleRsaAesTransferCipherManager sampleRsaAesTc = (ZKSampleRsaAesTransferCipherManager) this.transferCipherManager;

            /*** ?????????????????? ***/
            mockReqBuilder = MockMvcRequestBuilders.get(url);

            // ????????????
            mvcResult = this.mvc.perform(mockReqBuilder).andExpect(MockMvcResultMatchers.status().isOk())
//                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            mockRes = mvcResult.getResponse();
            resStr = mockRes.getContentAsString();
            log.info("[^_^:20190625-1121-001] " + resStr);
            log.info("[^_^:20190625-1121-001] === ???????????????????????????????????? ------------- ");

            /*** ????????????????????? ***/
            byte[] publicKey = sampleRsaAesTc.getZKRSAKeyByRsaId(null).getPublicKey();
            byte[] dataKey = "???????????????????????????".getBytes();
            byte[] salt = ZKEncryptUtils.genSalt(dataKey);

            log.info("[^_^:20190626-1632-001-0] ????????? ?????????" + new String(dataKey));
            String encDataKeyStr = ZKEncodingUtils.encodeHex(ZKEncryptRsaUtils.encrypt(dataKey, publicKey));
            log.info("[^_^:20190626-1632-001-1] ????????? ?????????" + encDataKeyStr);

            /*** get ***/
            mockReqBuilder = MockMvcRequestBuilders.get(url);
            // ?????????????????? rsa ID?????????????????????????????????????????????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_id, "KKK");
            // ?????? rsa ?????????????????? ??????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_key, encDataKeyStr);
            // ????????????
            mvcResult = this.mvc.perform(mockReqBuilder).andExpect(MockMvcResultMatchers.status().isOk())
//                  .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            mockRes = mvcResult.getResponse();
            resEncData = mockRes.getContentAsByteArray();
            log.info("[^_^:20190625-1121-002-0] ????????? ????????????:" + new String(resEncData));
            resStr = new String(
                    ZKEncryptAesUtils.decrypt(ZKEncodingUtils.decodeHex(new String(resEncData)), dataKey, salt));
            log.info("[^_^:20190625-1121-002-1] ????????? ??????????????? " + resStr);
            log.info("[^_^:20190625-1121-001] === get ????????????????????????????????? ------------- ");

            /*** post ***/
            mockReqBuilder = MockMvcRequestBuilders.post(url);
            // ?????????????????? rsa ID?????????????????????????????????????????????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_id, "KKK");
            // ?????? rsa ?????????????????? ??????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_key, encDataKeyStr);
            // ????????????
            mvcResult = this.mvc.perform(mockReqBuilder).andExpect(MockMvcResultMatchers.status().isOk())
//                  .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            mockRes = mvcResult.getResponse();
            resEncData = mockRes.getContentAsByteArray();
            log.info("[^_^:20190625-1121-002-0] ????????? ????????????:" + new String(resEncData));
            resStr = new String(
                    ZKEncryptAesUtils.decrypt(ZKEncodingUtils.decodeHex(new String(resEncData)), dataKey, salt));
            log.info("[^_^:20190625-1121-002-1] ????????? ??????????????? " + resStr);
            log.info("[^_^:20190625-1121-001] === post ????????????????????????????????? ------------- ");

            /*** delete ***/
            mockReqBuilder = MockMvcRequestBuilders.delete(url);
            // ?????????????????? rsa ID?????????????????????????????????????????????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_id, "KKK");
            // ?????? rsa ?????????????????? ??????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_key, encDataKeyStr);
            // ????????????
            mvcResult = this.mvc.perform(mockReqBuilder).andExpect(MockMvcResultMatchers.status().isOk())
//                  .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            mockRes = mvcResult.getResponse();
            resEncData = mockRes.getContentAsByteArray();
            log.info("[^_^:20190625-1121-002-0] ????????? ????????????:" + new String(resEncData));
            resStr = new String(
                    ZKEncryptAesUtils.decrypt(ZKEncodingUtils.decodeHex(new String(resEncData)), dataKey, salt));
            log.info("[^_^:20190625-1121-002-1] ????????? ??????????????? " + resStr);
            log.info("[^_^:20190625-1121-001] === delete ????????????????????????????????? ------------- ");

            /*** put ***/
            mockReqBuilder = MockMvcRequestBuilders.put(url);
            // ?????????????????? rsa ID?????????????????????????????????????????????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_id, "KKK");
            // ?????? rsa ?????????????????? ??????????????????
            mockReqBuilder.header(ZKRSA_ReqHeader.pn_key, encDataKeyStr);
            // ????????????
            mvcResult = this.mvc.perform(mockReqBuilder).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print()).andReturn();
            mockRes = mvcResult.getResponse();
            resEncData = mockRes.getContentAsByteArray();
            log.info("[^_^:20190625-1121-002-0] ????????? ????????????:" + new String(resEncData));
            resStr = new String(
                    ZKEncryptAesUtils.decrypt(ZKEncodingUtils.decodeHex(new String(resEncData)), dataKey, salt));
            log.info("[^_^:20190625-1121-002-1] ????????? ??????????????? " + resStr);
            log.info("[^_^:20190625-1121-001] === put ????????????????????????????????? ------------- ");

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }

    }

    /*** ?????????????????? ***/
    @Test
    public void test() {
        try {

//            MockHttpServletRequestBuilder mockReqBuilder = null;
//            MvcResult mvcResult = null;
//            MockHttpServletResponse mockRes = null;
//            String resStr = null;
//
//            String url = "";
//
//            mockReqBuilder = MockMvcRequestBuilders.get(url);
//
//            mvcResult = this.mvc.perform(mockReqBuilder).andExpect(MockMvcResultMatchers.status().isOk())
////                    .andDo(MockMvcResultHandlers.print())
//                    .andReturn();
//            mockRes = mvcResult.getResponse();
//            resStr = mockRes.getContentAsString();
//            log.info("[^_^:20190320-0856-001] " + resStr);
////            myResMsg = JsonUtils.jsonStrToObject(resStr, MyResMsg.class);
////            TestCase.assertEquals("0", myResMsg.getErrCode());
//            TestCase.assertNotNull(resStr);

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }

    }

}
