package com.jinfang.controller.majorLeaer;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.EpReplyTeacher;
import com.jinfang.entityEnum.ReplyTeacherType;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.service.EpReplyTeacherService;
import com.jinfang.vo.IntoTeacherVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 答辩组教师管理控制层
 * @author: Gjm
 * @create: 2021-01-14 14:03
 **/
@RestController
@RequestMapping("/SetReplyTeacher")
@Api("答辩组教师管理接口")
public class SetReplyTeacherController extends BaseController {
    @Autowired
    EpReplyTeacherService epReplyTeacherService;

    @ApiOperation("答辩组教师分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页码"),
            @ApiImplicitParam(name = "grade",value = "届别"),
            @ApiImplicitParam(name = "groupName",value = "组名"),
            @ApiImplicitParam(name = "groupName",value = "教师名称"),

    })
    @GetMapping("/findPage")
    public Result findPage(EpReplyTeacher epReplyTeacher){
        Long majorId = getUserInfo().getMajorId();
        if (majorId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"所属专业未获取到，请检查!");
        }
        epReplyTeacher.setMajorId(majorId);
        return epReplyTeacherService.findPage(epReplyTeacher);
    }
    @ApiOperation("设置角色或者删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id"),
            @ApiImplicitParam(name = "roleId",value = "设置教师填1，组长填2"),
            @ApiImplicitParam(name = "groupName",value = "组名"),
            @ApiImplicitParam(name = "isDel",value = "如果删除，填1"),

    })
    @PostMapping("/update")
    public Result update(EpReplyTeacher epReplyTeacher){
        return getUpdateResultState(epReplyTeacherService.update(epReplyTeacher));
    }
    @ApiOperation("拉入教师")
    @PostMapping("/intoTeacher")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grade",value = "届别"),
            @ApiImplicitParam(name = "groupName",value = "组名"),
            @ApiImplicitParam(name = "teacherId",value = "教师id"),

    })
    public Result intoTeacher(@RequestBody List<EpReplyTeacher> teacherList){
        Long majorId = getUserInfo().getMajorId();
        if (majorId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"所属专业未获取到，请检查!");
        }

        for (EpReplyTeacher temp: teacherList){
            if (temp.getTeacherId()==null || temp.getGrade() ==null){
                return Result.error(ResultEnum.PARAM_ERROR.getCode(),"教师ID或届别未获取到，请检查!");
            }
            temp.setMajorId(majorId);
            //默认为答辩组教师s
            temp.setRoleId(ReplyTeacherType.DEFENSE.getCode());
        }
        boolean gradeOpen = epReplyTeacherService.isGradeOpen(teacherList.get(0).getGrade(), majorId);
        if (!gradeOpen){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),teacherList.get(0).getGrade()+"届别该专业还未开启，请检查!");
        }
        Result result = getSaveResultState(epReplyTeacherService.save(teacherList));
        return result;
    }

}
