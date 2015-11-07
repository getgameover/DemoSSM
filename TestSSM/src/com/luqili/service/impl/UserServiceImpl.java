package com.luqili.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.luqili.db.beans.User;
import com.luqili.db.dao.UserDAO;
import com.luqili.service.UserService;
@Service(value="userService")
public class UserServiceImpl implements UserService {
	@Resource
	private UserDAO userDAO;
	
	@Override
	public User saveUser(String username, String password, Integer age, Character sex) {
		User u=new User();
		u.setUsername(username);
		u.setPassword(password);
		u.setPage(age);
		u.setSex(sex);
		Integer c=userDAO.saveUser(u);
		System.out.println(c.toString());
		if(c==1){//保存成功
			return u;
		}
		return null;
	}

	@Override
	public User getUserById(Integer id) {
		if(id==null){
			return null;
		}
		User u=userDAO.selectUser(id);
		return u;
	}

}
