package com.jinfang.entity;

import java.io.Serializable;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
*  考核成绩分析法学生课程目标成绩
* @author GJM 2021-01-28
*/
@Data
public class CcScoreStuIndigrade implements Serializable {


    private static final long serialVersionUID = -3860252895023509084L;
    /**
    * 编号
    */
    private Long id;

    /**
    * 创建日期
    */
    private Date createDate;

    /**
    * 修改日期
    */
    private Date modifyDate;

    /**
    * 开课课程成绩组成元素与课程目标关联
    */
    private Long gradecomposeIndicationId;

    /**
    * 学生编号
    */
    private Long studentId;

    /**
    * 成绩
    */
    private BigDecimal grade;



    /**
    * 等级明细编号
    */
    private Long levelDetailId;

    /**
    * 学生成绩类型（默认1，1：考核成绩，2：评分表，3财经大学的考核成绩）
    */
    private Integer type=1;

    /**
    * 是否已删除 0未删除 1已删除
    */
    private String isDel;


}
