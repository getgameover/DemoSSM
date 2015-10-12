package com.lu.db.dao.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import com.lu.db.beans.User;
import com.lu.db.dao.DaoSupport;
import com.lu.db.dao.UserDao;
@Repository(value="userDao")
public class UserDaoImpl extends DaoSupport implements UserDao{
	
	@Override
	public User getUserById(Integer id ){
		return getSqlSession().selectOne("com.lu.db.beans.User.selectUser", id);
	}
	@Override
	public User getUserByName(String name ){
		return getSqlSession().selectOne("com.lu.db.beans.User.selectUserByName", name);
	}
	@Override
	public List<User> getUserLikeName(String name ){
		return getSqlSession().selectList("com.lu.db.beans.User.selectUserLikeName", getLikeKey(name));
	}
	@Override
	public Integer insertUser(User user){
		return getSqlSession().insert("com.lu.db.beans.User.saveUser", user);
	}
	public Integer getUserByAge(Integer age){
		String sql=new SQL(){{
			SELECT("count(u.id)");
			FROM("User u");
			WHERE("u.page=?");
		}}.toString();
		
		return getSqlSession().selectOne(sql, age);
	}
}


