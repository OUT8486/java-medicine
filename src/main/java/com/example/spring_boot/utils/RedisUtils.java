package com.example.spring_boot.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, long timeout, TimeUnit unit) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        if (timeout <= 0) {
            throw new IllegalArgumentException("Timeout must be greater than 0");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public Object get(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        return redisTemplate.opsForValue().get(key);
    }

    public Boolean delete(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        return redisTemplate.delete(key);
    }

    public Boolean hasKey(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        return redisTemplate.hasKey(key);
    }

    public void expire(String key, long timeout, TimeUnit unit) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        if (timeout <= 0) {
            throw new IllegalArgumentException("Timeout must be greater than 0");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        redisTemplate.expire(key, timeout, unit);
    }
}
