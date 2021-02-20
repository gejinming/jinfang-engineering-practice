package com.jinfang.mapper;

import com.jinfang.entity.EpStudentScore;
import com.jinfang.httpdto.Result;

import java.util.Date;
import java.util.List;

/**
 * @Description：TODO
 * @Author：GJM
 * @Date：2021/2/18 12:08 下午
 */
public interface EpStudentScoreMapper {
    int save(List<EpStudentScore> list);

    int update(Long majorId, Integer grade, Date modifyDate);

    List<EpStudentScore> findStudentGradecompose(Integer grade,Long majorId);

    List<EpStudentScore>  findPage(EpStudentScore epStudentScore);
}
