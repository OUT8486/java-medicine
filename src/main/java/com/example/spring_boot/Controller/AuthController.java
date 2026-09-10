package com.example.spring_boot.controller;

import com.example.spring_boot.entity.Result;
import com.example.spring_boot.service.LoginAttemptService;
import com.example.spring_boot.service.TokenBlacklistService;
import com.example.spring_boot.service.UserService;
import com.example.spring_boot.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证接口：登录签发 JWT（含失败次数限制），登出将令牌加入黑名单吊销。
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Autowired
    private LoginAttemptService loginAttemptService;

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

        String attemptKey = username.trim().toLowerCase();
        if (loginAttemptService.isBlocked(attemptKey)) {
            return Result.error(429, "登录失败次数过多，请 15 分钟后再试");
        }

        String role = userService.login(username.trim(), password);
        if (role == null || role.isEmpty()) {
            loginAttemptService.loginFailed(attemptKey);
            return Result.error(401, "用户名或密码错误");
        }
        loginAttemptService.loginSucceeded(attemptKey);

        Map<String, Object> data = new HashMap<>();
        data.put("user_name", username.trim());
        data.put("role", role);
        data.put("token", jwtUtil.generateToken(username.trim(), role));
        return Result.success(data);
    }

    @PostMapping("/logout")
    public Result<String> logout(@RequestHeader(value = "Authorization", required = false) String authorization) {
        String token = extractToken(authorization);
        if (token != null) {
            tokenBlacklistService.blacklist(token, jwtUtil.getRemainingMillis(token));
        }
        return Result.success("退出成功");
    }

    private String extractToken(String header) {
        if (header == null || header.isBlank()) {
            return null;
        }
        if (header.startsWith("Bearer ")) {
            return header.substring(7).trim();
        }
        return header.trim();
    }
}
