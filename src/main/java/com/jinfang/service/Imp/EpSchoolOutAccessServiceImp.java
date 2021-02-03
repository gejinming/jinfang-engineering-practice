package com.jinfang.service.Imp;

import com.jinfang.entity.EpSchoolOutAccess;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpSchoolOutAccessMapper;
import com.jinfang.page.MybatisPageHelper;
import com.jinfang.service.EpSchoolOutAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.util.List;

/**
 * @description: 校外评定表处理类
 * @author: GJM
 * @date: 2021-02-02 10:44
 **/
@Service
public class EpSchoolOutAccessServiceImp implements EpSchoolOutAccessService {

    @Autowired
    private EpSchoolOutAccessMapper epSchoolOutAccessMapper;

    @Override
    public int save(EpSchoolOutAccess record) {
        return epSchoolOutAccessMapper.save(record);
    }

    @Override
    public int delete(EpSchoolOutAccess record) {
        return 0;
    }

    @Override
    public int delete(List<EpSchoolOutAccess> records) {
        return 0;
    }

    @Override
    public EpSchoolOutAccess findById(Long id) {
        return epSchoolOutAccessMapper.findById(id);
    }

    @Override
    public int update(EpSchoolOutAccess record) {
        return epSchoolOutAccessMapper.update(record);
    }

    @Override
    public Result findPage(EpSchoolOutAccess record) {
        MybatisPageHelper.pageHelper(record.getPage(),record.getLimit());
        List<EpSchoolOutAccess> page = epSchoolOutAccessMapper.findPage(record);
        return Result.ok(MybatisPageHelper.getPageResult(page));
    }
}
