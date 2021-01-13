package com.jinfang.service;

import com.jinfang.entity.SysUser;
import org.springframework.stereotype.Service;

/**
 * @description: 用户服务层
 * @author: Gjm
 * @create: 2021-01-11 14:42
 **/
@Service
public interface SysUserService extends CurdService<SysUser>{

    boolean findUserById(Long id);

    SysUser findUserByName(String name);
}
