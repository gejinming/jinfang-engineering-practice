package com.jinfang.service.Imp;

import com.jinfang.entity.CcScoreStuIndigrade;
import com.jinfang.entity.CcStudentIndicationGrade;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.mapper.CcStudentIndicationGradeMapper;
import com.jinfang.service.CcStudentIndicationGradeService;
import com.jinfang.service.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 教师打分并录入到专业系统中去
 * @author: Gjm
 * @create: 2021-01-28 14:46
 **/
@Service
public class CcStudentIndicationGradeServiceImp implements CcStudentIndicationGradeService {
    @Autowired(required = false)
    private CcStudentIndicationGradeMapper csmapper;
    @Autowired(required = false)
    private IdGenerator idGenerator;
    @Override
    public List<CcStudentIndicationGrade> findGradecomposeList(Long majorId, String roleName,Integer grade) {
        return csmapper.findGradecomposeList(majorId,roleName,grade);
    }

    @Override
    public List<CcStudentIndicationGrade> findGradeIndication(Long courseGradecomposeId, Long studentId) {
        return csmapper.findGradeIndication(courseGradecomposeId,studentId);
    }

    @Override
    public Result save(List<CcScoreStuIndigrade> list) {
        Date date = new Date();
        List<CcScoreStuIndigrade> updateList = new ArrayList<>();
        List<CcScoreStuIndigrade> addList = new ArrayList<>();
        for (CcScoreStuIndigrade temp : list){
            Long gradecomposeIndicationId = temp.getGradecomposeIndicationId();
            BigDecimal grade = temp.getGrade();
            Long studentId = temp.getStudentId();
            if (temp.getId()!=null && grade!=null){
                temp.setModifyDate(date);
                updateList.add(temp);
            }else {
                if (gradecomposeIndicationId==null){
                    return Result.error(ResultEnum.PARAM_ERROR.getCode(),"gradecomposeIndicationId未获取到，请检查!");
                }
                if (grade==null){
                    return Result.error(ResultEnum.PARAM_ERROR.getCode(),"gradecomposeIndicationId未获取到，请检查!");
                }
                if (studentId==null){
                    return Result.error(ResultEnum.PARAM_ERROR.getCode(),"studentId未获取到，请检查!");
                }
                temp.setId(idGenerator.getNextValue());
                temp.setCreateDate(date);
                temp.setModifyDate(date);
                addList.add(temp);
            }


        }
        int save=0;
        if (updateList.size()>0){
            save=csmapper.update(updateList);
        }
        if (addList.size()>0){
            save = csmapper.save(addList);
        }
        if (save>0){
            return Result.ok("保存成功");
        }
        return Result.error("保存失败，请重试！");
    }
}
