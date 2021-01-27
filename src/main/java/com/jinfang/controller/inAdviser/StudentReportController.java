package com.jinfang.controller.inAdviser;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.CheckReportList;
import com.jinfang.entity.EpWeekDayReport;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.service.EpAdviserStudentService;
import com.jinfang.service.EpWeekDayReportService;
import com.jinfang.vo.LoginUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @description: 指导老师检查周报日报
 * @author: Gjm
 * @create: 2021-01-27 15:26
 **/
@RestController
@RequestMapping("/checkReport")
@Api(tags = "指导老师检查周报日报接口")
public class StudentReportController extends BaseController {
    @Autowired
    private EpAdviserStudentService epAdviserStudentService;
    @Autowired
    private EpWeekDayReportService epWeekDayReportService;

    @ApiOperation("分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页码"),
            @ApiImplicitParam(name = "companyName",value = "单位名称"),
            @ApiImplicitParam(name = "studentName",value = "学生名称")
    })

    @GetMapping("/findPage")
    public Result findPage(CheckReportList checkReportList){
        LoginUserVo userInfo = getUserInfo();
        if (userInfo.getUserId()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"未获取到用户id，请检查!");
        }
        if (userInfo.getMajorId()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"未获取到用户专业id，请检查!");
        }
        checkReportList.setMajorId(userInfo.getMajorId());
        checkReportList.setTeacherId(userInfo.getUserId());
        return epAdviserStudentService.CheckReportPage(checkReportList);

    }
    @ApiOperation("学生报告列表")
    @ApiImplicitParam(name = "studentCompanyId",value = "studentCompanyId")
    @GetMapping("/studentReportList")
    public Result studentReportList(Long studentCompanyId){

        if (studentCompanyId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"未获取到studentCompanyId，请检查!");
        }
        List<EpWeekDayReport> epWeekDayReports = epWeekDayReportService.studentReportList(studentCompanyId);

        return Result.ok(epWeekDayReports);

    }
    @ApiOperation("填写评语")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id"),
            @ApiImplicitParam(name = "assessContent",value = "评语"),
    })
    @PostMapping("/submit")
    public Result submit(@RequestBody EpWeekDayReport epWeekDayReport){
        if (epWeekDayReport.getId()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"未获取到报告id，请检查!");
        }
        if (epWeekDayReport.getAssessContent()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"未获取到报告评语，请检查!");
        }
        epWeekDayReport.setAssessDate(new Date());
        return getUpdateResultState(epWeekDayReportService.update(epWeekDayReport));
    }

}
