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
   /**
    * @param studentId: 学生ID
    * @param isHistory: 是否历史记录，新需求是学生的每一条信息都是历史记录所以显示时都显示出
    * @Author: Gjm
    * @Date: 2021/3/12 2:34 下午
    * @return: 实习报告列表
    **/
    List<EpPracticeReport> findStudentReport(Long studentId,Integer isHistory);
    /*
     * @Description:答辩组教师查询学生列表
     * @Author: Gjm
     * @Date: 2021/2/19 11:59 上午
     * @param epPracticeReport:
     * @return: java.util.List<com.jinfang.entity.EpPracticeReport>
     **/
    List<EpPracticeReport> replyReportFindPage(EpPracticeReport epPracticeReport);
}
