package com.jinfang.controller.majorLeaer;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.EpGradecomposeWeight;
import com.jinfang.entity.EpStudentScore;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.mapper.EpGradecomposeWeightMapper;
import com.jinfang.service.EpGradecomposeWeightService;
import com.jinfang.util.PriceUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description：TODO设置成绩组成权重
 * @Author：GJM
 * @Date：2021/2/10 4:39 下午
 */
@RestController
@RequestMapping("/setWeight")
@Api(tags = "生成最终成绩接口")
public class SetGradecomposeWeightController extends BaseController {
    @Autowired
    private EpGradecomposeWeightService epGradecomposeWeightService;

    @ApiOperation("根据届别查询各成绩组成权重")
    @ApiImplicitParam(name = "grade", value = "届别")
    @GetMapping("/findGradecomposeWeight")
    public Result findGradecomposeWeight(Integer grade) {
        Long majorId = getUserInfo().getMajorId();
        if (majorId == null) {
            return Result.error(ResultEnum.PARAM_ERROR.getCode(), "所属专业未获取到，请检查!");
        }
        EpGradecomposeWeight epGradecomposeWeight = new EpGradecomposeWeight();
        epGradecomposeWeight.setGrade(grade);
        epGradecomposeWeight.setMajorId(majorId);
        return Result.ok(epGradecomposeWeightService.findPage(epGradecomposeWeight));
    }

    @ApiOperation("设置成绩组成权重并生成成绩")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grade", value = "届别"),
            @ApiImplicitParam(name = "courseGradecomposeId", value = "成绩组成编号"),
            @ApiImplicitParam(name = "weight", value = "权重"),
    })
    @PostMapping("/setGradecomposeWeight")
    public Result setGradecomposeWeight(@RequestBody List<EpGradecomposeWeight> epGradecomposeWeights) {
        if (epGradecomposeWeights == null) {
            return Result.error(ResultEnum.PARAM_ERROR.getCode(), "权重设置未获取到，请检查!");
        }
        Long majorId = getUserInfo().getMajorId();
        if (majorId == null) {
            return Result.error(ResultEnum.PARAM_ERROR.getCode(), "所属专业未获取到，请检查!");
        }
        BigDecimal maxWeight = new BigDecimal("1");
        BigDecimal initWeight = new BigDecimal("0");
        for (EpGradecomposeWeight temp : epGradecomposeWeights) {
            if (temp.getCourseGradecomposeId() == null) {
                return Result.error(ResultEnum.PARAM_ERROR.getCode(), "权重设置成绩组成编号未获取到，请检查!");
            }
            if (temp.getGrade() == null) {
                return Result.error(ResultEnum.PARAM_ERROR.getCode(), "权重设置届别未获取到，请检查!");
            }
            BigDecimal weight = temp.getWeight();
            if (weight == null) {
                return Result.error(ResultEnum.PARAM_ERROR.getCode(), "权重设置成绩组成编号未获取到，请检查!");
            }
            initWeight = initWeight.add(weight);
            Date date = new Date();
            temp.setCreateDate(date);
            temp.setModifyDate(date);
            temp.setMajorId(majorId);
        }
        //设置权重不能大于1
        if (PriceUtils.greaterThan(initWeight, maxWeight)) {
            return Result.error(ResultEnum.PARAM_ERROR.getCode(), "总权重不能大于1，请检查!");
        }
        //设置权重必须等于1
        if (!PriceUtils.eqThan(initWeight, maxWeight)) {
            return Result.error(ResultEnum.PARAM_ERROR.getCode(), "总权重必须等于1，请检查!");
        }
        return epGradecomposeWeightService.save(epGradecomposeWeights);

    }
    @ApiOperation("学生成绩分页查询")
    @ApiImplicitParam(name = "grade", value = "届别")
    @GetMapping("/findPage")
    public Result findPage(EpStudentScore epStudentScore){
        if (epStudentScore.getGrade() == null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(), "届别未获取到，请检查!");
        }
        epStudentScore.setMajorId(getUserInfo().getMajorId());
        return epGradecomposeWeightService.studentScorePage(epStudentScore);
    }


}
