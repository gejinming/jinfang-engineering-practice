package com.jinfang.controller.majorLeaer;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.EpReplyinfo;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.service.EpReplyinfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * @description: 设置答辩信息
 * @author: Gjm
 * @create: 2021-01-20 14:21
 **/
@RestController
@RequestMapping("/setReplyInfo")
@Api(tags = "设置答辩信息接口")
public class SetReplyInfoController extends BaseController {
    @Autowired
    private EpReplyinfoService epReplyinfoService;

    @ApiOperation("分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页码"),
            @ApiImplicitParam(name ="grade",value = "届别筛选")
    })
    @GetMapping("/findPage")
    public Result findPage(EpReplyinfo epReplyinfo){
        epReplyinfo.setMajorId(getUserInfo().getMajorId());
        return epReplyinfoService.findPage(epReplyinfo);
    }

    @ApiOperation("新增答辩信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="grade",value = "届别筛选"),
            @ApiImplicitParam(name = "startTime",value = "答辩时间"),
            @ApiImplicitParam(name = "address",value = "答辩地点"),
            @ApiImplicitParam(name ="groupName",value = "答辩组名称")
    })
    @PostMapping("/save")
    public Result save(@RequestBody EpReplyinfo epReplyinfo){
        if (epReplyinfo.getGrade()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"届别为空，请检查!");
        }
        if (epReplyinfo.getStartTime()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"答辩时间为空，请检查!");
        }
        if (epReplyinfo.getAddress()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"答辩地点为空，请检查!");
        }
        if (epReplyinfo.getGroupName()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"答辩组名为空，请检查!");
        }
        Date date = new Date();
        epReplyinfo.setMajorId(getUserInfo().getMajorId());
        epReplyinfo.setCreateDate(date);
        epReplyinfo.setModifyDate(date);
        return getSaveResultState(epReplyinfoService.save(epReplyinfo));
    }
    @ApiOperation("修改答辩信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="id",value = "id编码"),
            @ApiImplicitParam(name = "startTime",value = "答辩时间"),
            @ApiImplicitParam(name = "address",value = "答辩地点")
    })
    @PostMapping("/update")
    public Result update(@RequestBody EpReplyinfo epReplyinfo){
        if (epReplyinfo.getStartTime()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"答辩时间为空，请检查!");
        }
        if (epReplyinfo.getAddress()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"答辩地点为空，请检查!");
        }
        Date date = new Date();
        epReplyinfo.setModifyDate(date);
        return getUpdateResultState(epReplyinfoService.update(epReplyinfo));
    }
    @ApiOperation("查看答辩信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="page",value = "页码"),
            @ApiImplicitParam(name = "limit",value = "条数")
    })
    @GetMapping("/findReplyInfo")
    public Result findReplyInfo(Integer page,Integer limit){
        Long majorId = getUserInfo().getMajorId();
        if (majorId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"专业id未获取到，请检查!");
        }

        return epReplyinfoService.findReplyInfo(majorId,page,limit);
    }
}
