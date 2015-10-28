package com.luqili.api.orm.mybatis.dialect;

/**
 * <p>
 * 
 * </p>
 * <font size=0.25>Copyright (C) 2015 bsteel. All Rights Reserved.</font>
 * @author L.Xu(@2015年3月17日)
 * @version 1.0
 */
public abstract class Dialect {

    public static enum Type {
        MYSQL, ORACLE
    }

    public abstract String getLimitString(String sql, int skipResults, int maxResults);
}
