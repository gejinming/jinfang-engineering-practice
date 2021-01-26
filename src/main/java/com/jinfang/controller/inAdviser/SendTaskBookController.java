package com.jinfang.controller.inAdviser;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.EpTaskBook;
import com.jinfang.entityEnum.TaskBookState;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.service.EpTaskBookService;
import com.jinfang.vo.LoginUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @description: 指导老师下发任务书
 * @author: Gjm
 * @create: 2021-01-25 10:06
 **/
@RestController
@RequestMapping("/sendBook")
@Api(tags = "指导老师下发任务书接口")
public class SendTaskBookController extends BaseController {
    @Autowired
    private EpTaskBookService epTaskBookService;

    @ApiOperation("分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grade", value = "届别"),
            @ApiImplicitParam(name = "state", value = "状态(0未下发，1下发，2接受，3未编写)")
    }
    )
    @GetMapping("/findPage")
    public Result findPage(EpTaskBook epTaskBook){
        LoginUserVo userInfo = getUserInfo();
        if (userInfo.getUserId()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"教师id未获取到，请检查!");
        }
        epTaskBook.setTeacherId(userInfo.getUserId());
        return epTaskBookService.findPage(epTaskBook);
    }

    @ApiOperation("查看任务书")
    @ApiImplicitParam(name = "taskBookId", value = "任务书Id")
    @GetMapping("/findById")
    public Result findById(Long taskBookId){
        EpTaskBook taskBook = epTaskBookService.findById(taskBookId);
        return Result.ok(taskBook);
    }

    @ApiOperation("新增任务书")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "practiceName", value = "实习课题"),
            @ApiImplicitParam(name = "adviserStudentId", value = "指导老师分配学生id"),
            @ApiImplicitParam(name = "content", value = "任务书内容"),
            @ApiImplicitParam(name = "definiceRequire", value = "要求"),
            @ApiImplicitParam(name = "epTaskBookPlans", value = "实习进度计划List")
    })
    @PostMapping("/save")
    public Result save(@RequestBody EpTaskBook epTaskBook){
        if (epTaskBook.getPracticeName()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"实习课题不能为空，请检查!");
        }
        if (epTaskBook.getContent()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"实习内容不能为空，请检查!");
        }
        if (epTaskBook.getDefiniceRequire()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"实习要求不能为空，请检查!");
        }
        if (epTaskBook.getAdviserStudentId()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"分配校内指导老师id不能为空，请检查!");
        }
        return getSaveResultState(epTaskBookService.save(epTaskBook));
    }
    @ApiOperation("修改任务书")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id"),
            @ApiImplicitParam(name = "practiceName", value = "实习课题"),
            @ApiImplicitParam(name = "adviserStudentId", value = "指导老师分配学生id"),
            @ApiImplicitParam(name = "content", value = "任务书内容"),
            @ApiImplicitParam(name = "definiceRequire", value = "要求"),
            @ApiImplicitParam(name = "epTaskBookPlans", value = "实习进度计划List")
    })
    @PostMapping("/update")
    public Result update(@RequestBody EpTaskBook epTaskBook){
        if (epTaskBook.getId()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"任务书id不能为空，请检查!");
        }
        if (epTaskBook.getPracticeName()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"实习课题不能为空，请检查!");
        }
        if (epTaskBook.getContent()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"实习内容不能为空，请检查!");
        }
        if (epTaskBook.getDefiniceRequire()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"实习要求不能为空，请检查!");
        }
        if (epTaskBook.getAdviserStudentId()==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"分配校内指导老师id不能为空，请检查!");
        }
        return getUpdateResultState(epTaskBookService.update(epTaskBook));
    }
    @ApiOperation("下发任务书")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id")
    })
    @PostMapping("/sendTaskBook")
    public Result sendTaskBook(@RequestBody EpTaskBook epTaskBook){
        Date date = new Date();
        epTaskBook.setState(TaskBookState.SEND.getCode());
        epTaskBook.setModifyDate(date);
        epTaskBook.setSendDate(date);
        return getUpdateResultState(epTaskBookService.updateTaskBookState(epTaskBook));
    }

    @ApiOperation("任务书历史记录")
    @ApiImplicitParam(name = "adviserStudentId", value = "adviserStudentId")
    @GetMapping("/findHistory")
    public Result findHistory(Long adviserStudentId){
        if (adviserStudentId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"分配校内指导老师id不能为空，请检查!");
        }
        return Result.ok(epTaskBookService.findHistory(adviserStudentId));
    }

}
