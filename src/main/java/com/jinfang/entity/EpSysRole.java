package com.jinfang.entity;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
*  ep_sys_role角色表
* @author GJM 2021-01-15
*/
@Data
public class EpSysRole implements Serializable {


    private static final long serialVersionUID = 685781320515984674L;
    /**
    * id
    */
    private Long id;

    /**
    * 角色菜单名称
    */
    private String roleName;

    /**
    * 名称
    */
    private String name;

    /**
    * 删除标识
    */
    private Integer isDel;

    /**
    * 角色：0：不可分配，1：可分配
    */
    private Integer type;


}
