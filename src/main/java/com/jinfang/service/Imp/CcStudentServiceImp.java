package com.jinfang.service.Imp;

import com.jinfang.entity.*;
import com.jinfang.entityEnum.ReplyTeacherType;
import com.jinfang.entityEnum.SystemRole;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.CcStudentMapper;
import com.jinfang.mapper.EpAdviserStudentMapper;
import com.jinfang.mapper.EpReplyTeacherMapper;
import com.jinfang.page.MybatisPageHelper;
import com.jinfang.page.PageResult;
import com.jinfang.service.CcStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 学生实现层
 * @author: Gjm
 * @create: 2021-01-12 16:43
 **/
@Service
public class CcStudentServiceImp implements CcStudentService {

    @Autowired
    private CcStudentMapper ccStudentMapper;
    @Autowired
    private EpAdviserStudentMapper epAdviserStudentMapper;
    @Autowired
    private EpReplyTeacherMapper epReplyTeacherMapper;

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
    public Result findMajorStudentlist(Long userId,ResultStudentInfoEntity record) {
        MybatisPageHelper.pageHelper(record.getPage(),record.getLimit());

        ArrayList<ResultStudentInfoEntity> resultStudentInfoEntities = new ArrayList<>();
        PageResult pageResult =null;
        ArrayList<Long> teacherIds = new ArrayList<>();
        //如果是专业负责人查询全部
        if (record.getRoleName().equals(SystemRole.LEADER)){
            List<ResultStudentInfoEntity> majorStudentlist = ccStudentMapper.findMajorStudentlist(record.getMajorId(), record.getGrade(), record.getStudentName(), record.getCompanyName());
            pageResult = MybatisPageHelper.getPageResult(majorStudentlist);
        }else if (record.getRoleName().equals(SystemRole.TEACHER)){
            //指导老师查询自己指导的学生
            teacherIds.add(userId);
            List<EpAdviserStudent> studentInfo = epAdviserStudentMapper.findStudentInfo(record.getMajorId(), record.getStudentName(), record.getGrade(), teacherIds, record.getCompanyName());
            pageResult = MybatisPageHelper.getPageResult(studentInfo);
        }else if(record.getRoleName().equals(SystemRole.HEADMAN)){
            List<EpReplyTeacher> teacher = epReplyTeacherMapper.findByTeacher(record.getGrade(), userId, ReplyTeacherType.HEADMAN.getCode());
            for(EpReplyTeacher temp : teacher){
                //届别
                Integer teacherGrade = temp.getGrade();
                String groupName = temp.getGroupName();
                EpReplyTeacher epReplyTeacher = new EpReplyTeacher();
                epReplyTeacher.setGrade(teacherGrade);
                epReplyTeacher.setMajorId(record.getMajorId());
                epReplyTeacher.setGroupName(groupName);
                //查询同一届别，同一组的答辩教师
                List<EpReplyTeacher> teachers = epReplyTeacherMapper.findPage(epReplyTeacher);
                for (EpReplyTeacher et : teachers){
                    teacherIds.add(et.getTeacherId());
                }
            }
            //答辩组长查询整个组的学生
            List<EpAdviserStudent> studentInfo = epAdviserStudentMapper.findStudentInfo(record.getMajorId(), record.getStudentName(), record.getGrade(), teacherIds, record.getCompanyName());
            pageResult = MybatisPageHelper.getPageResult(studentInfo);
        }
        return Result.ok(pageResult);
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
        return ccStudentMapper.findStudentInfo(id);
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
