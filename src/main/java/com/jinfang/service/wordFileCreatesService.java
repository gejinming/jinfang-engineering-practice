package com.jinfang.service;

import ch.qos.logback.core.util.FileUtil;
import com.jinfang.entity.*;
import com.jinfang.mapper.*;
import com.jinfang.util.DateUtil;
import com.jinfang.util.FileDirectoryUtil;
import com.jinfang.util.FileDownloadUtil;
import com.jinfang.util.WordUtil;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.create.table.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @Description：TODOword文件生成类
 * @Author：GJM
 * @Date：2021/2/7 2:33 下午
 */
@Service
@Slf4j
public class wordFileCreatesService {


    @Autowired
    private EpTaskBookMapper epTaskBookMapper;
    @Autowired
    private CcStudentMapper ccStudentMapper;
    @Autowired
    private EpTaskBookPlanMapper epTaskBookPlanMapper;
    @Autowired
    private EpPracticeReportMapper epPracticeReportMapper;
    @Autowired
    private EpSchoolOutAccessMapper epSchoolOutAccessMapper;
    @Autowired
    private EpPracticeCheckMapper epPracticeCheckMapper;
    /**
     * 任务书生成模版
     */
    private final String taskbookTemplate = "task_book.ftl";
    /**
     * 校外评定表生成模版
     */
    private final String assessTemplate = "out_adviser_assess.ftl";
    /**
     * 实习检查表生成模版
     */
    private final String practiceCheckTemplate = "practice_check.ftl";

    /**
     * 生成word文件路径
     */
    @Value("${template.target}")
    private String wordTarget;

    /**
     * 打包生成zip
     *
     * @param studentId:
     * @param grade:
     * @param response:
     * @Author: Gjm
     * @Date: 2021/3/2 2:53 下午
     * @return: void
     **/
    public void downFileZip(Long studentId, Integer grade, HttpServletResponse response) {
        //组装数据
        CcStudent studentInfo = ccStudentMapper.findInfoById(studentId);
        if (studentInfo == null) {
            log.error("生成word时没有找到学生信息");
            return;
        }
        //创建日期文件夹
        FileDirectoryUtil.DirMeta dirMeta = FileDirectoryUtil.createDir(wordTarget);
        String path = dirMeta.getPath();
        ArrayList<Map<String, String>> filePaths = new ArrayList<>();
        //实习报告文件路径
        List<EpPracticeReport> studentReport = epPracticeReportMapper.findStudentReport(studentId, 0);
        //应该只有一个才对,取文件路径打入压缩包中
        if (!studentReport.isEmpty()) {
            String reportPath = studentReport.get(0).getPath();
            if (reportPath == null){
                log.warn("【生成压缩包信息】==>"+studentInfo.getClassName() + "-" + studentInfo.getName()+"没有实习报告的文件");
            }else{
                HashMap<String, String> reportpathMap = new HashMap<>();
                reportpathMap.put("fileName", "实习报告.pdf");
                reportpathMap.put("filePath", reportPath);
                filePaths.add(reportpathMap);
            }

        }

        //生成任务书word
        Map<String, String> taskBook = createTaskBook(studentId, grade, studentInfo, path);
        if (taskBook != null) {
            filePaths.add(taskBook);
        }
        //生成校外评定word
        Map<String, String> outSchoolAssess = createOutSchoolAssess(studentId, grade, studentInfo, path);
        if (taskBook != null) {
            filePaths.add(outSchoolAssess);
        }
        //生成实习检查表
        List<Map<String, String>> practiceCheckTablePaths = createPracticeCheckTable(studentId, grade, studentInfo, path);
        if (practiceCheckTablePaths != null && !practiceCheckTablePaths.isEmpty()) {
            for (Map<String, String> map : practiceCheckTablePaths) {
                filePaths.add(map);
            }
        }
        //压缩后的文件名
        String zipFileFullPath = studentInfo.getClassName() + "-" + studentInfo.getName() + ".zip";
        //要压缩的文件夹路径
        String zipFullPath = path + File.separator + zipFileFullPath;
        if (filePaths.isEmpty()){
            log.warn("【生成压缩包错误】==>"+studentInfo.getClassName() + "-" + studentInfo.getName()+"没有要生成的文件");
            return;
        }
        WordUtil.createZip(zipFullPath, filePaths);
        //String path=wordTarget+zipFileFullPath;
        try {
            FileDownloadUtil.downLoadfile(zipFullPath, zipFileFullPath, response);
        } catch (UnsupportedEncodingException e) {
            log.error("【下载压缩包错误】==>"+e.getMessage());
            e.printStackTrace();
        }
        //删除生成的文件夹
        FileDirectoryUtil.deleteDirectory(path);
    }

    /*
     * @Description:生成学生任务书
     * @Author: Gjm
     * @Date: 2021/2/9 11:24 上午
     * @param studentId: 学生ID
     * @param grade: 届别
     * @param studentInfo:学生信息
     * @param path: 生成文档存放路径
     * @return: java.lang.String 生成文件全路径
     **/
    public Map<String, String> createTaskBook(Long studentId, Integer grade, CcStudent studentInfo, String path) {
        EpTaskBook studentTaskBooks = epTaskBookMapper.findStudentTaskBooks(grade, studentId);
        if (studentTaskBooks == null) {
            log.warn("【service:生成实习任务书word文件】:==> :" + studentInfo.getName() +"不存在任务书");
            return null;
        }
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("studentName", studentInfo.getName());
        dataMap.put("collegeName", studentInfo.getInstituteName());
        dataMap.put("className", studentInfo.getClassName());
        dataMap.put("studentNo", studentInfo.getStudentNo());
        dataMap.put("practiceName", studentTaskBooks.getPracticeName());
        dataMap.put("content", studentTaskBooks.getContent());
        dataMap.put("definiceRequire", studentTaskBooks.getDefiniceRequire());
        dataMap.put("adviserName", studentTaskBooks.getAdviserName());
        dataMap.put("year", DateUtil.getYear(new Date()));
        dataMap.put("month", DateUtil.getMonth(new Date()));
        dataMap.put("day", DateUtil.getDay(new Date()));
        if (studentTaskBooks.getId() != null) {
            List<EpTaskBookPlan> taskBookPlan = epTaskBookPlanMapper.findTaskBookPlan(studentTaskBooks.getId());
            ArrayList<Object> planList = new ArrayList<>();
            for (int i = 0; i < taskBookPlan.size(); i++) {
                EpTaskBookPlan epTaskBookPlan = taskBookPlan.get(i);
                HashMap<Object, Object> plan = new HashMap<>();
                plan.put("startDate", DateUtil.dateToString(epTaskBookPlan.getStartDate(), "yyyy-MM-dd"));
                plan.put("endDate", DateUtil.dateToString(epTaskBookPlan.getEndDate(), "yyyy-MM-dd"));
                plan.put("num", i + 1);
                plan.put("content", epTaskBookPlan.getContent());
                planList.add(plan);
            }
            dataMap.put("planList", planList);
        }
        String fileName = "实习任务书.doc";
        log.info("【service:开始生成实习任务书word文件】:==> :" + studentInfo.getName() + "-" + fileName);
        try {
            WordUtil.createWord(dataMap, taskbookTemplate, path, fileName);
        } catch (Exception e) {
            log.error("【service:生成word文件失败】:==>" + studentInfo.getName() + "-" + fileName+e.getMessage());
            e.printStackTrace();
            return null;
        }
        log.info("【service:生成word文件】:==> :" + studentInfo.getName() + "-" + fileName + "成功。");
        HashMap<String, String> taskBookPathMap = new HashMap<>();
        taskBookPathMap.put("fileName", fileName);
        taskBookPathMap.put("filePath", path + File.separator + fileName);
        return taskBookPathMap;
    }

    /*
     * @Description:生成学生校外评定表
     * @Author: Gjm
     * @Date: 2021/2/9 11:24 上午
     * @param studentId: 学生ID
     * @param grade: 届别
     * @param studentInfo:学生信息
     * @param path: 生成文档存放路径
     * @return: java.lang.String 生成文件全路径
     **/
    public Map<String, String> createOutSchoolAssess(Long studentId, Integer grade, CcStudent studentInfo, String path) {
        //校外评定表信息
        EpSchoolOutAccess studentAssess = epSchoolOutAccessMapper.findStudentAssess(studentId, grade);
        if (studentAssess == null) {
            log.warn("【service:生成word文件失败】:==>" + studentInfo.getName() + "不存在校外评定表");
            return null;
        }
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("studentName", studentInfo.getName());
        dataMap.put("studentNo", studentInfo.getStudentNo());
        dataMap.put("majorName", studentInfo.getMajorName());
        dataMap.put("className", studentInfo.getClassName());
        dataMap.put("companyName", studentAssess.getCompanyName());
        dataMap.put("address", studentAssess.getAddress());
        dataMap.put("outAdviserName", studentAssess.getOutAdviserName());
        dataMap.put("job", studentAssess.getJob());
        dataMap.put("practiceCase", studentAssess.getPracticeCase());
        dataMap.put("year", DateUtil.getYear(new Date()));
        dataMap.put("month", DateUtil.getMonth(new Date()));
        dataMap.put("day", DateUtil.getDay(new Date()));
        String fileName = "校外评定表.doc";
        log.info("【service:开始生成校外评定表word文件】:==> :" + studentInfo.getName() + "-" + fileName);
        try {
            WordUtil.createWord(dataMap, assessTemplate, path, fileName);
        } catch (Exception e) {
            log.error("【service:生成word文件失败】:==>" + studentInfo.getName() + "-" + fileName+e.getMessage());
            e.printStackTrace();
            return null;
        }
        log.info("【service:生成word文件】:==> :" + studentInfo.getName() + "-" + fileName + "成功。");
        Map<String, String> map = new HashMap<>();
        map.put("fileName", fileName);
        map.put("filePath", path + File.separator + fileName);
        return map;
    }

    /**
     * @param studentId:
     * @param grade:
     * @param studentInfo:
     * @param path:
     * @Description:生成实习检查表
     * @Author: Gjm
     * @Date: 2021/3/1 5:07 下午
     * @return: java.util.Map<java.lang.String, java.lang.String>
     **/
    public List<Map<String, String>> createPracticeCheckTable(Long studentId, Integer grade, CcStudent studentInfo, String path) {
        ArrayList<Map<String, String>> result = new ArrayList<>();
        EpTaskBook studentTaskBooks = epTaskBookMapper.findStudentTaskBooks(grade, studentId);
        if (studentTaskBooks == null) {
            log.warn("【生成实习检查表】：==> : " + studentInfo.getName() + "不存在任务书。");
            return null;
        }
        Long adviserStudentId = studentTaskBooks.getAdviserStudentId();
        EpPracticeCheck epPracticeCheck = new EpPracticeCheck();
        epPracticeCheck.setAdviserStudentId(adviserStudentId);
        //实习检查表列表
        List<EpPracticeCheck> practiceChecks = epPracticeCheckMapper.findPage(epPracticeCheck);
        if (practiceChecks.isEmpty()) {
            log.warn("【生成实习检查表】：==> : " + studentInfo.getName() + "不存在实习检查表。");
            return null;
        }
        //循环生成每周的实习检查表
        for (EpPracticeCheck temp : practiceChecks) {
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("studentName", studentInfo.getName());
            dataMap.put("studentNo", studentInfo.getStudentNo());
            dataMap.put("majorName", studentInfo.getMajorName());
            dataMap.put("instituteName", studentInfo.getInstituteName());
            dataMap.put("className", studentInfo.getClassName());
            dataMap.put("practiceName", studentTaskBooks.getPracticeName());
            dataMap.put("adviserName", studentTaskBooks.getAdviserName());
            dataMap.put("weekNum", temp.getWeekNum());
            dataMap.put("startYear", DateUtil.getYear(temp.getStartDate()));
            dataMap.put("startMonth", DateUtil.getMonth(temp.getStartDate()));
            dataMap.put("startDay", DateUtil.getDay(temp.getStartDate()));
            dataMap.put("endYear", DateUtil.getYear(temp.getEndDate()));
            dataMap.put("endMonth", DateUtil.getMonth(temp.getEndDate()));
            dataMap.put("endDay", DateUtil.getDay(temp.getEndDate()));
            dataMap.put("requireContent", temp.getRequireContent());
            dataMap.put("practiceSchedule", temp.getPracticeSchedule());
            dataMap.put("matterVaction", temp.getMatterVaction());
            dataMap.put("sickVaction", temp.getSickVaction());
            dataMap.put("truant", temp.getTruant());
            dataMap.put("year", DateUtil.getYear(temp.getStartDate()));
            dataMap.put("month", DateUtil.getMonth(temp.getStartDate()));
            dataMap.put("day", DateUtil.getDay(temp.getStartDate()));
            String fileName = "第" + temp.getWeekNum() + "周实习检查表.doc";
            log.info("【service:开始生成实习检查表word文件】:==> :" + studentInfo.getName() + "-" + fileName);
            try {
                WordUtil.createWord(dataMap, practiceCheckTemplate, path, fileName);
            } catch (Exception e) {
                log.info("【service:生成word文件失败】:==>" + studentInfo.getName() + "-" + fileName +e.getMessage());
                e.printStackTrace();
                break;
            }
            log.info("【service:生成word文件】:==> :" + studentInfo.getName() + "-" + fileName + "成功。");
            Map<String, String> map = new HashMap<>();
            map.put("fileName", fileName);
            map.put("filePath", path + File.separator + fileName);
            result.add(map);
        }

        return result;
    }

}
