package com.example.spring_boot.utils;

import java.util.UUID;

/**
 * 业务主键生成器：前缀 + 16 位十六进制，总长不超过 20，适配 VARCHAR(20) 主键。
 */
public final class IdGenerator {

    private IdGenerator() {
    }

    public static String next(String prefix) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return prefix + uuid.substring(0, 16);
    }
}
