package com.jinfang.controller;


import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.service.SystemService;
import com.jinfang.vo.LoginUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
public class BaseController {

    @Resource
    protected HttpServletRequest request;

    @Resource
    protected HttpServletResponse response;

    @Autowired
    private SystemService systemService;
    /*
     * @param
     * @return java.lang.Long
     * @description: 通过token取schoolId
     * @date 2021/1/13 15:10
     */
    protected LoginUserVo getUserInfo(){
        String headerToken=request.getHeader("token");
        LoginUserVo loginUserVo = systemService.getLoginUserVo(headerToken, false);
        return loginUserVo;
    }

    protected Result getSaveResultState(int save){
        if (save>0){
            return Result.ok();
        }
        return  Result.error(ResultEnum.SAVE_ERROR.getCode(),ResultEnum.SAVE_ERROR.getMessage());
    }
    protected Result getUpdateResultState(int update){
        if (update>=0){
            return Result.ok();
        }
        return  Result.error(ResultEnum.UPDATE_ERROR.getCode(),ResultEnum.UPDATE_ERROR.getMessage());
    }

}
