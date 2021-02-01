package com.jinfang.controller.inAdviser;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.CcScoreStuIndigrade;
import com.jinfang.entity.CcStudentIndicationGrade;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.service.CcStudentIndicationGradeService;
import com.jinfang.vo.LoginUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 成绩组成打分
 * @author: Gjm
 * @create: 2021-01-28 11:40
 **/
@RestController
@RequestMapping("/gradecompose")
@Api(tags = "成绩组成打分接口")
public class WriteStudentScoreController extends BaseController {
    @Autowired
    private CcStudentIndicationGradeService studentIndicationGradeService;

    @ApiOperation("查询角色可以打分的成绩组成列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName",value = "角色名"),
            @ApiImplicitParam(name = "grade",value = "届别")
    })
    @GetMapping("/findGradecompose")
    public Result findGradecompose(String roleName,Integer grade){
        Long majorId = getUserInfo().getMajorId();
        if (majorId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"所属专业未获取到，请重试!");
        }
        if (roleName==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"角色名未获取到，请检查!");
        }
        if (grade==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"届别未获取到，请重试!");
        }
        List<CcStudentIndicationGrade> gradecomposeList = studentIndicationGradeService.findGradecomposeList(majorId, roleName, grade);
        return Result.ok(gradecomposeList);
    }
    @ApiOperation("根据成绩组成获取课程目标及成绩")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseGradecomposeId",value = "开课成绩组成id"),
            @ApiImplicitParam(name = "studentId",value = "学生id")
    })
    @GetMapping("/findIndicationScore")
    public Result findScore(Long courseGradecomposeId,Long studentId){
        if (courseGradecomposeId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"开课成绩组成id未获取到，请检查!");
        }
        if (studentId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"学生id未获取到，请重试!");
        }
        List<CcStudentIndicationGrade> findIndicationScore = studentIndicationGradeService.findGradeIndication(courseGradecomposeId, studentId);
        return Result.ok(findIndicationScore);
    }

    @ApiOperation("保存课程目标成绩")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "如果为空就传null"),
            @ApiImplicitParam(name = "gradecomposeIndicationId",value = "开课成绩组成课程目标id"),
            @ApiImplicitParam(name = "studentId",value = "学生id"),
            @ApiImplicitParam(name = "grade",value = "成绩")
    })
    @PostMapping("/save")
    public Result save(@RequestBody List<CcScoreStuIndigrade> ccScoreStuIndigrades){
        return studentIndicationGradeService.save(ccScoreStuIndigrades);
    }

    @ApiOperation("答辩组长查询学生打分列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentName",value = "学生姓名"),
            @ApiImplicitParam(name = "state",value = "状态(0未打分，1已打分)"),
            @ApiImplicitParam(name = "grade",value = "届别")
    })
    @GetMapping("/findStudentList")
    public Result findStudentList(Integer grade,String StringName,Integer state){
        LoginUserVo userInfo = getUserInfo();
        Long userId = userInfo.getUserId();
        if (userId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"用户id未获取到，请重试!");
        }

        return Result.ok();
    }
}
