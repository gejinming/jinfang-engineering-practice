package com.jinfang.controller.majorLeaer;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.EpGradecomposeWeight;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.mapper.EpGradecomposeWeightMapper;
import com.jinfang.service.EpGradecomposeWeightService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description：TODO设置成绩组成权重
 * @Author：GJM
 * @Date：2021/2/10 4:39 下午
 */
@RestController
@RequestMapping("/setWeight")
@Api(tags = "设置成绩组成权重")
public class SetGradecomposeWeightController extends BaseController {
    @Autowired
    private EpGradecomposeWeightService  epGradecomposeWeightService;

    @ApiOperation("根据届别查询成绩组成权重")
    @ApiImplicitParam(name = "grade",value = "届别")
    @GetMapping("findGradecomposeWeight")
    public Result findGradecomposeWeight(Integer grade){
        Long majorId = getUserInfo().getMajorId();
        if (majorId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"所属专业未获取到，请检查!");
        }
        EpGradecomposeWeight epGradecomposeWeight = new EpGradecomposeWeight();
        epGradecomposeWeight.setGrade(grade);
        epGradecomposeWeight.setMajorId(majorId);
        return Result.ok(epGradecomposeWeightService.findPage(epGradecomposeWeight));
    }

}
