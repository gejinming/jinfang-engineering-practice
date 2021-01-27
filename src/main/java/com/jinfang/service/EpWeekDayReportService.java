package com.jinfang.service;

import com.jinfang.entity.EpWeekDayReport;
import com.jinfang.httpdto.Result;

import java.util.List;

/**
 * @author ：Gjm
 * @date ：Created in 2021/1/27 11:13
 * @description：ep_week_day_report学生日报周报
 * @version: 1.0
 */
public interface EpWeekDayReportService extends CurdService<EpWeekDayReport> {

    Result sumitReport(EpWeekDayReport epWeekDayReport);
    /*
     * @Description:根据studentCompanyId查询学生报告
     * @Author: Gjm
     * @Date: 2021/1/27 17:17
     **/
    List<EpWeekDayReport> studentReportList(Long studentCompanyId);
}
