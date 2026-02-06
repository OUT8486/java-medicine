package com.example.spring_boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {

    // 1. 根路径：访问 http://localhost:8080/ 
    // 未登录跳转到登录页面，已登录跳转到首页
    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        // 检查用户是否已登录
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        
        if (loggedInUser == null || loggedInUser.isEmpty()) {
            // 未登录，跳转到登录页面
            return "redirect:/login";
        } else {
            // 已登录，跳转到首页
            String userRole = (String) session.getAttribute("userRole");
            model.addAttribute("loggedInUser", loggedInUser);
            model.addAttribute("userRole", userRole);
            return "index";
        }
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

}