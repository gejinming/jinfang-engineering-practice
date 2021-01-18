package com.jinfang.mapper;

import com.jinfang.entity.CcTeacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：Gjm
 * @date ：Created in 2021/1/8 11:43
 * @description：教师mapper
 * @version: 1.0
 */
public interface CcTeacherMapper {
    /*
    * 查询可以拉入答辩组的教师
    * */
    List<CcTeacher> findReplyTeacherList(Long schoolId,Long majorId,String teacherName,
                                         String majorName,Integer grade);
    /*
    * 根据教师ID和角色名称查询数量
    * */
    int  selectCountByRoleName(@Param("teacherId") Long teacherId, @Param("roleName") String roleName);

    CcTeacher findById(Long teahcerId);
}
