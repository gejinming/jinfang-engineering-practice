package com.jinfang.vo;

import lombok.Data;

import java.util.List;

/**
 * @description: 登录元数据
 * @author: Gjm
 * @create: 2021-01-11 11:35
 **/
@Data
public class LoginUserVo {
    //登录角色 ：学生、教师
    private String role;
    //系统角色
    private List<String> roles;
    //用户id
    private Long userId;
    //账号
    private String loginName;
    //用户名称
    private String name;
    //学校
    private Long schoolId;
    //专业
    private Long majorId;
    //届别
    private Integer grade;
    //校外指导老师ID
    private Long adviserId;


}
