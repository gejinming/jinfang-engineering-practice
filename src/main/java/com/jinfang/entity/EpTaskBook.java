package com.jinfang.entity;

import java.io.Serializable;

import com.jinfang.page.PageEntity;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
*  ep_task_book任务书
* @author GJM 2021-01-25
*/
@Data
public class EpTaskBook extends PageEntity implements Serializable {


    private static final long serialVersionUID = -1177669589348158089L;

    /**
    * id
    */
    private Long id;

    /**
    * 实习课题
    */
    private String practiceName;

    /**
    * 内容
    */
    private String content;

    /**
    * 要求
    */
    private String definiceRequire;

    /**
    * 创建时间
    */
    private Date createDate;

    /**
    * 修改时间
    */
    private Date modifyDate;

    /**
    * 下发状态（0未下发，1下发，2接受）
    */
    private Integer state;
    /**
     * 下发日期
     */
    private Date sendDate;
    /**
    * 指导老师分配学生id
    */
    private Long adviserStudentId;

    /**
    * 接收时间
    */
    private Date receiveTime;

    /**
    * 删除标识
    */
    private Integer isDel=0;

    /**
    * 是否为历史（0否，1是）
    */
    private Integer isHistory;

    private List<EpTaskBookPlan> epTaskBookPlans;

    private Integer grade;

    private Long teacherId;

    private String studentName;

    private String adviserName;

    private Long studentId;




}
