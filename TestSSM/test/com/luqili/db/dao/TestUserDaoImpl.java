package com.luqili.db.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.luqili.db.base.TestSpringBase;
import com.luqili.db.beans.User;

public class TestUserDaoImpl extends TestSpringBase {
	@Autowired
	private UserDAO userDAO;
	
	@Test
	public final void getUserId(){
		User u=userDAO.selectUser(2);
		System.out.println(u);
	}
	
	@Test
	public final void getUserByAge(){
		
	} 
}
