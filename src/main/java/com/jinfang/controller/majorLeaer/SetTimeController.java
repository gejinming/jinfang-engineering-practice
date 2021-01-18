package com.jinfang.controller.majorLeaer;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.EpTimeControl;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.service.EpTimeControlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @description: 专业负责人参数设置
 * @author: Gjm
 * @create: 2021-01-13 16:39
 **/
@RestController
@RequestMapping("/setTime")
@Api(tags = "设置时间控制接口")
@CrossOrigin
public class SetTimeController extends BaseController {

    @Autowired
    private EpTimeControlService epTimeControlService;

    @ApiOperation(value = "设置时间控制")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grade",value = "届别"),
            @ApiImplicitParam(name = "startTime",value = "开始时间"),
            @ApiImplicitParam(name = "endTime",value = "结束时间")
    })

    @PostMapping("/setTimeControl")
    public Result setTimeControl(@RequestBody EpTimeControl epTimeControl){
        if (epTimeControl.getGrade()==null || epTimeControl.getStartTime()==null ||epTimeControl.getEndTime() ==null){
           return Result.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
        }
        if (getUserInfo().getMajorId() ==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"所属专业未获取到，请检查!");
        }
        Date date = new Date();
        epTimeControl.setCreateDate(date);
        epTimeControl.setModifyDate(date);
        epTimeControl.setMajorId(getUserInfo().getMajorId());
        epTimeControl.setUserId(getUserInfo().getUserId());
        //判断届别是否存在
        boolean isExist = epTimeControlService.findByGrade(epTimeControl);
        if (!isExist){
            return Result.error(ResultEnum.SAVE_ERROR.getCode(),"此届别已设置时间控制，请检查！");
        }
        return getSaveResultState(epTimeControlService.save(epTimeControl)) ;
    }


    @ApiOperation(value = "获取时间控制列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页码"),
            @ApiImplicitParam(name = "limit",value = "显示行数"),
            @ApiImplicitParam(name = "grade",value = "届别")
    })
    @GetMapping("/findPage")
    public Result findPage(EpTimeControl epTimeControl){
        if (epTimeControl.getPage()==null){
           return Result.error(ResultEnum.PARAM_ERROR.getCode(),"错误：页码为空");
        }
        epTimeControl.setMajorId(getUserInfo().getMajorId());
        return epTimeControlService.findPage(epTimeControl);

    }
    @ApiOperation(value = "根据id获取时间控制信息")
    @GetMapping("/findById")
    public Result findById(Long id){
        if (id==null){
           return Result.error("错误：id为空");
        }
        EpTimeControl epTimeControl = epTimeControlService.findById(id);
        if (epTimeControl==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"错误：未找到数据信息");
        }
        return Result.ok(epTimeControl);
    }
    @ApiOperation(value = "修改时间或者删除")

    @PostMapping("/updateTime")
    public Result updateTime(@RequestBody EpTimeControl epTimeControl){
        if (epTimeControl.getIsDel()==null){
            if (epTimeControl.getId()==null||
                    epTimeControl.getGrade()==null || epTimeControl.getStartTime()==null
                    ||epTimeControl.getEndTime() ==null){
                return Result.error(ResultEnum.PARAM_ERROR.getCode(),ResultEnum.PARAM_ERROR.getMessage());
            }
        }

        Date date = new Date();
        epTimeControl.setModifyDate(date);
        return  getUpdateResultState(epTimeControlService.update(epTimeControl));
    }



}
