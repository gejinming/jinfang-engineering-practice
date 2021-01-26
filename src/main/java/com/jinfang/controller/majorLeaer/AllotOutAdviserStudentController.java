package com.jinfang.controller.majorLeaer;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.EpOutAdviserStudent;
import com.jinfang.entity.EpPracticeCompany;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.service.EpOutAdviserService;
import com.jinfang.service.EpOutAdviserStudentService;
import com.jinfang.service.EpPracticeCompanyService;
import com.jinfang.service.EpStudentCompanyService;
import com.jinfang.vo.LoginUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @description: 校外指导老师分配学生
 * @author: Gjm
 * @create: 2021-01-20 15:40
 **/
@RestController
@RequestMapping("/outAdviserStudent")
@Api(tags = "校外指导老师分配学生接口")
public class AllotOutAdviserStudentController extends BaseController {

    @Autowired
    private EpPracticeCompanyService epPracticeCompanyService;
    @Autowired
    private EpStudentCompanyService epStudentCompanyService;
    @Autowired
    private EpOutAdviserService epOutAdviserService;
    @Autowired
    private EpOutAdviserStudentService epOutAdviserStudentService;

    @ApiOperation("分页查询")
    @ApiImplicitParam(name="page",value = "页码")
    @GetMapping("/findPage")
    public Result findPage(EpPracticeCompany epPracticeCompany){
        Long majorId = getUserInfo().getMajorId();
        if (majorId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"所属专业未获取到，请检查!");
        }
        epPracticeCompany.setMajorId(majorId);
        return epPracticeCompanyService.allotAdviserStudentList(epPracticeCompany);
    }
    @ApiOperation("查询分配或未分配的学生列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="grade",value = "页码"),
            @ApiImplicitParam(name="companyId",value = "单位id"),
            @ApiImplicitParam(name="isAllot",value = "查询已分配的学生列表填0，分配列表填1")
    })
    @GetMapping("/noAlltoStudent")
    public Result noAlltoStudent(Integer grade,Long companyId,Integer isAllot){
        if (grade==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"届别未获取到，请检查!");
        }
        if (companyId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"单位id未获取到，请检查!");
        }
        if (isAllot==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"isAllot未获取到，请检查!");
        }
        return  Result.ok(epStudentCompanyService.findCompanyStudentList(companyId,grade,isAllot));
    }

    @ApiOperation("查询单位校外老师列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="companyId",value = "单位id")
    })
    @GetMapping("/getAdviser")
    public Result getAdviserList(Long companyId){
        return Result.ok(epOutAdviserService.findByCompanyId(companyId));
    }
    @ApiOperation("分配学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name="studentCompanyId",value = "学生选择实习单位id"),
            @ApiImplicitParam(name="adviserId",value = "指导老师id")
    })
    @PostMapping("allotStudent")
    public Result allotStudent(@RequestBody EpOutAdviserStudent epOutAdviserStudent){
        return  getSaveResultState(epOutAdviserStudentService.save(epOutAdviserStudent));
    }
    @ApiOperation("删除或修改已分配学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name="studentCompanyId",value = "学生选择实习单位id"),
            @ApiImplicitParam(name="adviserId",value = "指导老师id"),
            @ApiImplicitParam(name="idDel",value = "删除填1"),
    })
    @PostMapping("delallotStudent")
    public Result delallotStudent(@RequestBody EpOutAdviserStudent epOutAdviserStudent){
        return  getUpdateResultState(epOutAdviserStudentService.update(epOutAdviserStudent));
    }
}
