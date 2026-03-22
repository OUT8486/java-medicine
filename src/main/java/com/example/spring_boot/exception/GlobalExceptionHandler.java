package com.example.spring_boot.exception;

import com.example.spring_boot.entity.Result;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常处理器
 * 统一处理所有 Controller 抛出的异常
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        System.err.println("系统异常：" + e.getMessage());
        e.printStackTrace();
        return Result.error(500, "服务器错误：" + e.getMessage());
    }

    /**
     * 处理数据库访问异常
     */
    @ExceptionHandler(DataAccessException.class)
    public Result<String> handleDataAccessException(DataAccessException e) {
        System.err.println("数据库异常：" + e.getMessage());
        return Result.error(500, "数据库操作失败：" + e.getMessage());
    }

    /**
     * 处理 404 异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<String> handleNotFoundException(NoHandlerFoundException e) {
        return Result.error(404, "接口不存在：" + e.getRequestURL());
    }
}
