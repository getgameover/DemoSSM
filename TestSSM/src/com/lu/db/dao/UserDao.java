package com.lu.db.dao;

import java.util.List;

import com.lu.db.beans.User;
public interface UserDao {
	public User getUserById(Integer id );
	public User getUserByName(String name );
	public List<User> getUserLikeName(String name );
	public Integer insertUser(User user);
	
}
