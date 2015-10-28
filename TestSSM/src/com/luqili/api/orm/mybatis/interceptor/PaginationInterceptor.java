package com.luqili.api.orm.mybatis.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.log4j.Logger;

import com.luqili.api.orm.mybatis.dialect.Dialect;
import com.luqili.api.orm.mybatis.dialect.MysqlDialect;
import com.luqili.api.orm.mybatis.dialect.OracleDialect;
import com.luqili.api.orm.mybatis.interceptor.ColTransInterceptor.ReflectUtil;
import com.luqili.db.tools.page.MybatisPage;

/**
 * <p>
 * 分页适配
 * </p>
 * <font size=0.25>Copyright (C) 2015 bsteel. All Rights Reserved.</font>
 * @author L.Xu(@2015年3月17日)
 * @version 1.0
 */

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PaginationInterceptor implements Interceptor {

    protected static Logger log = Logger.getLogger(PaginationInterceptor.class);

    /* (non-Javadoc) 
     * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation) 
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        StatementHandler delegate = (StatementHandler) ColTransInterceptor.ReflectUtil.getFieldValue(handler, "delegate");
        BoundSql boundSql = delegate.getBoundSql();
        Object obj = boundSql.getParameterObject();
        if (obj instanceof MybatisPage) {
            log.debug("=========== Pagination Interceptor ===========");
            MybatisPage<?> page = (MybatisPage<?>) obj;
            MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
            Connection connection = (Connection) invocation.getArgs()[0];
            String sql = boundSql.getSql();
            if (page.isAutoCount()) {
                this.setTotalRecord(page, mappedStatement, connection);
            }
            String pageSql = this.getPageSql(page, mappedStatement, sql);
            ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
        }
        return invocation.proceed();
    }

    /**
     * 根据page对象获取对应的分页查询Sql语句，这里只做了两种数据库类型，Mysql和Oracle
     * 其它的数据库都 没有进行分页
     *
     * @param page 分页对象
     * @param sql 原sql语句
     * @return
     */
    private String getPageSql(MybatisPage<?> page, MappedStatement mappedStatement, String sql) {
        String databaseType = mappedStatement.getConfiguration().getVariables().getProperty("dialect");
        log.debug("=========== Database type :" + databaseType + " ===========");
        Dialect dialect = null;
        String rval = sql;
        Dialect.Type dialectType = Dialect.Type.valueOf(databaseType.toUpperCase());
        switch (dialectType) {
            case ORACLE:
                dialect = new OracleDialect();
                break;
            case MYSQL:
                dialect = new MysqlDialect();
                break;
        }
        if (dialect != null && page.getPageSize() > 0) {
            rval = dialect.getLimitString(sql, page.getOffset(), page.getPageSize());
        }
        return rval;
    }

    /**
     * 给当前的参数对象page设置总记录数
     *
     * @param page Mapper映射语句对应的参数对象
     * @param mappedStatement Mapper映射语句
     * @param connection 当前的数据库连接
     */
    private void setTotalRecord(MybatisPage<?> page, MappedStatement mappedStatement, Connection connection) {
        BoundSql boundSql = mappedStatement.getBoundSql(page);
        String sql = boundSql.getSql();
        String countSql = this.getCountSql(sql);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, page);
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, page, countBoundSql);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(countSql);
            parameterHandler.setParameters(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int totalRecord = rs.getInt(1);
                page.setTotalCount(totalRecord);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据原Sql语句获取对应的查询总记录数的Sql语句
     * @param sql
     * @return
     */
    private String getCountSql(String sql) {
        String lowersql = sql.toLowerCase();
        StringBuilder result = new StringBuilder();
        result.append("select ");
        int index = lowersql.indexOf("from");
        String alliasSql = lowersql.substring(0, index);
        if (alliasSql.indexOf("distinct") > 0) {
            Matcher match = DISTINCT_PATTERN.matcher(alliasSql);
            if (match.find()) {
                result.append("count(distinct(" + match.group(1) + ")) ");
            }
        }
        else {
            result.append("count(*) ");
        }
        return result.append(sql.substring(index)).toString();
    }

    private static final Pattern DISTINCT_PATTERN = Pattern.compile("select\\s+distinct\\s+([^\\s]+)\\s+");

    public static void main(String[] args) {
        Matcher match = DISTINCT_PATTERN.matcher("select distinct uc.id               as company_id ");
        if (match.find()) {
            System.out.println(match.group(1));
        }
    }

    /* (non-Javadoc) 
     * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object) 
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /* (non-Javadoc) 
     * @see org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties) 
     */
    @Override
    public void setProperties(Properties properties) {

    }

}
