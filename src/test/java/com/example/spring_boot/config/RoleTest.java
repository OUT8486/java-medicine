package com.example.spring_boot.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RoleTest {

    @Test
    void fromLabelResolvesKnownRoles() {
        assertEquals(Role.ADMIN, Role.fromLabel("管理员"));
        assertEquals(Role.USER, Role.fromLabel("用户"));
    }

    @Test
    void fromLabelReturnsNullForUnknownOrNull() {
        assertNull(Role.fromLabel("unknown"));
        assertNull(Role.fromLabel(null));
    }

    @Test
    void labelsAreStable() {
        assertEquals("管理员", Role.ADMIN.label());
        assertEquals("用户", Role.USER.label());
    }
}
