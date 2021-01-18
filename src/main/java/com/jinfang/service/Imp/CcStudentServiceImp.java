package com.jinfang.service.Imp;

import com.jinfang.entity.CcStudent;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.CcStudentMapper;
import com.jinfang.service.CcStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 学生实现层
 * @author: Gjm
 * @create: 2021-01-12 16:43
 **/
@Service
public class CcStudentServiceImp implements CcStudentService {

    @Autowired(required = false)
    private CcStudentMapper ccStudentMapper;

    @Override
    public CcStudent findByStudentLogin(String studentNo, String password) {
        CcStudent student = ccStudentMapper.findByStudentLogin(studentNo, password);
        return student;
    }

    @Override
    public boolean findStudentById(Long id) {
        CcStudent studentById = ccStudentMapper.findByStudentById(id);
        if (studentById!=null){
            return true;
        }
        return false;
    }

    @Override
    public CcStudent findInfoById(Long id) {
        return ccStudentMapper.findInfoById(id);
    }

    @Override
    public int save(CcStudent record) {
        return 0;
    }

    @Override
    public int delete(CcStudent record) {
        return 0;
    }

    @Override
    public int delete(List<CcStudent> records) {
        return 0;
    }

    @Override
    public CcStudent findById(Long id) {
        return null;
    }

    @Override
    public int update(CcStudent record) {
        return 0;
    }

    @Override
    public Result findPage(CcStudent record) {
        return null;
    }

}
