package com.jinfang.controller.majorLeaer;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.EpReplyGroupStudent;
import com.jinfang.entity.EpReplyinfo;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.service.EpReplyGroupStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description：TODO
 * @Author：GJM
 * @Date：2021/2/18 2:55 下午
 */
@RestController
@RequestMapping("/alltoStudent")
@Api(tags = "答辩组分配学生")
public class AlltoReplyStudentController extends BaseController {
    @Autowired
    private EpReplyGroupStudentService studentService;
    @ApiOperation("分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页码"),
            @ApiImplicitParam(name ="grade",value = "届别筛选")
    })
    @GetMapping("/findPage")
    public Result findPage(EpReplyinfo epReplyinfo){
        epReplyinfo.setMajorId(getUserInfo().getMajorId());
        return studentService.findPage(epReplyinfo);
    }
    @ApiOperation("分配学生列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="grade",value = "届别"),
            @ApiImplicitParam(name ="studentName",value = "学生姓名（筛选）"),
            @ApiImplicitParam(name ="studentNo",value = "学号（筛选）"),
            @ApiImplicitParam(name ="className",value = "班级（筛选）")
    })
    @GetMapping("/findUnAllocatStudentList")
    public Result findUnAllocatStudentList(Integer grade,String studentName,String studentNo,String className){
        if (grade==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"届别未获取到，请检查!");
        }
        Long majorId = getUserInfo().getMajorId();
        if (majorId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"所属专业编号未获取到，请检查!");
        }
        return Result.ok(studentService.findUnAllocatStudentList(majorId,grade,studentName,studentNo,className));
    }
    @ApiOperation("已分配学生列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="grade",value = "届别"),
            @ApiImplicitParam(name ="groupName",value = "组别"),
    })
    @GetMapping("/findAllocatStudentList")
    public Result findAllocatStudentList(Integer grade,String groupName){
        if (grade==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"届别未获取到，请检查!");
        }
        if (groupName==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"组别未获取到，请检查!");
        }
        Long majorId = getUserInfo().getMajorId();
        if (majorId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"所属专业编号未获取到，请检查!");
        }
        return Result.ok(studentService.findAllocatStudentList(majorId,grade,groupName));
    }
    @ApiOperation("分配学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="grade",value = "届别"),
            @ApiImplicitParam(name ="groupName",value = "组别"),
            @ApiImplicitParam(name ="studentId",value = "学生ID"),
    })
    @PostMapping("/allTo")
    public Result allTo(@RequestBody List<EpReplyGroupStudent> epReplyGroupStudents){
        if (epReplyGroupStudents==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"没有分配学生，请检查!");
        }
        Long majorId = getUserInfo().getMajorId();
        if (majorId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"所属专业编号未获取到，请检查!");
        }
        for (EpReplyGroupStudent temp : epReplyGroupStudents){
            temp.setMajorId(majorId);
            if (temp.getGrade()==null){
                return Result.error(ResultEnum.PARAM_ERROR.getCode(),"届别未获取到，请检查!");
            }
            if (temp.getGroupName()==null){
                return Result.error(ResultEnum.PARAM_ERROR.getCode(),"组别未获取到，请检查!");
            }
            if (temp.getStudentId()==null){
                return Result.error(ResultEnum.PARAM_ERROR.getCode(),"组别未获取到，请检查!");
            }
        }
        return getSaveResultState(studentService.save(epReplyGroupStudents));
    }
    @ApiOperation("删除分配")
    @ApiImplicitParam(name = "id",value = "id")
    @PostMapping("/delete")
    public Result delete(@RequestBody EpReplyGroupStudent epReplyGroupStudent){
        if (epReplyGroupStudent.getId()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"id未获取到，请检查!");
        }
        return getUpdateResultState(studentService.delete(epReplyGroupStudent.getId()));
    }
}
