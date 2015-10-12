package com.luqili.service.copy;

import com.luqili.db.beans.User;

public interface UserService {
	public User saveUser(String username,String password,Integer age,Character sex);
	public User getUserById(Integer id);
}
