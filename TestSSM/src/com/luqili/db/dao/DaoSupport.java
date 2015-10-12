package com.luqili.db.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;

public abstract class DaoSupport extends SqlSessionDaoSupport {
	public String getLikeKey(String key){
		StringBuffer sbf = new StringBuffer();
		sbf.append("%");
		sbf.append(key);
		sbf.append("%");
		return sbf.toString();
	}
}
