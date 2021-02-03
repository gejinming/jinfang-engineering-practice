package com.jinfang.entity;

import java.io.Serializable;

import com.jinfang.page.PageEntity;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
*  sys_user
* @author gjm 2021-01-08
*/
@Data
public class SysUser extends PageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * id
    */
    private Long id;

    /**
    * create_date
    */
    private Date createDate;

    /**
    * modify_date
    */
    private Date modifyDate;

    /**
    * department
    */
    private String department;

    /**
    * email
    */
    private String email;

    /**
    * is_enabled
    */
    private String isEnabled;

    /**
    * is_locked
    */
    private String isLocked;

    /**
    * locked_date
    */
    private Date lockedDate;

    /**
    * login_date
    */
    private Date loginDate;

    /**
    * login_failure_count
    */
    private Integer loginFailureCount;

    /**
    * login_ip
    */
    private String loginIp;

    /**
    * name
    */
    private String name;

    /**
    * password
    */
    private String password;

    /**
    * loginname
    */
    private String loginName;

    /**
     * loginname原始
     */
    private String outloginName;

    /**
    * 用户类型(0：管理员）
    */
    private Integer type;

    /**
    * 是否绑定邮箱
    */
    private String isBindEmail;

    /**
    * 是否绑定手机
    */
    private String isBindMobile;

    /**
    * 是否删除
    */
    private Integer isDel;


}
