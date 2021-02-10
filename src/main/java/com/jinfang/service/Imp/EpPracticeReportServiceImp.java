package com.jinfang.service.Imp;

import com.jinfang.entity.EpPracticeReport;
import com.jinfang.entity.ResultStudentInfoEntity;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpPracticeReportMapper;
import com.jinfang.page.MybatisPageHelper;
import com.jinfang.service.EpPracticeReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description：TODO
 * @Author：GJM
 * @Date：2021/2/4 2:45 下午
 */
@Service
public class EpPracticeReportServiceImp implements EpPracticeReportService {
    @Autowired
    private EpPracticeReportMapper epPracticeReportMapper;
    @Override
    public int save(EpPracticeReport record) {
        return epPracticeReportMapper.save(record);
    }

    @Override
    public int delete(EpPracticeReport record) {
        return 0;
    }

    @Override
    public int delete(List<EpPracticeReport> records) {
        return 0;
    }

    @Override
    public EpPracticeReport findById(Long id) {
        return null;
    }

    @Override
    public int update(EpPracticeReport record) {
        return epPracticeReportMapper.update(record);
    }

    @Override
    public Result findPage(EpPracticeReport record) {
        MybatisPageHelper.pageHelper(record.getPage(),record.getLimit());
        return Result.ok(MybatisPageHelper.getPageResult(epPracticeReportMapper.findPage(record)));
    }

    @Override
    public Result findStudentReport(Long studentId,Integer page, Integer limit,Integer isHistory) {
        List<EpPracticeReport> studentReport = epPracticeReportMapper.findStudentReport(studentId,isHistory);
        if (page==null){
            return Result.ok(studentReport);
        }
        MybatisPageHelper.pageHelper(page,limit);
        return Result.ok(MybatisPageHelper.getPageResult(studentReport));
    }
}
