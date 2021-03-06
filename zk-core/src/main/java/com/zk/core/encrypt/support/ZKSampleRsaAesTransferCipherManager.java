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
 * @Title: ZKSampleRsaAesTransferCipherManager.java 
 * @author Vinson 
 * @Package com.zk.core.encrypt.support 
 * @Description: TODO(simple description this file what to do.) 
 * @date Dec 18, 2019 2:52:27 PM 
 * @version V1.0   
*/
package com.zk.core.encrypt.support;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.core.encrypt.ZKRSAKey;
import com.zk.core.encrypt.ZKTransferCipherManager;
import com.zk.core.encrypt.utils.ZKEncryptAesUtils;
import com.zk.core.encrypt.utils.ZKEncryptRsaUtils;
import com.zk.core.encrypt.utils.ZKEncryptUtils;
import com.zk.core.exception.ZKUnknownException;
import com.zk.core.utils.ZKCollectionUtils;
import com.zk.core.utils.ZKEncodingUtils;
import com.zk.core.utils.ZKExceptionsUtils;
import com.zk.core.utils.ZKJsonUtils;
import com.zk.core.utils.ZKStreamUtils;
import com.zk.core.utils.ZKStringUtils;
import com.zk.core.web.wrapper.ZKRequestWrapper;
import com.zk.core.web.wrapper.ZKResponseWrapper;

/** 
* @ClassName: ZKSampleRsaAesTransferCipherManager 
* @Description: TODO(simple description this class what to do.) 
* @author Vinson 
* @version 1.0 
*/
public class ZKSampleRsaAesTransferCipherManager implements ZKTransferCipherManager {

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * rsa ?????????????????????????????????????????????
     */
    public static interface ZKRSA_ReqHeader {

        /**
         * RSA ??????ID???????????????????????????????????????
         */
        public static final String pn_id = "_rsa";

        /**
         * ???????????????????????????????????????????????????
         */
        public static final String pn_key = "_encKey";

        /**
         * ?????????
         */
        public static final String pn_parameter = "_encData";
    }

    private static interface EncInfo {

        // ????????????
        public static final String dataEncKey = "_dataEncKey";

        // ???????????? ???
        public static final String dataEncKeySalt = "_dataEncKeySalt";
    }

    @Override
    public ZKRequestWrapper decrypt(HttpServletRequest request) {

        try {
//            log.info("[^_^:20190625-1723-001] ZKTransferCipherFilter ??????????????????");

            String paramStr = request.getParameter(ZKRSA_ReqHeader.pn_parameter);
            String encKeyStr = request.getHeader(ZKRSA_ReqHeader.pn_key);
            String rsaId = request.getHeader(ZKRSA_ReqHeader.pn_id);

            // ??? rsa ??????
            ZKRSAKey zkRsaKey = this.getZKRSAKeyByRsaId(rsaId);
            // ????????????????????????
//            log.info("[^_^:20190625-1540-001-0] ????????? ?????? -> " + encKeyStr);
            byte[] dataEncKey = this.decryptDataKey(zkRsaKey.getPrivateKey(), ZKEncodingUtils.decodeHex(encKeyStr));
//            log.info("[^_^:20190625-1540-001-1] ????????? ?????? -> " + ZKEncodingUtils.encodeHex(dataEncKey));

            // ??????
            byte[] salt = ZKEncryptUtils.genSalt(dataEncKey);
            Map<String, String[]> zkParameterMap = decryptParameterMap(dataEncKey, salt, paramStr);
            byte[] reqBody = decryptRequestBody(dataEncKey, salt, request);

            ZKRequestWrapper zkReq = new ZKRequestWrapper(request, zkParameterMap, reqBody);

            zkReq.getEncInfo().put(EncInfo.dataEncKey, dataEncKey);
            zkReq.getEncInfo().put(EncInfo.dataEncKeySalt, salt);

            return zkReq;
        }
        catch(UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw ZKExceptionsUtils.unchecked(e);
        }
    }

    // ??????????????????HttpServletRequest requestBody ??????
    protected byte[] decryptRequestBody(byte[] dataEncKey, byte[] salt, HttpServletRequest req) {

        ServletInputStream sis = null;
        ByteArrayOutputStream os = null;
        try {
            sis = req.getInputStream();
            os = new ByteArrayOutputStream();
            ZKStreamUtils.readAndWrite(sis, os);
            byte[] reqBody = os.toByteArray();
//            log.info("[^_^:20190627-1031-003-0] ????????? reqBody -> " + new String(reqBody));
            reqBody = this.decryptData(dataEncKey, salt, ZKEncodingUtils.decodeHex(new String(reqBody)));
//            log.info("[^_^:20190627-1031-003-1] ????????? reqBody -> " + new String(reqBody));

            return reqBody;
        }
        catch(IOException e) {
            throw new ZKUnknownException("?????? request body ??????!", e);
        } finally {
            ZKStreamUtils.closeStream(sis);
            ZKStreamUtils.closeStream(os);
        }

    }

    /**
     * ?????????????????????HttpServletRequest request.getParameterMap() ????????????????????? map
     *
     * @Title: decryptParameterMap
     * @Description: ?????????????????????HttpServletRequest request.getParameterMap() ?????????????????????
     *               map
     * @author Vinson
     * @date Jun 27, 2019 9:46:08 AM
     * @param encKey
     *            ??????????????????????????????????????????
     * @param paramStr
     *            ??????????????????
     * @return
     * @throws UnsupportedEncodingException
     * @return Map<String,String[]>
     */
    protected Map<String, String[]> decryptParameterMap(byte[] dataEncKey, byte[] salt, String paramStr)
            throws UnsupportedEncodingException {

        if (ZKStringUtils.isEmpty(paramStr)) {
            return new HashMap<String, String[]>();
        }

        // ???????????? parameter
//        log.info("[^_^:20190625-1540-002-0] ????????? ?????????paramStr -> " + paramStr);
        paramStr = new String(this.decryptData(dataEncKey, salt, ZKEncodingUtils.decodeHex(paramStr)));
//        log.info("[^_^:20190625-1540-002-1] ????????? ?????????paramStr -> " + paramStr);
        return ZKCollectionUtils.mapToReqParametMap(ZKJsonUtils.jsonStrToMap(paramStr));

    }

    @Override
    public void encrypt(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        if (request instanceof ZKRequestWrapper && response instanceof ZKResponseWrapper) {
            ZKRequestWrapper zkReq = (ZKRequestWrapper) request;
            ZKResponseWrapper zkRes = (ZKResponseWrapper) response;
            OutputStream out = null;
            try {
                byte[] dataEncKey = (byte[]) zkReq.getEncInfo().get(EncInfo.dataEncKey);
                byte[] salt = (byte[]) zkReq.getEncInfo().get(EncInfo.dataEncKeySalt);
                byte[] resData = zkRes.getData();
                resData = this.encryptData(dataEncKey, salt, resData);
                resData = ZKEncodingUtils.encodeHex(resData).getBytes();
                out = zkRes.getResponse().getOutputStream();
                out.write(resData);
                out.flush();
                out.close();
            }
            catch(IOException e) {
                throw ZKExceptionsUtils.unchecked(e);
            } finally {
                ZKStreamUtils.closeStream(out);
            }
        }
        else {
            // ????????????????????????
            log.error("[^_^:20190627-1121-001] ???????????????????????????????????????");
            throw new ZKUnknownException("???????????????????????????????????????");
        }
    }

    @Override
    public boolean isEnc(HttpServletRequest request) {

        String rsaId = request.getHeader(ZKRSA_ReqHeader.pn_id);
        if (rsaId == null) {
            return false;
        }
        return true;
    }

    /*** ************************************************ ***/
    private ZKRSAKey zkRSAKey = null;

    // ?????? rsa ID ???RSA ??????
    public ZKRSAKey getZKRSAKeyByRsaId(String rsaId) {
        try {
            if (zkRSAKey == null) {
                zkRSAKey = ZKEncryptRsaUtils.genZKRSAKey(2048, new SecureRandom());
            }
            return zkRSAKey;
        }
        catch(Exception e) {
            throw new ZKUnknownException("??? RSA ????????????", e);
        }

    }

    // ?????? ???????????? ?????????????????? RSA ??????????????? ???????????? ????????????????????????????????????????????? rsa ?????????
    protected ZKRSAKey getZKRSAKeyByAppId(String appId) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * AES ????????????
     *
     * @Title: decryptData
     * @Description: AES ????????????
     * @author Vinson
     * @date Jun 27, 2019 9:44:26 AM
     * @param encKey
     * @param content
     * @return
     * @return byte[]
     */
    private byte[] decryptData(byte[] encKey, byte[] salt, byte[] content) {

        try {
            return ZKEncryptAesUtils.decrypt(content, encKey, salt);
        }
        catch(Exception e) {
            throw new ZKUnknownException("AES ??????????????????", e);
        }
    }

    /**
     * AES ????????????
     *
     * @Title: encryptData
     * @Description: AES ????????????
     * @author Vinson
     * @date Jun 27, 2019 9:44:44 AM
     * @param encKey
     * @param content
     * @return
     * @return byte[]
     */
    public byte[] encryptData(byte[] encKey, byte[] content) {
        try {
            return encryptData(encKey, ZKEncryptUtils.genSalt(encKey), content);
        }
        catch(Exception e) {
            throw new ZKUnknownException("AES ?????????????????? ??????", e);
        }
    }

    public byte[] encryptData(byte[] encKey, byte[] salt, byte[] content) {
        try {
            return ZKEncryptAesUtils.encrypt(content, encKey, salt);
        }
        catch(Exception e) {
            throw new ZKUnknownException("AES ?????????????????? ??????", e);
        }
    }

    /**
     * RSA ????????????????????????
     *
     * @Title: decryptDataKey
     * @Description: RSA ????????????????????????
     * @author Vinson
     * @date Jun 27, 2019 9:45:02 AM
     * @param privateKey
     * @param encKey
     * @return
     * @return byte[]
     */
    private byte[] decryptDataKey(byte[] privateKey, byte[] encKey) {
        try {
            return ZKEncryptRsaUtils.decrypt(encKey, privateKey);
        }
        catch(Exception e) {
            throw new ZKUnknownException("RSA ?????????????????? ?????? ??????", e);
        }
    }

    /**
     * RSA ????????????????????????
     *
     * @Title: encryptDataKey
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Jun 27, 2019 9:45:24 AM
     * @param publicKey
     * @param encKey
     * @return
     * @return byte[]
     */
    public byte[] encryptDataKey(byte[] publicKey, byte[] encKey) {
        try {
            return ZKEncryptRsaUtils.encrypt(encKey, publicKey);
        }
        catch(Exception e) {
            throw new ZKUnknownException("RSA ?????????????????? ?????? ??????", e);
        }
    }

}
