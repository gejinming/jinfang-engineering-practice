package com.jinfang.service.Imp;

import com.jinfang.entity.EpPracticeCompany;
import com.jinfang.entity.EpWeekDayReport;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpPracticeCompanyMapper;
import com.jinfang.mapper.EpWeekDayReportMapper;
import com.jinfang.page.MybatisPageHelper;
import com.jinfang.service.EpWeekDayReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description: 学生日报周报
 * @author: Gjm
 * @create: 2021-01-27 11:13
 **/
@Service
public class EpWeekDayReportServiceImp implements EpWeekDayReportService {

    @Autowired(required = false)
    private EpWeekDayReportMapper epWeekDayReportMapper;
    @Autowired(required = false)
    private EpPracticeCompanyMapper epPracticeCompanyMapper;

    @Override
    public int save(EpWeekDayReport record) {
        return epWeekDayReportMapper.save(record);
    }

    @Override
    public int delete(EpWeekDayReport record) {
        return 0;
    }

    @Override
    public int delete(List<EpWeekDayReport> records) {
        return 0;
    }

    @Override
    public EpWeekDayReport findById(Long id) {
        return null;
    }

    @Override
    public int update(EpWeekDayReport record) {
        return epWeekDayReportMapper.update(record);
    }

    @Override
    public Result findPage(EpWeekDayReport record) {
        MybatisPageHelper.pageHelper(record.getPage(),record.getLimit());
        List<EpWeekDayReport> page = epWeekDayReportMapper.findPage(record);
        return Result.ok(MybatisPageHelper.getPageResult(page));
    }

    @Override
    public Result sumitReport(EpWeekDayReport epWeekDayReport) {
        //先判断是否选择了实习单位
        EpPracticeCompany chooseCompany = epPracticeCompanyMapper.chooseCompany(epWeekDayReport.getStudentId(), epWeekDayReport.getGrade());
        if (chooseCompany==null){
            return Result.error("你还未选择实习单位，不能填写周报。");
        }
        Long studentCompanyId = chooseCompany.getStudentCompanyId();
        epWeekDayReport.setStudentCompanyId(studentCompanyId);
        epWeekDayReport.setCreateDate(new Date());
        int save = save(epWeekDayReport);
        if (save>0){
            return Result.ok("提交成功");
        }
        return Result.error("提交失败");
    }

    @Override
    public List<EpWeekDayReport> studentReportList(Long studentCompanyId) {

        return  epWeekDayReportMapper.studentReportList(studentCompanyId);
    }
}
