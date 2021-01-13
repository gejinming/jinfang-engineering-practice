package com.jinfang.mapper;

import com.jinfang.entity.SysUser;

/**
 * @description: 用户mapper
 * @author: Gjm
 * @create: 2021-01-11 11:30
 **/

public interface SysUserMapper {

    SysUser findById(Long id);

    SysUser findByName(String name);
}
