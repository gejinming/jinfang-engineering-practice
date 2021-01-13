package com.jinfang.service.Imp;

import com.jinfang.entity.EpOutAdviser;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpOutAdviserMapper;
import com.jinfang.service.EpOutAdviserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 校外指导老师实现层
 * @author: Gjm
 * @create: 2021-01-13 09:56
 **/
@Service
public class EpOutAdviserServiceImp implements EpOutAdviserService {

    @Autowired(required = false)
    private  EpOutAdviserMapper epOutAdviserMapper;

    @Override
    public boolean isOutAdviser(String phone, String password) {
        EpOutAdviser outAdviser = epOutAdviserMapper.findOutAdviser(phone, password);
        if (outAdviser==null){
            return false;
        }
        return true;
    }

    @Override
    public EpOutAdviser findAdviserInfoBySysId(Long id) {
        return  epOutAdviserMapper.findAdviserInfoBySysId(id);
    }

    @Override
    public int save(EpOutAdviser record) {
        return 0;
    }

    @Override
    public int delete(EpOutAdviser record) {
        return 0;
    }

    @Override
    public int delete(List<EpOutAdviser> records) {
        return 0;
    }

    @Override
    public EpOutAdviser findById(Long id) {
        return null;
    }

    @Override
    public Result findPage(EpOutAdviser record) {
        return null;
    }
}
