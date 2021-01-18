package com.jinfang.vo;

import lombok.Data;

import java.util.List;

/**
 * @description: 拉入教师参数实体类
 * @author: Gjm
 * @create: 2021-01-15 10:12
 **/
@Data
public class IntoTeacherVo {
    private Long grade;
    private List<Long> teacherIds;
    private String groupName;
    private Long majorId;
    private Integer roleId=1;
}
