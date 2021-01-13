package com.jinfang.service.Imp;

import com.jinfang.entity.SysUser;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.SysUserMapper;
import com.jinfang.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 用户实现层
 * @author: Gjm
 * @create: 2021-01-12 11:45
 **/
@Service
public class SysUserServiceImp implements SysUserService {

    @Autowired(required = false)
    private SysUserMapper sysUserMapper;

    @Override
    public int save(SysUser record) {
        return 0;
    }

    @Override
    public int delete(SysUser record) {
        return 0;
    }

    @Override
    public int delete(List<SysUser> records) {
        return 0;
    }

    @Override
    public SysUser findById(Long id) {
        SysUser sysUser = sysUserMapper.findById(id);
        return sysUser;
    }


    @Override
    public Result findPage(SysUser record) {
        return null;
    }

    public boolean findUserById(Long id){
        SysUser sysUser = findById(id);
        if (sysUser != null){
            return true;
        }
        return false;
    }

    public SysUser findUserByName(String userName){
        SysUser sysUser = sysUserMapper.findByName(userName);
        return sysUser;
    }
}
