package com.jinfang.entity;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
*  ep_practice_check
* @author GJM 2021-03-01
*/
@Data
public class EpPracticeCheck implements Serializable {


    private static final long serialVersionUID = 5024986930989440555L;
    /**
    * id
    */
    private Long id;

    /**
    * 指导老师分配学生id
    */
    private Long adviserStudentId;

    /**
    * 周数
    */
    private Integer weekNum;

    /**
    * 开始日期
    */
    private Date startDate;

    /**
    * 结束日期
    */
    private Date endDate;

    /**
    * 执行要求情况
    */
    private String requireContent;

    /**
    * 实习进度 1符合，2基本符合，3不符合
    */
    private Integer practiceSchedule;

    /**
    * 事假
    */
    private String matterVaction;

    /**
    * 病假
    */
    private String sickVaction;

    /**
    * 旷课
    */
    private String truant;

    /**
    * 填写日期
    */
    private Date createDate;

    /**
    * 修改日期
    */
    private Date modifyDate;

    /**
    * 删除标识
    */
    private Integer isDel;


}
