package com.example.spring_boot.service;

import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginAttemptServiceTest {

    @SuppressWarnings("unchecked")
    private ValueOperations<String, String> mockOps(StringRedisTemplate template) {
        ValueOperations<String, String> ops = mock(ValueOperations.class);
        when(template.opsForValue()).thenReturn(ops);
        return ops;
    }

    @Test
    void blockedWhenAttemptsReachThreshold() {
        StringRedisTemplate template = mock(StringRedisTemplate.class);
        ValueOperations<String, String> ops = mockOps(template);
        when(ops.get("login:fail:admin")).thenReturn("5");

        LoginAttemptService service = new LoginAttemptService(template);
        assertTrue(service.isBlocked("admin"));
    }

    @Test
    void notBlockedWhenBelowThreshold() {
        StringRedisTemplate template = mock(StringRedisTemplate.class);
        ValueOperations<String, String> ops = mockOps(template);
        when(ops.get("login:fail:admin")).thenReturn("2");

        LoginAttemptService service = new LoginAttemptService(template);
        assertFalse(service.isBlocked("admin"));
    }

    @Test
    void failsOpenWhenRedisUnavailable() {
        StringRedisTemplate template = mock(StringRedisTemplate.class);
        when(template.opsForValue()).thenThrow(new RuntimeException("redis down"));

        LoginAttemptService service = new LoginAttemptService(template);
        assertFalse(service.isBlocked("admin"));
    }
}
