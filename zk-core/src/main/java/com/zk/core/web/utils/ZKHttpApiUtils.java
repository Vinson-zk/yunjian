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
 * @Title: ZKHttpApiUtils.java 
 * @author Vinson 
 * @Package com.zk.core.web.utils 
 * @Description: TODO(simple description this file what to do.) 
 * @date Dec 12, 2019 9:54:59 AM 
 * @version V1.0   
*/
package com.zk.core.web.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.core.commons.ZKContentType;
import com.zk.core.commons.ZKFileType;
import com.zk.core.commons.ZKX509TrustManager;
import com.zk.core.exception.ZKCodeException;
import com.zk.core.utils.ZKEnvironmentUtils;
import com.zk.core.utils.ZKExceptionsUtils;
import com.zk.core.utils.ZKFileUtils;
import com.zk.core.utils.ZKJsonUtils;
import com.zk.core.utils.ZKStreamUtils;
import com.zk.core.utils.ZKStringUtils;

/** 
* @ClassName: ZKHttpApiUtils 
* @Description: TODO(simple description this class what to do.) 
* @author Vinson 
* @version 1.0 
*/
public class ZKHttpApiUtils {

    private final static Logger logger = LoggerFactory.getLogger(ZKHttpApiUtils.class);

    private final static String default_charset = "UTF-8";

    // ???????????????
    private static PoolingHttpClientConnectionManager httpConnectionPool;

    // ????????????
    private static RequestConfig requestConfig;

    private static interface defaultConfig {
        // ??????????????? ?????? 100
        public static final int maxTotal = 2;

        // ???????????? ?????? 2
        public static final int maxPerRoute = 2;

        // socket ?????????????????????????????? ?????? 50000 ??????
        public static final int socketTimeout = 50000;

        // connect ???????????????????????????????????? 50000 ??????
        public static final int connectTimeout = 50000;

        // request ???????????????????????????????????? 50000 ??????
        public static final int requestTimeout = 50000;
    }

    static {
        init(0, 0, 0, 0, 0, null);
    }

    /**
     * 
     *
     * @Title: init
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Jun 28, 2019 2:09:25 PM
     * @param maxTotal
     *            ??????????????? ?????? 100; ????????? 0 ??????????????????
     * @param maxPerRoute
     *            ???????????? ?????? 2; ????????? 0 ??????????????????
     * @param socketTimeout
     *            socket ?????????????????????????????? ?????? 50000 ??????; ????????? 0 ??????????????????
     * @param connectTimeout
     *            connect ???????????????????????????????????? 50000 ??????; ????????? 0 ??????????????????
     * @param requestTimeout
     *            request ???????????????????????????????????? 50000 ??????; ????????? 0 ??????????????????
     * @param tm
     * @return void
     */
    public static void init(int maxTotal, int maxPerRoute, int socketTimeout, int connectTimeout, int requestTimeout,
            TrustManager[] tm) {

        // ??????????????? ?????? 100
        if (maxTotal == 0) {
            maxTotal = defaultConfig.maxTotal;
        }
        // ???????????? ?????? 2
        if (maxPerRoute == 0) {
            maxPerRoute = defaultConfig.maxPerRoute;
        }
        // socket ?????????????????????????????? ?????? 50000 ??????
        if (socketTimeout == 0) {
            socketTimeout = defaultConfig.socketTimeout;
        }
        // connect ???????????????????????????????????? 50000 ??????
        if (connectTimeout == 0) {
            connectTimeout = defaultConfig.connectTimeout;
        }
        // request ???????????????????????????????????? 50000 ??????
        if (requestTimeout == 0) {
            requestTimeout = defaultConfig.requestTimeout;
        }

        if (tm == null) {
            tm = new TrustManager[] { new ZKX509TrustManager() };
        }

        try {
            // SSLContextBuilder builder = new SSLContextBuilder();
            // builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            // SSLConnectionSocketFactory sslcsf = new
            // SSLConnectionSocketFactory(builder.build());

            /**************************************************/

            // ??????SSLContext????????? HTTPS ??? SSL ??????
            SSLContext sslContext = SSLContext.getInstance("SSL");
//            TrustManager[] tm = { new ZKX509TrustManager() };
            // ?????????
            sslContext.init(null, tm, new java.security.SecureRandom());
            // // ??????SSLSocketFactory??????
            // SSLSocketFactory sslsf = sslContext.getSocketFactory();
            SSLConnectionSocketFactory sslcsf = new SSLConnectionSocketFactory(sslContext);

            /**************************************************/
            // ?????????????????? HTTP ??? HTPPS???????????? ????????????http???https???????????????socket?????????????????????
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslcsf)
                    .build();

            // ????????????????????????
            httpConnectionPool = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            // ???????????????????????????200??????????????????????????????????????????????????????
            httpConnectionPool.setMaxTotal(maxTotal);
            // ??????????????????
            httpConnectionPool.setDefaultMaxPerRoute(maxPerRoute);
            // ?????????????????????????????????requestConfig; ????????????????????????
            requestConfig = RequestConfig.custom().setConnectionRequestTimeout(requestTimeout)
                    .setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        logger.info("[20170912-0819-001] HttpUtils init success!");

    }

    /**
     * ??????????????????????????????
     * @return
     */
    public static CloseableHttpClient getHttpClient() {

        return getHttpClient(null);
    }

    /***
     * ??????????????????????????????
     * @param proxy
     *            ????????????,????????????
     * @return CloseableHttpClient
     */
    public static CloseableHttpClient getHttpClient(HttpHost proxy) {

        HttpClientBuilder clientBuilder = HttpClients.custom();
        // ?????????????????????
        clientBuilder.setConnectionManager(httpConnectionPool);
        // ??????????????????
        clientBuilder.setDefaultRequestConfig(requestConfig);
        // ??????????????????
        clientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
        // ????????????
        if (proxy != null) {
            // RequestConfig defaultRequestConfig =
            // RequestConfig.custom().setProxy(proxy).build();
            // clientBuilder.setDefaultRequestConfig(defaultRequestConfig);
            clientBuilder.setRoutePlanner(new DefaultProxyRoutePlanner(proxy));
        }
        return clientBuilder.build();
    }

    /**
     * ????????? api ?????? url ?????????
     *
     * @Title: getWxApiUrlConfig
     * @Description: TODO(simple description this method what to do.)
     * @author zk
     * @date 2018???9???14??? ??????2:34:23
     * @param key
     *            ??????API?????? key
     * @return
     * @return String
     */
    public static String getUrlConfig(String key) {
        String url = ZKEnvironmentUtils.getString(key);
        if (ZKStringUtils.isEmpty(url)) {
            logger.error("[>_<:20180918-0822-001] ZK: ????????? API ????????????; {key:{}, url:{}}", key, url);
            // ???????????????
//            throw new ZKUnknownException(String.format("????????? API ????????????; {key:%s, url:%s}", key, url));
            // ????????????????????????
            throw new ZKCodeException("zk.000003", "????????????????????????", null,
                    String.format("????????? API ????????????; {key:%s, url:%s}", key, url));
        }
        return url;
    }

//    /**
//     * ????????????????????????????????????
//     */
//    private static byte[] getContent(CloseableHttpResponse chRes) {
//        HttpEntity entity = chRes.getEntity();
//        try {
//            byte[] contentByte = EntityUtils.toByteArray(chRes.getEntity());
//            EntityUtils.consume(entity);
//            return contentByte;
//        }
//        catch(IOException e) {
//            logger.error("[>_<:20180907-1308-001] ????????????????????????????????????");
//            e.printStackTrace();
//        } finally {
//            if (chRes != null) {
//                // ????????????
//                // HttpClientUtils.closeQuietly(httpClient);
//                HttpClientUtils.closeQuietly(chRes);
//            }
//        }
//        return null;
//    }

    private static void setHeadersByMap(HttpRequestBase req, Map<String, String> headers) {
        // ???????????????
        if (headers != null) {
            Iterator<Entry<String, String>> hIterator = headers.entrySet().iterator();
            Entry<String, String> entry = null;
            while (hIterator.hasNext()) {
                entry = hIterator.next();
                req.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * ????????????
     *
     * @Title: sendRequest
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Dec 12, 2019 3:44:41 PM
     * @param reqBase
     *            ??????
     * @param proxy
     *            ??????
     * @param os
     *            ?????????????????????
     * @param outHeaders
     *            ??????????????????
     * @return int ?????????
     */
    public static int sendRequest(HttpRequestBase reqBase, HttpHost proxy, OutputStream os,
            Map<String, String> outHeaders) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        int resStatusCode = -1;
        try {
            // ??????????????????
            reqBase.setConfig(requestConfig);
            // ???????????????httpClient??????.
            httpClient = getHttpClient(proxy);
            // ????????????
            response = httpClient.execute(reqBase);
            if (response != null) {
                resStatusCode = response.getStatusLine().getStatusCode();
                // ??????????????????
                if (os != null) {
                    ZKWebUtils.readResponse(response, os);
                }
                // ???????????????
                if (outHeaders != null) {
                    Header[] hs = response.getAllHeaders();
                    for (Header h : hs) {
                        outHeaders.put(h.getName(), h.getValue());
                    }
                }
            }
        }
        catch(Exception e) {
            logger.error("[>_<:20180907-1247-001] ???????????????url:{}", reqBase.getURI());
            e.printStackTrace();
        } finally {
            if (response != null) {
                // ????????????
//                HttpClientUtils.closeQuietly(httpClient);
                HttpClientUtils.closeQuietly(response);
            }
        }
        return resStatusCode;
    }

    /***
     * GET ???????????????????????????
     *
     * @Title: get
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Dec 12, 2019 4:24:13 PM
     * @param url
     *            ?????? url
     * @param headers
     *            ?????????
     * @param proxy
     *            ??????
     * @param os
     *            ?????????????????????
     * @param outHeaders
     *            ??????????????????
     * @return int ?????????
     */
    public static int get(String url, Map<String, String> headers, HttpHost proxy, OutputStream os,
            Map<String, String> outHeaders) {
        logger.info("[^_^:20191212-1623-001] ?????? get ??????:{}, headers:{}", url, ZKJsonUtils.writeObjectJson(headers));
        // ??????get??????
        HttpGet httpGet = new HttpGet(url);
        // ???????????????
        setHeadersByMap(httpGet, headers);
        return sendRequest(httpGet, proxy, os, outHeaders);
    }

    /**
     * get ??????????????????????????????????????????????????????
     *
     * @Title: get
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Dec 12, 2019 4:08:46 PM
     * @param url
     *            ?????? url
     * @param headers
     *            ?????????
     * @param proxy
     *            ??????
     * @param outStringBuffer
     *            ???????????????????????????
     * @param outCharset
     *            ???????????????????????????????????????
     * @param outHeaders
     *            ???????????????
     * @return int
     */
    public static int get(String url, Map<String, String> headers, HttpHost proxy, StringBuffer outStringBuffer,
            String outCharset, Map<String, String> outHeaders) {
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            int statusCode = get(url, headers, proxy, os, outHeaders);
            if (ZKStringUtils.isEmpty(outCharset)) {
                outCharset = default_charset;
            }
            outStringBuffer.append(new String(os.toByteArray(), outCharset));
            return statusCode;
        }
        catch(UnsupportedEncodingException e) {
            throw new RuntimeException("??????????????????", e);
        } finally {
            if (os != null) {
                ZKStreamUtils.closeStream(os);
            }
        }
    }

    /*** get ????????????????????? UTF-8 ??????????????????????????????????????? ***/
    public static int get(String url, Map<String, String> headers, StringBuffer outStringBuffer) {
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            int statusCode = get(url, headers, null, os, null);
            outStringBuffer.append(new String(os.toByteArray(), default_charset));
            return statusCode;
        }
        catch(UnsupportedEncodingException e) {
            throw new RuntimeException("??????????????????", e);
        } finally {
            if (os != null) {
                ZKStreamUtils.closeStream(os);
            }
        }
    }

    /*** get ????????????????????? UTF-8 ??????????????????????????????????????? ***/
    public static int get(String url, Map<String, String> headers, OutputStream os, Map<String, String> outHeaders) {
        return get(url, headers, null, os, outHeaders);
    }

    /***
     * POSR ??????
     *
     * @Title: post
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Dec 12, 2019 4:25:25 PM
     * @param url
     *            ?????? url
     * @param content
     *            ????????????
     * @param contentType
     *            ?????????????????????
     * @param reqCharset
     *            ????????????????????????????????????
     * @param headers
     *            ?????????
     * @param proxy
     *            ??????
     * @param os
     *            ?????????????????????
     * @param outHeaders
     *            ??????????????????
     * @return int ????????????
     */
    public static int post(String url, String content, String reqContentType, String reqCharset,
            Map<String, String> headers, HttpHost proxy, OutputStream os, Map<String, String> outHeaders) {
        logger.info("[^_^:20191212-1627-001] ?????? post ??????:{}, content:{}, reqContentType:{}, reqCharset:{}, headers:{}",
                url, content, reqContentType, reqCharset, ZKJsonUtils.writeObjectJson(headers));

        // ?????? httpPost ??????
        HttpPost httpPost = new HttpPost(url);
        // ???????????????
        setHeadersByMap(httpPost, headers);
        // ??????????????????
        if (content != null && content.trim().length() > 0) {
            reqCharset = (ZKStringUtils.isEmpty(reqCharset)) ? default_charset : reqCharset;
            StringEntity stringEntity = new StringEntity(content, reqCharset);
            if (reqContentType != null) {
                stringEntity.setContentType(reqContentType);
            }
            else {
                stringEntity.setContentType(ZKContentType.X_FORM_UTF8.toString());
            }
            stringEntity.setContentEncoding(reqCharset);
            httpPost.setEntity(stringEntity);
        }
        return sendRequest(httpPost, proxy, os, outHeaders);
    }

    /*** POSR ?????? ??????????????????????????????????????? ***/
    public static int post(String url, InputStream contentIs, String reqContentType, String reqCharset,
            Map<String, String> headers, HttpHost proxy, OutputStream os, Map<String, String> outHeaders) {
        logger.info("[^_^:20191212-1627-002] ?????? post ??????:{}, content:{}, reqContentType:{}, reqCharset:{}, headers:{}",
                url, "contentIs", reqContentType, reqCharset, ZKJsonUtils.writeObjectJson(headers));

        // ?????? httpPost ??????
        HttpPost httpPost = new HttpPost(url);
        // ???????????????
        setHeadersByMap(httpPost, headers);
        // ??????????????????
        if (contentIs != null) {
            InputStreamEntity inputStreamEntity = new InputStreamEntity(contentIs);
            reqCharset = (ZKStringUtils.isEmpty(reqCharset)) ? default_charset : reqCharset;
            if (reqContentType != null) {
                inputStreamEntity.setContentType(reqContentType);
            }
            else {
                inputStreamEntity.setContentType(ZKContentType.X_FORM_UTF8.toString());
            }
            inputStreamEntity.setContentEncoding(reqCharset);
            httpPost.setEntity(inputStreamEntity);
        }
        return sendRequest(httpPost, proxy, os, outHeaders);
    }


    /*** POSR ?????? ????????????????????????????????????????????? ***/
    public static int post(String url, String content, String reqContentType, String reqCharset,
            Map<String, String> headers, HttpHost proxy, StringBuffer outStringBuffer, String outCharset,
            Map<String, String> outHeaders) {
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            int statusCode = post(url, content, reqContentType, reqCharset, headers, proxy, os, outHeaders);
            if (ZKStringUtils.isEmpty(outCharset)) {
                outCharset = default_charset;
            }
            outStringBuffer.append(new String(os.toByteArray(), outCharset));
            return statusCode;
        }
        catch(UnsupportedEncodingException e) {
            throw new RuntimeException("??????????????????", e);
        } finally {
            if (os != null) {
                ZKStreamUtils.closeStream(os);
            }
        }
    }

    /*** POSR ?????? ???????????? UTF-8 ????????????????????????????????????????????? ***/
    public static int post(String url, String content, String reqContentType, String reqCharset,
            Map<String, String> headers, HttpHost proxy, StringBuffer outStringBuffer, Map<String, String> outHeaders) {
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            int statusCode = post(url, content, reqContentType, reqCharset, headers, proxy, os, outHeaders);
            outStringBuffer.append(new String(os.toByteArray(), default_charset));
            return statusCode;
        }
        catch(UnsupportedEncodingException e) {
            throw new RuntimeException("??????????????????", e);
        } finally {
            if (os != null) {
                ZKStreamUtils.closeStream(os);
            }
        }
    }

    /*** POSR ?????? ???????????????????????? Json ????????????????????? UTF-8 ????????????????????????????????????????????? ***/
    public static int postJson(String url, String content, String reqCharset, Map<String, String> headers,
            HttpHost proxy, OutputStream os, Map<String, String> outHeaders) {
        int statusCode = post(url, content, ZKContentType.JSON_UTF8.toString(), reqCharset, headers, proxy, os,
                outHeaders);
        return statusCode;
    }

    public static int postJson(String url, String content, String reqCharset, Map<String, String> headers,
            HttpHost proxy, StringBuffer outStringBuffer, Map<String, String> outHeaders) {
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            int statusCode = postJson(url, content, reqCharset, headers, proxy, os, outHeaders);
            outStringBuffer.append(new String(os.toByteArray(), default_charset));
            return statusCode;
        }
        catch(UnsupportedEncodingException e) {
            throw new RuntimeException("??????????????????", e);
        } finally {
            if (os != null) {
                ZKStreamUtils.closeStream(os);
            }
        }
    }

    public static int postJson(String url, String content, Map<String, String> headers) {
        int statusCode = post(url, content, ZKContentType.JSON_UTF8.toString(), null, headers, null,
                (OutputStream) null, null);
        return statusCode;
    }

    public static int postJson(String url, String content, Map<String, String> headers, StringBuffer outStringBuffer) {
        return postJson(url, content, null, headers, outStringBuffer);
    }

    public static int postJson(String url, String content, String reqCharset, Map<String, String> headers,
            StringBuffer outStringBuffer) {
        return postJson(url, content, reqCharset, headers, null, outStringBuffer, null);
    }

    public static int postJson(String url, String content, String reqCharset, Map<String, String> headers,
            OutputStream os, Map<String, String> outHeaders) {
        return postJson(url, content, reqCharset, headers, null, os, outHeaders);
    }

    /*** POSR ?????? ???????????????????????? XML ????????????????????? UTF-8 ????????????????????????????????????????????? ***/
    public static int postXml(String url, String content, String reqCharset, Map<String, String> headers,
            HttpHost proxy, StringBuffer outStringBuffer, Map<String, String> outHeaders) {
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            int statusCode = post(url, content, ZKContentType.XML.toString(), reqCharset, headers, proxy,
                    os, outHeaders);
            outStringBuffer.append(new String(os.toByteArray(), default_charset));
            return statusCode;
        }
        catch(UnsupportedEncodingException e) {
            throw new RuntimeException("??????????????????", e);
        } finally {
            if (os != null) {
                ZKStreamUtils.closeStream(os);
            }
        }
    }

    public static int postXml(String url, String content, String reqCharset, Map<String, String> headers,
            StringBuffer outStringBuffer) {
        return postXml(url, content, reqCharset, headers, null, outStringBuffer, null);
    }
    
    /*** ???????????? ***/
    public static int uploadFileByStream(String url, File file, String fileName, Map<String, String> headers,
            HttpHost proxy, OutputStream os, Map<String, String> outHeaders) {
        InputStream contentIs = null;
        try {
            HttpPost httpPost = new HttpPost(url); // ??????httpPost
            // ???????????????
            if (headers == null) {
                headers = new HashMap<>();
            }
//            headers.put("Content-Type", ZKContentType.OCTET_STREAM_UTF8.toString());
            // ?????????????????? ISO-8859-1 ?????????????????????????????????????????????????????????????????? UTF-8
            headers.put("fileName", ZKStringUtils.encodedString(fileName, "ISO-8859-1"));
            setHeadersByMap(httpPost, headers);
            contentIs = new FileInputStream(file);
            return post(url, contentIs, ZKContentType.OCTET_STREAM.toString(), null, headers, proxy, os,
                    outHeaders);
        }
        catch(Exception e) {
            throw ZKExceptionsUtils.unchecked(e);
        } finally {
            if (contentIs != null) {
                ZKStreamUtils.closeStream(contentIs);
            }
        }
    }

    /**
     * ????????????
     *
     * @Title: uploadFileByMultipart
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Dec 18, 2019 11:17:37 AM
     * @param url
     * @param files
     *            ?????????????????????????????????????????????
     * @param headers
     *            ???????????????????????? Content-Type
     * @param proxy
     * @param os
     * @param outHeaders
     * @return
     * @return int
     */
    public static int uploadFileByMultipart(String url, Map<File, String> files,
            Map<String, String> headers, HttpHost proxy, OutputStream os, Map<String, String> outHeaders) {
        logger.info("[^_^:20190312-1134-001] ?????? uploadFileByMultipart ??????:{}, files:{}, headers:{}", url,
                ZKJsonUtils.writeObjectJson(files),
                ZKJsonUtils.writeObjectJson(headers));

        try {
            if (files != null && !files.isEmpty()) {
                // ?????? httpPost ??????
                HttpPost httpPost = new HttpPost(url);
                final String BOUNDARY = "7cd4a6d158c";
                final String MP_BOUNDARY = "--" + BOUNDARY;
                final String LINE_FEED = "\r\n";
                final String END_MP_BOUNDARY = "--" + BOUNDARY + "--";
//                final String contentType = ZKContentType.X_FORM.getContentType();
    
                // ?????????????????? Multipart file ????????? ?????????
                if (headers == null) {
                    headers = new HashMap<>();
                }
                headers.put("Content-Type", ZKContentType.MULTIPART_FORM_DATA_UTF8 + "; boundary=" + BOUNDARY);
//                headers.put("Content-Type",
//                        org.apache.http.entity.ContentType.MULTIPART_FORM_DATA + "; boundary=" + BOUNDARY);

                // ???????????????
                setHeadersByMap(httpPost, headers);
                // ??????????????????
                ArrayList<InputStream> cIs = new ArrayList<InputStream>();
                // ??????????????????; ???
                // ????????????
                StringBuffer strBuffer = null;
                InputStream boundaryHeaderIs = null;
                ZKFileType fileType = null;
                for (Entry<File, String> fileEntry : files.entrySet()) {

                    strBuffer = new StringBuffer();
                    /**
                     * Content-Disposition: ??????
                     * 
                     * @name ?????????
                     * @fileName ?????????
                     */
                    strBuffer.append(LINE_FEED).append(MP_BOUNDARY).append(LINE_FEED);
                    strBuffer.append("Content-Disposition: form-data;name=\"")
                            .append(fileEntry.getValue()).append("\"; filename=\"")
                            // ISO-8859-1
                            .append(fileEntry.getKey().getName()).append("\"").append(LINE_FEED);
                    fileType = ZKFileUtils.getFileType(fileEntry.getKey());
                    strBuffer.append("Content-Type: ").append(fileType == null ? "" : fileType.getType());
//                    strBuffer.append("Content-Type: ").append(contentType);
                    strBuffer.append(LINE_FEED).append(LINE_FEED);
                    boundaryHeaderIs = new ByteArrayInputStream(strBuffer.toString().getBytes());
                    cIs.add(boundaryHeaderIs);
                    // ???????????????
                    cIs.add(new FileInputStream(fileEntry.getKey()));
                }
                // ?????????????????? ??????
                strBuffer = new StringBuffer();
                strBuffer.append(LINE_FEED).append(END_MP_BOUNDARY);
                InputStream boundaryEndFlagIs = new ByteArrayInputStream(strBuffer.toString().getBytes());
                cIs.add(boundaryEndFlagIs);
    
                // ???????????????????????????????????????????????????
                Enumeration<InputStream> e = Collections.enumeration(cIs);
                SequenceInputStream sInputStream = new SequenceInputStream(e);
                InputStreamEntity inputStreamEntity = new InputStreamEntity(sInputStream);
                // inputStreamEntity.setContentType("form-data");
                // inputStreamEntity.setContentEncoding(charset.defaultCharset().displayName());
                httpPost.setEntity(inputStreamEntity);
                return sendRequest(httpPost, proxy, os, outHeaders);
            }
        }catch (Exception e) {
            throw ZKExceptionsUtils.unchecked(e);
        }
        return -1;
    }

    /**
     * ????????????
     *
     * @Title: uploadFileByMultipart
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Dec 12, 2019 5:04:34 PM
     * @param url
     * @param maps
     *            ???????????????
     * @param fileLists
     *            ?????????????????????????????????????????????
     * @param headers
     *            ???????????????????????? Content-Type
     * @param proxy
     * @param os
     * @param outHeaders
     * @return int
     */
    public static int uploadFileByMultipart(String url, Map<String, String> maps, Map<File, String> files,
            Map<String, String> headers, HttpHost proxy, OutputStream os, Map<String, String> outHeaders) {
        HttpPost httpPost = new HttpPost(url); // ??????httpPost
        // ???????????????
        // ?????????????????? Multipart file ????????? ?????????
        if (headers == null) {
            headers = new HashMap<>();
        }
        // ??????????????????????????????
//        headers.put("Content-Type", ZKContentType.MULTIPART_FORM_DATA_UTF8.toString());
//        final String BOUNDARY = "7cd4a6d158c";
//        headers.put("Content-Type", ZKContentType.MULTIPART_FORM_DATA_UTF8 + "; boundary=" + BOUNDARY);
        setHeadersByMap(httpPost, headers);
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        // ????????????????????????filename ????????????
//        meBuilder = meBuilder.setCharset(ZKCoreConstants.Consts.UTF_8);
        meBuilder.setMode(HttpMultipartMode.RFC6532);

        if (maps != null) {
            for (String key : maps.keySet()) {
                meBuilder.addPart(key, new StringBody(maps.get(key), org.apache.http.entity.ContentType.TEXT_PLAIN));
//                meBuilder.addPart(key, new StringBody(maps.get(key), ZKContentType.TEXT_PLAIN_UTF8));
            }
        }

        if (files != null) {
//            ZKFileType fileType = null;
            for (Entry<File, String> fileEntry : files.entrySet()) {
//                FileBody fileBody = new FileBody(fileEntry.getKey(),
//                        ContentType.create("application/octet-stream", "UTF-8"), fileEntry.getKey().getName());
                FileBody fileBody = new FileBody(fileEntry.getKey());
                meBuilder.addPart(fileEntry.getValue(), fileBody);
            }
        }
        HttpEntity reqEntity = meBuilder.build();
        httpPost.setEntity(reqEntity);
        return sendRequest(httpPost, proxy, os, outHeaders);
    }
}
