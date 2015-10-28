package com.luqili.api.orm.mybatis.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.log4j.Logger;

import com.luqili.tools.CopyUtils;


/**
 * <p>
 * mybatis 拦截器 这里用来做一系列的数据库适配
 * </p>
 * <font size=0.25>Copyright (C) 2015 bsteel. All Rights Reserved.</font>
 * @author L.Xu(@2015年3月17日)
 * @version 1.0
 */
@Intercepts({ @Signature(method = "handleResultSets", type = ResultSetHandler.class, args = { Statement.class }) })
public class ColTransInterceptor implements Interceptor {

    private static final Pattern COL_TO_PROP_REG = Pattern.compile("(?<=[a-z])_([a-z])");
    protected static Logger    log             =Logger.getLogger(ColTransInterceptor.class);

    private static List<String>  excludeTypes    = new ArrayList<String>(10);

    static {
        excludeTypes.add("java.lang.Long");
        excludeTypes.add("java.lang.Integer");
        excludeTypes.add("java.lang.String");
        excludeTypes.add("java.util.Date");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //获取方法返回类型 进行必要的类型转换
        DefaultResultSetHandler handler = (DefaultResultSetHandler) invocation.getTarget();
        MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(handler, "mappedStatement");
        List<ResultMap> resultMaps = mappedStatement.getResultMaps();
        Class genType = resultMaps.get(0).getType();
        if (excludeTypes.indexOf(genType.getName()) < 0) {
            //取得方法的参数Statement
            Statement statement = (Statement) invocation.getArgs()[0];
            List<Object> result = fetchResult(statement);
            statement.close();
            log.debug("=========== genType:" + genType + " ===========");
            Method method = invocation.getMethod();
            if (genType instanceof Class && !("java.util.HashMap".equals(genType.getName()))) {
                log.debug("=========== Translate result ===========");
                List<Object> transResult = new ArrayList<Object>();
                for (Object obj : result) {
                    transResult.add(CopyUtils.copyProperties(obj, (Class) genType));
                }
                result = transResult;
            }
            Class rtype = method.getReturnType();
            log.debug("=========== method return type:" + rtype + " ===========");
            if (rtype != null && !rtype.isAssignableFrom(List.class) && CollectionUtils.isNotEmpty(result)) {
                return result.get(0);
            }
            return result;
        }
        return invocation.proceed();
    }

    /**
     * @param statement
     * @return
     * @throws SQLException
     */
    private List<Object> fetchResult(Statement statement) throws SQLException {
        // 取得结果集
        ResultSet rs = statement.getResultSet();
        ResultSetMetaData m = rs.getMetaData();
        int columns = rs.getMetaData().getColumnCount();
        Map<String, Object> map = null;
        List<Object> result = new ArrayList<Object>();
        //显示列,表格的表头
        while (rs.next()) {
            map = new HashMap<String, Object>();
            for (int i = 1 ; i <= columns ; i++) {
                map.put(changeColToProp(m.getColumnName(i)), rs.getString(i));
            }
            result.add(map);
        }
        rs.close();
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    /**
     * 字段转为属性
     * 默认规则  user_status ==> userStatus
     * @param column
     * @return
     */
    private String changeColToProp(String column) {
        Matcher matcher = COL_TO_PROP_REG.matcher(StringUtils.lowerCase(column));
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * <p>
     * 反射工具类
     * </p>
     * <font size=0.25>Copyright (C) 2015 bsteel. All Rights Reserved.</font>
     * @author L.Xu(@2015年4月1日)
     * @version 1.0
     * filename:ColTransInterceptor.java 
     */
    public static class ReflectUtil {

        /**
         * 利用反射获取指定对象的指定属性
         * @param obj 目标对象
         * @param fieldName 目标属性
         * @return 目标属性的值
         */
        public static Object getFieldValue(Object obj, String fieldName) {
            Object result = null;
            Field field = ReflectUtil.getField(obj, fieldName);
            if (field != null) {
                field.setAccessible(true);
                try {
                    result = field.get(obj);
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        /**
         * 利用反射获取指定对象里面的指定属性
         * @param obj 目标对象
         * @param fieldName 目标属性
         * @return 目标字段
         */
        private static Field getField(Object obj, String fieldName) {
            Field field = null;
            for (Class<?> clazz = obj.getClass() ; clazz != Object.class ; clazz = clazz.getSuperclass()) {
                try {
                    field = clazz.getDeclaredField(fieldName);
                    break;
                }
                catch (NoSuchFieldException e) {
                    // 这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
                }
            }
            return field;
        }

        /**
         * 利用反射设置指定对象的指定属性为指定的值
         * @param obj 目标对象
         * @param fieldName 目标属性
         * @param fieldValue 目标值
         */
        public static void setFieldValue(Object obj, String fieldName, String fieldValue) {
            Field field = ReflectUtil.getField(obj, fieldName);
            if (field != null) {
                try {
                    field.setAccessible(true);
                    field.set(obj, fieldValue);
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
