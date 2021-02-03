package com.jinfang.mapper;

import com.jinfang.entity.CcStudent;
import com.jinfang.entity.CheckReportEntity;
import com.jinfang.entity.EpAdviserStudent;

import java.util.List;

/**
 * @author ：Gjm
 * @date ：Created in 2021/1/20 15:32
 * @description：ep_adviser_student校内教师分配的学生
 * @version: 1.0
 */
public interface EpAdviserStudentMapper extends CurdMapper<EpAdviserStudent> {
    /*
     * @Description:获取未分配的学生数量
     * @Author: Gjm
     * @Date: 2021/1/20 17:12
     **/
    Integer unallocatedStudent(Long majorId);
    /*
     * @Description:根据届别，专业，教师id查询
     * 该教师分配的学生列表
     * @Author: Gjm
     * @Date: 2021/1/21 9:57
     **/
    List<CcStudent> findAllocatStudentList(Integer grade,Long majorId,Long teacherId);
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
     * @Description:检查周、日报列表
     * @Author: Gjm
     * @Date: 2021/1/27 16:00
     **/
    List<CheckReportEntity> CheckReportPage(CheckReportEntity record);

    List<EpAdviserStudent> findGradeStudentList(Long majorId,Integer grade);

    /*
     * @Description:通用查询学生姓名、班级、实习单位信息
     * 筛选条件专业id、届别、姓名，分配的教师id
     * @Author: Gjm
     * @Date: 2021/1/29 17:18
     **/
    List<EpAdviserStudent> findStudentInfo(Long majorId,String studentName,Integer grade,List<Long> teacherList);
}
