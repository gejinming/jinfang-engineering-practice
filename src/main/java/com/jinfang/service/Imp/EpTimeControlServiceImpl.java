package com.jinfang.service.Imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinfang.entity.CcTeacher;
import com.jinfang.entity.EpTimeControl;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpTimeControlMapper;
import com.jinfang.page.MybatisPageHelper;
import com.jinfang.service.EpTimeControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 时间控制实现层
 * @author: Gjm
 * @create: 2021-01-14 09:32
 **/
@Service
public class EpTimeControlServiceImpl implements EpTimeControlService {
    @Autowired(required = false)
    EpTimeControlMapper epTimeControlMapper;

    @Override
    public int save(EpTimeControl record) {
        return epTimeControlMapper.insert(record);
    }

    @Override
    public int delete(EpTimeControl record) {
        return 0;
    }

    @Override
    public int delete(List<EpTimeControl> records) {
        return 0;
    }

    @Override
    public EpTimeControl findById(Long id) {
        return epTimeControlMapper.findById(id);
    }

    @Override
    public int update(EpTimeControl record) {
        return epTimeControlMapper.update(record);
    }

    @Override
    public Result findPage(EpTimeControl record) {
        if (record !=null && record.getPage() !=null ){
            PageHelper.startPage(record.getPage(), record.getLimit());
        }
        List<EpTimeControl> list = epTimeControlMapper.findPage(record);
        return Result.ok( MybatisPageHelper.getPageResult(list));
    }
    /*
     * @param grade
     * @return boolean
     * @description: 如果不存在返回true
     * @date 2021/1/15 9:31
     */
    @Override
    public boolean findByGrade(EpTimeControl record) {
       EpTimeControl list = epTimeControlMapper.findByGrade(record.getGrade(),record.getMajorId());
        if (list==null){
            return true;
        }
        return false;
    }
}
