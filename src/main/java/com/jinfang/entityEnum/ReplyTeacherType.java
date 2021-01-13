package com.jinfang.entityEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ReplyTeacherType {

    DEFENSE("答辩组教师",1),
    HEADMAN("答辩组组长",2);

    @Getter
    private String name;
    @Getter
    private Integer code;



}
