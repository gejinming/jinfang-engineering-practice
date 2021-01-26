package com.jinfang.service.Imp;

import com.jinfang.entity.CcStudent;
import com.jinfang.entity.EpAdviserStudent;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpAdviserStudentMapper;
import com.jinfang.page.MybatisPageHelper;
import com.jinfang.service.EpAdviserStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @description: ep_adviser_student校内教师分配的学生
 * @author: Gjm
 * @create: 2021-01-20 15:35
 **/
@Service
public class EpAdviserStudentServiceImp implements EpAdviserStudentService {
    @Autowired(required = false)
    private EpAdviserStudentMapper epAdviserStudentMapper;

    @Override
    public int save(EpAdviserStudent record) {
        return epAdviserStudentMapper.save(record);
    }

    @Override
    public int delete(EpAdviserStudent record) {
        return 0;
    }

    @Override
    public int delete(List<EpAdviserStudent> records) {
        return epAdviserStudentMapper.delete(records);
    }

    @Override
    public EpAdviserStudent findById(Long id) {
        return epAdviserStudentMapper.findById(id);
    }

    @Override
    public int update(EpAdviserStudent record) {
        return epAdviserStudentMapper.update(record);
    }

    @Override
    public Result findPage(EpAdviserStudent record) {
        MybatisPageHelper.pageHelper(record.getPage(),record.getLimit());
        HashMap<Object, Object> map = new HashMap<>();
        map.put("list",MybatisPageHelper.getPageResult(epAdviserStudentMapper.findPage(record)));
        //未分配的学生数
        Integer studentNum = epAdviserStudentMapper.unallocatedStudent(record.getMajorId());
        map.put("studentNum",studentNum);
        return Result.ok(map);
    }

    @Override
    public List<CcStudent> findAllocatStudentList(Integer grade, Long majorId, Long teacherId) {
        return epAdviserStudentMapper.findAllocatStudentList(grade,majorId,teacherId);
    }

    @Override
    public List<CcStudent> findUnAllocatStudentList(Integer grade, Long majorId) {

        return epAdviserStudentMapper.findUnAllocatStudentList(grade,majorId);
    }

    @Override
    public int save(List<EpAdviserStudent> list) {
        return epAdviserStudentMapper.save(list);
    }
}
