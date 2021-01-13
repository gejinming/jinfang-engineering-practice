package com.jinfang.entityEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SystemRole {

    STUDENT("学生","students"),
    LEADER("专业负责人","editor"), HEADMAN("答辩组组长","groupleader"),

    DEFENSE("答辩组教师","teacher"),CHECKER("评阅教师","readTeacher"),

     TEACHER("校内指导教师","adviser"),OUTTEACHER("校外指导教师","outadviser");


    @Getter
    private String name;
    @Getter
    private String roleName;



}
