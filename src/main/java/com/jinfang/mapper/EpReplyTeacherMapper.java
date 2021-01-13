package com.jinfang.mapper;

import com.jinfang.entity.EpReplyTeacher;

import java.util.List;


/**
* ep_reply_teache
* @author GJM
* @date 2021/01/12
*/
public interface EpReplyTeacherMapper {

    /**
    * [新增]
    * @author GJM
    * @date 2021/01/12
    **/
    int insert(EpReplyTeacher epReplyTeacher);

    /**
    * [刪除]
    * @author GJM
    * @date 2021/01/12
    **/
    int delete(int id);

    /**
    * [更新]
    * @author GJM
    * @date 2021/01/12
    **/
    int update(EpReplyTeacher epReplyTeacher);

    /**
    * [查询] 根据主键 id 查询
    * @author GJM
    * @date 2021/01/12
    **/
    EpReplyTeacher load(int id);
    /**
     * [查询] 根据届别和教师Id 角色
     * @author GJM
     * @date 2021/01/12
     **/
    List<EpReplyTeacher> findByTeacher(Integer grade,Long teacherId,Integer roleId);

    /**
    * [查询] 分页查询
    * @author GJM
    * @date 2021/01/12
    **/
    List<EpReplyTeacher> pageList(int offset, int pagesize);

    /**
    * [查询] 分页查询 count
    * @author GJM
    * @date 2021/01/12
    **/
    int pageListCount(int offset,int pagesize);

}
