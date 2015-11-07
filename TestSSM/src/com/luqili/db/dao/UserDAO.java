package com.luqili.db.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.luqili.db.beans.User;
@Repository
@Transactional
public interface UserDAO {
	public User selectUser(Integer id );
	public User getUserByName(String name );
	public List<User> getUserLikeName(String name );
	public Integer saveUser(User user);
}
