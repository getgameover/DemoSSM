package com.luqili.db.dao;

import java.util.List;

import com.luqili.db.beans.User;
public interface UserDao {
	public User getUserById(Integer id );
	public User getUserByName(String name );
	public List<User> getUserLikeName(String name );
	public Integer insertUser(User user);
	
}
