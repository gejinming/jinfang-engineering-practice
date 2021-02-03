package com.jinfang.controller.majorLeaer;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.EpOutAdviser;
import com.jinfang.entity.EpPracticeCompany;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.mapper.EpPracticeCompanyMapper;
import com.jinfang.service.EpOutAdviserService;
import com.jinfang.service.EpOutAdviserStudentService;
import com.jinfang.service.EpPracticeCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 实习单位管理
 * @author: Gjm
 * @create: 2021-01-19 10:11
 **/
@RestController
@RequestMapping("/setCompany")
@Api(tags = "实习单位管理接口")
public class SetCompanyController extends BaseController {
    @Autowired
    private EpPracticeCompanyService epPracticeCompanyService;
    @Autowired
    private EpOutAdviserService epOutAdviserService;
    @Autowired
    private EpOutAdviserStudentService epOutAdviserStudentService;

    @ApiOperation("分页查询实习单位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页码"),
            @ApiImplicitParam(name = "name",value = "单位名称")
    })
    @GetMapping("/findPage")
    public Result findPage(EpPracticeCompany epPracticeCompany){
        Long majorId = getUserInfo().getMajorId();
        if (majorId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"所属专业未获取到，请检查!");
        }
        epPracticeCompany.setMajorId(majorId);
        return epPracticeCompanyService.findPage(epPracticeCompany);
    }
    @ApiOperation("新增实习单位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "单位名称"),
            @ApiImplicitParam(name = "address",value = "地址"),
            @ApiImplicitParam(name = "phone",value = "电话"),
            @ApiImplicitParam(name = "outAdvisers",value = "校外指导老师列表"),
    })
    @PostMapping("/save")
    public Result saveCompany(@RequestBody EpPracticeCompany epPracticeCompany){
        Long majorId = getUserInfo().getMajorId();
        String phone = epPracticeCompany.getPhone();
        String name = epPracticeCompany.getName();
        String address = epPracticeCompany.getAddress();
        if (majorId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"所属专业未获取到，请检查!");
        }
        if (phone==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"单位联系方式未设置，请检查!");
        }
        if (name==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"单位名称未设置，请检查!");
        }
        if (address==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"单位地址未设置，请检查!");
        }
        if (epPracticeCompanyService.findByName(majorId,name,null)){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"单位名称已存在，请检查!");
        }
        List<EpOutAdviser> outAdvisers = epPracticeCompany.getOutAdvisers();
        //判断新增校外老师的时候是否有手机号重复
        ArrayList<String> phoneList = new ArrayList<>();
        for (EpOutAdviser temp: outAdvisers){
            String tempPhone = temp.getPhone();
            String tempName = temp.getName();
            String tempPassword = temp.getPassword();
            if (tempName==null){
                return Result.error(ResultEnum.PARAM_ERROR.getCode(),"校外指导老师姓名未设置，请检查!");
            }
            if (tempPhone==null){
                return Result.error(ResultEnum.PARAM_ERROR.getCode(),"校外指导老师手机号未设置，请检查!");
            }
            if (tempPassword==null){
                temp.setPassword("1234");
            }
            if (phoneList.contains(tempPhone)){
                return Result.error(ResultEnum.PARAM_ERROR.getCode(),"添加的校外指导老师手机号"+tempPhone+"有重复，请检查!");
            }
            phoneList.add(tempPhone);
            //验证手机号是否已经存在，因为账号就是手机号不能重复
            boolean outAdviser = epOutAdviserService.isOutAdviser(tempPhone, null);
            if (outAdviser){
                return Result.error(ResultEnum.PARAM_ERROR.getCode(),"校外指导老师手机号"+tempPhone+"已被使用，请检查!");
            }
        }
        Date date = new Date();
        epPracticeCompany.setMajorId(majorId);
        epPracticeCompany.setCreateDate(date);
        epPracticeCompany.setModifyDate(date);
        epPracticeCompany.setSchoolId(getUserInfo().getSchoolId());
        return getSaveResultState(epPracticeCompanyService.save(epPracticeCompany));
    }
    @ApiOperation("查看实习单位")
    @ApiImplicitParam(name="id",value = "实习单位id")
    @GetMapping("findById")
    public Result findById(Long id){
        if (id==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"单位id未获取到，请检查!");
        }
        return Result.ok(epPracticeCompanyService.findById(id));
    }
    @ApiOperation("修改实习单位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "单位名称"),
            @ApiImplicitParam(name = "address",value = "地址"),
            @ApiImplicitParam(name = "phone",value = "电话"),
            @ApiImplicitParam(name = "outAdvisers",value = "校外指导老师列表"),
    })
    @PostMapping("/update")
    public Result update(@RequestBody EpPracticeCompany epPracticeCompany){
        Long id = epPracticeCompany.getId();

        if (id==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"单位编号为空，请检查!");
        }
        Long majorId = getUserInfo().getMajorId();
        String phone = epPracticeCompany.getPhone();
        String name = epPracticeCompany.getName();
        String address = epPracticeCompany.getAddress();
        if (majorId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"所属专业未获取到，请检查!");
        }
        if (phone==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"单位联系方式未设置，请检查!");
        }
        if (name==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"单位名称未设置，请检查!");
        }
        if (address==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"单位地址未设置，请检查!");
        }
        if (epPracticeCompanyService.findByName(majorId,epPracticeCompany.getName(),epPracticeCompany.getId())){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"单位名称已存在，请检查!");
        }
        epPracticeCompany.setModifyDate(new Date());
        return  getUpdateResultState(epPracticeCompanyService.update(epPracticeCompany));
    }
    @ApiOperation("删除实习单位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "单位id")
    })
    @PostMapping("/delete")
    public Result delete(@RequestBody EpPracticeCompany epPracticeCompany){
        Long id = epPracticeCompany.getId();
        if (id==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"单位编号为空，请检查!");
        }
        //判断是否有学生选择
        boolean existStudent = epPracticeCompanyService.isExistStudent(id);
        if (existStudent){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"该单位存在实习学生不能删除，请检查!");
        }
        epPracticeCompany.setIsDel(1);
        epPracticeCompany.setModifyDate(new Date());
        int deleCompany = epPracticeCompanyService.update(epPracticeCompany);
        return  getUpdateResultState(deleCompany);

    }

    @ApiOperation("新增校外指导老师")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId",value = "单位id"),
            @ApiImplicitParam(name = "name",value = "姓名"),
            @ApiImplicitParam(name = "phone",value = "电话"),
            @ApiImplicitParam(name = "outAdvisers",value = "校外指导老师列表"),
    })
    @PostMapping("/insertOutAdiviser")
    public Result insertOutAdiviser(@RequestBody EpOutAdviser epOutAdviser){
        if (epOutAdviser.getCompanyId()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"单位编号未设置，请检查!");
        }
        if (epOutAdviser.getName()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"姓名未设置，请检查!");
        }
        if (epOutAdviser.getPhone()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"手机号未设置，请检查!");
        }
        if (epOutAdviser.getPassword()==null){
            epOutAdviser.setPassword("1234");
        }
        //验证手机号是否已经存在，因为账号就是手机号不能重复
        boolean outAdviser = epOutAdviserService.isOutAdviser(epOutAdviser.getPhone(), null);
        if (outAdviser){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"该手机号已被使用，请检查!");
        }
        epOutAdviser.setSchoolId(getUserInfo().getSchoolId());
        return getSaveResultState(epOutAdviserService.save(epOutAdviser));
    }
    @ApiOperation("修改或删除校外指导老师")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "编号id"),
            @ApiImplicitParam(name = "isDel",value = "删除填1")
    })
    @PostMapping("/delOutAdviser")
    public Result delOutAdviser(@RequestBody EpOutAdviser epOutAdviser){
        if (epOutAdviser.getId()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"单位编号未设置，请检查!");
        }
        if (epOutAdviser.getIsDel()==1){
            //判断是否有学生
            boolean existStudents = epOutAdviserStudentService.isExistStudents(epOutAdviser.getId());
            if (existStudents){
                return Result.error(ResultEnum.PARAM_ERROR.getCode(),"该老师存在实习学生不能删除，请检查!");
            }
        }
        if (epOutAdviser.getPhone()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"手机号未获取到，请检查!");
        }
        epOutAdviser.setSchoolId(getUserInfo().getSchoolId());
       return getUpdateResultState(epOutAdviserService.update(epOutAdviser));
    }
}
