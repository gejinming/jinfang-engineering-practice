package com.jinfang.service.Imp;

import com.jinfang.entity.CcStudent;
import com.jinfang.entity.EpStudentCompany;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpStudentCompanyMapper;
import com.jinfang.service.EpStudentCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:学生选择实习单位
 * @author: Gjm
 * @create: 2021-01-22 17:19
 **/
@Service
public class EpStudentCompanyServiceImp implements EpStudentCompanyService {

    @Autowired(required = false)
    private EpStudentCompanyMapper epStudentCompanyMapper;

    @Override
    public int save(EpStudentCompany record) {
        return 0;
    }

    @Override
    public int delete(EpStudentCompany record) {
        return 0;
    }

    @Override
    public int delete(List<EpStudentCompany> records) {
        return 0;
    }

    @Override
    public EpStudentCompany findById(Long id) {
        return null;
    }

    @Override
    public int update(EpStudentCompany record) {
        return 0;
    }

    @Override
    public Result findPage(EpStudentCompany record) {
        return null;
    }

    @Override
    public List<CcStudent> findCompanyStudentList(Long companyId, Integer grade,Integer isAllot) {
        return epStudentCompanyMapper.findCompanyStudentList(companyId,grade,isAllot);
    }
}
