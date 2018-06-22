package com.example.springbootdemo5.controller;

import com.example.springbootdemo5.model.User;
import com.example.springbootdemo5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 新增一位用户
     */
    @RequestMapping("/add")
    public User addUser(User user){
        // 随机生成一个uuid并去掉"-"作为用户id
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString().replace("-","");
        user.setId(id);
        return userService.insert(user);
    }

    /**
     * 分页查询用户
     */
    @RequestMapping("/all")
    public Object findAllUser(
            @RequestParam(name="pageNum", required = false, defaultValue = "1")
                    Integer pageNum,
            @RequestParam(name="pageNum", required = false, defaultValue = "10")
                    Integer pageSize
        ){
        return userService.queryAll(pageNum, pageSize);
    }

    /**
     * RESTFUL:根据id查找一位用户
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}" , method = RequestMethod.GET)
    public User queryUserById(@PathVariable("id") String id){
        return userService.queryById(id);
    }

    /**
     * 根据id更新用户
     * @return
     */
    @RequestMapping("/update")
    public User updateUserById(User user){
        return userService.updateUser(user);
    }

    /**
     * RESTFUL:根据id删除用户
     * @param id
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteUserById(@PathVariable("id") String id){
        userService.deleteUser(id);
    }
}
