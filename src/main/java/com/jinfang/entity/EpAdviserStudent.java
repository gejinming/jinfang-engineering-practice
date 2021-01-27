package com.jinfang.entity;

import java.io.Serializable;

import com.jinfang.page.PageEntity;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
*  ep_adviser_student校内教师分配的学生
* @author GJM 2021-01-20
*/
@Data
public class EpAdviserStudent extends PageEntity implements Serializable {


    private static final long serialVersionUID = 1048939647963991375L;
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
    * 教师id
    */
    private Long teacherId;

    /**
    * 学生id
    */
    private Long studentId;

    /**
    * 删除标识
    */
    private Integer isDel;

    /**
     * 专业名称
     */
    private String majorName;
    /**
     * 教师名称
     */
    private String teacherName;
    /**
     * 分配的学生数量
     */
    private Integer studentNum;
    /**
     * 学生名称
     */
    private String studentName;

    /**
     * 教学班名称
     */
    private String className;
    /**
     * 学号
     */
    private String studentNo;

    private Integer state;

    private String colleageName;

    private Long taksBookId;

    private Long adviserStudentId;





}
