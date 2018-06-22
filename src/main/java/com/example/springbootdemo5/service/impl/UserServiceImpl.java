package com.example.springbootdemo5.service.impl;

import com.example.springbootdemo5.dao.UserDao;
import com.example.springbootdemo5.model.User;
import com.example.springbootdemo5.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@CacheConfig(cacheNames="userCache") // 本类内方法指定使用缓存时，缓存默认的名称就是userCache
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 新增用户
     * @param user
     */
    @Override
    @CachePut(key = "#p0.id") // #p0表示第一个参数user，取user的id作为key（这里用key="#user.id"也可以）
    // 必须要有返回值，否则没数据放到缓存中
    public User insert(User user) {
        try {
            userDao.insert(user);
            /*
            对象中可能只有只几个有效字段，其他字段值靠数据库生成
            所以先从数据库查询出再存入缓存中
             */
            return userDao.queryById(user.getId());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
     * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
     * @param pageNum   当前页
     * @param pageSize  每页大小
     * @return
     */
    @Override
    public PageInfo<User> queryAll(int pageNum, int pageSize) {
        try {
            // 开启分页，设置参数
            PageHelper.startPage(pageNum, pageSize);
            // 先调用数据访问层查询
            List<User> users = userDao.queryAll();
            // 创建PageInfo，封装
            PageInfo<User> pageInfo = new PageInfo<>(users);

            return pageInfo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据id查找一位用户
     * @param id
     * @return
     */
    @Override
    @Cacheable(key = "#p0") // @Cacheable会先查询缓存，如果缓存中存在，则不执行方法
    public User queryById(String id) {
        try {
            System.err.println("根据id=" + id +"获取用户对象，从数据库中获取");
            return userDao.queryById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @Override
    @CachePut(key = "#p0.id")
    public User updateUser(User user) {
        try {
            userDao.update(user);
            // 更新了数据，重新从数据库查一遍放入缓存
            return queryById(user.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除用户
     * @param id
     */
    @Override
    @CacheEvict(key = "#p0") // 删除缓存名称为userCache,key等于指定的id对应的缓存
    public void deleteUser(String id) {
        try {
            userDao.delete(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
