package com.example.spring_boot.entity;

import lombok.Data;

/**
 * 统一 API 响应结果封装类
 * @param <T> 数据类型
 */
@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;
    private Long total; // 分页时使用

    /**
     * 成功响应 (code=200)
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }

    /**
     * 成功响应 (带分页总数)
     */
    public static <T> Result<T> success(T data, Long total) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        result.setTotal(total);
        return result;
    }

    /**
     * 错误响应 (code=500)
     */
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }

    /**
     * 错误响应 (自定义 code)
     */
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
