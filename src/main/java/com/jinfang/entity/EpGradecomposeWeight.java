package com.jinfang.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description：TODO成绩组成权重表
 * @Author：GJM
 * @Date：2021/2/10 4:36 下午
 */
@Data
public class EpGradecomposeWeight {

    /**
     * id
     */
    private Long id;

    /**
     * 届别
     */
    private Integer grade;

    /**
     * 专业id
     */
    private Long majorId;

    /**
     * 开课课程成绩组成元素编号，与cc_course_gradecompose id关联
     */
    private Long courseGradecomposeId;

    /**
     * 权重
     */
    private BigDecimal weight;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 删除标识
     */
    private Integer isDel;

    private String gradecomposeName;

}
