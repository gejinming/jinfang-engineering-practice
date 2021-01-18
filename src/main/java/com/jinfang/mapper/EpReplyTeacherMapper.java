package com.jinfang.mapper;

import com.jinfang.entity.EpReplyTeacher;
import com.jinfang.vo.IntoTeacherVo;

import java.util.List;
import java.util.Map;


/**
* ep_reply_teache答辩组教师管理
* @author GJM
* @date 2021/01/12
*/
public interface EpReplyTeacherMapper extends CurdMapper<EpReplyTeacher>{

    /**
     * [查询] 根据届别和教师Id 角色
     * @author GJM
     * @date 2021/01/12
     **/
    List<EpReplyTeacher> findByTeacher(Integer grade,Long teacherId,Integer roleId);
    /*
     * @param vo
     * @description: 批量拉入教师
     * @date 2021/1/15 10:52
     */
    int save(List<EpReplyTeacher> vo);
    /*
     * @Description:根据组名修改其他教师的roleid
     * @Author: Gjm
     * @Date: 2021/1/18 11:21
     **/
    int updateByGroupName(String groupName);
    /*
     * @Description:判断开启的届别是否存在
     * @Author: Gjm
     * @Date: 2021/1/18 15:21
     **/
    Map<String,Object> findGradeIsOpen(Integer grade,Long majorId);

}
