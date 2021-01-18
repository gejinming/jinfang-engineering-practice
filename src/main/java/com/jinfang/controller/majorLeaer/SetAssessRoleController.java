package com.jinfang.controller.majorLeaer;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.EpAssessRole;
import com.jinfang.entity.EpSysRole;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.service.EpAssessRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 设置评分角色
 * @author: Gjm
 * @create: 2021-01-18 15:51
 **/
@RestController
@RequestMapping("/setRole")
@Api(tags = "设置评分角色接口")
public class SetAssessRoleController extends BaseController {
    @Autowired
    EpAssessRoleService epAssessRoleService;

    @ApiOperation("分页查询评分角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页码"),
            @ApiImplicitParam(name = "grade",value = "届别")

    })
    @GetMapping("/findPage")
    public Result findPage(EpAssessRole assessRole){
       return epAssessRoleService.findPage(assessRole);
    }
    @ApiOperation("查询评分角色列表")
    @GetMapping("/findRoleList")
    public Result findRoleList(){
        List<EpSysRole> roleList = epAssessRoleService.findRoleList(1,null);
        return Result.ok(roleList);
    }
    @ApiOperation("设置评分角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "ID编码,ID未null则为新增，否则修改"),
            @ApiImplicitParam(name = "grade",value = "届别"),
            @ApiImplicitParam(name = "roleIds",value = "角色id"),
            @ApiImplicitParam(name = "courseGradecomposeId",value = "成绩组成开课id"),

    })
    @PostMapping("/setGradecomposeRole")
    public Result setGradecomposeRole(EpAssessRole assessRole){
        if (assessRole.getGrade()==null || assessRole.getRoleIds()==null ){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"届别未获取到，请检查!");
        }
        if (assessRole.getRoleIds()==null ){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"评分角色ID未获取到，请检查!");
        }
        if (assessRole.getCourseGradecomposeId()==null ){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"成绩组成开课ID未获取到，请检查!");
        }
        //新增
        if (assessRole.getId()==null){
            assessRole.setMajorId(getUserInfo().getMajorId());
            return getSaveResultState(epAssessRoleService.save(assessRole));
        }else{
           //修改
            return getUpdateResultState(epAssessRoleService.update(assessRole));
        }
    }

}
