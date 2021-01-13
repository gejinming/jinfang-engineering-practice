package com.jinfang.controller;

import com.github.pagehelper.PageInfo;
import com.jinfang.dto.page.PageRequest;

import com.jinfang.service.SystemService;
import com.jinfang.vo.LoginUserMeta;
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


    /*protected void addSchoolId(PageRequest pageRequest, LoginUserMeta loginUserMeta) {
        if (loginUserMeta == null || loginUserMeta.getSchoolId() == null) {
            throw new IllegalArgumentException("SchoolId is empty");
        }

//        pageRequest.getColumnFilters().put("schoolId",
//                ColumnFilter.builder().name("schoolId").value(loginUserMeta.getSchoolId().toString()).build());

        pageRequest.getExtProps().put("schoolId", loginUserMeta.getSchoolId());
    }

    protected void addTeacherId(PageRequest pageRequest, LoginUserMeta loginUserMeta) {
        if (loginUserMeta == null || loginUserMeta.getTeacherId() == null) {
            throw new IllegalArgumentException("SchoolId is empty");
        }

//        pageRequest.getColumnFilters().put("teacher_id",
//                ColumnFilter.builder().name("teacher_id").value(loginUserMeta.getTeacherIdText()).build());

        pageRequest.getExtProps().put("teacherId", loginUserMeta.getTeacherId());
    }

    protected void addGrade(PageRequest pageRequest, LoginUserMeta loginUserMeta) {
        if (loginUserMeta == null) {
            throw new IllegalArgumentException("loginUserMeta is empty");
        }

        pageRequest.getExtProps().put("grade", loginUserMeta.getGrade());
    }

    protected void addCondition(PageRequest pageRequest, String name, Object value) {
        if (value == null) {
            throw new IllegalArgumentException(name + " is empty");
        }

//        pageRequest.getColumnFilters().put("teacher_id",
//                ColumnFilter.builder().name("teacher_id").value(loginUserMeta.getTeacherIdText()).build());

        pageRequest.getExtProps().put(name, value);
    }*/
}
