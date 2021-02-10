package com.jinfang.controller.inAdviser;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.EpPracticeReport;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.service.EpPracticeReportService;
import com.jinfang.vo.LoginUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Description：指导老师检查学生实习报告
 * @Author：GJM
 * @Date：2021/2/8 11:57 上午
 */
@RestController
@RequestMapping("/check")
@Api(tags = "指导老师检查学生实习报告接口")
public class CheckStudentReportController extends BaseController {
    @Autowired
    private EpPracticeReportService epPracticeReportService;

    @ApiOperation("分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grade", value = "届别"),
            @ApiImplicitParam(name = "state", value = "状态（0未提交，1提交，2通过，3驳回）"),
            @ApiImplicitParam(name = "companyName", value = "实习单位名称"),
    }
    )
    @GetMapping("/findPage")
    public Result findPage(EpPracticeReport epPracticeReport){
        LoginUserVo userInfo = getUserInfo();
        if (userInfo==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"教师信息未获取到，请检查!");
        }
        epPracticeReport.setTeacherId(userInfo.getUserId());
        return epPracticeReportService.findPage(epPracticeReport);
    }
    @ApiOperation("提交实习报告意见")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "实习报告ID"),
            @ApiImplicitParam(name = "state", value = "状态2通过，3驳回"),
            @ApiImplicitParam(name = "teacherOpinion", value = "指导教师意见"),
    }
    )
    @PostMapping("/submit")
    public Result submit(@RequestBody EpPracticeReport epPracticeReport){
        LoginUserVo userInfo = getUserInfo();
        if (userInfo==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"教师信息未获取到，请检查!");
        }
        epPracticeReport.setCheckId(userInfo.getUserId());
        epPracticeReport.setCheckDate(new Date());
        int update = epPracticeReportService.update(epPracticeReport);
        return  getUpdateResultState(update);
    }
}
