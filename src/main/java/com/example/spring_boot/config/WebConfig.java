package com.example.spring_boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 配置：注册认证拦截器，保护全部 /api/** 接口，
 * 仅登录、注册与健康检查放行。
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String[] PUBLIC_PATHS = {
            "/api/auth/login",
            "/api/users/login",
            "/api/users/register",
            "/health"
    };

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(PUBLIC_PATHS);
    }
}
