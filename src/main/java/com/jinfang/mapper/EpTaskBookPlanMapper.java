package com.jinfang.mapper;

import com.jinfang.entity.EpTaskBookPlan;

import java.util.List;

/**
 * @author ：Gjm
 * @date ：Created in 2021/1/25 14:25
 * @description：ep_task_book_plan任务书进度安排
 * @version: 1.0
 */
public interface EpTaskBookPlanMapper extends CurdMapper<EpTaskBookPlan> {
    /*
     * @Description:根据任务书id查找计划
     * @Author: Gjm
     * @Date: 2021/1/25 17:44
     **/
    List<EpTaskBookPlan> findTaskBookPlan(Long bookId);
    /*
     * @Description:批量保存计划
     * @Author: Gjm
     * @Date: 2021/1/25 17:44
     **/
    int save(List<EpTaskBookPlan> list);
}
