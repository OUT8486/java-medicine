package com.example.spring_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring_boot.dao.UserMapper;
import com.example.spring_boot.entity.Users;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    // 用户登录
    public String LoginUser(String user_name, String password) {
        return userMapper.LoginUser(user_name, password);
    }
    // 用户注册
    public int RegisterUser(Users user) {
        return userMapper.RegisterUser(user);
    }
}
