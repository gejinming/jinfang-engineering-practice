package com.jinfang.service;

import com.jinfang.entity.EpTimeControl;

/**
 * @author ：Gjm
 * @date ：Created in 2021/1/14 9:31
 * @description：时间控制
 * @version: 1.0
 */
public interface EpTimeControlService extends CurdService<EpTimeControl> {
    /*
     * @param grade
     * @return boolean
     * @description: 判断届别是否已经设置
     * @date 2021/1/15 9:28
     */
    boolean findByGrade(EpTimeControl epTimeControl);
}
