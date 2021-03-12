package com.jinfang.service.Imp;

import com.jinfang.entity.EpPracticeCheck;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpPracticeCheckMapper;
import com.jinfang.service.EpPracticeCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description：TODO
 * @Author：GJM
 * @Date：2021/3/1 3:37 下午
 */
@Service
public class EpPracticeCheckServiceImp implements EpPracticeCheckService {
    @Autowired
    private EpPracticeCheckMapper epPracticeCheckMapper;

    @Override
    public int save(EpPracticeCheck record) {
        return epPracticeCheckMapper.save(record);
    }

    @Override
    public int delete(EpPracticeCheck record) {
        return 0;
    }

    @Override
    public int delete(List<EpPracticeCheck> records) {
        return 0;
    }

    @Override
    public EpPracticeCheck findById(Long id) {
        return epPracticeCheckMapper.findById(id);
    }

    @Override
    public int update(EpPracticeCheck record) {
        return epPracticeCheckMapper.update(record);
    }

    @Override
    public Result findPage(EpPracticeCheck record) {
        return Result.ok(epPracticeCheckMapper.findPage(record));
    }
}
