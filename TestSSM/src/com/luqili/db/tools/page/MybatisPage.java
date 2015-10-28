package com.luqili.db.tools.page;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * mybatis 分页封装
 * </p>
 * <font size=0.25>Copyright (C) 2015 bsteel. All Rights Reserved.</font>
 * @author L.Xu(@2015年4月1日)
 * @version 1.0
 * filename:MybatisPage.java 
 */
public class MybatisPage<T> extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    private Page<T>           page;

    /**
     * 
     */
    public MybatisPage() {
        super();
    }

    /**
     * @param page 分页类中可以塞入各种参数用于Mybatis的查询语句中的变量替换<p><i>eg.</i><br><p><code>MybatisPage page = new MybatisPage();<br>page.put("martSign","BEPS");<br></code></p>此Page模型类中可以放入各种各样的参数用于Mybatis中各类查询的变量传参</p>
     */
    public MybatisPage(Page<T> page) {
        super();
        this.page = page;
    }

    /**
     * @param page
     */
    public MybatisPage(MybatisPage<?> page) {
        super();
        this.page = new Page<T>(page.getPageSize());
        this.page.setOrder(page.getOrder());
        this.page.setOrderBy(page.getOrderBy());
        this.page.setAutoCount(page.isAutoCount());
        this.page.setPageNo(page.getPageNo());
        this.putAll(page);
    }

    public int getPageNo() {
        return page.getPageNo();
    }

    /**
     * 获得每页的记录数量, 默认为-1.
     */
    public int getPageSize() {
        return page.getPageSize();
    }

    /**
     * 获得排序字段,无默认值. 多个排序字段时用','分隔.
     */
    public String getOrderBy() {
        return page.getOrderBy();
    }

    /**
     * 获得排序方向, 无默认值.
     */
    public String getOrder() {
        return page.getOrder();
    }

    /**
     * 设置页内的记录列表.
     */
    public void setResult(final List<T> result) {
        page.setResult(result);
    }

    /**
     * 分页数据偏移量
     */
    public int getOffset() {
        return (page.getPageNo() - 1) * page.getPageSize();
    }

    /**
     * 设置总记录数.
     */
    public void setTotalCount(final long totalCount) {
        page.setTotalCount(totalCount);
    }

    /**
     * @return the page
     */
    public Page<T> getPage() {
        return page;
    }

    public boolean isAutoCount() {
        return page.isAutoCount();
    }

    public void setAutoCount(final boolean autoCount) {
        page.setAutoCount(autoCount);
    }

}
