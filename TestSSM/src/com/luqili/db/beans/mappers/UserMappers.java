package com.luqili.db.beans.mappers;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.luqili.db.beans.User;
@Repository
public interface UserMappers {
	public User selectUser(Integer id );
	public User getUserByName(String name );
	public List<User> getUserLikeName(String name );
	public Integer saveUser(User user);
}
