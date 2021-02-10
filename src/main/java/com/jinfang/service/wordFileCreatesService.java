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
    /**
     * 任务书生成模版
     */
    private final String taskbookTemplate = "task_book.ftl";
    /**
     * 校外评定表生成模版
     */
    private final String assessTemplate = "out_adviser_assess.ftl";

    /**
     * 生成word文件路径
     */
    @Value("${template.target}")
    private String wordTarget;

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
            HashMap<String, String> reportpathMap = new HashMap<>();
            reportpathMap.put("fileName", "实习报告.pdf");
            reportpathMap.put("filePath", reportPath);
            filePaths.add(reportpathMap);
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
        //压缩后的文件名
        String zipFileFullPath = studentInfo.getClassName() + "-" + studentInfo.getName() + ".zip";
        //要压缩的文件夹路径
        String zipFullPath = path + File.separator + zipFileFullPath;
        WordUtil.createZip(zipFullPath, filePaths);
        //String path=wordTarget+zipFileFullPath;
        try {
            FileDownloadUtil.downLoadfile(zipFullPath, zipFileFullPath, response);
        } catch (UnsupportedEncodingException e) {
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

        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("studentName", studentInfo.getName());
        dataMap.put("collegeName", studentInfo.getInstituteName());
        dataMap.put("className", studentInfo.getClassName());
        dataMap.put("studentNo", studentInfo.getStudentNo());
        dataMap.put("practiceName", studentTaskBooks.getPracticeName());
        dataMap.put("content", studentTaskBooks.getContent());
        dataMap.put("definiceRequire", studentTaskBooks.getDefiniceRequire());
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
        log.info("【service:开始生成word文件】:==> :" + studentInfo.getName() + "-" + fileName);
        try {
            WordUtil.createWord(dataMap, taskbookTemplate, path, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("【service:生成word文件失败】:==>" + studentInfo.getName() + "-" + fileName);
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
        log.info("【service:开始生成word文件】:==> :" + studentInfo.getName() + "-" + fileName);
        try {
            WordUtil.createWord(dataMap, assessTemplate, path, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("【service:生成word文件失败】:==>" + studentInfo.getName() + "-" + fileName);
            return null;
        }
        log.info("【service:生成word文件】:==> :" + studentInfo.getName() + "-" + fileName + "成功。");
        Map<String, String> map = new HashMap<>();
        map.put("fileName", fileName);
        map.put("filePath", path + File.separator + fileName);
        return map;
    }

}
