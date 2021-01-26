package com.jinfang.service.Imp;

import com.jinfang.entity.EpOutAdviser;
import com.jinfang.entity.EpOutAdviserStudent;
import com.jinfang.entity.SysUser;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpOutAdviserMapper;
import com.jinfang.mapper.SysUserMapper;
import com.jinfang.service.EpOutAdviserService;
import com.jinfang.service.IdGenerator;
import com.jinfang.util.PasswdKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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
    @Autowired(required = false)
    private IdGenerator idGenerator;
    @Autowired(required = false)
    private SysUserMapper sysUserMapper;
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
    public int delete(Long companyId) {
        return epOutAdviserMapper.delete(companyId);
    }

    @Override
    public List<EpOutAdviser> findByCompanyId(Long companyId) {
        return epOutAdviserMapper.findByCompanyId(companyId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(EpOutAdviser record) {
        int insert = epOutAdviserMapper.insert(record);
        Long schoolId = record.getSchoolId();
        //将指导老师的账号加入sys_user中
        if (insert>0){
            Date date = new Date();
            ArrayList<SysUser> sysUserArrayList = new ArrayList<>();
            SysUser sysUser = new SysUser();
            sysUser.setId(idGenerator.getNextValue());
            sysUser.setCreateDate(date);
            sysUser.setModifyDate(date);
            sysUser.setName(record.getName());
            sysUser.setLoginName(schoolId+"-"+record.getPhone());
            sysUser.setPassword(PasswdKit.entryptPassword(record.getPassword()));
            sysUserArrayList.add(sysUser);
            insert = sysUserMapper.insert(sysUserArrayList);
        }
        return insert;
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
    public int update(EpOutAdviser record) {
        return epOutAdviserMapper.update(record);
    }

    @Override
    public Result findPage(EpOutAdviser record) {
        return null;
    }
}
