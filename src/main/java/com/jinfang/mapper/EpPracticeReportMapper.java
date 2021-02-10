package com.jinfang.mapper;

import com.jinfang.entity.EpPracticeReport;
import com.jinfang.entity.ResultStudentInfoEntity;

import java.util.List;

/**
 * @Description：实习报告
 * @Author：GJM
 * @Date：2021/2/4 2:44 下午
 */
public interface EpPracticeReportMapper extends CurdMapper<EpPracticeReport> {
    /*
     * @Description:查询学生的信息
     * @Author: Gjm
     * @Date: 2021/2/4 3:28 下午
     * @return: 学生个人的信息
     **/
    List<EpPracticeReport> findStudentReport(Long studentId,Integer isHistory);
}
