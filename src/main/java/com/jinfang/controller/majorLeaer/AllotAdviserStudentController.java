package com.jinfang.controller.majorLeaer;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.CcStudent;
import com.jinfang.entity.EpAdviserStudent;
import com.jinfang.entity.EpReplyinfo;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.service.EpAdviserStudentService;
import com.jinfang.service.EpGuideStudentnumService;
import com.jinfang.service.EpTaskBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 校内指导老师分配学生
 * @author: Gjm
 * @create: 2021-01-20 15:40
 **/
@RestController
@RequestMapping("/adviserStudent")
@Api(tags = "校内指导老师分配学生接口")
public class AllotAdviserStudentController extends BaseController {
    @Autowired
    private EpAdviserStudentService epAdviserStudentService;
    @Autowired
    private EpGuideStudentnumService epGuideStudentnumService;
    @Autowired
    private EpTaskBookService epTaskBookService;

    @ApiOperation("分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页码"),
            @ApiImplicitParam(name ="grade",value = "届别筛选")
    })
    @GetMapping("/findPage")
    public Result findPage(EpAdviserStudent epAdviserStudent){
        epAdviserStudent.setMajorId(getUserInfo().getMajorId());
        return epAdviserStudentService.findPage(epAdviserStudent);
    }
    @ApiOperation("查询已分配的学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="grade",value = "届别"),
            @ApiImplicitParam(name = "teacherId",value = "教师id"),
    })
    @GetMapping("/findAllocatStudentList")
    public Result findAllocatStudentList(Integer grade,Long teacherId){
        if (grade==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"届别未获取到，请检查!");
        }
        if (teacherId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"教师id未获取到，请检查!");
        }
        Long majorId = getUserInfo().getMajorId();
        if (majorId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"所属专业编号未获取到，请检查!");
        }
        return Result.ok(epAdviserStudentService.findAllocatStudentList(grade,majorId,teacherId));
    }
    @ApiOperation("删除已分配的学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="id",value = "编号id"),
    })
    @PostMapping("/delete")
    public Result delete(@RequestBody List<EpAdviserStudent> epAdviserStudents){
        if (epAdviserStudents==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"未选择删除学生编号，请检查!");
        }
        for (EpAdviserStudent temp: epAdviserStudents){
            Long id = temp.getId();
            boolean existTaskBook = epTaskBookService.isExistTaskBook(id);
            if (existTaskBook){
                return Result.error(ResultEnum.PARAM_ERROR.getCode(),"删除的学生已存在任务书不能删除，请检查!");
            }
        }
        return getUpdateResultState(epAdviserStudentService.delete(epAdviserStudents));
    }
    @ApiOperation("查询可以分配的学生列表")
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
        return Result.ok(epAdviserStudentService.findUnAllocatStudentList(grade,majorId,studentName,studentNo,className));
    }
    @ApiOperation("分配学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name="studentId",value = "学生id"),
            @ApiImplicitParam(name="teacherId",value = "教师id"),
            @ApiImplicitParam(name="grade",value = "届别")

    })
    @PostMapping("/save")
    public  Result save(@RequestBody List<EpAdviserStudent> epAdviserStudents){
        Long majorId = getUserInfo().getMajorId();
        if (majorId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"所属专业编号未获取到，请检查!");
        }
        Integer grade=0;
        Long teacherId=0l;
        for (EpAdviserStudent temp : epAdviserStudents){
            grade = temp.getGrade();
            Long studentId = temp.getStudentId();
            teacherId = temp.getTeacherId();
            if (grade==null && grade==0){
                return Result.error(ResultEnum.PARAM_ERROR.getCode(),"届别未获取到，请检查!");
            }
            if (studentId==null){
                return Result.error(ResultEnum.PARAM_ERROR.getCode(),"学生编号未获取到，请检查!");
            }
            if (teacherId==null && teacherId ==0l){
                return Result.error(ResultEnum.PARAM_ERROR.getCode(),"教师编号未获取到，请检查!");
            }
            temp.setMajorId(majorId);
        }
        int studentNum = epGuideStudentnumService.upStudentNum(majorId, grade);
        if (studentNum==0){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"校内指导老师学生上限数未设置，请检查！");
        }
        //当前老师已经分配到的学生
        List<CcStudent> allocatStudentList = epAdviserStudentService.findAllocatStudentList(grade, majorId, teacherId);
        int allocatStudentNum = allocatStudentList.size();
        //现在要分配的学生数
        int willStudentNum = epAdviserStudents.size();
        if (allocatStudentNum+willStudentNum>studentNum){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"该教师已经分配了"
                    +allocatStudentNum+"名学生，分配上限为"+studentNum+"名");
        }

        return getSaveResultState(epAdviserStudentService.save(epAdviserStudents));
    }
}
