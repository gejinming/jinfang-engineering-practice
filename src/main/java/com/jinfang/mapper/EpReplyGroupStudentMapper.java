package com.jinfang.mapper;

import com.jinfang.entity.CcStudent;
import com.jinfang.entity.EpAdviserStudent;
import com.jinfang.entity.EpReplyGroupStudent;

import java.util.List;

/**
 * @Description：学生分配答辩组
 * @Author：GJM
 * @Date：2021/2/18 2:51 下午
 */
public interface EpReplyGroupStudentMapper {
    int save(List<EpReplyGroupStudent> list);


    int delete(Long id);
    /*
     * @Description:查询未分配答辩组的学生
     * @Author: Gjm
     * @Date: 2021/2/18 3:28 下午
     * @param majorId:
     * @param grade:
     * @param studentName:
     * @param studentNo:
     * @param className:
    * @return: java.util.List<com.jinfang.entity.CcStudent>
     **/
    List<CcStudent> findUnAllocatStudentList(Long majorId,Integer grade,String studentName,String studentNo,String className);
    /*
     * @Description:查询答辩组已分配的学生
     * @Author: Gjm
     * @Date: 2021/2/18 3:30 下午
     * @param majorId:
     * @param grade:
     * @param groupName:
    * @return: java.util.List<com.jinfang.entity.CcStudent>
     **/
    List<CcStudent> findAllocatStudentList(Long majorId,Integer grade,String groupName);

    List<EpAdviserStudent> findGroupStudentInfo(Long majorId, Integer grade, String groupName,String studentName,String companyName);

}
