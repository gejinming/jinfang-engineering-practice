package com.jinfang.service;

import com.jinfang.entity.EpGradecomposeWeight;
import com.jinfang.entity.EpStudentScore;
import com.jinfang.httpdto.Result;

import java.util.List;

/**
 * @Description：TODO
 * @Author：GJM
 * @Date：2021/2/10 4:49 下午
 */
public interface EpGradecomposeWeightService extends CurdService<EpGradecomposeWeight>{
    Result save(List<EpGradecomposeWeight> list);

    Result studentScorePage(EpStudentScore epStudentScore);
}
