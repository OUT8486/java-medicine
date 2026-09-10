package com.example.spring_boot.config;

/**
 * 系统角色定义。label 与数据库 users.role 字段、JWT 的 role 声明保持一致。
 */
public enum Role {
    ADMIN("管理员"),
    USER("用户");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    public static Role fromLabel(String label) {
        if (label != null) {
            for (Role r : values()) {
                if (r.label.equals(label)) {
                    return r;
                }
            }
        }
        return null;
    }
}
