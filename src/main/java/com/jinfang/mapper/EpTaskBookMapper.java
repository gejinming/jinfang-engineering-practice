package com.jinfang.mapper;

import com.jinfang.entity.EpTaskBook;
import com.jinfang.entity.StudentTaskBook;

import java.util.List;

/**
 * @author ：Gjm
 * @date ：Created in 2021/1/25 10:14
 * @description：ep_task_book任务书
 * @version: 1.0
 */
public interface EpTaskBookMapper extends CurdMapper<EpTaskBook> {

    /*
     * @Description:历史记录
     * @Author: Gjm
     * @Date: 2021/1/26 11:07
     **/
    List<EpTaskBook> findHistory(Long adviserStudentId);
    /*
     * @Description:学生接收任务书列表
     * @Author: Gjm
     * @Date: 2021/1/26 14:08
     **/
    List<StudentTaskBook> findStudentTaskBook(Long studentId);

    EpTaskBook findTaskBook(Long adviserStudentId);

    EpTaskBook findStudentTaskBooks(Integer grade,Long studentId);
}
