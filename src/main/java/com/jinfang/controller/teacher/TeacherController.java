package com.jinfang.controller.teacher;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinfang.controller.BaseController;
import com.jinfang.entity.CcTeacher;
import com.jinfang.httpdto.Result;
import com.jinfang.service.CcteacherService;
import com.jinfang.vo.LoginUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: Gjm
 * @create: 2021-01-08 11:52
 **/
@Controller
@ResponseBody
@RequestMapping("/teacher")
public class TeacherController extends BaseController {

    @Autowired
    CcteacherService ccteacherService;

    @GetMapping("/findPage")
    public Result findPage(CcTeacher teacher){
        /*List<CcTeacher> teacherList = ccteacherService.findpage(teacher);
        PageInfo<CcTeacher> ccTeacherPageInfo = new PageInfo<>(teacherList);*/
        LoginUserVo loginUserVo = getUserInfo();
        return  ccteacherService.findPage(teacher);
    }
}
