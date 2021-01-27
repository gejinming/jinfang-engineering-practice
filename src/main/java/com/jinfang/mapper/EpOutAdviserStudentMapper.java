package com.jinfang.mapper;

import com.jinfang.entity.EpOutAdviserStudent;

import java.util.List;

/**
 * @author ：Gjm
 * @date ：Created in 2021/1/21 15:30
 * @description：ep_out_adviser_student校外老师分配的学生
 * @version: 1.0
 */
public interface EpOutAdviserStudentMapper extends CurdMapper<EpOutAdviserStudent> {

    /*
     * @Description:查询校外老师分配的学生
     * @Author: Gjm
     * @Date: 2021/1/21 15:43
     **/
    List<EpOutAdviserStudent> findTeacherStudent(Long teacherId);

    /*
     * @Description:查询学生分配的校外指导老师
     * @Author: Gjm
     * @Date: 2021/1/27 9:48
     **/
    EpOutAdviserStudent findStudentAdviser(Long studentId,Integer grade);
}
