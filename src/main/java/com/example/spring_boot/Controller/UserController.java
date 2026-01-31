package com.example.spring_boot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.spring_boot.pojo.Users;
import com.example.spring_boot.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    // 跳转至登录页面
    @GetMapping("/login")
    public String toLoginPage() {
        return "login";
    }

    // 登录验证接口
    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> LoginUser(@RequestParam("user_name") String user_name, 
                                       @RequestParam("password") String password,
                                       HttpSession session) {
        String result = userService.LoginUser(user_name, password);
        Map<String, Object> response = new HashMap<>();
        
        if (result != null && !result.isEmpty()) {
            // 登录成功
            response.put("success", true);
            response.put("message", "登录成功");
            response.put("role", result);
            
            // 保存用户信息到session
            session.setAttribute("loggedInUser", user_name);
            session.setAttribute("userRole", result);
        } else {
            // 登录失败
            response.put("success", false);
            response.put("message", "用户名或密码错误");
        }
        
        return response;
    }

    // 注册页面跳转
    @GetMapping("/register")
    public String toRegisterPage() {
        return "register";
    }

    // 用户注册接口
    @PostMapping("/register")
    @ResponseBody
    public Map<String, Object> registerUser(
            @RequestParam("user_id") String user_id,
            @RequestParam("user_name") String user_name,
            @RequestParam("password") String password,
            @RequestParam("role") String role) {

        Map<String, Object> response = new HashMap<>();
        
        // 基本验证
        if (user_id == null || user_id.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "用户ID不能为空");
            return response;
        }
        
        if (user_name == null || user_name.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "用户名不能为空");
            return response;
        }
        
        if (password == null || password.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "密码不能为空");
            return response;
        }
        
        if (password.length() < 6) {
            response.put("success", false);
            response.put("message", "密码长度至少需要6位");
            return response;
        }
        
        Users user = new Users();
        user.setUser_id(user_id);
        user.setUser_name(user_name);
        user.setPassword(password);
        user.setRole(role != null ? role : "用户");

        try {
            int result = userService.RegisterUser(user);
            if (result > 0) {
                // 注册成功
                response.put("success", true);
                response.put("message", "注册成功");
            } else {
                // 注册失败
                response.put("success", false);
                response.put("message", "注册失败，请稍后重试");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "注册失败：" + e.getMessage());
        }
        
        return response;
    }
    
    // 用户登出接口
    @GetMapping("/logout")
    @ResponseBody
    public Map<String, Object> logout(HttpSession session) {
        session.invalidate(); // 清除session
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "登出成功");
        return response;
    }
}