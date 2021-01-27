package com.jinfang.entity;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
*  cc_course_periode
* @author GJM 2021-01-27
*/
@Data
public class CcCoursePeriode implements Serializable {


    private static final long serialVersionUID = -3852977198056298411L;
    /**
    * id
    */
    private Long id;

    /**
    * 届别
    */
    private String periodDate;

    /**
    * 课程id
    */
    private Long courseId;

    /**
    * 创建日期
    */
    private Date createDate;

    /**
    * 修改日期
    */
    private Date modifyDate;

    /**
    * 开启老师id
    */
    private Long teacherId;

    /**
    * 学校id
    */
    private Long schoolId;

    /**
    * 专业id
    */
    private Long majorId;

    /**
    * 教学班id
    */
    private Long classId;

    /**
    * 1开启毕业设计，2开启工程实习
    */
    private Integer courseType;

    /**
    * 删除标识
    */
    private Integer isDel;


}
