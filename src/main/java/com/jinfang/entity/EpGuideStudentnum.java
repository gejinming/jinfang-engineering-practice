package com.jinfang.entity;

import java.io.Serializable;

import com.jinfang.page.PageEntity;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
*  ep_guide_studentnum校内指导教师指导学生数上限表
* @author GJM 2021-01-20
*/
@Data
public class EpGuideStudentnum extends PageEntity implements Serializable {


    private static final long serialVersionUID = -3723235458309885318L;
    /**
    * id
    */
    private Long id;

    /**
    * 配置所属专业id
    */
    private Long majorId;

    /**
    * 届别
    */
    private Integer grade;

    /**
    * 上限学生数
    */
    private Integer studentNum;

    /**
    * 创建时间
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
