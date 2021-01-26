package com.jinfang.service.Imp;

import com.jinfang.entity.EpTaskBook;
import com.jinfang.entity.EpTaskBookPlan;
import com.jinfang.entity.StudentTaskBook;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpTaskBookMapper;
import com.jinfang.mapper.EpTaskBookPlanMapper;
import com.jinfang.page.MybatisPageHelper;
import com.jinfang.service.EpTaskBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description: 下发任务书
 * @author: Gjm
 * @create: 2021-01-25 10:15
 **/
@Service
public class EpTaskBookServiceImp implements EpTaskBookService {
    @Autowired(required = false)
    private EpTaskBookMapper epTaskBookMapper;
    @Autowired(required = false)
    private EpTaskBookPlanMapper epTaskBookPlanMapper;
    @Override
    public int save(EpTaskBook record) {
        Date date = new Date();
        record.setCreateDate(date);
        record.setModifyDate(date);
        //先新增任务书
        int save = epTaskBookMapper.save(record);

        if (save>0){
            //新增任务书计划
            List<EpTaskBookPlan> epTaskBookPlans = record.getEpTaskBookPlans();
            if (epTaskBookPlans.size()>0){
                for (EpTaskBookPlan temp : epTaskBookPlans){
                    temp.setBookId(record.getId());
                }
                save =epTaskBookPlanMapper.save(epTaskBookPlans);
            }
        }
        return save;
    }

    @Override
    public int delete(EpTaskBook record) {
        return 0;
    }

    @Override
    public int delete(List<EpTaskBook> records) {
        return 0;
    }

    @Override
    public EpTaskBook findById(Long id) {
        EpTaskBook taskBook = epTaskBookMapper.findById(id);
        List<EpTaskBookPlan> taskBookPlan = epTaskBookPlanMapper.findTaskBookPlan(id);
        taskBook.setEpTaskBookPlans(taskBookPlan);
        return taskBook;
    }

    @Override
    public int update(EpTaskBook record) {
        //修改任务书，改一次作为历史记录一次，先把原数据给改状态isHistory=1
        Date date = new Date();
        record.setIsHistory(1);
        record.setCreateDate(date);
        record.setModifyDate(date);
        int update = epTaskBookMapper.update(record);
        if (update>0){
            EpTaskBookPlan epTaskBookPlan = new EpTaskBookPlan();
            epTaskBookPlan.setBookId(record.getId());
            epTaskBookPlan.setIsHistory(1);
            update=epTaskBookPlanMapper.update(epTaskBookPlan);
        }
        //作为历史记录之后再将新数据新增进去
        if (update>=0){
             update = save(record);
        }
        return update;
    }

    @Override
    public Result findPage(EpTaskBook record) {
        MybatisPageHelper.pageHelper(record.getPage(),record.getLimit());
        List<EpTaskBook> page = epTaskBookMapper.findPage(record);
        return Result.ok(MybatisPageHelper.getPageResult(page));
    }

    @Override
    public int updateTaskBookState(EpTaskBook record) {
        return epTaskBookMapper.update(record);
    }

    @Override
    public List<EpTaskBook> findHistory(Long adviserStudentId) {
        return epTaskBookMapper.findHistory(adviserStudentId);
    }

    @Override
    public Result findStudentTaskBook(Long studentId) {
        MybatisPageHelper.pageHelper(1,10);
        List<StudentTaskBook> studentTaskBook = epTaskBookMapper.findStudentTaskBook(studentId);

        return Result.ok(MybatisPageHelper.getPageResult(studentTaskBook));
    }

    @Override
    public boolean isExistTaskBook(Long adviserStudentId) {
        EpTaskBook taskBook = epTaskBookMapper.findTaskBook(adviserStudentId);
        if (taskBook!=null){
            return true;
        }
        return false;
    }
}
