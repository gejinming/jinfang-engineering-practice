package com.jinfang.service.Imp;

import com.jinfang.entity.EpOutAdviser;
import com.jinfang.entity.EpOutAdviserStudent;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpOutAdviserStudentMapper;
import com.jinfang.service.EpOutAdviserService;
import com.jinfang.service.EpOutAdviserStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:校外老师分配的学生
 * @author: Gjm
 * @create: 2021-01-21 15:32
 **/
@Service
public class EpOutAdviserStudentServiceImp implements EpOutAdviserStudentService {
    @Autowired(required = false)
    private EpOutAdviserStudentMapper epOutAdviserStudentMapper;

    @Override
    public int save(EpOutAdviserStudent record) {
        return epOutAdviserStudentMapper.save(record);
    }

    @Override
    public int delete(EpOutAdviserStudent record) {
        return 0;
    }

    @Override
    public int delete(List<EpOutAdviserStudent> records) {
        return 0;
    }

    @Override
    public EpOutAdviserStudent findById(Long id) {
        return null;
    }

    @Override
    public int update(EpOutAdviserStudent record) {
        return epOutAdviserStudentMapper.update(record);
    }

    @Override
    public Result findPage(EpOutAdviserStudent record) {
        return null;
    }

    @Override
    public List<EpOutAdviserStudent> findTeacherStudent(Long teacherId) {
        return epOutAdviserStudentMapper.findTeacherStudent(teacherId);
    }

    @Override
    public boolean isExistStudents(Long teacherId) {
        List<EpOutAdviserStudent> teacherStudent = findTeacherStudent(teacherId);
        if (teacherStudent.size()>0){
            return true;
        }
        return false;
    }
}
