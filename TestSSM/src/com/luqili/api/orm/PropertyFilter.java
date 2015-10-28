package com.luqili.api.orm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.luqili.tools.ConvertUtils;
import com.luqili.tools.RequestUtils;

/**
 * <p>
 * 与具体ORM实现无关的属性过滤条件封装类, 主要记录页面中简单的搜索过滤条件.
 * </p>
 * <font size=0.25>Copyright (C) 2015 bsteel. All Rights Reserved.</font>
 * @author lxu(Create on 2015年1月29日)
 * @version 1.0
 * @FileName : PropertyFilter.java
 */
public class PropertyFilter implements Serializable {

    private static final long  serialVersionUID = 1L;
    /** 多个属性间OR关系的分隔符. */
    public static final String OR_SEPARATOR     = "_OR_";

    /**
     * <p>
     * 属性比较表达式：比较类型<br><br>
     * <table>
     * <tr>
     * <td>比较类型</td>
     * <td>比较类型含义</td>
     * </tr>
     * <tr>
     * <td>EQ</td>
     * <td>equal，相等</td>
     * </tr>
     * <tr>
     * <td>LIKE</td>
     * <td>like，相似</td>
     * </tr>
     * <tr>
     * <td>LT</td>
     * <td>less than，小于</td>
     * </tr>
     * <tr>
     * <td>GT</td>
     * <td>greater than，大于</td>
     * </tr>
     * <tr>
     * <td>LE</td>
     * <td>less or equal，小于等于</td>
     * </tr>
     * <tr>
     * <td>GE</td>
     * <td>greater or equal，大于等于</td>
     * </tr>
     * <tr>
     * <td>NEQ</td>
     * <td>not equal，不等于</td>
     * </tr>
     * <tr>
     * <td>NULL</td>
     * <td>is null，为空</td>
     * </tr>
     * <tr>
     * <td>IN</td>
     * <td>in，包含</td>
     * </tr>
     * </table>
     * </p>
     * <font size=0.25>Copyright (C) 2015 bsteel. All Rights Reserved.</font>
     * @author Lei.XU(Create on 2015年1月29日)
     * @version 1.0
     * @FileName : PropertyFilter.java
     */
    public enum MatchType {
        EQ, LIKE, LT, GT, LE, GE, NEQ, NULL, IN;
    }

    /**
     * <p>
     * 属性比较表达式：数据类型<br>
     * <br>
     * <table>
     * <tr>
     * <td><b>数据类型缩写</b></td>
     * <td><b>类型含义</b></td>
     * <tr>
     * <td>S</td>
     * <td>String</td>
     * </tr>
     * <tr>
     * <td>I</td>
     * <td>Integer</td>
     * </tr>
     * <tr>
     * <td>L</td>
     * <td>Long</td>
     * </tr>
     * <tr>
     * <td>N</td>
     * <td>Double</td>
     * </tr>
     * <tr>
     * <td>D</td>
     * <td>Date</td>
     * </tr>
     * <tr>
     * <td>B</td>
     * <td>Boolean</td>
     * </tr>
     * <tr>
     * <td>T</td>
     * <td>List</td>
     * </tr>
     * <tr>
     * <td>G</td>
     * <td>BigDecimal</td>
     * </tr>
     * </table>
     * </p>
     * <font size=0.25>Copyright (C) 2015 bsteel. All Rights Reserved.</font>
     * @author Lei.XU(Create on 2015年1月29日)
     * @version 1.0
     * @FileName : PropertyFilter.java
     */
    public enum PropertyType {
        S(String.class),
        I(Integer.class),
        L(Long.class),
        N(Double.class),
        D(Date.class),
        B(Boolean.class),
        T(List.class),
        G(BigDecimal.class);

        private Class<?> clazz;

        private PropertyType(Class<?> clazz) {
            this.clazz = clazz;
        }

        public Class<?> getValue() {
            return clazz;
        }
    }

    private MatchType matchType     = null;
    private Object    matchValue    = null;

    private Class<?>  propertyClass = null;
    private String[]  propertyNames = null;

    public PropertyFilter() {
    }

    /**
     * 利用属性比较表达式编译成查询条件
     * <br>
     * 利用<b>filterName</b>中的表达式通过解析得到所需的查询条件，查询条件构成方式为:
     * <br>
     * <b>MatchType</b> + <b>PropertyType</b> + _ + <b>PO对象属性名</b> (+ _OR_ +<b>另一个需要查询的PO对象属性名</b> ...)<br>
     * <br>
     * eg. 
     * new PropertyFilter("LIKES_NAME_OR_LOGIN_NAME","张三") 这个句子代表的意义:
     * SQL查询where条件中的 where name like '%张三%' or login_name like '%张三%'
     * @see PropertyType
     * @see MatchType
     * @param filterName 比较属性字符串,含待比较的比较类型、属性值类型及属性列表. 
     * @param value 待比较的值.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public PropertyFilter(final String filterName, final String value) {

        String firstPart = StringUtils.substringBefore(filterName, "_");
        String matchTypeCode = StringUtils.substring(firstPart, 0, firstPart.length() - 1);
        String propertyTypeCode = StringUtils.substring(firstPart, firstPart.length() - 1, firstPart.length());

        try {
            matchType = Enum.valueOf(MatchType.class, matchTypeCode);
        }
        catch (RuntimeException e) {
            throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性比较类型.", e);
        }

        try {
            propertyClass = Enum.valueOf(PropertyType.class, propertyTypeCode).getValue();
        }
        catch (RuntimeException e) {
            throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性值类型.", e);
        }

        String propertyNameStr = StringUtils.substringAfter(filterName, "_");
        Assert.isTrue(StringUtils.isNotBlank(propertyNameStr), "filter名称" + filterName + "没有按规则编写,无法得到属性名称.");
        propertyNames = StringUtils.splitByWholeSeparator(propertyNameStr, PropertyFilter.OR_SEPARATOR);

        if (propertyTypeCode.equals(PropertyType.T.name())) {
            String[] values = value.split(",");
            String propertyTypeCode_inner = values[0];//值里面的第一个表示数据类型
            Class<?> propertyClass_inner = Enum.valueOf(PropertyType.class, propertyTypeCode_inner).getValue();
            List lst = new ArrayList();
            for (int i = 1 ; i < values.length ; i++) {
                lst.add(ConvertUtils.convertStringToObject(values[i], propertyClass_inner));
            }
            this.matchValue = lst;
        }
        else
            this.matchValue = ConvertUtils.convertStringToObject(value, propertyClass);
    }

    /**
     * 从HttpRequest中创建PropertyFilter列表, 默认Filter属性名前缀为filter.
     * 
     * @see #buildFromHttpRequest(HttpServletRequest, String)
     */
    public static List<PropertyFilter> buildFromHttpRequest(final HttpServletRequest request) {
        return buildFromHttpRequest(request, "filter");
    }

    /**
     * 从HttpRequest中创建PropertyFilter列表
     * PropertyFilter命名规则为Filter属性前缀_比较类型属性类型_属性名.
     * 
     * eg.
     * filter_EQS_name
     * filter_LIKES_name_OR_email
     */
    public static List<PropertyFilter> buildFromHttpRequest(final HttpServletRequest request, final String filterPrefix) {
        List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();

        //从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> filterParamMap = RequestUtils.getParametersStartingWith(request, filterPrefix + "_");

        //分析参数Map,构造PropertyFilter列表
        for (Map.Entry<String, Object> entry : filterParamMap.entrySet()) {
            String filterName = entry.getKey();
            String value = (String) entry.getValue();
            //如果value值为空,则忽略此filter.
            if (StringUtils.isNotBlank(value)) {
                if (filterName.startsWith("EQD_")) {
                    if (value.length() == 10) {
                        filterList.add(new PropertyFilter("GED_" + filterName.substring(4), value + " 00:00:00"));
                        filterList.add(new PropertyFilter("LED_" + filterName.substring(4), value + " 23:59:59"));
                    }
                    else
                        filterList.add(new PropertyFilter(filterName, value));
                }
                else if (filterName.startsWith("GED_")) {
                    if (value.length() == 10)
                        filterList.add(new PropertyFilter("GED_" + filterName.substring(4), value + " 00:00:00"));
                    else
                        filterList.add(new PropertyFilter(filterName, value));
                }
                else if (filterName.startsWith("LED_")) {
                    if (value.length() == 10)
                        filterList.add(new PropertyFilter("LED_" + filterName.substring(4), value + " 23:59:59"));
                    else
                        filterList.add(new PropertyFilter(filterName, value));
                }
                else
                    filterList.add(new PropertyFilter(filterName, value));
            }
        }

        return filterList;
    }

    /**
     * 获取比较值的类型.
     */
    public Class<?> getPropertyClass() {
        return propertyClass;
    }

    /**
     * 获取比较方式.
     */
    public MatchType getMatchType() {
        return matchType;
    }

    /**
     * 获取比较值.
     */
    public Object getMatchValue() {
        return matchValue;
    }

    /**
     * 获取比较属性名称列表.
     */
    public String[] getPropertyNames() {
        return propertyNames;
    }

    /**
     * 获取唯一的比较属性名称.
     */
    public String getPropertyName() {
        Assert.isTrue(propertyNames.length == 1, "There are not only one property in this filter.");
        return propertyNames[0];
    }

    /**
     * 是否比较多个属性.
     */
    public boolean hasMultiProperties() {
        return (propertyNames.length > 1);
    }
}
