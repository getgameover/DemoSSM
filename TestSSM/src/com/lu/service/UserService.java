package com.lu.service;

import com.lu.db.beans.User;

public interface UserService {
	public User saveUser(String username,String password,Integer age,Character sex);
	public User getUserById(Integer id);
}
