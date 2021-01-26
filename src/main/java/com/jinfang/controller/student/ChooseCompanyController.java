package com.jinfang.controller.student;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.EpPracticeCompany;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.service.EpPracticeCompanyService;
import com.jinfang.vo.LoginUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 学生选择实习单位
 * @author: Gjm
 * @create: 2021-01-26 15:44
 **/
@RestController
@RequestMapping("/chooseCompany")
@Api(tags = "学生选择实习单位接口")
public class ChooseCompanyController extends BaseController {
    @Autowired
    private EpPracticeCompanyService epPracticeCompanyService;

    @ApiOperation("分页查询实习单位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页码"),
            @ApiImplicitParam(name = "name",value = "单位名称")
    })
    @GetMapping("/findPage")
    public Result findPage(EpPracticeCompany epPracticeCompany){
        LoginUserVo userInfo = getUserInfo();
        Long majorId = userInfo.getMajorId();
        if (majorId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"所属专业未获取到，请检查!");
        }
        Long userId = userInfo.getUserId();
        epPracticeCompany.setMajorId(majorId);
        return epPracticeCompanyService.chooseCompanyFindPageList(epPracticeCompany,userId);
    }
   /* @ApiOperation("选择实习单位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "公司id"),
            @ApiImplicitParam(name = "name",value = "单位名称")
    })
    @GetMapping("/findPage")*/

}
