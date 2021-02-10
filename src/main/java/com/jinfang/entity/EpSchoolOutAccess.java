package com.jinfang.entity;

import java.io.Serializable;

import com.jinfang.page.PageEntity;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
*  ep_school_out_access校外评定表
* @author GJM 2021-02-02
*/
@Data
public class EpSchoolOutAccess extends PageEntity implements Serializable {


    private static final long serialVersionUID = 4989000720650246907L;
    /**
    * id
    */
    private Long id;

    /**
    * 校外老师分配学生id
    */
    private Long adviserStudentId;

    /**
    * 实习情况
    */
    private String practiceCase;

    /**
    * 职务
    */
    private String job;

    /**
    * 单位评论
    */
    private String companyRemark;

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

    private Integer grade;

    private String studentName;

    private Integer state;

    private Long adviserId;

    private String outAdviserName;

    private String companyName;

    private String address;

}
