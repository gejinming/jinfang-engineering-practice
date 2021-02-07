package com.jinfang.service;

import com.jinfang.entity.CcStudent;
import com.jinfang.entity.ResultStudentInfoEntity;
import com.jinfang.httpdto.Result;

import java.util.List;

/**
 * @description:
 * @author: Gjm
 * @create: 2021-01-12 16:41
 **/

public interface CcStudentService extends CurdService<CcStudent>  {

    CcStudent findByStudentLogin(String studentNo,String password);

    boolean findStudentById(Long id);

    CcStudent findInfoById(Long id);

    /*
     * @Description:查询专业所有学生
     * @Author: Gjm
     * @Date: 2021/2/5 4:30 下午
     * @return: 学生信息
     **/
    Result findMajorStudentlist(Long userId,ResultStudentInfoEntity record);
}
