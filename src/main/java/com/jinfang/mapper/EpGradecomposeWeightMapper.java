package com.jinfang.mapper;

import com.jinfang.entity.EpGradecomposeWeight;

import java.util.List;

/**
 * @Description：TODO成绩组成权重mapper
 * @Author：GJM
 * @Date：2021/2/10 4:47 下午
 */
public interface EpGradecomposeWeightMapper extends CurdMapper<EpGradecomposeWeight>{

    int save(List<EpGradecomposeWeight> list);


}
