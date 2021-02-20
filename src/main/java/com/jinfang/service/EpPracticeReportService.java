package com.jinfang.service;

import com.jinfang.entity.EpPracticeReport;
import com.jinfang.entity.ResultStudentInfoEntity;
import com.jinfang.httpdto.Result;

import java.util.List;

/**
 * @Description：TODO
 * @Author：GJM
 * @Date：2021/2/4 2:45 下午
 */
public interface EpPracticeReportService extends CurdService<EpPracticeReport> {
    /*
     * @Description:查询学生的信息
     * @Author: Gjm
     * @Date: 2021/2/4 3:28 下午
     * @return: 学生个人的信息
     **/
    Result findStudentReport(Long studentId, Integer page, Integer limit,Integer isHistory);

    Result replyReportFindPage(EpPracticeReport epPracticeReport);
}
