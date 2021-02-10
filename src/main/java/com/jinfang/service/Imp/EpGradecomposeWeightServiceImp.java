package com.jinfang.service.Imp;

import com.jinfang.entity.EpGradecomposeWeight;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpGradecomposeWeightMapper;
import com.jinfang.service.EpGradecomposeWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description：TODO
 * @Author：GJM
 * @Date：2021/2/10 4:50 下午
 */
@Service
public class EpGradecomposeWeightServiceImp implements EpGradecomposeWeightService {
    @Autowired
    private EpGradecomposeWeightMapper epGradecomposeWeightMapper;

    @Override
    public int save(EpGradecomposeWeight record) {
        return 0;
    }

    @Override
    public int delete(EpGradecomposeWeight record) {
        return 0;
    }

    @Override
    public int delete(List<EpGradecomposeWeight> records) {
        return 0;
    }

    @Override
    public EpGradecomposeWeight findById(Long id) {
        return null;
    }

    @Override
    public int update(EpGradecomposeWeight record) {
        return 0;
    }

    @Override
    public Result findPage(EpGradecomposeWeight record) {
        return Result.ok(epGradecomposeWeightMapper.findPage(record));
    }
}
