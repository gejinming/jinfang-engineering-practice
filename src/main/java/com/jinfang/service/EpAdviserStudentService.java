package com.jinfang.service;

import com.jinfang.entity.CcStudent;
import com.jinfang.entity.CheckReportEntity;
import com.jinfang.entity.EpAdviserStudent;
import com.jinfang.httpdto.Result;

import java.util.List;

/**
 * @author ：Gjm
 * @date ：Created in 2021/1/20 15:33
 * @description：ep_adviser_student校内教师分配的学生
 * @version: 1.0
 */
public interface EpAdviserStudentService extends CurdService<EpAdviserStudent> {

    /*
     * @Description:根据届别，专业，教师id查询
     * 该教师已经分配的学生列表
     * @Author: Gjm
     * @Date: 2021/1/21 9:57
     **/
    List<CcStudent> findAllocatStudentList(Integer grade, Long majorId, Long teacherId);

    /*
     * @Description:根据届别，专业，教师id查询
     * 该教师可以分配的学生列表
     * @Author: Gjm
     * @Date: 2021/1/21 9:57
     **/
    List<CcStudent> findUnAllocatStudentList(Integer grade,Long majorId);

    /*
     * @Description:分配学生
     * @Author: Gjm
     * @Date: 2021/1/21 11:03
     **/
    int save(List<EpAdviserStudent> list);

    /*
     * @Description:检查周、日报分页列表
     * @Author: Gjm
     * @Date: 2021/1/27 16:00
     **/
    Result CheckReportPage(CheckReportEntity record);
}
