package com.lu.db.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lu.db.base.TestSpringBase;
import com.lu.db.beans.User;
import com.lu.db.dao.impl.UserDaoImpl;

public class TestUserDaoImpl extends TestSpringBase {
	@Autowired
	private UserDaoImpl userDao;
	
	@Test
	public final void getUserId(){
		User u=userDao.getUserById(1);
		System.out.println(u);
	}
	
	@Test
	public final void getUserByAge(){
		Integer count=userDao.getUserByAge(13);
		System.out.println(count);
		
	} 
}
