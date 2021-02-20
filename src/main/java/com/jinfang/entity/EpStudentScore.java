package com.jinfang.entity;

import java.io.Serializable;

import com.jinfang.page.PageEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * ep_student_score
 *
 * @author GJM 2021-02-18
 */
@Data
public class EpStudentScore extends PageEntity implements Serializable {


    private static final long serialVersionUID = -8859736522056162204L;
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
     * 学生id
     */
    private Long studentId;

    /**
     * 成绩
     */
    private BigDecimal score;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 是否删除 0 :未删除，1：已删除
     */
    private Integer isDel;

    private Long courseGradecomposeId;

    private String studentNo;

    private String studentName;

    private String className;

    private String gradecomposeName;

    private String companyName;


}
