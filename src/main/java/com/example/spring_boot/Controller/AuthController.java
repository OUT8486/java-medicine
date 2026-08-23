package com.example.spring_boot.controller;

import com.example.spring_boot.entity.Result;
import com.example.spring_boot.service.UserService;
import com.example.spring_boot.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证接口：登录签发 JWT，退出（无状态，客户端删除令牌即可）。
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 登录：校验用户名 + BCrypt 密码，成功后签发 JWT。
     * POST /api/auth/login  Body: { "username": "...", "password": "..." }
     */
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

        String role = userService.login(username.trim(), password);
        if (role == null || role.isEmpty()) {
            return Result.error(401, "用户名或密码错误");
        }

        Map<String, Object> data = new HashMap<>();
        data.put("user_name", username.trim());
        data.put("role", role);
        data.put("token", jwtUtil.generateToken(username.trim(), role));
        return Result.success(data);
    }

    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success("退出成功");
    }
}
