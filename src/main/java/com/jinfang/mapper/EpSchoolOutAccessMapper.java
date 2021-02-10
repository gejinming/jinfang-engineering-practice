package com.jinfang.mapper;

import com.jinfang.entity.EpSchoolOutAccess;

/**
 * @description: 校外评定表
 * @author: GJM
 * @date: 2021-02-02 10:42
 **/
public interface EpSchoolOutAccessMapper extends CurdMapper<EpSchoolOutAccess>{
    /*
     * @Description:根据学生ID和届别查询校外评定表信息和实习单位、校外指导老师信息
     * @Author: Gjm
     * @Date: 2021/2/9 2:15 下午
    * @param studentId:
     * @param grade:
    * @return: com.jinfang.entity.EpSchoolOutAccess
     **/
    EpSchoolOutAccess findStudentAssess(Long studentId,Integer grade);

}
