package com.example.spring_boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {

    // 1. 根路径：访问 http://localhost:8080/ 跳转到首页
    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        // 获取登录用户信息
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        String userRole = (String) session.getAttribute("userRole");
        
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("userRole", userRole);
        return "index";
    }

    // 2. 首页路径：访问 http://localhost:8080/index 跳转到首页
    @GetMapping("/index")
    public String indexPage(Model model, HttpSession session) {
        // 获取登录用户信息
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        String userRole = (String) session.getAttribute("userRole");
        
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("userRole", userRole);
        return "index";
    }

    // 3. 仪表盘路径：访问 http://localhost:8080/dashboard 跳转到仪表盘
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        // 获取登录用户信息
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        String userRole = (String) session.getAttribute("userRole");
        
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("userRole", userRole);
        return "index";
    }

    // 4. 测试页面：访问 http://localhost:8080/test 跳转到测试页面
    @GetMapping("/test")
    public String test() {
        return "test";
    }

    // 5. 链接测试页面：访问 http://localhost:8080/links-test 跳转到链接测试页面
    @GetMapping("/links-test")
    public String linksTest() {
        return "links-test";
    }

    // 6. 前端测试页面：访问 http://localhost:8080/test-frontend 跳转到前端测试页面
    @GetMapping("/test-frontend")
    public String testFrontend() {
        return "test-frontend";
    }
    
    // 7. Session测试页面：访问 http://localhost:8080/test-session 跳转到session测试页面
    @GetMapping("/test-session")
    public String testSession(Model model, HttpSession session) {
        // 获取登录用户信息
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        String userRole = (String) session.getAttribute("userRole");
        
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("userRole", userRole);
        return "test-session";
    }
}