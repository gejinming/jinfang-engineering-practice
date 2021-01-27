package com.jinfang.mapper;

import com.jinfang.entity.EpWeekDayReport;

import java.util.List;

/**
 * @description: ep_week_day_report学生日报周报
 * @author: Gjm
 * @create: 2021-01-27 11:12
 **/

public interface EpWeekDayReportMapper extends CurdMapper<EpWeekDayReport> {
    /*
     * @Description:指导老师下的学生的周报、日报
     * @Author: Gjm
     * @Date: 2021/1/27 16:22
     **/
    List<EpWeekDayReport> findTeacherStudentReport(Long teacherId,Long majorId);
    /*
     * @Description:根据studentCompanyId查询学生报告
     * @Author: Gjm
     * @Date: 2021/1/27 17:17
     **/
    List<EpWeekDayReport> studentReportList(Long studentCompanyId);
}
