package com.jinfang.service;

import com.jinfang.entity.CcStudent;
import com.jinfang.entity.EpReplyGroupStudent;
import com.jinfang.entity.EpReplyinfo;
import com.jinfang.httpdto.Result;

import java.util.List;

/**
 * @Description：TODO
 * @Author：GJM
 * @Date：2021/2/18 2:52 下午
 */
public interface EpReplyGroupStudentService {
    int save(List<EpReplyGroupStudent> list);

    int delete(Long id);

    Result findPage(EpReplyinfo epReplyinfo);

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
    List<CcStudent> findUnAllocatStudentList(Long majorId, Integer grade, String studentName, String studentNo, String className);
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
}
