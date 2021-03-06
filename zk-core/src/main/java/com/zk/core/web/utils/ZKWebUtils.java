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
 * @Title: ZKWebUtils.java 
 * @author Vinson 
 * @Package com.zk.core.web.utils 
 * @Description: TODO(simple description this file what to do.) 
 * @date Dec 3, 2019 4:59:24 PM 
 * @version V1.0   
*/
package com.zk.core.web.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

import com.zk.core.commons.ZKCoreConstants;
import com.zk.core.utils.ZKEncodingUtils;
import com.zk.core.utils.ZKEnvironmentUtils;
import com.zk.core.utils.ZKFileUtils;
import com.zk.core.utils.ZKJsonUtils;
import com.zk.core.utils.ZKLocaleUtils;
import com.zk.core.utils.ZKStreamUtils;
import com.zk.core.utils.ZKStringUtils;
import com.zk.core.utils.ZKUtils;
import com.zk.core.web.handler.ZKLocaleResolver;

/** 
* @ClassName: ZKWebUtils 
* @Description: TODO(simple description this class what to do.) 
* @author Vinson 
* @version 1.0 
*/
public class ZKWebUtils extends org.springframework.web.util.WebUtils {

    protected static Logger log = LoggerFactory.getLogger(ZKWebUtils.class);

    /**
     * ???????????????????????????
     */
    public static final String Locale_Flag_In_Header = "locale";

    public static ApplicationContext getAppCxt() {
        return ZKEnvironmentUtils.getApplicationContext();
    }

    /**
     * ?????????????????? HttpServletRequest
     *
     * @Title: getRequest
     * @author Vinson
     * @date Mar 16, 2019 7:33:56 AM
     * @return
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null && requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }

        return null;
    }

    /**
     * ?????????????????? HttpServletResponse
     *
     * @Title: getResponse
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Sep 2, 2019 9:23:50 AM
     * @return
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null && requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getResponse();
        }

        return null;
    }

    /**
     * 
    *
    * @Title: getLocaleResolver 
    * @Description: TODO(simple description this method what to do.) 
    * @author Vinson 
    * @date Feb 17, 2021 12:07:59 AM 
    * @return
    * @return LocaleResolver
     */
    public static LocaleResolver getLocaleResolver() {
        return getLocaleResolver(getAppCxt(), null);
    }
    
    public static LocaleResolver getLocaleResolver(ApplicationContext ctx) {
        return getLocaleResolver(ctx, null);
    }

    /**
     * ??????????????????
     * 
     * 1???????????? ApplicationContext ????????? ApplicationContext ????????????????????????
     * 
     * 2????????????????????????????????????, ??????????????? Request ?????????????????????????????? Request ????????????????????? Request???
     *
     * @Title: getLocaleResolver
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Sep 2, 2019 9:43:16 AM
     * @param ctx
     * @param hReq
     * @return
     * @return LocaleResolver
     */
    public static LocaleResolver getLocaleResolver(ApplicationContext ctx, HttpServletRequest hReq) {
        LocaleResolver localeResolver = null;
        if (ctx != null) {
            localeResolver = ctx.getBean(LocaleResolver.class);
        }
        if (localeResolver == null) {
            localeResolver = getLocaleResolverByRequest(hReq == null?getRequest():hReq);
        }
        return localeResolver;
    }

    /**
     * ?????? Request ???????????????????????????
     *
     * @Title: getLocaleResolver
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Aug 31, 2019 5:02:59 PM
     * @param hReq
     *            hReq ??? null ???????????? null
     * @return
     * @return LocaleResolver
     */
    public static LocaleResolver getLocaleResolverByRequest(HttpServletRequest hReq) {
        if (hReq != null) {
            return RequestContextUtils.getLocaleResolver(hReq);
        }
        return null;
    }

    public static String getCleanParam(ServletRequest request, String paramName) {
        return ZKStringUtils.clean(request.getParameter(paramName));
    }

    public static HttpServletRequest toHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    public static HttpServletResponse toHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }

    /**
     * ??????AJAX???????????????
     */
    public static void redirectUrl(HttpServletRequest request, HttpServletResponse response, String url) {
        try {
            if (ZKWebUtils.isAjaxRequest(request)) {
                request.getRequestDispatcher(url).forward(request, response); // AJAX?????????Redirect??????Forward
            }
            else {
                response.sendRedirect(request.getContextPath() + url);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ?????????Ajax????????????
     * 
     * @param request
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {

        String accept = request.getHeader("accept");
        if (accept != null && accept.indexOf("application/json") != -1) {
            return true;
        }

        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1) {
            return true;
        }

        String uri = request.getRequestURI();
        if (ZKStringUtils.endsWithIgnoreCase(uri, ".json") || ZKStringUtils.endsWithIgnoreCase(uri, ".xml")) {
            return true;
        }

        String ajax = request.getParameter("__ajax");
        if (ZKStringUtils.inStringIgnoreCase(ajax, "json", "xml")) {
            return true;
        }

        return false;
    }

    /**
     * ??????????????????JSON???XML???JSONP??????????????????????????????JsonP?????????????????????__callback=??????????????????
     * 
     * @param request
     *            ???????????????????????????????????????????????????JSON???XML???JSONP
     * @param response
     *            ????????????
     * @param object
     *            ?????????JSON??????????????????
     * @return null
     */
    public static String renderObject(HttpServletResponse hRes, Object object) {

        HttpServletRequest hReq = getRequest();
        String uri = hReq.getRequestURI();
        if (ZKStringUtils.endsWithIgnoreCase(uri, ".xml")
                || ZKStringUtils.equalsIgnoreCase(hReq.getParameter("__ajax"), "xml")) {
//            return renderString(hRes, ZKXmlMapper.toXml(object));
            return renderString(hRes, ZKJsonUtils.writeObjectJson(object), null);
        }
        else {
//            String functionName = hReq.getParameter("__callback");
//            if (ZKStringUtils.isNotBlank(functionName)){
//                return renderString(hRes, ZKJsonUtils.writeObjectJson(new JSONObject(functionName, object)));
//            }else{
//                return renderString(hRes, ZKJsonUtils.writeObjectJson(object));
//            }
            return renderString(hRes, ZKJsonUtils.writeObjectJson(object), null);
        }
    }

    /**
     * ??????????????????????????????
     * 
     * @param response
     *            ????????????
     * @param string
     *            ?????????????????????
     * @return null
     */
    public static String renderString(HttpServletResponse hRes, String str, String contentType) {
//          response.reset(); // ?????????????????????????????????Header?????????????????????ajax????????????????????????Cookie??????
        if (contentType == null) {
            if ((ZKStringUtils.startsWith(str, "{") && ZKStringUtils.endsWith(str, "}"))
                    || (ZKStringUtils.startsWith(str, "[") && ZKStringUtils.endsWith(str, "]"))) {
                contentType = "application/json";
            }
            else if (ZKStringUtils.startsWith(str, "<") && ZKStringUtils.endsWith(str, ">")) {
                contentType = "application/xml";
            }
            else {
                contentType = "text/html";
            }
        }
        hRes.setContentType(contentType);
//        hRes.setCharacterEncoding("utf-8");
        OutputStream os = null;
        try {
            os = hRes.getOutputStream();
            os.write(str.getBytes());
            os.flush();
        }
        catch(Exception e) {
            e.printStackTrace();
        } finally {
            ZKStreamUtils.closeStream(os);
        }

        return null;
    }

    /**
     * ????????????????????????????????? true: "true", "t", "1", "yes", "enabled", "y", "yes", "on" ???
     * true;
     *
     * @Title: isTrue
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Dec 3, 2019 5:36:56 PM
     * @param request
     * @param paramName
     * @return
     * @return boolean
     */
    public static boolean isTrue(ServletRequest request, String paramName) {
        String value = getCleanParam(request, paramName);
        return ZKUtils.isTrue(value);
    }


    /**
     * ?????????????????????????????????
     *
     * @Title: setSystemLocale
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Jun 14, 2019 5:19:02 PM
     * @param locale
     * @return void
     */
    public static void setLocale(Locale locale) {
        setLocale(locale, getLocaleResolver(getAppCxt(), getRequest()), getRequest(), getResponse());
    }

    /**
     * ???????????????????????? ???????????????????????????
     * 
     * ?????? localLocaleResolver ???null???????????? Locale.setDefault(locale); ???????????????????????????
     *
     * @Title: setLocale
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Mar 14, 2019 10:55:37 AM
     * @param locale
     * @param ctx
     * @param req
     * @param rep
     * @return void
     */
    public static void setLocale(Locale locale, LocaleResolver localLocaleResolver, HttpServletRequest hReq,
            HttpServletResponse hRes) {

        if (localLocaleResolver != null && hRes != null) {
            try {
                localLocaleResolver.setLocale(hReq, hRes, locale);
            }
            catch(Exception e) {
                log.error("[>_<:20190314-1056-002] ????????????????????????????????????????????????locale:[{}]", locale);
                e.printStackTrace();
            }
        }
        else {
            ZKLocaleUtils.setLocale(locale);
        }
    }

    /**
     * ????????????????????????????????????????????????????????? getLocale(HttpServletRequest httpServletRequest)
     *
     * @Title: getLocale
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Mar 14, 2019 11:20:07 AM
     * @return
     * @return Locale
     */
    public static Locale getLocale() {
        return getLocale(getRequest());
    }

    /**
     * ?????? httpServletRequest ???????????????
     *
     * @Title: getLocale
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Mar 14, 2019 10:47:48 AM
     * @param httpServletRequest
     * @return
     * @return Locale
     */
    public static Locale getLocale(HttpServletRequest httpServletRequest) {
        return getLocale(httpServletRequest, null);
    }

    /**
     * ?????????????????????
     * 
     * 1???????????? HttpServletRequest ?????????????????????
     * 
     * 2???????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * 
     * 3??????????????????????????????????????????????????? ??????????????????
     *
     * @Title: getLocale
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Sep 2, 2019 9:35:48 AM
     * @param localeResolver
     * @param httpServletRequest
     * @return
     * @return Locale
     */
    public static Locale getLocale(HttpServletRequest hReq, LocaleResolver localeResolver) {

        Locale locale = null;
        if (hReq != null) {
            // ????????? HttpServletRequest ????????????????????????????????????
            locale = getLocaleByRequest(hReq);
        }

        if (locale == null) {
            if(hReq == null) {
                hReq = getRequest();
            }
            if(localeResolver == null) {
                localeResolver = getLocaleResolver(getAppCxt(), hReq);
            }
            if(localeResolver != null) {
                locale = getLocaleByLocaleResolver(localeResolver, hReq);
            }
        }

        if (locale == null) {
            return getDefautLocale();
        }

        return locale;
    }
    
    /**
     * ??????????????????????????????????????????
    *
    * @Title: getLocaleByLocaleResolver 
    * @Description: TODO(simple description this method what to do.) 
    * @author Vinson 
    * @date Feb 17, 2021 12:03:37 AM 
    * @param localeResolver
    * @param hReq
    * @return
    * @return Locale
     */
    public static Locale getLocaleByLocaleResolver(LocaleResolver localeResolver, HttpServletRequest hReq) {
        if (localeResolver != null) {
            if (hReq == null) {
                if (localeResolver instanceof ZKLocaleResolver) {
                    return ((ZKLocaleResolver) localeResolver).getDefaultLocale();
                }
            }
            else {
                // ???????????????????????????????????????????????????????????????????????????
                return localeResolver.resolveLocale(hReq);
            }
        }
        return null;
    }

    /**
     * ??? request ????????????????????????????????????
     */
    public static Locale getLocaleByRequest(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String localeStr = request.getHeader(Locale_Flag_In_Header);
        localeStr = localeStr == null ? "" : localeStr.replace("-", "_");
        if(ZKStringUtils.isEmpty(localeStr)) {
            return null;
        }
        return ZKLocaleUtils.distributeLocale(localeStr);
    }

    /**
     * ?????????????????????
     *
     * @Title: getDefautLocale
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Dec 3, 2019 5:48:41 PM
     * @return
     * @return Locale
     */
    public static Locale getDefautLocale() {
        return Locale.getDefault();
    }

    /******************************************************************/
    /**
     * ??????????????????
     * 
     * @param hReq
     * @return ????????????
     * @throws NoSuchAlgorithmException
     */
    public static String setRequestSign(HttpServletRequest hReq) throws NoSuchAlgorithmException {
        String sign = ZKUtils.genRequestSign(); // ?????? ????????????
        // ?????????????????? session ?????? ????????????
        hReq.getSession().setAttribute(ZKCoreConstants.Global.requestSign, sign);
        return sign;
    }

    /**
     * ?????????????????????????????????????????????????????????????????????????????????????????????
     * 
     * @param hReq
     * @return true ??????????????????????????? false ??????????????????????????????
     */
    public static boolean isRepeatSubmit(HttpServletRequest hReq) {
        String requestSign = ZKWebUtils.getCleanParam(hReq, ZKCoreConstants.Global.requestSign);
        // 1????????????????????????????????????????????? ????????????????????????????????????????????????
        if (requestSign == null) {
//            hReq.setAttribute(ZKCoreConstants.Global.isRepeatSubmit, "true");
            return true;
        }
        // ??????????????? Session ?????????????????? requestSign
        String sessionRequestSign = (String) hReq.getSession().getAttribute(ZKCoreConstants.Global.requestSign);
        // 2???????????????????????? Session ????????????????????????????????????????????????????????????
        if (sessionRequestSign == null) {
//            hReq.setAttribute(ZKCoreConstants.Global.isRepeatSubmit, "true");
            return true;
        }
        // 3???????????? Session ?????????????????? ????????????????????????????????? ??????????????????????????????????????????
        if (!requestSign.equals(sessionRequestSign)) {
//            hReq.setAttribute(ZKCoreConstants.Global.isRepeatSubmit, "true");
            return true;
        }
        // ??????????????????
        cleanRequestSign(hReq);
        return false;
    }

    /**
     * ???????????????????????????
     *
     * @Title: isForceSubmit
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Dec 27, 2019 11:24:57 AM
     * @param hReq
     * @return
     * @return boid
     */
    public static boolean isForceSubmit(HttpServletRequest hReq) {
        return ZKWebUtils.isTrue(hReq, ZKCoreConstants.Global.isForceSubmit);
    }

    /**
     * ?????? session ?????? ????????????
     *
     * @Title: cleanRequestSign
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Dec 27, 2019 11:06:35 AM
     * @param hReq
     * @return void
     */
    public static void cleanRequestSign(HttpServletRequest hReq) {
        // ?????? session ?????? ????????????
        hReq.getSession().removeAttribute(ZKCoreConstants.Global.requestSign);
    }

    /**
     * ???????????? CloseableHttpResponse?????????????????????????????????????????????????????? CloseableHttpResponse
     * ?????????????????????????????????????????????
     *
     * @Title: readResponse
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Jul 6, 2020 5:08:44 PM
     * @param response
     * @param os
     * @throws IOException
     * @return void
     */
    public static void readResponse(HttpResponse response, OutputStream os) throws IOException {
        if (response.getEntity() != null) {
            response.getEntity().writeTo(os);
        }
    }

    /**
     * ???????????? CloseableHttpResponse??????????????????????????????????????????????????????????????? CloseableHttpResponse
     * ?????????????????????????????????????????????
     *
     * @Title: readResponse
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Jul 6, 2020 5:07:55 PM
     * @param response
     * @return
     * @return byte[]
     */
    public static byte[] readResponse(HttpResponse response) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            if (response.getEntity() != null) {
                response.getEntity().writeTo(baos);
            }
        }
        catch(Exception e) {
            log.error("[>_<:20200706-1706-001] ?????? CloseableHttpResponse ?????????errMsg: {}", e.getMessage());
            e.printStackTrace();
        }
        return baos.toByteArray();
    }
    
    /**
     * ??? request ?????????????????????
    *
    * @Title: getBytesByRequest 
    * @Description: TODO(simple description this method what to do.) 
    * @author Vinson 
    * @date Feb 10, 2021 3:27:33 PM 
    * @param req
    * @return
    * @return byte[]
     */
    public static byte[] getBytesByRequest(HttpServletRequest req) {
        InputStream is = null;
        ByteArrayOutputStream baOs = null;
        try {
            is = req.getInputStream();
            baOs = new ByteArrayOutputStream();
            ZKStreamUtils.readAndWrite(is, baOs);
            return baOs.toByteArray();
        }
        catch(Exception e) {
            e.printStackTrace();
            log.error("[>_<:20180905-0821-001] ?????? HttpServletRequest ??????????????????");
        } finally {
            if (is != null) {
                ZKStreamUtils.closeStream(is);
            }
            if (baOs != null) {
                ZKStreamUtils.closeStream(baOs);
            }
        }
        return null;
    }

    public static HttpServletResponse downloadFile(HttpServletResponse hRes, String filePath) throws IOException {
        return downloadFile(hRes, new File(filePath));
    }

    /**
     * ????????????
     * 
     * @param file
     * @param response
     * @return
     * @throws IOException
     */
    public static HttpServletResponse downloadFile(HttpServletResponse hRes, File file) throws IOException {
        if (file == null) {
            log.error("[>_<:20210417-1612-001] ??????????????? null");
        }

        File deleteFile = null;
        if (file.isDirectory()) {
            // ?????????????????????????????????????????????????????????????????????????????????
            log.info("[^_^:20210417-1612-002] ????????????????????????????????????????????????????????????:{}", file.getPath());
            file = ZKFileUtils.compress(file);
            deleteFile = file;
        }

        OutputStream toClient = null;
        try {
            // ??????response
            hRes.reset();
            hRes.setStatus(200);

            hRes.setHeader("Pragma", "No-cache");
            hRes.setHeader("Cache-Control", "No-cache");
            hRes.setDateHeader("Expires", 0);

            // ??????????????????????????????
            hRes.setContentType("application/octet-stream");
            // ?????????????????????????????????????????????????????????URLEncoder.encode??????????????????
            hRes.setHeader("Content-Disposition",
                    "attachment;filename=" + ZKEncodingUtils.urlEncode(file.getName(), "UTF-8"));

            toClient = new BufferedOutputStream(hRes.getOutputStream());
            ZKFileUtils.readFile(file, toClient);
            toClient.flush();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        } finally {
            if (deleteFile != null) {
                ZKFileUtils.deleteFile(deleteFile);
            }
            ZKStreamUtils.closeStream(toClient);
        }
        return hRes;
    }

//    public static void issueRedirect(ServletRequest request, ServletResponse response, String url) throws IOException {
////        response.send
////        request.getRequestDispatcher(url).
////        request.getRequestDispatcher(url).forward(request, response);
////        issueRedirect(request, response, url, null, true, true);
//    }

    /**
     * ???????????????????????? uri
     * 
     * @Title: getPathWithinApplication
     * @Description: TODO(simple description this method what to do.)
     * @author Vinson
     * @date Jul 27, 2021 8:20:35 PM
     * @param http
     * @return
     * @return String
     */
    public static String getPathWithinApplication(HttpServletRequest hReq) {
        String contextPath = getContextPath(hReq);
        String requestUri = getRequestUri(hReq);
        if (ZKStringUtils.startsWithIgnoreCase(requestUri, contextPath)) {
            // Normal case: URI contains context path.
            String path = requestUri.substring(contextPath.length());
            return (ZKStringUtils.hasText(path) ? path : "/");
        }
        else {
            // Special case: rather unusual.
            return requestUri;
        }
    }

    public static String getRequestUri(HttpServletRequest request) {
        String uri = (String) request.getAttribute(WebUtils.INCLUDE_REQUEST_URI_ATTRIBUTE);
        if (uri == null) {
            uri = request.getRequestURI();
        }
        return normalize(decodeAndCleanUriString(request, uri));
    }

    private static String decodeAndCleanUriString(HttpServletRequest request, String uri) {
        uri = decodeRequestString(request, uri);
        int semicolonIndex = uri.indexOf(';');
        return (semicolonIndex != -1 ? uri.substring(0, semicolonIndex) : uri);
    }

    public static String getContextPath(HttpServletRequest request) {
        String contextPath = (String) request.getAttribute(INCLUDE_CONTEXT_PATH_ATTRIBUTE);
        if (contextPath == null) {
            contextPath = request.getContextPath();
        }
        contextPath = normalize(decodeRequestString(request, contextPath));
        if ("/".equals(contextPath)) {
            // the normalize method will return a "/" and includes on Jetty,
            // will also be a "/".
            contextPath = "";
        }
        return contextPath;
    }

    @SuppressWarnings({"deprecation"})
    public static String decodeRequestString(HttpServletRequest request, String source) {
        String enc = determineEncoding(request);
        try {
            return URLDecoder.decode(source, enc);
        }
        catch(UnsupportedEncodingException ex) {
            if (log.isWarnEnabled()) {
                log.warn("Could not decode request string [" + source + "] with encoding '" + enc
                        + "': falling back to platform default encoding; exception message: " + ex.getMessage());
            }
            return URLDecoder.decode(source);
        }
    }

    protected static String determineEncoding(HttpServletRequest request) {
        String enc = request.getCharacterEncoding();
        if (enc == null) {
            enc = DEFAULT_CHARACTER_ENCODING;
        }
        return enc;
    }

    public static String normalize(String path) {
        return normalize(path, true);
    }

    private static String normalize(String path, boolean replaceBackSlash) {

        if (path == null)
            return null;

        // Create a place for the normalized path
        String normalized = path;

        if (replaceBackSlash && normalized.indexOf('\\') >= 0)
            normalized = normalized.replace('\\', '/');

        if (normalized.equals("/."))
            return "/";

        // Add a leading "/" if necessary
        if (!normalized.startsWith("/"))
            normalized = "/" + normalized;

        // Resolve occurrences of "//" in the normalized path
        while (true) {
            int index = normalized.indexOf("//");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) + normalized.substring(index + 1);
        }

        // Resolve occurrences of "/./" in the normalized path
        while (true) {
            int index = normalized.indexOf("/./");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) + normalized.substring(index + 2);
        }

        // Resolve occurrences of "/../" in the normalized path
        while (true) {
            int index = normalized.indexOf("/../");
            if (index < 0)
                break;
            if (index == 0)
                return (null); // Trying to go outside our context
            int index2 = normalized.lastIndexOf('/', index - 1);
            normalized = normalized.substring(0, index2) + normalized.substring(index + 3);
        }

        // Return the normalized path that we have completed
        return (normalized);

    }

    /**
     * ????????????????????????
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Real-IP");
        if (ZKStringUtils.isBlank(remoteAddr)) {
            remoteAddr = request.getHeader("X-Forwarded-For");
        }
        if (ZKStringUtils.isBlank(remoteAddr)) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        }
        if (ZKStringUtils.isBlank(remoteAddr)) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return ZKStringUtils.isNotBlank(remoteAddr) ? remoteAddr : request.getRemoteAddr();
    }

}
