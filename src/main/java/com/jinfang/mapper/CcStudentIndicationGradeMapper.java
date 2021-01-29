package com.jinfang.mapper;

import com.jinfang.entity.CcScoreStuIndigrade;
import com.jinfang.entity.CcStudentIndicationGrade;

import java.util.List;

/**
 * @author ：Gjm
 * @date ：Created in 2021/1/28 14:37
 * @description：打分查询需要的数据
 * @version: 1.0
 */
public interface CcStudentIndicationGradeMapper {

    /*
     * @Description:根据评分角色查看是否具有某个成绩组成的打分权限
     * @Author: Gjm
     * @Date: 2021/1/28 14:41
     **/
    List<CcStudentIndicationGrade> findGradecomposeList(Long majorId,String roleName,Integer grade);
    /*
     * @Description:根据开课成绩组成元素id和学生id查询成绩
     * @Author: Gjm
     * @Date: 2021/1/28 14:42
     **/
    List<CcStudentIndicationGrade> findGradeIndication(Long courseGradecomposeId,Long studentId);

    int save(List<CcScoreStuIndigrade> list);

    int update(List<CcScoreStuIndigrade> list);
    /**
     * @Description:根据开课课程成绩组成课程目标点id查找学生成绩是否存在
     * @Author: Gjm
     * @Date: 2021/1/28 17:29
     **/
    CcScoreStuIndigrade findStudentGrade(Long gradecomposeIndicationId,Long studentId);

}
