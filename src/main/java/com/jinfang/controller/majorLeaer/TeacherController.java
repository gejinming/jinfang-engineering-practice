package com.jinfang.controller.majorLeaer;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.CcTeacher;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.service.CcteacherService;
import com.jinfang.vo.LoginUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@RestController
@RequestMapping("/teacher")
@Api(tags = "查询教师数据接口")
public class TeacherController extends BaseController {

    @Autowired
    CcteacherService ccteacherService;

    @ApiOperation("教师列表,参数可用于筛选")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grade",value = "届别（必传）"),
            @ApiImplicitParam(name = "majorName",value = "专业名称（筛选）"),
            @ApiImplicitParam(name = "teacherName",value = "教师名称（筛选）")
    })
    @GetMapping("/findReplyTeacherList")
    public Result findReplyTeacherList(String teacherName,String majorName,Integer grade){
        Long schoolId = getUserInfo().getSchoolId();
        Long majorId = getUserInfo().getMajorId();
        if (schoolId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"教师所属学校未获取到，请检查!");
        }
        if (majorId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"教师所属专业未获取到，请检查!");
        }
        List<CcTeacher> replyTeacherList = ccteacherService.findReplyTeacherList(schoolId, majorId, teacherName, majorName, grade);
        return  Result.ok(replyTeacherList);
    }
}
