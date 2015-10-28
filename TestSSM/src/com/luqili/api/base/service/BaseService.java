package com.luqili.api.base.service;

import java.io.Serializable;
import java.util.List;

import com.luqili.api.orm.PropertyFilter;
import com.luqili.db.tools.page.Page;



/**
 * <p>
 * 通用接口封装<br><br>
 * 包含保存和更新，删除实体，根据主键查找，获取全部对象，单一条件查询，多条件查询，分页查询等操作。
 * </p>
 * <font size=0.25>Copyright (C) 2015 bsteel. All Rights Reserved.</font>
 * @author lxu(Create on 2015年3月5日)
 * @version 1.0
 * @FileName : BaseService.java
 * @param <T> Data Transfer Object 数据传输对象类型
 * @param <ID> 主键类型
 */
public interface BaseService<T, ID extends Serializable> {

    /**
     * 保存和更新
     * @param dto 需要被保存和更新的对象
     */
    public T saveOrUpdate(T dto);

    /**
     * 删除实体
     * @param dto 需要被删除的对象
     */
    public void delete(T dto);

    /**
     * 根据主键删除实体
     * @param pk 主键ID
     */
    public void delete(ID pk);

    /**
     * 主键查找
     * @param id 根据主键查找对象
     * @return 返回的对象
     */
    public T get(ID id);

    /**
     * 获取全部对象, 支持按属性行序.
     * @param orderByProperty 按照某属性字段排序
     * @param isAsc 是否为正序
     * @return 所有对象
     */
    public List<T> getAll(String orderByProperty, boolean isAsc);

    /**
     * 按属性查找对象集合, 匹配方式为相等.（单属性查询）
     * @param propertyName 查询对象的属性名称（不是数据库字段名）
     * @param value 属性比较值
     * @return 合乎条件的对象集合
     */
    public List<T> findBy(String propertyName, Object value);

    /**
     * 按属性过滤条件列表查找对象列表
     * @param filters 过滤表达式集合
     * @return 根据过滤表达式查询得到的结果列表
     */
    public List<T> find(List<PropertyFilter> filters);

    /**
     * 按属性过滤条件列表分页查找对象.
     * @param page
     * @param filters
     * @return
     */
    public Page<T> findPage(final Page<T> page, final List<PropertyFilter> filters);

}
