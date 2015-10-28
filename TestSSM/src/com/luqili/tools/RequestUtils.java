package com.luqili.tools;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.util.Assert;

import com.luqili.db.tools.page.MybatisPage;
import com.luqili.db.tools.page.Page;

/**
 * <p>
 * request请求工具类
 * </p>
 * <font size=0.25>Copyright (C) 2015 bsteel. All Rights Reserved.</font>
 * @author L.Xu(@2015年3月31日)
 * @version 1.0
 * filename:RequestUtils.java 
 */
public class RequestUtils {

    /**
     * 从request中获得参数Map，并返回可读的Map
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map<String, String[]> properties = request.getParameterMap();
        // 返回值Map
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Iterator<Map.Entry<String, String[]>> entries = properties.entrySet().iterator();
        Map.Entry<String, String[]> entry;
        String name = StringUtils.EMPTY;
        String value = StringUtils.EMPTY;
        while (entries.hasNext()) {
            entry = entries.next();
            name = entry.getKey();
            String[] valueObj = entry.getValue();
            if (null == valueObj) {
                value = StringUtils.EMPTY;
            }
            else {
                if (valueObj.length == 1) {
                    value = valueObj[0];
                }
                else {
                    value = StringUtils.join(valueObj, ",");
                }
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

    /**
     * 
     * @param request
     * @param name
     * @return
     */
    public static long getLong(HttpServletRequest request, String name) {
        return NumberUtils.toLong(StringUtils.trim(request.getParameter(name)));
    }

    /**
     * 
     * @param request
     * @param name
     * @return
     */
    public static String getString(HttpServletRequest request, String name) {
        return StringUtils.trim(request.getParameter(name));
    }

    /**
     * 
     * @param request
     * @param name
     * @return
     */
    public static int getInt(HttpServletRequest request, String name) {
        return NumberUtils.toInt(StringUtils.trim(request.getParameter(name)));
    }

    /**
     * 判断是否为ajax请求
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(header);
    }

    /**
     * 取得带相同前缀的Request Parameters.
     * 
     * 返回的结果的Parameter名已去除前缀.
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
        Assert.notNull(request, "Request must not be null");
        Enumeration paramNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<String, Object>();
        if (prefix == null) {
            prefix = "";
        }
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if ("".equals(prefix) || paramName.startsWith(prefix)) {
                String unprefixed = paramName.substring(prefix.length());
                String[] values = request.getParameterValues(paramName);
                if (values == null || values.length == 0) {
                    // Do nothing, no values found at all.
                }
                else if (values.length > 1) {
                    params.put(unprefixed, values);
                }
                else {
                    params.put(unprefixed, values[0]);
                }
            }
        }
        return params;
    }

    /**
     * 构建分页参数对象
     * @param request
     * @return
     */
    public static <T> Page<T> buildPage(HttpServletRequest req) {
        Page<T> page = new Page<T>(20);
        page.setPageNo(getInt(req, "page"));
        int pageSize = getInt(req, "rows");
        if (pageSize > 0) {
            page.setPageSize(pageSize);
        }
        return page;
    }

    public static <T> MybatisPage<T> buildMybatisPage(HttpServletRequest req) {
        return buildMybatisPage(req, "filter_");
    }

    public static <T> MybatisPage<T> buildMybatisPage(HttpServletRequest req, String prefix) {
        Page<T> page = buildPage(req);
        MybatisPage<T> batisPage = new MybatisPage<T>(page);
        Map<String, Object> filterParamMap = getParametersStartingWith(req, prefix);
        batisPage.putAll(filterParamMap);
        return batisPage;
    }
}
