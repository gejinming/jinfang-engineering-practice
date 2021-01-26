package com.jinfang.service;

import com.jinfang.entity.EpOutAdviserStudent;

import java.util.List;

/**
 * @description: 校外老师分配的学生
 * @author: Gjm
 * @create: 2021-01-21 15:32
 **/

public interface EpOutAdviserStudentService extends CurdService<EpOutAdviserStudent> {

    /*
     * @Description:查询校外老师分配的学生
     * @Author: Gjm
     * @Date: 2021/1/21 15:43
     **/
    List<EpOutAdviserStudent> findTeacherStudent(Long teacherId);

    boolean isExistStudents(Long teacherId);
}
