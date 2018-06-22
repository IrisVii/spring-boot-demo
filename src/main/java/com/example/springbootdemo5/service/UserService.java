package com.example.springbootdemo5.service;

import com.example.springbootdemo5.model.User;
import com.github.pagehelper.PageInfo;

/**
 * 用户业务层接口
 */
public interface UserService {
    /**
     * 新增一位用户
     */
    User insert(User user);

    /**
     * 查询所有用户
     */
    PageInfo<User> queryAll(int pageNum, int pageSize);

    /**
     * 根据id查找一位用户
     */
    User queryById(String id);

    /**
     * 更新用户
     */
    User updateUser(User user);

    /**
     * 删除用户
     */
    void deleteUser(String id);
}
