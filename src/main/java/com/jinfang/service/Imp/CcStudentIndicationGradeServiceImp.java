package com.jinfang.service.Imp;

import com.jinfang.entity.CcScoreStuIndigrade;
import com.jinfang.entity.CcStudentIndicationGrade;
import com.jinfang.entity.EpAdviserStudent;
import com.jinfang.entity.EpReplyTeacher;
import com.jinfang.entityEnum.ReplyTeacherType;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.mapper.CcStudentIndicationGradeMapper;
import com.jinfang.mapper.EpAdviserStudentMapper;
import com.jinfang.mapper.EpReplyTeacherMapper;
import com.jinfang.page.MybatisPageHelper;
import com.jinfang.page.PageResult;
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
    @Autowired(required = false)
    private EpAdviserStudentMapper epAdviserStudentMapper;
    @Autowired(required = false)
    private EpReplyTeacherMapper epReplyTeacherMapper;
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

    @Override
    public Result findStudentList(EpAdviserStudent epAdviserStudent) {
        Long teacherId = epAdviserStudent.getTeacherId();
        Integer grade = epAdviserStudent.getGrade();
        Long majorId = epAdviserStudent.getMajorId();
        String studentName = epAdviserStudent.getStudentName();
       /*
        1.先查询当前登录的人是否是答辩组长
        2.查询跟这个答辩组长同一组同一届的所有老师id
        */
        //查询这个登陆角色是否是答辩组长 取出组名
        ArrayList<Long> teacherIds = new ArrayList<>();
        List<EpReplyTeacher> teacher = epReplyTeacherMapper.findByTeacher(grade, teacherId, ReplyTeacherType.HEADMAN.getCode());
        for(EpReplyTeacher temp : teacher){
            //届别
            Integer teacherGrade = temp.getGrade();
            String groupName = temp.getGroupName();
            EpReplyTeacher epReplyTeacher = new EpReplyTeacher();
            epReplyTeacher.setGrade(teacherGrade);
            epReplyTeacher.setMajorId(majorId);
            epReplyTeacher.setGroupName(groupName);
            //查询同一届别，同一组的答辩教师
            List<EpReplyTeacher> teachers = epReplyTeacherMapper.findPage(epReplyTeacher);
            for (EpReplyTeacher et : teachers){
                teacherIds.add(et.getTeacherId());
            }
        }
        //答辩组所有学生列表
        List<EpAdviserStudent> epAdviserStudentList = epAdviserStudentMapper.findStudentInfo(majorId, studentName, grade, teacherIds,null);
        //再验证是否已经打分，
        /*
        * 1.先看当前登陆人的角色有哪些成绩组成打分的权限
        * 2.再根据成绩组成和学生ID判断cc_score_stu_indigrade是否存着成绩
        * */
        ArrayList<CcStudentIndicationGrade> ccStudentIndicationGrades = new ArrayList<>();
        //成绩组成
        List<CcStudentIndicationGrade> gradecomposeList = findGradecomposeList(majorId, epAdviserStudent.getRoleName(), grade);
        for (CcStudentIndicationGrade temp : gradecomposeList){
            Long courseGradecomposeId = temp.getCourseGradecomposeId();
            //成绩组成的所有学生成绩
            List<CcStudentIndicationGrade> gradeIndicationScore = findGradeIndication(courseGradecomposeId, null);
            for (CcStudentIndicationGrade studentScore:gradeIndicationScore){
                ccStudentIndicationGrades.add(studentScore);
            }
        }
        for (EpAdviserStudent temp : epAdviserStudentList){
            Long studentId = temp.getStudentId();
            //都默认未打分
            temp.setState(0);
            for (CcStudentIndicationGrade studentIndicationGrade : ccStudentIndicationGrades){
                Long gradeStudentId = studentIndicationGrade.getStudentId();

                if (studentId.equals(gradeStudentId) && temp.getState()==0){
                    //存着就设置已打分
                    temp.setState(1);
                }
            }
        }

        return Result.ok(epAdviserStudentList);
    }
}
