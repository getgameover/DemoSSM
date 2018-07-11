package com.luqili.db.dao.impl;

import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.luqili.db.beans.User;
import com.luqili.db.dao.DaoSupport;
import com.luqili.db.dao.UserDao;

@Repository
@Transactional
public class UserDaoImpl extends DaoSupport implements UserDao {
    
    @Override
    public User getUserById(Integer id) {
        
        return getSqlSession().selectOne("com.luqili.db.beans.User.selectUser", id);
    }
    
    @Override
    public User getUserByName(String name) {
        return getSqlSession().selectOne("com.luqili.db.beans.User.selectUserByName", name);
    }
    
    @Override
    public List<User> getUserLikeName(String name) {
        return getSqlSession().selectList("com.luqili.db.beans.User.selectUserLikeName", getLikeKey(name));
    }
    
    @Override
    public Integer insertUser(User user) {
        return getSqlSession().insert("com.luqili.db.beans.User.saveUser", user);
    }
    
    public Integer getUserByAge(Integer age) {
        String sql = new SQL() {
            {
                SELECT("count(u.id)");
                FROM("user u");
                WHERE("u.page=?");
            }
        }.toString();
        
        return getSqlSession().selectOne(sql, age);
    }
}
