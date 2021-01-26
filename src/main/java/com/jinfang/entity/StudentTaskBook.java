package com.jinfang.entity;

import lombok.Data;

/**
 * @description: 学生接收任务书列表字段实体类
 * @author: Gjm
 * @create: 2021-01-26 11:37
 **/
@Data
public class StudentTaskBook {

    private String companyName;
    private String adviserName;
    private String teacherName;
    private Integer state;
    private Long taskBookId;
}
