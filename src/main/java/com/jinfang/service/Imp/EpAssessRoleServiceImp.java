package com.jinfang.service.Imp;

import com.jinfang.entity.EpAssessRole;
import com.jinfang.entity.EpSysRole;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpAssessRoleMapper;
import com.jinfang.service.EpAssessRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:成绩组成评分角色实现
 * @author: Gjm
 * @create: 2021-01-15 17:16
 **/
@Service
public class EpAssessRoleServiceImp implements EpAssessRoleService {

    @Autowired(required = false)
    EpAssessRoleMapper epAssessRoleMapper;

    @Override
    public int save(EpAssessRole record) {
        return epAssessRoleMapper.save(record);
    }

    @Override
    public int delete(EpAssessRole record) {
        return 0;
    }

    @Override
    public int delete(List<EpAssessRole> records) {
        return 0;
    }

    @Override
    public EpAssessRole findById(Long id) {
        return null;
    }

    @Override
    public int update(EpAssessRole record) {
        return epAssessRoleMapper.update(record);
    }

    @Override
    public Result findPage(EpAssessRole record) {
        List<EpAssessRole> page = epAssessRoleMapper.findPage(record);
        for (EpAssessRole temp : page){
            String roleIds = temp.getRoleIds();
            if (roleIds !=null){
                List<EpSysRole> roleList = epAssessRoleMapper.findRoleList(1, roleIds);
                temp.setRoleList(roleList);
            }

        }
        return Result.ok(page);
    }

    @Override
    public List<EpSysRole> findRoleList(Integer type,String roleIds) {
        return epAssessRoleMapper.findRoleList(1,roleIds);
    }
}
