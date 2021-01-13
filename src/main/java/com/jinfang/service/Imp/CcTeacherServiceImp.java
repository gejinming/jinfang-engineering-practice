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
    public Result findPage(CcTeacher record) {
        if (record !=null && record.getPage() !=null ){
            PageHelper.startPage(record.getPage(), record.getLimit());
        }
        List<CcTeacher> list = ccTeacherMapper.findPage();
        PageInfo<CcTeacher> pageInfo = new PageInfo<>(list);
        return Result.ok( MybatisPageHelper.getPageResult(pageInfo));
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
}
