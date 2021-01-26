package com.jinfang.entity;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
*  ep_student_company学生选择实习单位
* @author GJM 2021-01-21
*/
@Data
public class EpStudentCompany implements Serializable {


    private static final long serialVersionUID = 4384460602526811214L;
    /**
    * id
    */
    private Long id;

    /**
    * 学生id
    */
    private Long studentId;

    /**
    * 单位id
    */
    private Long companyId;

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

    /**
    * 实习是否结束（0未结束，1结束）
    */
    private Integer isEnd;

    private Integer grade;

    private String companyName;





}
