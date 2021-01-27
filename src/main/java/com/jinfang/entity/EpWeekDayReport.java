package com.jinfang.entity;

import java.io.Serializable;

import com.jinfang.page.PageEntity;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
*  ep_week_day_report学生日报周报
* @author GJM 2021-01-27
*/
@Data
public class EpWeekDayReport extends PageEntity implements Serializable {


    private static final long serialVersionUID = 152723456262561646L;
    /**
    * id
    */
    private Long id;

    /**
    * 学生选择实习单位id
    */
    private Long studentCompanyId;

    /**
    * 内容
    */
    private String content;

    /**
    * 创建时间
    */
    private Date createDate;

    /**
    * 报告类型（1日报，2周报）
    */
    private Integer reportType;

    /**
    * 评语
    */
    private String assessContent;

    /**
    * 检查时间
    */
    private Date assessDate;

    /**
    * 删除标识
    */
    private Integer isDel;

    private String companyName;

    private Integer grade;

    private Long studentId;



}
