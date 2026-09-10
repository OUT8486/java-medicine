package com.example.spring_boot.config;

import com.example.spring_boot.entity.Result;
import com.example.spring_boot.service.TokenBlacklistService;
import com.example.spring_boot.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 认证拦截器：
 * 1. 校验 Authorization 头中的 JWT（含黑名单吊销校验）；
 * 2. 将当前用户名/角色写入请求属性；
 * 3. 对标注 @RequireRole / @AdminOnly 的接口做角色校验。
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    public static final String ATTR_USERNAME = "auth_username";
    public static final String ATTR_ROLE = "auth_role";

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        String token = resolveToken(request);
        if (token == null) {
            return reject(response, 401, "未登录或缺少令牌");
        }
        if (tokenBlacklistService.isBlacklisted(token)) {
            return reject(response, 401, "令牌已失效，请重新登录");
        }

        try {
            Claims claims = jwtUtil.parseToken(token);
            String username = claims.getSubject();
            String role = claims.get("role", String.class);
            if (username == null || username.isBlank()) {
                return reject(response, 401, "令牌无效");
            }
            request.setAttribute(ATTR_USERNAME, username);
            request.setAttribute(ATTR_ROLE, role == null ? "" : role);

            if (!hasRequiredRole(handlerMethod, role)) {
                return reject(response, 403, "无权限执行此操作");
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return reject(response, 401, "令牌无效或已过期");
        }
    }

    private boolean hasRequiredRole(HandlerMethod handlerMethod, String role) {
        RequireRole requireRole = handlerMethod.getMethodAnnotation(RequireRole.class);
        if (requireRole == null) {
            requireRole = handlerMethod.getBeanType().getAnnotation(RequireRole.class);
        }
        if (requireRole != null && requireRole.value().length > 0) {
            String actual = role == null ? "" : role;
            boolean allowed = Arrays.stream(requireRole.value())
                    .anyMatch(r -> r.label().equals(actual));
            if (!allowed) {
                return false;
            }
        }
        boolean adminOnly = handlerMethod.getMethodAnnotation(AdminOnly.class) != null
                || handlerMethod.getBeanType().isAnnotationPresent(AdminOnly.class);
        if (adminOnly && !Role.ADMIN.label().equals(role)) {
            return false;
        }
        return true;
    }

    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || header.isBlank()) {
            return null;
        }
        if (header.startsWith("Bearer ")) {
            return header.substring(7).trim();
        }
        return header.trim();
    }

    private boolean reject(HttpServletResponse response, int code, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(objectMapper.writeValueAsString(Result.error(code, message)));
        return false;
    }
}
