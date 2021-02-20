package com.jinfang.service.Imp;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jinfang.entity.EpGradecomposeWeight;
import com.jinfang.entity.EpStudentScore;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpGradecomposeWeightMapper;
import com.jinfang.mapper.EpStudentCompanyMapper;
import com.jinfang.mapper.EpStudentScoreMapper;
import com.jinfang.page.MybatisPageHelper;
import com.jinfang.service.EpGradecomposeWeightService;
import com.jinfang.util.PriceUtils;
import com.oracle.tools.packager.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Description：TODO
 * @Author：GJM
 * @Date：2021/2/10 4:50 下午
 */
@Service
@Slf4j
public class EpGradecomposeWeightServiceImp implements EpGradecomposeWeightService {
    @Autowired
    private EpGradecomposeWeightMapper epGradecomposeWeightMapper;
    @Autowired
    private EpStudentScoreMapper epStudentScoreMapper;
    @Autowired
    private EpStudentCompanyMapper epStudentCompanyMapper;

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
        return epGradecomposeWeightMapper.delete(records);
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

    @Override
    public Result save(List<EpGradecomposeWeight> list) {
        //删除旧数据
        int delete = delete(list);
        Integer grade=list.get(0).getGrade();
        Long majorId = list.get(0).getMajorId();
        int save = epGradecomposeWeightMapper.save(list);
        if (save>0){
           return saveStudentScore(grade,majorId);
        }
        return Result.error();
    }

    @Override
    public Result studentScorePage(EpStudentScore epStudentScore) {
        HashMap<Object, Object> result = Maps.newHashMap();
        //各成绩组成的成绩
        List<EpStudentScore> studentGradecomposeScore = epStudentScoreMapper.findStudentGradecompose(epStudentScore.getGrade(), epStudentScore.getMajorId());
        result.put("studentGradecomposeScore",studentGradecomposeScore);
        //最终成绩
        MybatisPageHelper.pageHelper(epStudentScore.getPage(),epStudentScore.getLimit());
        result.put("studentScore",MybatisPageHelper.getPageResult(epStudentScoreMapper.findPage(epStudentScore)));
        return Result.ok(result);
    }

    /*
     * @param grade: 届别
     * @Description:生成学生成绩
     * @Author: Gjm
     * @Date: 2021/2/19 2:41 下午
     * @return: int
     **/
    private Result saveStudentScore(Integer grade,Long majorId){
        /**
         * 加权算法：最终成绩=成绩组成1的分数*成绩组成1的权重+成绩组成2的分数*成绩组成2的权重+成绩组成3的分数*成绩组成3的权重+……
         * 其中每项成绩组成由一个或多个课程目标成绩组成，成绩组成的分数为课程目标成绩的合值。
         */
        //成绩组成综合成绩
        List<EpStudentScore> studentGradecomposeScore = epStudentScoreMapper.findStudentGradecompose(grade, majorId);
        if (studentGradecomposeScore.isEmpty()){
            log.info("学生各成绩组成的成绩还未录入");
            return Result.error("学生各成绩组成的成绩还未录入，请检查！");
        }
        //成绩组成权重
        EpGradecomposeWeight epGradecomposeWeight = new EpGradecomposeWeight();
        epGradecomposeWeight.setMajorId(majorId);
        epGradecomposeWeight.setGrade(grade);
        List<EpGradecomposeWeight> gradecomposeWeightList = epGradecomposeWeightMapper.findPage(epGradecomposeWeight);
        if (gradecomposeWeightList.isEmpty()){
            log.info("学生各成绩组成的权重还未设置");
            return Result.error("学生各成绩组成的权重还未设置，请检查！");
        }
        ArrayList<EpStudentScore> epStudentScores = new ArrayList<>();
        ArrayList<Long> studentIds = new ArrayList<>();
        //学生成绩累加最终成绩
        Map<Long, BigDecimal> idToAllScoreMap = Maps.newHashMap();
        for (EpStudentScore temp : studentGradecomposeScore){
            Long studentId = temp.getStudentId();
            BigDecimal gradecomposeScore = temp.getScore();
            Long courseGradecomposeId = temp.getCourseGradecomposeId();
            for (EpGradecomposeWeight ew : gradecomposeWeightList){
                if (ew.getCourseGradecomposeId().equals(courseGradecomposeId)){
                    BigDecimal weight = ew.getWeight();
                    //成绩组成1的分数*成绩组成1的权重
                    BigDecimal score = PriceUtils.mul(gradecomposeScore, weight, 2);
                    if (idToAllScoreMap.get(studentId)==null){
                        idToAllScoreMap.put(studentId, score);
                        studentIds.add(studentId);
                    }else {
                        idToAllScoreMap.put(studentId, PriceUtils._add(idToAllScoreMap.get(studentId),score));
                    }
                }

            }

        }
        for (Map.Entry<Long, BigDecimal> temp : idToAllScoreMap.entrySet()){
            Date date = new Date();
            EpStudentScore epStudentScore = new EpStudentScore();
            epStudentScore.setScore(temp.getValue());
            epStudentScore.setStudentId(temp.getKey());
            epStudentScore.setGrade(grade);
            epStudentScore.setCreateDate(date);
            epStudentScore.setModifyDate(date);
            epStudentScore.setMajorId(majorId);
            epStudentScore.setIsDel(0);
            epStudentScores.add(epStudentScore);
        }
        int save=0;
        if (!epStudentScores.isEmpty()){
            //删除旧数据
            epStudentScoreMapper.update(majorId, grade, new Date());
            save=epStudentScoreMapper.save(epStudentScores);
        }
        if (save>0){
            //生成成绩后学生实习结束
            epStudentCompanyMapper.updateEnd(grade,studentIds);
            log.info("最终成绩生成，学生实习结束");
            return Result.ok();
        }
        log.error("最终成绩生成失败");
        return Result.error("生成最终成绩失败，请重试！");
    }
}
