package com.jinfang.entityEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TaskBookState {

    NOSEND("未下发",0),
    SEND("下发",1),
    RECEIVE("已接收",2);

    @Getter
    private String name;
    @Getter
    private Integer code;



}
