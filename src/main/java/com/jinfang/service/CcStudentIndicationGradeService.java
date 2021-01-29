package com.jinfang.service;

import com.jinfang.entity.CcScoreStuIndigrade;
import com.jinfang.entity.CcStudentIndicationGrade;
import com.jinfang.httpdto.Result;

import java.util.List;

/**
 * @description:
 * @author: Gjm
 * @create: 2021-01-28 14:45
 **/

public interface CcStudentIndicationGradeService {
    /*
     * @Description:根据评分角色查看是否具有某个成绩组成的打分权限
     * @Author: Gjm
     * @Date: 2021/1/28 14:41
     **/
    List<CcStudentIndicationGrade> findGradecomposeList(Long majorId, String roleName,Integer grade);
    /*
     * @Description:根据开课成绩组成元素id和学生id查询成绩
     * @Author: Gjm
     * @Date: 2021/1/28 14:42
     **/
    List<CcStudentIndicationGrade> findGradeIndication(Long courseGradecomposeId,Long studentId);
    /*
     * @Description:保存学生成绩
     * @Author: Gjm
     * @Date: 2021/1/28 16:00
     **/
    Result save(List<CcScoreStuIndigrade> list);
}
