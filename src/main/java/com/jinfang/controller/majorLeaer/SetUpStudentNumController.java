package com.jinfang.controller.majorLeaer;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.EpGuideStudentnum;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.service.EpGuideStudentnumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @description: 设置指导老师学生上限
 * @author: Gjm
 * @create: 2021-01-20 10:47
 **/
@RestController
@RequestMapping("/setUpStudentNum")
@Api(tags = "设置指导老师学生上限接口")
public class SetUpStudentNumController extends BaseController {
    @Autowired
    private EpGuideStudentnumService epGuideStudentnumService;

    @ApiOperation("分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页码"),
            @ApiImplicitParam(name = "grade",value = "届别筛选")
    })
    @GetMapping("/findPage")
    public Result findPage(EpGuideStudentnum epGuideStudentnum){
        Long majorId = getUserInfo().getMajorId();
        if (majorId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"所属专业未获取到，请检查!");
        }
        epGuideStudentnum.setMajorId(majorId);
        return epGuideStudentnumService.findPage(epGuideStudentnum);
    }

    @ApiOperation("新增指导老师学生上限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grade",value = "届别"),
            @ApiImplicitParam(name = "studentNum",value = "上限学生数")
    })
    @PostMapping("/save")
    public Result save(@RequestBody EpGuideStudentnum epGuideStudentnum){
        if (epGuideStudentnum.getGrade()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"届别为空，请检查!");
        }
        if (epGuideStudentnum.getStudentNum()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"上限学生数为空，请检查!");
        }
        Long majorId = getUserInfo().getMajorId();
        if (majorId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"所属专业未获取到，请检查!");
        }
        Date date = new Date();
        epGuideStudentnum.setMajorId(majorId);
        epGuideStudentnum.setCreateDate(date);
        epGuideStudentnum.setModifyDate(date);
        return getSaveResultState(epGuideStudentnumService.save(epGuideStudentnum));
    }
    @ApiOperation("修改指导老师学生上限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "上限编号"),
            @ApiImplicitParam(name = "studentNum",value = "上限学生数")
    })
    @PostMapping("/update")
    public Result update(@RequestBody EpGuideStudentnum epGuideStudentnum){
        if (epGuideStudentnum.getId()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"编号id为空，请检查!");
        }
        if (epGuideStudentnum.getStudentNum()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"上限学生数为空，请检查!");
        }
        Date date = new Date();
        epGuideStudentnum.setModifyDate(date);
        return getUpdateResultState(epGuideStudentnumService.update(epGuideStudentnum));
    }

}
