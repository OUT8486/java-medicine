package com.example.spring_boot.controller;

import com.example.spring_boot.dao.UserMapper;
import com.example.spring_boot.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        if (username == null || username.trim().isEmpty()) {
            return Result.error(400, "用户名不能为空");
        }
        if (password == null || password.trim().isEmpty()) {
            return Result.error(400, "密码不能为空");
        }

        String role = userMapper.LoginUser(username, password);
        if (role != null && !role.isEmpty()) {
            Map<String, Object> data = new HashMap<>();
            data.put("user_name", username);
            data.put("role", role);
            data.put("token", "token-" + username + "-" + System.currentTimeMillis());
            return Result.success(data);
        } else {
            return Result.error(401, "用户名或密码错误");
        }
    }

    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success("退出成功");
    }
}