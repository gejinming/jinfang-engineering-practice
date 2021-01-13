package com.jinfang.mapper;

import com.jinfang.entity.CcStudent;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
* cc_student
* @author GJM
* @date 2021/01/12
*/

public interface CcStudentMapper {



    /**
    * [查询] 根据学号、密码 登录
    * @author GJM
    * @date 2021/01/12
    **/
    CcStudent findByStudentLogin(String studentNo,String password);
    /*
     * @param id
     * @return com.jinfang.entity.CcStudent
     * @description: 根据学生id查询
     * @date 2021/1/13 10:52
     */
    CcStudent findByStudentById(Long id);
    /*
     * @param id
     * @return com.jinfang.entity.CcStudent
     * @description: 通过id查找学生的具体信息，包含专业、班级、学校等信息
     * @date 2021/1/13 16:05
     */
    CcStudent findInfoById(Long id);

}
