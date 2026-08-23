package com.example.spring_boot.service;

import com.example.spring_boot.dao.UserMapper;
import com.example.spring_boot.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 登录：按用户名查用户，再比对 BCrypt 密码哈希。
     * 成功返回角色，失败返回 null。
     */
    public String login(String user_name, String rawPassword) {
        Users user = userMapper.selectUserByUsername(user_name);
        if (user == null || !passwordEncoder.matches(rawPassword, user.getPassword())) {
            return null;
        }
        return user.getRole();
    }

    /**
     * 注册/创建用户：强制指定角色（禁止客户端自选），密码 BCrypt 加密。
     */
    public int registerUser(Users user, String role) {
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.RegisterUser(user);
    }
}
