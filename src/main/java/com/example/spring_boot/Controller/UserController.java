package com.example.spring_boot.controller;

import com.example.spring_boot.config.AdminOnly;
import com.example.spring_boot.entity.Result;
import com.example.spring_boot.entity.Users;
import com.example.spring_boot.service.UserService;
import com.example.spring_boot.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理控制器。
 * 登录走 /api/users/login（兼容旧参数 user_name）；
 * 注册走 /api/users/register，角色固定为“用户”；
 * 管理员通过 /api/users 创建带角色的账号。
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 登录（兼容旧参数名 user_name）。
     * POST /api/users/login  Body: { "user_name": "admin", "password": "123456" }
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

        String role = userService.login(userName.trim(), password);
        if (role == null || role.isEmpty()) {
            return Result.error(401, "用户名或密码错误");
        }

        Map<String, Object> data = new HashMap<>();
        data.put("user_name", userName.trim());
        data.put("role", role);
        data.put("token", jwtUtil.generateToken(userName.trim(), role));
        return Result.success(data);
    }

    /**
     * 注册：角色由服务端强制为“用户”，user_id 缺省时自动生成。
     * POST /api/users/register  Body: { "user_name": "...", "password": "..." }
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody Users user) {
        if (user.getUser_name() == null || user.getUser_name().trim().isEmpty()) {
            return Result.error(400, "用户名不能为空");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return Result.error(400, "密码不能为空");
        }
        if (user.getPassword().length() < 6) {
            return Result.error(400, "密码长度至少需要 6 位");
        }

        prepareUserForCreate(user);
        try {
            int result = userService.registerUser(user, "用户");
            if (result > 0) {
                return Result.success("注册成功");
            }
            return Result.error(500, "注册失败，请稍后重试");
        } catch (DuplicateKeyException e) {
            return Result.error(400, "用户名已存在");
        } catch (Exception e) {
            return Result.error(500, "注册失败，请稍后重试");
        }
    }

    /**
     * 管理员创建账号（可指定角色）。
     * POST /api/users  Body: { "user_name": "...", "password": "...", "role": "管理员" }
     */
    @PostMapping
    @AdminOnly
    public Result<String> create(@RequestBody Users user) {
        if (user.getUser_name() == null || user.getUser_name().trim().isEmpty()) {
            return Result.error(400, "用户名不能为空");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return Result.error(400, "密码不能为空");
        }
        if (user.getPassword().length() < 6) {
            return Result.error(400, "密码长度至少需要 6 位");
        }
        String role = user.getRole();
        if (role == null || role.isBlank()) {
            role = "用户";
        }
        if (!"管理员".equals(role) && !"用户".equals(role)) {
            return Result.error(400, "角色只能为“管理员”或“用户”");
        }

        prepareUserForCreate(user);
        try {
            int result = userService.registerUser(user, role);
            if (result > 0) {
                return Result.success("创建成功");
            }
            return Result.error(500, "创建失败，请稍后重试");
        } catch (DuplicateKeyException e) {
            return Result.error(400, "用户名已存在");
        } catch (Exception e) {
            return Result.error(500, "创建失败，请稍后重试");
        }
    }

    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success("退出成功");
    }

    private void prepareUserForCreate(Users user) {
        if (user.getUser_id() == null || user.getUser_id().trim().isEmpty()) {
            user.setUser_id("U" + System.currentTimeMillis());
        }
        user.setUser_name(user.getUser_name().trim());
    }
}
