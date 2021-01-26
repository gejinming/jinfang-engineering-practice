package com.jinfang.mapper;

import com.jinfang.entity.EpOutAdviserStudent;
import com.jinfang.entity.EpPracticeCompany;
import com.jinfang.entity.EpStudentCompany;
import com.jinfang.httpdto.Result;

import java.util.List;

/**
 * @author ：Gjm
 * @date ：Created in 2021/1/18 17:46
 * @description：实习单位
 * @version: 1.0
 */
public interface EpPracticeCompanyMapper extends CurdMapper<EpPracticeCompany> {

    EpPracticeCompany findByName(Long majorId,String name);

    /*
    * 选择当前实习单位的学生列表
    * */
    List<EpStudentCompany> chooseCompanyStudentList(Long companyId);

    List<EpPracticeCompany> allotAdviserStudentList(Long majorId);

    /*
     * @Description:查询当前专业最近一个届别的未分配校外指导老师数
     * 和未选择实习单位的学生数。
     * isAllTo=0则是未分配校外指导老师学生数
     * isAllTo=1则是未选择实习单位的学生数
     * @Author: Gjm
     * @Date: 2021/1/25 16:26
     **/
    Integer allotStudentNum(Long majorId,Integer isAllTo);
    /*
     * @Description:查询学生已选择的实习单位
     * @Author: Gjm
     * @Date: 2021/1/26 15:53
     **/
    EpPracticeCompany chooseCompany(Long studentId);

    List<EpPracticeCompany> chooseCompanyFindPageList(EpPracticeCompany epPracticeCompany);


}
