package com.jinfang.service.Imp;

import com.jinfang.entity.EpTaskBook;
import com.jinfang.entity.EpTaskBookPlan;
import com.jinfang.entity.StudentTaskBook;
import com.jinfang.entityEnum.TaskBookState;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpTaskBookMapper;
import com.jinfang.mapper.EpTaskBookPlanMapper;
import com.jinfang.page.MybatisPageHelper;
import com.jinfang.service.EpTaskBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        record.setState(TaskBookState.NOSEND.getCode());
        //先新增任务书
        int save = epTaskBookMapper.save(record);

        if (save>0){
            //新增任务书计划
            List<EpTaskBookPlan> epTaskBookPlans = record.getEpTaskBookPlans();
            save = saveTaskBookPlan(epTaskBookPlans, record.getId());

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
    @Transactional(rollbackFor = Exception.class)
    public int update(EpTaskBook record) {
        /*
        * 修改任务书，如果该任务书已经下发了，
        * 把上一次的数据作为历史记录一次，先把原数据给改状态isHistory=1
        * 未下发状态的修改就不加历史记录了。
        * */

        int update=0;
        if (record.getState() == TaskBookState.SEND.getCode()){
            EpTaskBook epTaskBook = new EpTaskBook();
            Date date = new Date();
            epTaskBook.setId(record.getId());
            epTaskBook.setIsHistory(1);
            epTaskBook.setModifyDate(date);
            update = epTaskBookMapper.update(epTaskBook);
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
        }else {
             update = epTaskBookMapper.update(record);
             if (!record.getEpTaskBookPlans().isEmpty()){
               //先清除原数据，再添加
                 EpTaskBookPlan epTaskBookPlan = new EpTaskBookPlan();
                 epTaskBookPlan.setBookId(record.getId());
                 epTaskBookPlan.setIsDel(1);
                 epTaskBookPlanMapper.update(epTaskBookPlan);
                 //添加
                 update = saveTaskBookPlan(record.getEpTaskBookPlans(), record.getId());
             }
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
    /**
     * 新增任务书计划
     * @param epTaskBookPlans: 任务书计划
     * @param taskBookId 任务书ID
     * @Author: Gjm
     * @Date: 2021/3/12 4:08 下午
     * @return: boolean
     **/
    public  int saveTaskBookPlan(List<EpTaskBookPlan> epTaskBookPlans,Long taskBookId){
        int save=0;
        if (epTaskBookPlans.size()>0){
            for (EpTaskBookPlan temp : epTaskBookPlans){
                temp.setBookId(taskBookId);
            }
            save =epTaskBookPlanMapper.save(epTaskBookPlans);
        }
        return save;

    }
}
