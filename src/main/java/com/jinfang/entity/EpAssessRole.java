package com.jinfang.entity;

import java.io.Serializable;

import com.jinfang.page.PageEntity;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
*  ep_assess_role成绩组成评分角色
* @author GJM 2021-01-15
*/
@Data
public class EpAssessRole extends PageEntity implements Serializable {


    private static final long serialVersionUID = -6783410763789276643L;
    /**
    * id
    */
    private Long id;

    /**
    * 届别
    */
    private Integer grade;

    /**
    * 配置所属专业id
    */
    private Long majorId;

    /**
    * 开课课程成绩组成元素编号
    */
    private Long courseGradecomposeId;

    /**
     * 成绩组成名称
     */
    private String gradecomposeName;

    /**
    * 角色id
    */
    private String roleIds;
    /**
     * 角色列表
     */
    private List<EpSysRole> roleList;



}
