package com.jinfang.controller.outAdviser;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.EpSchoolOutAccess;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.service.EpSchoolOutAccessService;
import com.jinfang.vo.LoginUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @description: 校外指导老师填写评语
 * @author: GJM
 * @date: 2021-02-02 11:51
 **/
@RequestMapping("/writeAssess")
@RestController
@Api(tags = "校外指导老师填写评语")
public class AdviserWriteAssessController extends BaseController {
    @Autowired
    private EpSchoolOutAccessService epSchoolOutAccessService;

    @ApiOperation("分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grade",value = "届别"),
            @ApiImplicitParam(name = "studentName",value = "学生姓名"),
            @ApiImplicitParam(name = "state",value = "0未填写，1已填写"),
    })
    @GetMapping("/findPage")
    public Result findPage(EpSchoolOutAccess epSchoolOutAccess){
        LoginUserVo userInfo = getUserInfo();
        //校外指导老师ID
        Long adviserId = userInfo.getAdviserId();
        if (adviserId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"教师id未获取到，请检查!");
        }
        epSchoolOutAccess.setAdviserId(adviserId);
        return epSchoolOutAccessService.findPage(epSchoolOutAccess);
    }
    @ApiOperation("根据评语表ID，查询详情")
    @ApiImplicitParam(name = "outAssessId",value = "评论表ID")
    @GetMapping("/findById")
    public Result findById(Long outAssessId){
        if (outAssessId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"评语表ID未获取到，请检查!");
        }
        return Result.ok(epSchoolOutAccessService.findById(outAssessId));
    }
    @ApiOperation("填写评语或者评定表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adviserStudentId",value = "校外指导老师分配学生ID"),
            @ApiImplicitParam(name = "practiceCase",value = "实习情况"),
            @ApiImplicitParam(name = "job",value = "职务"),
            @ApiImplicitParam(name = "companyRemark",value = "单位评语"),
    })
    @PostMapping("/save")
    public Result save(@RequestBody EpSchoolOutAccess epSchoolOutAccess){
        Date date = new Date();
        if (epSchoolOutAccess.getAdviserStudentId()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"校外指导老师分配学生ID未获取到，请检查!");
        }
        epSchoolOutAccess.setCreateDate(date);
        epSchoolOutAccess.setModifyDate(date);
        return getSaveResultState(epSchoolOutAccessService.save(epSchoolOutAccess));
    }
    @ApiOperation("修改评语或者评定表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ID",value = "评语表ID"),
            @ApiImplicitParam(name = "practiceCase",value = "实习情况"),
            @ApiImplicitParam(name = "job",value = "职务"),
            @ApiImplicitParam(name = "companyRemark",value = "单位评语"),
    })
    @PostMapping("/update")
    public Result update(@RequestBody EpSchoolOutAccess epSchoolOutAccess){
        if (epSchoolOutAccess.getId()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"评语表ID未获取到，请检查!");
        }
        Date date = new Date();
        epSchoolOutAccess.setModifyDate(date);
        return Result.ok(getUpdateResultState(epSchoolOutAccessService.update(epSchoolOutAccess)));
    }

}
