package com.example.spring_boot.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtUtilTest {

    private static final String SECRET = "unit-test-secret-0123456789abcdef";

    private JwtUtil jwtUtil(long expirationMs) {
        return new JwtUtil(SECRET, expirationMs);
    }

    @Test
    void generateAndParseToken() {
        JwtUtil util = jwtUtil(86_400_000L);
        String token = util.generateToken("admin", "管理员");

        Claims claims = util.parseToken(token);

        assertEquals("admin", claims.getSubject());
        assertEquals("管理员", claims.get("role", String.class));
    }

    @Test
    void expiredTokenIsRejected() {
        JwtUtil util = jwtUtil(-1000L);
        String token = util.generateToken("admin", "管理员");

        assertThrows(JwtException.class, () -> util.parseToken(token));
    }

    @Test
    void tamperedTokenIsRejected() {
        JwtUtil util = jwtUtil(86_400_000L);
        String token = util.generateToken("admin", "管理员");
        String tampered = token.substring(0, token.length() - 2) + "xx";

        assertThrows(JwtException.class, () -> util.parseToken(tampered));
    }
}
