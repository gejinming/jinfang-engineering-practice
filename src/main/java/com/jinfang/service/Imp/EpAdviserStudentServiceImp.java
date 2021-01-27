package com.jinfang.service.Imp;

import com.jinfang.entity.CcStudent;
import com.jinfang.entity.CheckReportList;
import com.jinfang.entity.EpAdviserStudent;
import com.jinfang.entity.EpWeekDayReport;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpAdviserStudentMapper;
import com.jinfang.mapper.EpWeekDayReportMapper;
import com.jinfang.page.MybatisPageHelper;
import com.jinfang.page.PageResult;
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
    @Autowired(required = false)
    private EpWeekDayReportMapper epWeekDayReportMapper;

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

    @Override
    public Result CheckReportPage(CheckReportList record) {
        MybatisPageHelper.pageHelper(record.getPage(),record.getLimit());
        //指导老师分配学生并且选择实习单位得列表
        List<CheckReportList> checkReportLists = epAdviserStudentMapper.CheckReportPage(record);
        //指导老师分配学生填写周报日报得列表
        List<EpWeekDayReport> studentReport = epWeekDayReportMapper.findTeacherStudentReport(record.getTeacherId(), record.getMajorId());
        for (CheckReportList temp : checkReportLists){
            Long studentCompanyId = temp.getStudentCompanyId();
            //周报数
            Integer weekNums=0;
            //未检查得周报数
            Integer noCheckWeekNums=0;
            //日报数
            Integer dayNums=0;
            //未检查得日报数
            Integer noCheckDayNums=0;
            for (EpWeekDayReport ep : studentReport){
                if (ep.getStudentCompanyId().equals(studentCompanyId)){
                    //日报
                    if (ep.getReportType()==1){
                        dayNums=dayNums+1;
                        if (ep.getAssessDate()==null){
                            noCheckDayNums=noCheckDayNums+1;
                        }
                    }else {
                        weekNums=weekNums+1;
                        //检查日期为空就是没检查得
                        if (ep.getAssessDate()==null){
                            noCheckWeekNums=noCheckWeekNums+1;
                        }
                    }

                }
            }
            String dayReportNums=dayNums.toString();
            if (noCheckDayNums>0){
                dayReportNums=dayReportNums+"(有"+noCheckDayNums+"篇未检查)";
            }
            String weekReportNums=weekNums.toString();
            if (noCheckWeekNums>0){
                weekReportNums=weekReportNums+"(有"+noCheckWeekNums+"篇未检查)";
            }
            temp.setDayReportNums(dayReportNums);
            temp.setWeekReportNums(weekReportNums);
        }
        PageResult pageResult = MybatisPageHelper.getPageResult(checkReportLists);

        return Result.ok(pageResult);
    }
}
