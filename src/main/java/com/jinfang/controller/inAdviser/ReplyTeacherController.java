package com.jinfang.controller.inAdviser;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.EpPracticeReport;
import com.jinfang.httpdto.Result;
import com.jinfang.service.EpPracticeReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description：TODO答辩组教师接口
 * @Author：GJM
 * @Date：2021/2/19 10:07 上午
 */
@RestController
@RequestMapping("/reply")
@Api(tags = "答辩组教师接口")
public class ReplyTeacherController extends BaseController {
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
        epPracticeReport.setTeacherId(getUserInfo().getUserId());
        return epPracticeReportService.replyReportFindPage(epPracticeReport);
    }

}
