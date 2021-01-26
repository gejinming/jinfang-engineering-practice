package com.jinfang.mapper;

import com.jinfang.entity.EpGuideStudentnum;

/**
 * @author ：Gjm
 * @date ：Created in 2021/1/20 10:02
 * @description：ep_guide_studentnum校内指导教师指导学生数上限表
 * @version: 1.0
 */
public interface EpGuideStudentnumMapper extends CurdMapper<EpGuideStudentnum> {
    /*
     * 查询上限学生数
     * */
    int upStudentNum(Long majorId,Integer grade);
}
