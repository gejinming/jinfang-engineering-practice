package com.jinfang.service.Imp;

import com.jinfang.entity.EpReplyinfo;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpReplyinfoMapper;
import com.jinfang.page.MybatisPageHelper;
import com.jinfang.service.EpReplyinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 答辩地点时间
 * @author: Gjm
 * @create: 2021-01-20 11:33
 **/
@Service
public class EpReplyinfoServiceImp implements EpReplyinfoService {

    @Autowired(required = false)
    private EpReplyinfoMapper epReplyinfoMapper;

    @Override
    public int save(EpReplyinfo record) {
        return epReplyinfoMapper.save(record);
    }

    @Override
    public int delete(EpReplyinfo record) {
        return 0;
    }

    @Override
    public int delete(List<EpReplyinfo> records) {
        return 0;
    }

    @Override
    public EpReplyinfo findById(Long id) {
        return epReplyinfoMapper.findById(id);
    }

    @Override
    public int update(EpReplyinfo record) {
        return epReplyinfoMapper.update(record);
    }

    @Override
    public Result findPage(EpReplyinfo record) {
        MybatisPageHelper.pageHelper(record.getPage(),record.getLimit());
        return Result.ok(MybatisPageHelper.getPageResult(epReplyinfoMapper.findPage(record)));
    }
}
