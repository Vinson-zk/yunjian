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
 * @Title: ZKHttpApiUtilsTest.java 
 * @author Vinson 
 * @Package com.zk.core.web.utils 
 * @Description: TODO(simple description this file what to do.) 
 * @date Dec 13, 2019 12:00:10 AM 
 * @version V1.0   
*/
package com.zk.core.web.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.zk.core.helper.ZKCoreEntity;
import com.zk.core.helper.ZKCoreTestHelperMvcSpringBootMain;
import com.zk.core.helper.configuration.ZKCoreChildConfiguration;
import com.zk.core.helper.configuration.ZKCoreParentConfiguration;
import com.zk.core.helper.controller.ZKCoreHttpApiUtilsController;
import com.zk.core.utils.ZKEnvironmentUtils;
import com.zk.core.utils.ZKFileUtils;
import com.zk.core.utils.ZKJsonUtils;
import com.zk.core.utils.ZKStreamUtils;
import com.zk.core.web.controller.ZKCoreFileControllerTest;

import junit.framework.TestCase;

/** 
* @ClassName: ZKHttpApiUtilsTest 
* @Description: TODO(simple description this class what to do.) 
* @author Vinson 
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ZKCoreParentConfiguration.class,
        ZKCoreChildConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ZKHttpApiUtilsTest {

    static String baseUrl;

    static String baseFileUrl;

    static {
        try {
            ZKCoreTestHelperMvcSpringBootMain.exit();
        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }

    }

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        baseUrl = String.format("http://127.0.0.1:%s/%s/%s/httpApi", port,
                ZKEnvironmentUtils.getString("zk.path.admin", "zk"), ZKEnvironmentUtils.getString("zk.path.core", "c"));
        baseFileUrl = String.format("http://127.0.0.1:%s/%s/%s/file", port,
                ZKEnvironmentUtils.getString("zk.path.admin", "zk"), ZKEnvironmentUtils.getString("zk.path.core", "c"));
        System.out.println("[^_^:20191218-1556-001] baseUrl:" + baseUrl);
        System.out.println("[^_^:20191218-1556-002] baseFileUrl:" + baseFileUrl);

    }

    @Test
    public void testGet() {
        try {
            String url = "";
            ByteArrayOutputStream os = null;
            StringBuffer outStringBuffer = null;
            String resStr = null;
            int resStatusCode = 0;
            String param = "";

            param = "????????????????????????????????????";
            url = baseUrl + "/get?param=" + param;
            outStringBuffer = new StringBuffer();
            resStatusCode = ZKHttpApiUtils.get(url, null, outStringBuffer);
            TestCase.assertEquals(200, resStatusCode);
            resStr = outStringBuffer.toString();
            TestCase.assertEquals(ZKCoreHttpApiUtilsController.msg_index + param, resStr);

            os = new ByteArrayOutputStream();
            resStatusCode = ZKHttpApiUtils.get(url, null, null, os, null);
            TestCase.assertEquals(200, resStatusCode);
            resStr = new String(os.toByteArray());
            TestCase.assertEquals(ZKCoreHttpApiUtilsController.msg_index + param, resStr);
            ZKStreamUtils.closeStream(os);

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

    @Test
    public void testPost() {
        try {
            String url = "";
            ByteArrayOutputStream os = null;
            StringBuffer outStringBuffer = null;
            String resStr = null;
            int resStatusCode = 0;
            String param = "";

            param = "??????????????????????????????????????????";
            url = baseUrl + "/get?param=" + param;
            outStringBuffer = new StringBuffer();
            resStatusCode = ZKHttpApiUtils.post(url, null, null, null, null, null, outStringBuffer, null);
            TestCase.assertEquals(200, resStatusCode);
            resStr = outStringBuffer.toString();
            TestCase.assertEquals(ZKCoreHttpApiUtilsController.msg_index + param, resStr);

            os = new ByteArrayOutputStream();
            resStatusCode = ZKHttpApiUtils.post(url, (InputStream) null, null, null, null, null, os, null);
            TestCase.assertEquals(200, resStatusCode);
            resStr = new String(os.toByteArray());
            TestCase.assertEquals(ZKCoreHttpApiUtilsController.msg_index + param, resStr);
            ZKStreamUtils.closeStream(os);

            url = baseUrl + "/post";
            param = "?????????????????????????????????????????????";
            url = baseUrl + "/post?param=" + param;
            outStringBuffer = new StringBuffer();
            resStatusCode = ZKHttpApiUtils.post(url, null, null, null, null, null, outStringBuffer, null);
            TestCase.assertEquals(200, resStatusCode);
            resStr = outStringBuffer.toString();
            TestCase.assertEquals(ZKCoreHttpApiUtilsController.msg_index + param, resStr);

            os = new ByteArrayOutputStream();
            resStatusCode = ZKHttpApiUtils.post(url, (InputStream) null, null, null, null, null, os, null);
            TestCase.assertEquals(200, resStatusCode);
            resStr = new String(os.toByteArray());
            TestCase.assertEquals(ZKCoreHttpApiUtilsController.msg_index + param, resStr);
            ZKStreamUtils.closeStream(os);

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

    @Test
    public void testPostJson() {
        try {
            String url = "";
            ByteArrayOutputStream os = null;
            StringBuffer outStringBuffer = null;
            String resStr = null;
            int resStatusCode = 0;
            String param = "";
            ZKCoreEntity entity = null;

            entity = new ZKCoreEntity();
            entity.setName("name ????????????????????????");
            entity.setAge(88);
            param = "??????????????????";
            url = baseUrl + "/postJson?param=" + param;
            os = new ByteArrayOutputStream();
            resStatusCode = ZKHttpApiUtils.postJson(url, ZKJsonUtils.writeObjectJson(entity), null, null, null, os,
                    null);
            TestCase.assertEquals(200, resStatusCode);
            resStr = new String(os.toByteArray());
            entity.setRemark(param);
            TestCase.assertEquals(ZKJsonUtils.writeObjectJson(entity), resStr);
            ZKStreamUtils.closeStream(os);

            entity = new ZKCoreEntity();
            entity.setName("name ??????????????????????????????????????????");
            entity.setAge(88);
            param = "????????????????????????????????????";
            url = baseUrl + "/postJson?param=" + param;
            outStringBuffer = new StringBuffer();
            resStatusCode = ZKHttpApiUtils.postJson(url, ZKJsonUtils.writeObjectJson(entity), null, null, null,
                    outStringBuffer, null);
            TestCase.assertEquals(200, resStatusCode);
            resStr = outStringBuffer.toString();
            entity.setRemark(param);
            TestCase.assertEquals(ZKJsonUtils.writeObjectJson(entity), resStr);

            entity = new ZKCoreEntity();
            entity.setName("name ??????????????????????????????????????????");
            entity.setAge(99);
            param = "???????????????????????????????????????";
            url = baseUrl + "/postJson?param=" + param;
            outStringBuffer = new StringBuffer();
            resStatusCode = ZKHttpApiUtils.postJson(url, ZKJsonUtils.writeObjectJson(entity), null, null,
                    outStringBuffer);
            TestCase.assertEquals(200, resStatusCode);
            resStr = outStringBuffer.toString();
            entity.setRemark(param);
            TestCase.assertEquals(ZKJsonUtils.writeObjectJson(entity), resStr);

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

    private final static String filePath = ZKCoreFileControllerTest.filePath;

    private final static String filePathSource = ZKCoreFileControllerTest.filePathSource;

    private final static String filePathTarget = ZKCoreFileControllerTest.filePathTarget;

    @Test
    public void testUploadMultipart() {

        // ?????????????????????????????????????????????
        ZKCoreFileControllerTest.deleteTestData();

        try {
            String url = "";
            ByteArrayOutputStream os = null;
            String resStr = null;
            int resStatusCode = 0;
            File file = null;
            String fileName = null;
            String fileContent = "";
            List<String> fs = null;
            Map<File, String> files = null;
            int fileCount = 0;

            files = new HashMap<File, String>();
            // ????????????
            fileCount = 2;
            for (int i = 1; i < fileCount + 1; ++i) {
                fileName = "API utils uploadMultipart ????????????1 ??????" + i + ".txt";
                fileContent = "????????????????????????????????????[" + i + "]";
                file = ZKFileUtils.createFile(filePath + File.separator + filePathSource, fileName, true);
                ZKFileUtils.writeFile(fileContent.getBytes(), file, false);
                if (i == 1) {
                    files.put(file, "f1");
                }
                else {
                    files.put(file, "fs");
                }
            }
            
            url = baseFileUrl + "/uploadMultipart";
            /*** uploadFileByMultipart ???????????? 1 ***/
            os = new ByteArrayOutputStream();
            resStatusCode = ZKHttpApiUtils.uploadFileByMultipart(url, files, null, null, os, null);
            TestCase.assertEquals(200, resStatusCode);
            resStr = new String(os.toByteArray());
            System.out.println("[^_^:20191217-2317-001] resStr: " + resStr);
            fs = ZKJsonUtils.jsonStrToList(resStr);
            ZKStreamUtils.closeStream(os);
            TestCase.assertEquals(fileCount, fs.size());

            for (String fStr : fs) {
                file = new File(fStr);
                resStr = new String(ZKFileUtils.readFile(file));
                file = new File(fStr.replace(filePathTarget, filePathSource));
                fileContent = new String(ZKFileUtils.readFile(file));
                TestCase.assertEquals(fileContent, resStr);
            }

            /*** uploadFileByMultipart ???????????? 2 ***/
            files = new HashMap<File, String>();
            // ????????????
            fileCount = 4;
            for (int i = 1; i < fileCount + 1; ++i) {
                fileName = "API utils uploadMultipart ????????????2 ??????" + i + ".txt";
                fileContent = "????????????????????????????????????????????????[" + i + "]";
                file = ZKFileUtils.createFile(filePath + File.separator + filePathSource, fileName, true);
                ZKFileUtils.writeFile(fileContent.getBytes(), file, false);
                if (i == 1) {
                    files.put(file, "f1");
                }
                else {
                    files.put(file, "fs");
                }
            }

            os = new ByteArrayOutputStream();
            resStatusCode = ZKHttpApiUtils.uploadFileByMultipart(url, null, files, null, null, os, null);
            TestCase.assertEquals(200, resStatusCode);
            resStr = new String(os.toByteArray());
            System.out.println("[^_^:20191217-2317-001] resStr: " + resStr);
            fs = ZKJsonUtils.jsonStrToList(resStr);
            ZKStreamUtils.closeStream(os);
            TestCase.assertEquals(fileCount, fs.size());

            for (String fStr : fs) {
                file = new File(fStr);
                resStr = new String(ZKFileUtils.readFile(file));
                file = new File(fStr.replace(filePathTarget, filePathSource));
                fileContent = new String(ZKFileUtils.readFile(file));
                TestCase.assertEquals(fileContent, resStr);
            }

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

    @Test
    public void testUploadStream() {

        // ?????????????????????????????????????????????
        ZKCoreFileControllerTest.deleteTestData();

        try {
            String url = "";
            ByteArrayOutputStream os = null;
            String resStr = null;
            int resStatusCode = 0;
            File file = null;
            String fileName = null;
            String fileContent = "";

            fileName = "API utils uploadStream ??????.txt";

            // ????????????
            file = ZKFileUtils.createFile(filePath + File.separator + filePathSource, fileName, true);
            fileContent = "????????????????????????????????????????????????";
            ZKFileUtils.writeFile(fileContent.getBytes(), file, false);

            url = baseFileUrl + "/uploadStream";
            os = new ByteArrayOutputStream();
            resStatusCode = ZKHttpApiUtils.uploadFileByStream(url, file, fileName, null, null, os, null);
            TestCase.assertEquals(200, resStatusCode);
            resStr = new String(os.toByteArray());
            System.out.println("[^_^:20191217-2317-002] resStr: " + resStr);
            file = new File(filePath + File.separator + filePathSource + File.separator + fileName);
            fileContent = new String(ZKFileUtils.readFile(file));
//            file = new File(resStr);
            resStr = new String(ZKFileUtils.readFile(file));
            TestCase.assertEquals(fileContent, resStr);
            ZKStreamUtils.closeStream(os);
        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        }
    }

}
