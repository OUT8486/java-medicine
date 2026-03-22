package com.example.spring_boot.controller;

import com.example.spring_boot.entity.Result;
import com.example.spring_boot.entity.Users;
import com.example.spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理控制器
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * POST /api/users/login
     * Body: { "user_name": "admin", "password": "123456" }
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> loginData) {
        String userName = loginData.get("user_name");
        String password = loginData.get("password");
        
        if (userName == null || userName.trim().isEmpty()) {
            return Result.error(400, "用户名不能为空");
        }
        
        if (password == null || password.trim().isEmpty()) {
            return Result.error(400, "密码不能为空");
        }
        
        String role = userService.LoginUser(userName, password);
        
        if (role != null && !role.isEmpty()) {
            Map<String, Object> data = new HashMap<>();
            data.put("user_name", userName);
            data.put("role", role);
            data.put("token", "token-" + userName + "-" + System.currentTimeMillis());
            
            return Result.success(data);
        } else {
            return Result.error(401, "用户名或密码错误");
        }
    }

    /**
     * 用户注册
     * POST /api/users/register
     * Body: { "user_id": "u001", "user_name": "张三", "password": "123456", "role": "管理员" }
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody Users user) {
        // 基本验证
        if (user.getUser_id() == null || user.getUser_id().trim().isEmpty()) {
            return Result.error(400, "用户 ID 不能为空");
        }
        
        if (user.getUser_name() == null || user.getUser_name().trim().isEmpty()) {
            return Result.error(400, "用户名不能为空");
        }
        
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return Result.error(400, "密码不能为空");
        }
        
        if (user.getPassword().length() < 6) {
            return Result.error(400, "密码长度至少需要 6 位");
        }
        
        try {
            int result = userService.RegisterUser(user);
            if (result > 0) {
                return Result.success("注册成功");
            } else {
                return Result.error("注册失败，请稍后重试");
            }
        } catch (Exception e) {
            return Result.error(500, "注册失败：" + e.getMessage());
        }
    }

    /**
     * 用户登出
     * POST /api/users/logout
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success("登出成功");
    }
}