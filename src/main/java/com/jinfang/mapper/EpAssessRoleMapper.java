package com.jinfang.mapper;

import com.jinfang.entity.EpAssessRole;
import com.jinfang.entity.EpSysRole;

import java.util.List;

/**
 * @author ：Gjm
 * @date ：Created in 2021/1/15 16:45
 * @description：ep_assess_role成绩组成评分角色
 * @version: 1.0
 */
public interface EpAssessRoleMapper extends CurdMapper<EpAssessRole> {
    /*
     * @Description:查询评分角色列表
     * @Author: Gjm
     * @Date: 2021/1/18 16:02
     **/
    List<EpSysRole> findRoleList(Integer type,String roleIds);

}
