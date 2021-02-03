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
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long companyId) {
        List<EpOutAdviser> epOutAdvisers = epOutAdviserMapper.findByCompanyId(companyId);
        ArrayList<String> loginNamelist = new ArrayList<>();
        for (EpOutAdviser temp :epOutAdvisers){
            loginNamelist.add(temp.getLoginName());
        }
        //先删除sys_user中的数据
        int updatelist=0;
        if (loginNamelist.size()>0){
            updatelist= sysUserMapper.updatelist(loginNamelist);
        }
        //删除校外表数据
        updatelist=epOutAdviserMapper.delete(companyId);

        return updatelist;
    }

    @Override
    public List<EpOutAdviser> findByCompanyId(Long companyId) {
        return epOutAdviserMapper.findByCompanyId(companyId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(EpOutAdviser record) {
        Long schoolId = record.getSchoolId();
        record.setLoginName(schoolId+"-"+record.getPhone());
        int insert = epOutAdviserMapper.insert(record);
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
    @Transactional(rollbackFor = Exception.class)
    public int update(EpOutAdviser record) {
        String newLoginName = record.getSchoolId() + "-" + record.getPhone();
        record.setLoginName(newLoginName);
        EpOutAdviser epOutAdviser = epOutAdviserMapper.findById(record.getId());
        String outloginName = epOutAdviser.getLoginName();
        int update = epOutAdviserMapper.update(record);
        //修改登陆表信息sys_user
        if( update>0){
            SysUser sysUser = new SysUser();
            sysUser.setLoginName(newLoginName);
            sysUser.setOutloginName(outloginName);
            sysUser.setName(record.getName());
            sysUser.setIsDel(record.getIsDel());
            update=sysUserMapper.update(sysUser);
        }
        return update;
    }

    @Override
    public Result findPage(EpOutAdviser record) {
        return null;
    }
}
