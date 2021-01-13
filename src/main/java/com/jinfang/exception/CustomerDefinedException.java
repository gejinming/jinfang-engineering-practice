package com.jinfang.exception;

import com.jinfang.httpdto.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper=true)
public class CustomerDefinedException extends RuntimeException{

    private Integer code;

    public CustomerDefinedException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public CustomerDefinedException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

}
