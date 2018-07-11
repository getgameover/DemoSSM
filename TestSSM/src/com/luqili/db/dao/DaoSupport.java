package com.luqili.db.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DaoSupport extends SqlSessionDaoSupport {
    public String getLikeKey(String key) {
        StringBuffer sbf = new StringBuffer();
        sbf.append("%");
        sbf.append(key);
        sbf.append("%");
        return sbf.toString();
    }
    
    @Autowired
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }
}
