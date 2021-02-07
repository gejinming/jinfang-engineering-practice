package com.jinfang.controller.student;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.EpPracticeReport;
import com.jinfang.entity.EpPracticeReportDoc;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.mapper.EpPracticeReportDocMapper;
import com.jinfang.service.EpPracticeReportService;
import com.jinfang.util.FileDownloadUtil;
import com.jinfang.vo.LoginUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @Description：学生提交工程实习报告
 * @Author：GJM
 * @Date：2021/2/4 3:16 下午
 */
@RestController
@RequestMapping("/proacticeReport")
@Api(tags = "学生提交工程实习接口")
@Slf4j
public class ProacticeReportController extends BaseController {
    @Autowired
    private EpPracticeReportService epPracticeReportService;
    @Autowired
    private EpPracticeReportDocMapper epPracticeReportDocMapper;
    @ApiOperation("学生实习报告列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页码"),
            @ApiImplicitParam(name = "limit",value = "显示行数"),
            @ApiImplicitParam(name = "isHistory",value = "默认0，1显示历史记录"),
    })
    @GetMapping("/findPage")
    public Result findPage(Integer page, Integer limit,Integer isHistory){

        LoginUserVo userInfo = getUserInfo();
        if (userInfo==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"用户信息未获取到，请检查!");
        }

        return  epPracticeReportService.findStudentReport(userInfo.getUserId(),page,limit,isHistory);
    }
    @ApiOperation("提交实习报告")
    @ApiImplicitParam(name = "id",value = "实习报告ID")
    @PostMapping("/submit")
    public Result submit(@RequestBody EpPracticeReport epPracticeReport){
        if (epPracticeReport.getId()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"实习报告ID未获取到，请检查!");
        }
        epPracticeReport.setState(1);
        epPracticeReport.setSubmitDate(new Date());
        int update = epPracticeReportService.update(epPracticeReport);
        if (update>0){
            return Result.ok("提交成功");
        }
        return Result.error("提交失败");
    }


}
