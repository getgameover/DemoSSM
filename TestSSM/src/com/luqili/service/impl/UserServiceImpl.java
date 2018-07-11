package com.luqili.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luqili.db.beans.User;
import com.luqili.db.dao.UserDao;
import com.luqili.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserDao userDao;
    
    @Override
    public User saveUser(String username, String password, Integer age, Character sex) {
        
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        u.setPage(age);
        u.setSex(sex);
        Integer c = userDao.insertUser(u);
        if (c == 1) {
            return u;
        }
        return null;
    }
    
    @Override
    public User getUserById(Integer id) {
        if (id == null) {
            return null;
        }
        User u = userDao.getUserById(id);
        return u;
    }
    
}
