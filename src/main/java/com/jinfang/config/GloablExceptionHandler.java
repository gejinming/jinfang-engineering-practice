package com.jinfang.config;

import com.jinfang.exception.CustomerDefinedException;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GloablExceptionHandler {

    /**-------- 通用异常 --------**/
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        log.error("通用异常：" + e.getMessage());
        return Result.setResult(ResultEnum.UNKNOWN_ERROR);
    }

    /**-------- 空指针异常 --------**/
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Result error(NullPointerException e) {
        log.error("空指针异常：" + e.getMessage());
        System.out.println("空指针异常：" + e.getMessage());
        return Result.setResult(ResultEnum.NULL_POINTER);
    }

    /**-------- 运行异常 --------**/
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Result error(Throwable e) {
        log.error("运行异常：" + e.getMessage());
        System.out.println("运行异常：" + e.getMessage());
        return Result.setResult(ResultEnum.SYSTEM_ERROR);
    }

    /**-------- 自定义异常 --------**/
    @ExceptionHandler(CustomerDefinedException.class)
    @ResponseBody
    public Result error(CustomerDefinedException e) {
        log.error("自定义异常：" + e.getMessage());
        return Result.error().message(e.getMessage()).code(e.getCode());
    }

    /**-------- 算法异常 --------**/
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e) {
        log.error("算法异常：" + e.getMessage());
        return Result.error().message(e.getMessage());
    }
    /*------------token异常-------*/
    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    public Result error(ExpiredJwtException e){
        log.error("token异常：" + e.getMessage());
        return Result.error().message(e.getMessage());
    }
    /**-------- HTTP请求异常 --------**/
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Result error(HttpRequestMethodNotSupportedException e) {
        log.error("HTTP请求异常：" + e.getMessage());
        return Result.error().message(e.getMessage());
    }

    /**-------- 数据库操作异常 --------**/
    @ExceptionHandler(DataAccessException.class)
    @ResponseBody
    public Result error(DataAccessException e) {
        log.error("数据库操作异常：" + e.getMessage());
        return Result.error().message(e.getMessage());
    }

}
