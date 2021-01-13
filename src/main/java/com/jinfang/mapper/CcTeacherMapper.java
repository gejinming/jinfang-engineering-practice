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
    * 分页
    * */
    List<CcTeacher> findPage();
    /*
    * 根据教师ID和角色名称查询数量
    * */
    int  selectCountByRoleName(@Param("teacherId") Long teacherId, @Param("roleName") String roleName);

    CcTeacher findById(Long teahcerId);
}
