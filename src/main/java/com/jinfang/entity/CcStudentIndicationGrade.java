package com.jinfang.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: 答辩打分返回实体类
 * @author: Gjm
 * @create: 2021-01-28 14:29
 **/
@Data
public class CcStudentIndicationGrade {
    //成绩组成名称
    private String gradecomposeName;
    //开课成绩组成id
    private Long courseGradecomposeId;
    //角色名称
    private String roleName;
    //课程目标内容
    private String content;
    //课程目标序号
    private Integer sort;
    //成绩id
    private Long id;
    //开课课程成绩组成元素与课程目标关联id
    private Long gradecomposeIndicationId;
    //学生成绩
    private BigDecimal grade;
    private Long studentId;

}
