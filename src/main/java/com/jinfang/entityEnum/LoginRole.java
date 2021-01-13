package com.jinfang.entityEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum LoginRole {

    STUDENT("学生","student"),TEACHER("教师","teacher");


    @Getter
    private String name;
    @Getter
    private String roleName;



}
