package com.jinfang.entity;

import java.io.PipedReader;
import java.io.Serializable;

import com.jinfang.page.PageEntity;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
*  ep_practice_report实习报告
* @author GJM 2021-02-04
*/
@Data
public class EpPracticeReport extends PageEntity implements Serializable {


    private static final long serialVersionUID = -7881442684593698300L;
    /**
    * id
    */
    private Long id;

    /**
    * 指导老师分配学生id
    */
    private Long adviserStudentId;

    /**
    * 实习报告文件id
    */
    private Long fileId;

    /**
    * 上传时间
    */
    private Date createDate;

    /**
    * 提交时间
    */
    private Date submitDate;

    /**
    * 文件状态（0未提交，1提交，2通过，3驳回）
    */
    private Integer state;

    /**
    * 指导教师意见
    */
    private String teacherOpinion;

    /**
    * 审核时间
    */
    private Date checkDate;

    /**
    * 审核人id
    */
    private Long checkId;

    /**
    * 删除标识
    */
    private Integer isDel;

    /**
    * 是否为历史（0否，1是）
    */
    private Integer isHistory;

    private String companyName;

    private String adviserName;

    private Long adviserStudentIds;

    private String path;

    private Long studentId;

    private String studentName;

    private Integer grade;

    private String className;

    private Long teacherId;

}
