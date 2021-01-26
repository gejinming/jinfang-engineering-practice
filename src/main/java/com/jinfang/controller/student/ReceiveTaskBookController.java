package com.jinfang.controller.student;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.EpTaskBook;
import com.jinfang.entity.StudentTaskBook;
import com.jinfang.entityEnum.TaskBookState;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.service.EpTaskBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @description: 学生接收任务书
 * @author: Gjm
 * @create: 2021-01-26 11:24
 **/
@RestController
@RequestMapping("/studentReceive")
@Api(tags = "学生任务书接口")
public class ReceiveTaskBookController extends BaseController {
    @Autowired
    private EpTaskBookService epTaskBookService;

    @ApiOperation("学生任务书列表")
    @GetMapping("/studentTaskBook")
    public Result studentTaskBook(){
        Long userId = getUserInfo().getUserId();
        if (userId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"获取学生id失败，请检查!");
        }

        return  epTaskBookService.findStudentTaskBook(userId);
    }
    @ApiOperation("接收任务书")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id")
    })
    @PostMapping("/receiveTaskBook")
    public Result sendTaskBook(@RequestBody EpTaskBook epTaskBook){
        Date date = new Date();
        epTaskBook.setState(TaskBookState.RECEIVE.getCode());
        epTaskBook.setModifyDate(date);
        epTaskBook.setReceiveTime(date);
        return getUpdateResultState(epTaskBookService.updateTaskBookState(epTaskBook));
    }
}
