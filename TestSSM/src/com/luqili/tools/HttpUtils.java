package com.luqili.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient; 
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/*********************************************************************************
//* Copyright (C) 2015 bsteel. All Rights Reserved.
//*
//* Filename:      HttpUtils.java
//* Revision:      1.0
//* Author:        lxu
//* Created On:    2015-1-30
//* Modified by:   
//* Modified On:   
//*
//* Description:   HTTP请求工具类 基于HTTCLIENT对GET，POST请求进行封装
/********************************************************************************/
public class HttpUtils {

    private static Logger                             log                          = Logger.getLogger(HttpUtils.class);
    //定义编码格式 UTF-8
    public static final String                        URL_PARAM_DECODECHARSET_UTF8 = "UTF-8";
    //定义编码格式 GBK
    public static final String                        URL_PARAM_DECODECHARSET_GBK  = "GBK";
    private static final String                       URL_PARAM_CONNECT_FLAG       = "&";
    private static final String                       EMPTY                        = "";

    private static int                                connectionTimeOut            = 25000;
    private static int                                socketTimeOut                = 25000;
    private static int                                maxConnectionPerHost         = 20;
    private static int                                maxTotalConnections          = 20;
    private static HttpClient                         client;
    private static MultiThreadedHttpConnectionManager connectionManager            = null;

    static {
        connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.getParams().setConnectionTimeout(connectionTimeOut);
        connectionManager.getParams().setSoTimeout(socketTimeOut);
        connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
        connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
        client = new HttpClient(connectionManager);
    }

    /**
     * GET 方式调用 默认UTF8编码
     * @param url
     * @return
     */
    public static String URLGet(String url) {
        return URLGet(url, null, URL_PARAM_DECODECHARSET_UTF8);
    }
    
    /**
     * 返回流
     */
    public static InputStream  UrlGetImage(String url){
    	return URLStream(url,null,URL_PARAM_DECODECHARSET_UTF8);
    }
    
    /**
     * GET 方式调用 默认UTF8编码
     * @param url
     * @return
     */
    public static String URLGet(String url, Map<String, String> params) {
        return URLGet(url, params, URL_PARAM_DECODECHARSET_UTF8);
    }

    /**
     * POST 方式调用 默认UTF8编码
     * @param url
     * @param params 请求参数
     * @return
     */
    public static String URLPost(String url, Map<String, String> params) {
        return URLPost(url, params, URL_PARAM_DECODECHARSET_UTF8);
    }

    /**
    * POST方式提交数据
    * @param url 待请求的URL
    * @param params 要提交的数据
    * @param enc 编码
    * @return 响应结果
    * @throws IOException  IO异常
    */
    public static String URLPost(String url, Map<String, String> params, String enc) {
        log.debug(" -- Http call url : " + url);
        log.debug(" -- Http call parmas : " + params);
        String response = EMPTY;
        PostMethod postMethod = null;
        try {
            postMethod = new PostMethod(url);
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
            //将表单的值放入postMethod中
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                String value = params.get(key);
                postMethod.addParameter(key, value);
            }
            //执行postMethod
            int statusCode = client.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
                response = postMethod.getResponseBodyAsString();
            }
            else {
                log.error("响应状态码 = " + postMethod.getStatusCode());
            }
            log.debug(" -- Http call result : " + response);
        }
        catch (HttpException e) {
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
        }
        catch (IOException e) {
            log.error("发生网络异常", e);
        }
        finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
                postMethod = null;
            }
        }
        return response;
    }

    /**
    * GET方式提交数据
    * @param url 待请求的URL
    * @param params 要提交的数据
    * @param enc 编码
    * @return 响应结果
    * @throws IOException IO异常
    */
    public static String URLGet(String url, Map<String, String> params, String enc) {
        String response = EMPTY;
        GetMethod getMethod = null;
        StringBuffer strtTotalURL = new StringBuffer(EMPTY);
        if (url.indexOf("?") == -1) {
            strtTotalURL.append(url).append("?").append(getUrl(params, enc));
        }
        else {
            strtTotalURL.append(url).append("&").append(getUrl(params, enc));
        }
        log.debug("GET请求URL = \n" + strtTotalURL.toString());

        try {
            getMethod = new GetMethod(strtTotalURL.toString());
            getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
            //执行getMethod
            int statusCode = client.executeMethod(getMethod);
            if (statusCode == HttpStatus.SC_OK) {
                response = getMethod.getResponseBodyAsString();
            }
            else {
                log.debug("响应状态码 = " + getMethod.getStatusCode());
            }
        }
        catch (HttpException e) {
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
        }
        catch (IOException e) {
            log.error("发生网络异常", e);
        }
        finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
                getMethod = null;
            }
        }
        return response;
    }
    /**
     * 
     * @param url
     * @param params
     * @param enc
     * @return
     */
    public static InputStream URLStream(String url, Map<String, String> params, String enc) {
        InputStream response = null;
        GetMethod getMethod = null;
        StringBuffer strtTotalURL = new StringBuffer(EMPTY);
        if (url.indexOf("?") == -1) {
            strtTotalURL.append(url).append("?").append(getUrl(params, enc));
        }
        else {
            strtTotalURL.append(url).append("&").append(getUrl(params, enc));
        }
        log.debug("GET请求URL = \n" + strtTotalURL.toString());

        try {
            getMethod = new GetMethod(strtTotalURL.toString());
            getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
            //执行getMethod
            int statusCode = client.executeMethod(getMethod);
            if (statusCode == HttpStatus.SC_OK) {
                response = getMethod.getResponseBodyAsStream();
            }
            else {
                log.debug("响应状态码 = " + getMethod.getStatusCode());
            }
        }
        catch (HttpException e) {
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
        }
        catch (IOException e) {
            log.error("发生网络异常", e);
        }
        finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
                getMethod = null;
            }
        }
        return response;
    }
    /**
    * 据Map生成URL字符串
    * @param map Map
    * @param valueEnc URL编码
    * @return URL
    */
    private static String getUrl(Map<String, String> map, String valueEnc) {
        if (null == map || map.keySet().size() == 0) {
            return (EMPTY);
        }
        valueEnc = StringUtils.defaultIfEmpty(valueEnc, URL_PARAM_DECODECHARSET_UTF8);
        StringBuffer url = new StringBuffer();
        Set<String> keys = map.keySet();
        for (Iterator<String> it = keys.iterator() ; it.hasNext() ;) {
            String key = it.next();
            if (map.containsKey(key)) {
                String val = map.get(key);
                String str = val != null ? val : EMPTY;
                try {
                    str = URLEncoder.encode(str, valueEnc);
                }
                catch (UnsupportedEncodingException e) {
                    log.error(e.getMessage(), e);
                }
                url.append(key).append("=").append(str).append(URL_PARAM_CONNECT_FLAG);
            }
        }
        String strURL = EMPTY;
        strURL = url.toString();
        if (URL_PARAM_CONNECT_FLAG.equals(EMPTY + strURL.charAt(strURL.length() - 1))) {
            strURL = strURL.substring(0, strURL.length() - 1);
        }
        return (strURL);
    }
}
