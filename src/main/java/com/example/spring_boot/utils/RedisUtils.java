package com.example.spring_boot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类。
 * 所有对 redisTemplate 的调用均做容错降级：Redis 不可用时静默失败并返回安全默认值，
 * 避免后端因缓存/会话服务异常而整体崩溃。
 */
@Component
public class RedisUtils {

    private static final Logger log = LoggerFactory.getLogger(RedisUtils.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            log.error("Redis set 失败，key = {}", key, e);
        }
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
        try {
            redisTemplate.opsForValue().set(key, value, timeout, unit);
        } catch (Exception e) {
            log.error("Redis set 失败，key = {}", key, e);
        }
    }

    public Object get(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("Redis get 失败，key = {}", key, e);
            return null;
        }
    }

    public Boolean delete(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        try {
            return redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("Redis delete 失败，key = {}", key, e);
            return false;
        }
    }

    public Boolean hasKey(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("Redis hasKey 失败，key = {}", key, e);
            return false;
        }
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
        try {
            redisTemplate.expire(key, timeout, unit);
        } catch (Exception e) {
            log.error("Redis expire 失败，key = {}", key, e);
        }
    }
}
