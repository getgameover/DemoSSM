package com.luqili.api.orm.mybatis.dialect;

/**
 * <p>
 * 
 * </p>
 * <font size=0.25>Copyright (C) 2015 bsteel. All Rights Reserved.</font>
 * @author L.Xu(@2015年3月17日)
 * @version 1.0
 */
public class MysqlDialect extends Dialect {

    /* (non-Javadoc) 
     * @see org.mybatis.extend.interceptor.IDialect#getLimitString(java.lang.String, int, int) 
     */
    @Override
    public String getLimitString(String sql, int offset, int limit) {

        sql = sql.trim();

        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);

        pagingSelect.append("select * from (");

        pagingSelect.append(sql);

        pagingSelect.append(" )_t limit ").append(offset).append("," + limit);

        return pagingSelect.toString();
    }
}
