package com.luqili.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.luqili.db.beans.User;
import com.luqili.db.dao.UserDao;
import com.luqili.service.UserService;
@Service(value="userService")
public class UserServiceImpl implements UserService {
	@Resource(name="userDao")
	private UserDao userDao;

	
	@Override
	public User saveUser(String username, String password, Integer age, Character sex) {
		User u=new User();
		u.setUsername(username);
		u.setPassword(password);
		u.setPage(age);
		u.setSex(sex);
		Integer c=userDao.insertUser(u);
		if(c==1){//±£´æ³É¹¦
			return u;
		}
		return null;
	}

	@Override
	public User getUserById(Integer id) {
		if(id==null){
			return null;
		}
		User u=userDao.getUserById(id);
		return u;
	}

}
