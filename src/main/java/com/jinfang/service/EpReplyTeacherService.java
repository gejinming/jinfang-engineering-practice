package com.jinfang.service;

import com.jinfang.entity.EpReplyTeacher;
import com.jinfang.vo.IntoTeacherVo;

import java.util.List;

/**
 * @description: 答辩组教师接口
 * @author: Gjm
 * @create: 2021-01-12 17:08
 **/

public interface EpReplyTeacherService extends CurdService<EpReplyTeacher> {
    /*
     * @param grade
    	 * @param teacherId
     * @return java.util.List<com.jinfang.entity.EpReplyTeacher>
     * @description: 根据届别和教师id,这里可能有多个，
     * 可能是多个专业的答辩教师，目前没有指定专业
     * @date 2021/1/12 17:32
     */
    List<EpReplyTeacher> findByTeacher(Integer grade, Long teacherId,Integer roleType);

    boolean isGroupLeader(Integer grade, Long teacherId,Integer roleType);

    int save(List<EpReplyTeacher> vo);

    boolean isGradeOpen(Integer grade ,Long majorId);
}
