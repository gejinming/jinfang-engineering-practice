package com.jinfang.mapper;

import com.jinfang.entity.EpOutAdviser;

/**
 * @author ：Gjm
 * @date ：Created in 2021/1/13 9:47
 * @description：校外指导老师接口
 * @version: 1.0
 */
public interface EpOutAdviserMapper {

    /*
     * @param phone
    	 * @param password
     * @return com.jinfang.entity.EpOutAdviser
     * @description: 根据手机号和密码查询指导老师
     * @date 2021/1/13 9:52
     */
    EpOutAdviser findOutAdviser(String phone,String password);
    /*
     * @param id
     * @return com.jinfang.entity.EpOutAdviser
     * @description: 通过专业认证用户表sys_user的id 查找校外教师信息
     * @date 2021/1/13 15:47
     */
    EpOutAdviser findAdviserInfoBySysId(Long id);
}
