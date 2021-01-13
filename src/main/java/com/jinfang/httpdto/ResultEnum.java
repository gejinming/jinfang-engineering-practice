package com.jinfang.httpdto;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(200,"执行成功"),
    UNEXIST_URL(404,"URL不存在"),
    UNEXIST_ERROR(404,"用户不存在或密码有误"),
    UNKNOWN_ERROR(520,"未知错误"),
    SYSTEM_ERROR(500, "服务器开小差了，请稍后再试"),
    PARAM_ERROR(201,"参数错误"),
    NULL_POINTER(202,"空指针异常"),
    TOKEN_ERR(203,"--token错误---"),;

    // 响应状态码
    private Integer code;
    // 响应信息
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
