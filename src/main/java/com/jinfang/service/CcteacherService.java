package com.jinfang.service;

import com.jinfang.entity.CcTeacher;

import java.util.List;

/**
 * @description: 教师服务接口
 * @author: Gjm
 * @create: 2021-01-08 11:50
 **/
public interface CcteacherService extends CurdService<CcTeacher>{

    boolean isSchoolLeader(Long teacherId);

    List<CcTeacher> findReplyTeacherList(Long schoolId,Long majorId,String teacherName,
                                         String majorName,Integer grade);
}
