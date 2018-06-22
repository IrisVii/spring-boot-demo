package com.example.springbootdemo5.dao;

import com.example.springbootdemo5.model.User;

import java.util.List;

/**
 * 用户Dao
 */
public interface UserDao {
    /**
     * 添加用户
     */
    void insert(User user);

    /**
     * 查询所有用户
     */
    List<User> queryAll();

    /**
     * 根据id查找一位用户
     */
    User queryById(String id);

    /**
     * 更新用户
     */
    void update(User user);

    /**
     * 删除用户
     */
    void delete(String id);
}
