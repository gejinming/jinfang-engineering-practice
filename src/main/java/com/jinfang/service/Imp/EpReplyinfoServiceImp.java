package com.jinfang.service.Imp;

import com.jinfang.entity.CcStudent;
import com.jinfang.entity.EpAdviserStudent;
import com.jinfang.entity.EpReplyTeacher;
import com.jinfang.entity.EpReplyinfo;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpAdviserStudentMapper;
import com.jinfang.mapper.EpReplyGroupStudentMapper;
import com.jinfang.mapper.EpReplyTeacherMapper;
import com.jinfang.mapper.EpReplyinfoMapper;
import com.jinfang.page.MybatisPageHelper;
import com.jinfang.page.PageResult;
import com.jinfang.service.EpAdviserStudentService;
import com.jinfang.service.EpReplyinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 答辩地点时间
 * @author: Gjm
 * @create: 2021-01-20 11:33
 **/
@Service
public class EpReplyinfoServiceImp implements EpReplyinfoService {

    @Autowired(required = false)
    private EpReplyinfoMapper epReplyinfoMapper;
    @Autowired(required = false)
    private EpReplyTeacherMapper epReplyTeacherMapper;
    @Autowired(required = false)
    private EpReplyGroupStudentMapper epReplyGroupStudentMapper;
    @Override
    public int save(EpReplyinfo record) {
        return epReplyinfoMapper.save(record);
    }

    @Override
    public int delete(EpReplyinfo record) {
        return 0;
    }

    @Override
    public int delete(List<EpReplyinfo> records) {
        return 0;
    }

    @Override
    public EpReplyinfo findById(Long id) {
        return epReplyinfoMapper.findById(id);
    }

    @Override
    public int update(EpReplyinfo record) {
        return epReplyinfoMapper.update(record);
    }

    @Override
    public Result findPage(EpReplyinfo record) {
        MybatisPageHelper.pageHelper(record.getPage(),record.getLimit());
        return Result.ok(MybatisPageHelper.getPageResult(epReplyinfoMapper.findPage(record)));
    }

    @Override
    public Result findReplyInfo(Long majorId,Integer page, Integer limit) {
        //已经设置的最大届别的答辩信息
        MybatisPageHelper.pageHelper(page,limit);
        List<EpReplyinfo> replyInfo = epReplyinfoMapper.findReplyInfo(majorId);
        PageResult pageResult = MybatisPageHelper.getPageResult(replyInfo);
        Integer grade = replyInfo.get(0).getGrade();
        EpReplyTeacher epReplyTeacher = new EpReplyTeacher();
        epReplyTeacher.setMajorId(majorId);
        epReplyTeacher.setGrade(grade);
        //届别答辩教师
        List<EpReplyTeacher> TeacherList = epReplyTeacherMapper.findPage(epReplyTeacher);
        ArrayList<String> groupList = new ArrayList<>();
        for (EpReplyinfo temp : replyInfo){
            String groupName = temp.getGroupName();
            String teacherString="";
            String studentString="";
            for (EpReplyTeacher teacher :TeacherList){
                if (groupName.equals(teacher.getGroupName())){
                    teacherString=teacherString+teacher.getTeacherName()+"、";
                    //组合当前组的学生
                    if (!groupList.contains(groupName)){
                        List<CcStudent> allocatStudentList = epReplyGroupStudentMapper.findAllocatStudentList(majorId, grade, groupName);
                        for (CcStudent student : allocatStudentList){
                            studentString=studentString+student.getName()+"、";
                        }
                        groupList.add(groupName);
                    }

                }

            }
            temp.setTeacher(teacherString);
            temp.setStudent(studentString);
        }
        return Result.ok(pageResult);
    }
}
