package com.jinfang.entity;

import com.jinfang.page.PageEntity;
import lombok.Data;

/**
 * @description: 返回学生常用信息的实体类
 * @author: GJM
 * @date: 2021-02-02 10:58
 **/
@Data
public class ResultStudentInfoEntity extends PageEntity {
    //届别
    private Integer grade;
    //学生ID
    private Long studentId;
    //姓名
    private String studentName;
    //学号
    private String studentNo;
    //专业ID
    private Long majorId;
    //专业名称
    private String majorName;
    //学院名称
    private String collegeName;
    //班级
    private String className;
    //状态名称
    private String stateName;
    //状态编号
    private Integer state;
    //实习单位ID
    private Long companyId;
    //实习单位名称
    private String companyName;
    //实习单位地址
    private String companyAdress;
    //校内指导老师分配学生编号ID
    private Long adviserStudentId;
    //校外评定表ID
    private Long outAssessId;
    //校外指导老师ID
    private Long outAdviserId;
    //校外指导老师名称
    private String outAdviserName;
    //校内指导老师ID
    private Long adversionId;
    //校内指导老师姓名
    private String adversionName;
    //实习报告ID
    private Long reportId;

    private String roleName;



}
