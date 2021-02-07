package com.jinfang.controller.inAdviser;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.ResultStudentInfoEntity;
import com.jinfang.httpdto.Result;
import com.jinfang.service.CcStudentService;
import com.jinfang.vo.LoginUserVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description：过程材料下载学生列表
 * @Author：GJM
 * @Date：2021/2/5 4:57 下午
 */
@RestController
@RequestMapping("/studentFile")
@Api(tags = "过程材料下载学生列表")
public class StudentFileController extends BaseController {
    @Autowired
    private CcStudentService ccStudentService;
    @GetMapping("/findPage")
    public Result findPage(ResultStudentInfoEntity record){
        LoginUserVo userInfo = getUserInfo();
        if (userInfo==null){
            Result.error("用户信息没有获取到，请重新登陆！");
        }

        Long userId = userInfo.getUserId();
        Long majorId = userInfo.getMajorId();
        if (userId==null || majorId==null){
            Result.error("用户信息没有获取到，请重新登陆！");
        }
        record.setMajorId(userInfo.getMajorId());
        return ccStudentService.findMajorStudentlist(userId,record);
    }
}
