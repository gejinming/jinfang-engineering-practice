package com.jinfang.service;

import com.jinfang.entity.EpOutAdviser;

/**
 * @author ：Gjm
 * @date ：Created in 2021/1/13 9:53
 * @description：校外指导老师
 * @version: 1.0
 */
public interface EpOutAdviserService extends CurdService<EpOutAdviser> {
    /*
     * @param phone
    	 * @param password
     * @return boolean
     * @description: 判断登录人是否是校外指导老师
     * @date 2021/1/13 9:55
     */
    boolean isOutAdviser(String phone,String password);
    /*
     * @param id
     * @return com.jinfang.entity.EpOutAdviser
     * @description: 通过专业认证用户表sys_user的id 查找校外教师信息
     * @date 2021/1/13 15:47
     */
    EpOutAdviser findAdviserInfoBySysId(Long id);
}
