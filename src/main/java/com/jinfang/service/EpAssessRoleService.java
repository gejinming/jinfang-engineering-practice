package com.jinfang.service;

import com.jinfang.entity.EpAssessRole;
import com.jinfang.entity.EpSysRole;

import java.util.List;

/**
 * @author ：Gjm
 * @date ：Created in 2021/1/15 17:11
 * @description：ep_assess_role成绩组成评分角色
 * @version: 1.0
 */
public interface EpAssessRoleService extends CurdService<EpAssessRole> {
    List<EpSysRole> findRoleList(Integer type,String roleIds);
}
