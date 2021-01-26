package com.jinfang.service;

import com.jinfang.entity.EpPracticeCompany;
import com.jinfang.httpdto.Result;


/**
 * @author ：Gjm
 * @date ：Created in 2021/1/18 17:48
 * @description：实习单位
 * @version: 1.0
 */
public interface EpPracticeCompanyService extends CurdService<EpPracticeCompany> {
    boolean findByName(Long majorId,String name,Long id);

    /*
     * @Description:当前实习单位是否存在学生选择
     * @Author: Gjm
     * @Date: 2021/1/21 15:12
     **/
    boolean isExistStudent(Long companyId);

    Result allotAdviserStudentList(EpPracticeCompany epPracticeCompany);

    Result chooseCompanyFindPageList(EpPracticeCompany epPracticeCompany,Long studentId);

}
