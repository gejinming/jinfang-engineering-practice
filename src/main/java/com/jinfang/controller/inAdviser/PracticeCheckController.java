package com.jinfang.controller.inAdviser;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.EpPracticeCheck;
import com.jinfang.httpdto.Result;
import com.jinfang.service.EpPracticeCheckService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Description：实习检查表接口
 * @Author：GJM
 * @Date：2021/3/1 3:47 下午
 */
@RestController
@RequestMapping("/practice/check")
@Api(tags = "实习检查表接口")
public class PracticeCheckController extends BaseController {
    @Autowired
    private EpPracticeCheckService epPracticeCheckService;

    @ApiOperation("实习检查表列表")
    @ApiImplicitParam(name = "adviserStudentId",value = "指导老师分配学生ID")
    @GetMapping("/findPage")
    public Result findPage(EpPracticeCheck epPracticeCheck){
        if (epPracticeCheck.getAdviserStudentId() == null){
            return Result.error("adviserStudentId未获取到！");
        }

        return epPracticeCheckService.findPage(epPracticeCheck);
    }
    @ApiOperation("新增实习检查表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adviserStudentId", value = "指导老师分配学生ID"),
            @ApiImplicitParam(name = "weekNum", value = "周数"),
            @ApiImplicitParam(name = "startDate", value = "开始日期"),
            @ApiImplicitParam(name = "endDate", value = "结束日期"),
            @ApiImplicitParam(name = "requireContent", value = "执行要求情况"),
            @ApiImplicitParam(name = "practiceSchedule", value = "实习进度"),
            @ApiImplicitParam(name = "matterVaction", value = "事假"),
            @ApiImplicitParam(name = "sickVaction", value = "病假"),
            @ApiImplicitParam(name = "truant", value = "旷课"),

    })
    @PostMapping("/save")
    public Result save(@RequestBody EpPracticeCheck epPracticeCheck){
        if (epPracticeCheck.getAdviserStudentId() == null){
            return Result.error("adviserStudentId未获取到,请检查！");
        }
        if (epPracticeCheck.getWeekNum() == null){
            return Result.error("周数未获取到,请检查！");
        }
        if (epPracticeCheck.getPracticeSchedule() == null){
            return Result.error("实习进度未获取到,请检查！");
        }
        if (epPracticeCheck.getStartDate() == null){
            return Result.error("开始日期未获取到,请检查！");
        }
        if (epPracticeCheck.getEndDate() == null){
            return Result.error("结束日期未获取到,请检查！");
        }
        if (epPracticeCheck.getRequireContent() == null){
            return Result.error("执行要求情况未获取到,请检查！");
        }
        Date date = new Date();
        epPracticeCheck.setCreateDate(date);
        epPracticeCheck.setModifyDate(date);
        epPracticeCheck.setIsDel(0);
        return getSaveResultState(epPracticeCheckService.save(epPracticeCheck));
    }
    @ApiOperation("修改实习检查表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id"),
            @ApiImplicitParam(name = "weekNum", value = "周数"),
            @ApiImplicitParam(name = "startDate", value = "开始日期"),
            @ApiImplicitParam(name = "endDate", value = "结束日期"),
            @ApiImplicitParam(name = "requireContent", value = "执行要求情况"),
            @ApiImplicitParam(name = "practiceSchedule", value = "实习进度"),
            @ApiImplicitParam(name = "matterVaction", value = "事假"),
            @ApiImplicitParam(name = "sickVaction", value = "病假"),
            @ApiImplicitParam(name = "truant", value = "旷课"),

    })
    @PostMapping("/update")
    public Result update(@RequestBody EpPracticeCheck epPracticeCheck){
        if (epPracticeCheck.getId() == null){
            return Result.error("id未获取到,请检查！");
        }
        if (epPracticeCheck.getWeekNum() == null){
            return Result.error("周数未获取到,请检查！");
        }
        if (epPracticeCheck.getPracticeSchedule() == null){
            return Result.error("实习进度未获取到,请检查！");
        }
        if (epPracticeCheck.getStartDate() == null){
            return Result.error("开始日期未获取到,请检查！");
        }
        if (epPracticeCheck.getEndDate() == null){
            return Result.error("结束日期未获取到,请检查！");
        }
        if (epPracticeCheck.getRequireContent() == null){
            return Result.error("执行要求情况未获取到,请检查！");
        }
        Date date = new Date();
        epPracticeCheck.setModifyDate(date);
        return getUpdateResultState(epPracticeCheckService.update(epPracticeCheck));
    }


}
