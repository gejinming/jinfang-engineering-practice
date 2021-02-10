package com.jinfang.controller.student;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.EpWeekDayReport;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.service.EpWeekDayReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 日报、周报
 * @author: Gjm
 * @create: 2021-01-27 11:23
 **/
@RestController
@RequestMapping("/writeReport")
@Api(tags = "学生填写日报、周报接口")
public class WriteReportController extends BaseController {
    @Autowired
    private EpWeekDayReportService epWeekDayReportService;

    @ApiOperation("分页查询实习单位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页码"),
            @ApiImplicitParam(name = "grade",value = "届别"),
    })
    @GetMapping("/findPage")
    public Result findPage(EpWeekDayReport epWeekDayReport){
        if (epWeekDayReport.getGrade()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"届别未获取到，请检查!");
        }
        Long userId = getUserInfo().getUserId();
        if (userId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"用户id未获取到，请检查!");
        }
        epWeekDayReport.setStudentId(userId);
        return epWeekDayReportService.findPage(epWeekDayReport);
    }

    @ApiOperation("填写日报或周报")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grade",value = "届别"),
            @ApiImplicitParam(name = "reportType",value = "类型，1周报，2日报"),
            @ApiImplicitParam(name = "content",value = "内容"),
            @ApiImplicitParam(name = "weekNum",value = "周数（周报填写）"),
            @ApiImplicitParam(name = "startDate",value = "开始时间（周报填写）"),
            @ApiImplicitParam(name = "endDate",value = "结束时间（周报填写）"),
    })
    @PostMapping("/writeReport")
    public Result writeReport(@RequestBody EpWeekDayReport epWeekDayReport){
        if (epWeekDayReport.getGrade()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"届别未获取到，请检查!");
        }
        if (epWeekDayReport.getReportType()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"报告类型未获取到，请检查!");
        }
        if (epWeekDayReport.getContent()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"报告内容未获取到，请检查!");
        }
        Long userId = getUserInfo().getUserId();
        if (userId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"用户id未获取到，请检查!");
        }
        epWeekDayReport.setStudentId(userId);
        return epWeekDayReportService.sumitReport(epWeekDayReport);
    }

}
