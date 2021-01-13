package com.jinfang.controller.login;

import com.jinfang.entity.CcStudent;
import com.jinfang.entity.SysUser;
import com.jinfang.entityEnum.ReplyTeacherType;
import com.jinfang.entityEnum.SystemRole;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.CcStudentMapper;
import com.jinfang.mapper.CcTeacherMapper;
import com.jinfang.service.*;
import com.jinfang.util.DateUtil;
import com.jinfang.util.JwtTokenUtils;
import com.jinfang.util.PasswdKit;
import com.jinfang.vo.LoginUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.Role;


/**
 * @description:登录接口
 * @author: Gjm
 * @create: 2021-01-12 10:18
 **/
@RestController
@RequestMapping("/login")
@Api(tags = "登录接口")
public class LoginController {

    @Autowired
    SystemService systemService;


    @ApiOperation("账号密码登录入口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName",value = "账号"),
            @ApiImplicitParam(name = "password",value = "密码"),
            @ApiImplicitParam(name = "role",value = "角色（学生或者教师）")
    }
    )
    @PostMapping("/userlogin")
    public Result login(String loginName, String password,String role){

        return systemService.checkUserPassword(loginName,password,role);

    }
    @ApiOperation("token登录入口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "token"),
    }
    )
    @PostMapping("/tokenLogin")
    public Result tokenLogin(String token){
        return systemService.tokenLogin(token);
    }
}
