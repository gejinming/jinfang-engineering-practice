package com.jinfang.httpdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.aspectj.apache.bcel.classfile.Code;

import java.util.HashMap;
import java.util.Map;

@Accessors(chain = true)
@Data
@AllArgsConstructor
public class Result {
    // 响应码
    private Integer code;

    // 响应消息
    private String message;

    // 响应数据
    private Object data ;

    // 私有构造器
    private Result() {

    }

    // 通用成功
    public static Result ok() {
        return new Result()
                .setCode(ResultEnum.SUCCESS.getCode())
                .setMessage(ResultEnum.SUCCESS.getMessage());
    }

    // 通用失败
    public static Result error() {
        return new Result()
                .setCode(ResultEnum.UNKNOWN_ERROR.getCode())
                .setMessage(ResultEnum.UNKNOWN_ERROR.getMessage());
    }

    public static Result error( Integer Code,String message) {
        return new Result()
                .setCode(Code)
                .setMessage(message);
    }

    public static Result error(String message) {
        return new Result()
                .setCode(ResultEnum.UNKNOWN_ERROR.getCode())
                .setMessage(message);
    }


    // 自定义返回信息
    public static Result setResult(ResultEnum result) {
        return new Result()
                .setCode(result.getCode())
                .setMessage(result.getMessage());
    }

    public static Result ok(String msg) {
        return new Result()
                .setCode(ResultEnum.SUCCESS.getCode())
                .setMessage(msg);
    }

    public static Result ok(Object data) {

        return new Result()
                .setData(data)
                .setCode(ResultEnum.SUCCESS.getCode())
                .setMessage(ResultEnum.SUCCESS.getMessage());
    }

    /** ------------使用链式编程，返回类本身-----------**/

    // 自定义状态信息
    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    // 自定义状态码
    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }



}
