package com.example.spring_boot.service;

import com.example.spring_boot.utils.RedisUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TokenBlacklistServiceTest {

    @Test
    void blacklistWritesToRedisWithTtl() {
        RedisUtils redisUtils = mock(RedisUtils.class);
        TokenBlacklistService service = new TokenBlacklistService(redisUtils);

        service.blacklist("some-token", 60_000L);

        verify(redisUtils).set(anyString(), any(), anyLong(), any());
    }

    @Test
    void isBlacklistedFalseOnCacheMiss() {
        RedisUtils redisUtils = mock(RedisUtils.class);
        when(redisUtils.hasKey(anyString())).thenReturn(false);
        TokenBlacklistService service = new TokenBlacklistService(redisUtils);

        assertFalse(service.isBlacklisted("some-token"));
    }

    @Test
    void blankTokenIsSafe() {
        RedisUtils redisUtils = mock(RedisUtils.class);
        TokenBlacklistService service = new TokenBlacklistService(redisUtils);

        assertFalse(service.isBlacklisted(null));
        assertFalse(service.isBlacklisted("  "));
        service.blacklist(null, 1000L);
    }
}
