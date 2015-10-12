package com.luqili.service;

import com.luqili.db.beans.User;

public interface UserService {
	public User saveUser(String username,String password,Integer age,Character sex);
	public User getUserById(Integer id);
}
