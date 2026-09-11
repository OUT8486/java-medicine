package com.example.spring_boot.utils;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IdGeneratorTest {

    @Test
    void generatesPrefixedIdWithinColumnLength() {
        String id = IdGenerator.next("SO");
        assertTrue(id.startsWith("SO"));
        assertEquals(18, id.length());
        assertTrue(id.length() <= 20);
    }

    @Test
    void generatesUniqueIds() {
        Set<String> ids = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            ids.add(IdGenerator.next("X"));
        }
        assertEquals(1000, ids.size());
    }
}
