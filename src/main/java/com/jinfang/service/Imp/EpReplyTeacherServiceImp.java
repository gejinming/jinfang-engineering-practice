package com.jinfang.service.Imp;

import com.jinfang.entity.EpReplyTeacher;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpReplyTeacherMapper;
import com.jinfang.service.EpReplyTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 答辩组教师表实现类
 * @author: Gjm
 * @create: 2021-01-12 17:37
 **/
@Service
public class EpReplyTeacherServiceImp implements EpReplyTeacherService {

    @Autowired(required = false)
    private EpReplyTeacherMapper epReplyTeacherMapper;

    @Override
    public List<EpReplyTeacher> findByTeacher(Integer grade, Long teacherId,Integer roleType) {

        return epReplyTeacherMapper.findByTeacher(grade,teacherId,roleType);
    }

    public boolean isGroupLeader(Integer grade, Long teacherId,Integer roleType){
        return epReplyTeacherMapper.findByTeacher(grade,teacherId,roleType).size()>0;
    }
    @Override
    public int save(EpReplyTeacher record) {
        return 0;
    }

    @Override
    public int delete(EpReplyTeacher record) {
        return 0;
    }

    @Override
    public int delete(List<EpReplyTeacher> records) {
        return 0;
    }

    @Override
    public EpReplyTeacher findById(Long id) {
        return null;
    }

    @Override
    public Result findPage(EpReplyTeacher record) {
        return null;
    }
}
