package com.jinfang.mapper;

import com.jinfang.entity.CcStudent;
import com.jinfang.entity.EpStudentCompany;

import java.util.List;

/**
 * @author ：Gjm
 * @date ：Created in 2021/1/22 17:17
 * @description：ep_student_company学生选择实习单位
 * @version: 1.0
 */
public interface EpStudentCompanyMapper extends CurdMapper<EpStudentCompany> {

    List<CcStudent> findCompanyStudentList(Long companyId, Integer grade,Integer isAllot);




}
