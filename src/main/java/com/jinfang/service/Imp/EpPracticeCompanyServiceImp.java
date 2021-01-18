package com.jinfang.service.Imp;

import com.jinfang.entity.EpPracticeCompany;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpPracticeCompanyMapper;
import com.jinfang.page.MybatisPageHelper;
import com.jinfang.service.EpPracticeCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 实习单位实现类
 * @author: Gjm
 * @create: 2021-01-18 17:49
 **/
@Service
public class EpPracticeCompanyServiceImp implements EpPracticeCompanyService {
    @Autowired(required = false)
    private EpPracticeCompanyMapper epPracticeCompanyMapper;
    @Override
    public int save(EpPracticeCompany record) {
        return epPracticeCompanyMapper.save(record);
    }

    @Override
    public int delete(EpPracticeCompany record) {
        return 0;
    }

    @Override
    public int delete(List<EpPracticeCompany> records) {
        return 0;
    }

    @Override
    public EpPracticeCompany findById(Long id) {
        return null;
    }

    @Override
    public int update(EpPracticeCompany record) {
        return 0;
    }

    @Override
    public Result findPage(EpPracticeCompany record) {
        MybatisPageHelper.pageHelper(record.getPage(),record.getLimit());
        return Result.ok(MybatisPageHelper.getPageResult(epPracticeCompanyMapper.findPage(record)));
    }
}
