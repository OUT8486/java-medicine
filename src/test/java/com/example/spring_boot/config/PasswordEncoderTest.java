package com.example.spring_boot.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordEncoderTest {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    void bcryptRoundTrip() {
        String hash = encoder.encode("123456");

        assertTrue(encoder.matches("123456", hash));
        assertFalse(encoder.matches("wrong-password", hash));
    }
}
