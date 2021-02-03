package com.jinfang.entity;

import com.jinfang.page.PageEntity;
import lombok.Data;

/**
 * @description: 指导老师检查周报日报返回实体类
 * @author: Gjm
 * @create: 2021-01-27 15:54
 **/
@Data
public class CheckReportEntity extends PageEntity {

    private Integer grade;

    private String companyName;

    private Long studentCompanyId;

    private String studentName;

    private String weekReportNums;

    private String dayReportNums;

    private Long teacherId;

    private Long majorId;

    private Long studentId;
}
