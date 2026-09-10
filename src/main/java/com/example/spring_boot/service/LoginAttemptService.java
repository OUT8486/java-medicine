package com.example.spring_boot.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 登录失败次数限制，防止暴力破解。
 * 使用独立的 StringRedisTemplate（避免与 JSON 序列化的 RedisTemplate 混用导致 INCR 失败）。
 * Redis 不可用时全部 fail-open，保证登录可用性。
 */
@Service
public class LoginAttemptService {

    private static final int MAX_ATTEMPTS = 5;
    private static final long LOCK_SECONDS = 900L;
    private static final String PREFIX = "login:fail:";

    private final StringRedisTemplate redisTemplate;

    public LoginAttemptService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isBlocked(String key) {
        if (key == null || key.isBlank()) {
            return false;
        }
        try {
            String value = redisTemplate.opsForValue().get(PREFIX + key);
            if (value == null) {
                return false;
            }
            return Long.parseLong(value) >= MAX_ATTEMPTS;
        } catch (Exception e) {
            return false;
        }
    }

    public void loginFailed(String key) {
        if (key == null || key.isBlank()) {
            return;
        }
        try {
            Long count = redisTemplate.opsForValue().increment(PREFIX + key);
            if (count != null && count == 1L) {
                redisTemplate.expire(PREFIX + key, LOCK_SECONDS, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            // fail-open
        }
    }

    public void loginSucceeded(String key) {
        if (key == null || key.isBlank()) {
            return;
        }
        try {
            redisTemplate.delete(PREFIX + key);
        } catch (Exception e) {
            // fail-open
        }
    }
}
