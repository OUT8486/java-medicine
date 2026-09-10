package com.example.spring_boot.service;

import com.example.spring_boot.utils.RedisUtils;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.concurrent.TimeUnit;

/**
 * JWT 黑名单：登出后将令牌指纹写入 Redis，直到其自然过期。
 * Redis 不可用时 hasKey 返回 false（fail-open），优先保证系统可用性。
 */
@Service
public class TokenBlacklistService {

    private static final String PREFIX = "jwt:blacklist:";

    private final RedisUtils redisUtils;

    public TokenBlacklistService(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    public void blacklist(String token, long ttlMillis) {
        if (token == null || token.isBlank() || ttlMillis <= 0) {
            return;
        }
        redisUtils.set(PREFIX + hash(token), "1", ttlMillis, TimeUnit.MILLISECONDS);
    }

    public boolean isBlacklisted(String token) {
        if (token == null || token.isBlank()) {
            return false;
        }
        return Boolean.TRUE.equals(redisUtils.hasKey(PREFIX + hash(token)));
    }

    private String hash(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(bytes.length * 2);
            for (byte b : bytes) {
                sb.append(Character.forDigit((b >> 4) & 0xF, 16));
                sb.append(Character.forDigit(b & 0xF, 16));
            }
            return sb.toString();
        } catch (Exception e) {
            return Integer.toHexString(token.hashCode());
        }
    }
}
