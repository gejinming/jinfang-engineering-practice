package com.jinfang.service.Imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinfang.entity.CcTeacher;
import com.jinfang.entityEnum.SystemRole;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.CcTeacherMapper;
import com.jinfang.page.MybatisPageHelper;
import com.jinfang.service.CcteacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 教师接口实现类
 * @author: Gjm
 * @create: 2021-01-11 15:53
 **/
@Service
public class CcTeacherServiceImp implements CcteacherService {

    @Autowired(required = false)
    private CcTeacherMapper ccTeacherMapper;

    @Override
    public int save(CcTeacher record) {
        return 0;
    }

    @Override
    public int delete(CcTeacher record) {
        return 0;
    }

    @Override
    public int delete(List<CcTeacher> records) {
        return 0;
    }

    @Override
    public CcTeacher findById(Long id) {
        return ccTeacherMapper.findById(id);
    }

    @Override
    public int update(CcTeacher record) {
        return 0;
    }

    @Override
    public Result findPage(CcTeacher record) {
       /* List<CcTeacher> list = ccTeacherMapper.findPage();*/
        return Result.ok();
    }

    /*
     * @param teacherId
     * @return boolean
     * @description: 判断是否为专业负责人
     * @date 2021/1/12 14:45
     */
    public boolean isSchoolLeader(Long teacherId) {
        int count = ccTeacherMapper.selectCountByRoleName(teacherId, SystemRole.LEADER.getName());
        return count > 0;
    }

    @Override
    public List<CcTeacher> findReplyTeacherList(Long schoolId,Long majorId,String teacherName,
                                                String majorName,Integer grade) {
        return ccTeacherMapper.findReplyTeacherList(schoolId,majorId,teacherName,majorName,grade);
    }
}
