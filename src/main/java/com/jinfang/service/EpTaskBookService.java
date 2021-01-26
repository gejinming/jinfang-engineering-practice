package com.jinfang.service;

import com.jinfang.entity.EpTaskBook;
import com.jinfang.entity.StudentTaskBook;
import com.jinfang.httpdto.Result;

import java.util.List;

/**
 * @author ：Gjm
 * @date ：Created in 2021/1/25 10:15
 * @description：ep_task_book任务书
 * @version: 1.0
 */
public interface EpTaskBookService extends CurdService<EpTaskBook> {

    /*
     * @Description:修改任务书状态
     * @Author: Gjm
     * @Date: 2021/1/26 10:12
     **/
    int updateTaskBookState(EpTaskBook epTaskBook);

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
    Result findStudentTaskBook(Long studentId);

    boolean isExistTaskBook(Long adviserStudentId);


}
