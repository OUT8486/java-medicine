package com.example.spring_boot.exception;

import com.example.spring_boot.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常处理器：
 * 异常详情只记录在服务端日志，不向客户端泄露内部信息。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        log.error("系统异常", e);
        return Result.error(500, "服务器内部错误，请稍后重试");
    }

    @ExceptionHandler(DataAccessException.class)
    public Result<String> handleDataAccessException(DataAccessException e) {
        log.error("数据库异常", e);
        return Result.error(500, "数据库操作失败，请稍后重试");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<String> handleNotFoundException(NoHandlerFoundException e) {
        return Result.error(404, "接口不存在");
    }
}
