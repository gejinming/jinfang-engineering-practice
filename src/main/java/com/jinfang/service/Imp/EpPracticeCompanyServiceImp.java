package com.jinfang.service.Imp;

import com.github.pagehelper.PageInfo;
import com.jinfang.entity.*;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpOutAdviserMapper;
import com.jinfang.mapper.EpPracticeCompanyMapper;
import com.jinfang.mapper.SysUserMapper;
import com.jinfang.page.MybatisPageHelper;
import com.jinfang.page.PageResult;
import com.jinfang.service.EpPracticeCompanyService;
import com.jinfang.service.IdGenerator;
import com.jinfang.util.PasswdKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @description: 实习单位实现类
 * @author: Gjm
 * @create: 2021-01-18 17:49
 **/
@Service
public class EpPracticeCompanyServiceImp implements EpPracticeCompanyService {
    @Autowired(required = false)
    private EpPracticeCompanyMapper epPracticeCompanyMapper;
    @Autowired(required = false)
    private EpOutAdviserMapper epOutAdviserMapper;
    @Autowired(required = false)
    private SysUserMapper sysUserMapper;
    @Autowired(required = false)
    private IdGenerator idGenerator;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(EpPracticeCompany record) {
        //先保存单位，获取到主键id，再保存校外老师
        int save = epPracticeCompanyMapper.save(record);
        Long schoolId = record.getSchoolId();
        if (save !=0){
            List<EpOutAdviser> outAdvisers = record.getOutAdvisers();
            if (record.getId()!=null && outAdvisers.size()>0){
                Date date = new Date();
                ArrayList<SysUser> sysUserList = new ArrayList<>();
                for (EpOutAdviser temp : outAdvisers){
                    temp.setCompanyId(record.getId());
                    String tempPassword = temp.getPassword();
                    SysUser sysUser = new SysUser();
                    sysUser.setId(idGenerator.getNextValue());
                    sysUser.setCreateDate(date);
                    sysUser.setModifyDate(date);
                    sysUser.setName(temp.getName());
                    sysUser.setLoginName(schoolId+"-"+record.getPhone());
                    sysUser.setPassword(PasswdKit.entryptPassword(tempPassword));
                    sysUserList.add(sysUser);
                }
                save=epOutAdviserMapper.save(outAdvisers);
                //保存过校外老师之后，再将校外老师的账号加入sys_user表中
                if (save>0){
                    save=sysUserMapper.insert(sysUserList);
                }


            }
        }

        return save;
    }

    @Override
    public int delete(EpPracticeCompany record) {
        return 0;
    }

    @Override
    public int delete(List<EpPracticeCompany> records) {
        return 0;
    }

    @Override
    public EpPracticeCompany findById(Long id) {
        EpPracticeCompany epPracticeCompany = epPracticeCompanyMapper.findById(id);
        List<EpOutAdviser> epOutAdvisers = epOutAdviserMapper.findByCompanyId(id);
        epPracticeCompany.setOutAdvisers(epOutAdvisers);
        return epPracticeCompany;
    }

    @Override
    public int update(EpPracticeCompany record) {

        return epPracticeCompanyMapper.update(record);
    }

    @Override
    public Result findPage(EpPracticeCompany record) {
        MybatisPageHelper.pageHelper(record.getPage(),record.getLimit());
        return Result.ok(MybatisPageHelper.getPageResult(epPracticeCompanyMapper.findPage(record)));
    }

    @Override
    public boolean findByName(Long majorId, String name,Long id) {
        //新增的时候判断，不用判断原来的名称
        if (id==null){
            if (epPracticeCompanyMapper.findByName(majorId,name)!=null){
                return true;
            }
        }else{
            EpPracticeCompany epPracticeCompany = epPracticeCompanyMapper.findById(id);
            //与原名称相同则不用判断
            if (!epPracticeCompany.getName().equals(name)){
                if (epPracticeCompanyMapper.findByName(majorId,name)!=null){
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean isExistStudent(Long companyId) {
        List<EpStudentCompany> epStudentCompanies = epPracticeCompanyMapper.chooseCompanyStudentList(companyId);
        if (epStudentCompanies.size()>0){
            return true;
        }
        return false;
    }

    @Override
    public Result allotAdviserStudentList(EpPracticeCompany epPracticeCompany) {
        Long majorId = epPracticeCompany.getMajorId();
        MybatisPageHelper.pageHelper(epPracticeCompany.getPage(),epPracticeCompany.getLimit());
        //学生选择的实习单位列表
        List<EpPracticeCompany> companyList = epPracticeCompanyMapper.allotAdviserStudentList(majorId);
        //分页
        PageInfo<EpPracticeCompany> pageInfo = new PageInfo<>(companyList);
        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setTotalPages(pageInfo.getPages());
        List<EpPracticeCompany> list = pageInfo.getList();
        //每个校外指导老师分配的学生数
        List<EpOutAdviser> adviserStudentNum = epOutAdviserMapper.findAdviserStudentNum(majorId);
        //每个实习单位未分配的学生数
        List<EpOutAdviser> noAllotStudentNum = epOutAdviserMapper.findNoAllotStudentNum(majorId);
        List<EpPracticeCompany> result = new ArrayList<>();
        for (EpPracticeCompany epc : list){
            EpPracticeCompany company = new EpPracticeCompany();
            Long companyId = epc.getId();
            String companyName = epc.getName();
            Integer grade = epc.getGrade();
            ArrayList<HashMap<String, String>> allotAdviserStudentNumList = new ArrayList<>();
            //匹配单位校外指导老师分配的学生数
            for (EpOutAdviser ea : adviserStudentNum){
                if (ea.getCompanyId().equals(companyId) && ea.getGrade().equals(grade)){
                    HashMap<String, String> allotAdviserStudentNumMap = new HashMap<>();
                    String adviserStudentNums=ea.getName()+":"+ea.getStudentNum();
                    allotAdviserStudentNumMap.put("adviserStudentNums",adviserStudentNums);
                    allotAdviserStudentNumList.add(allotAdviserStudentNumMap);
                }

            }
            ArrayList<HashMap<String, Integer>> noAllotStudentNums = new ArrayList<>();
            for (EpOutAdviser ea : noAllotStudentNum){
                if (ea.getCompanyId().equals(companyId) && ea.getGrade().equals(grade)){
                    HashMap<String, Integer> noAllotStudentNumMap = new HashMap<>();
                    noAllotStudentNumMap.put("studentNum",ea.getStudentNum());
                    noAllotStudentNums.add(noAllotStudentNumMap);
                }
            }
            company.setGrade(grade);
            company.setName(companyName);
            company.setId(companyId);
            company.setAllotAdviserStudentNumList(allotAdviserStudentNumList);
            company.setNoAllotStudentNums(noAllotStudentNums);
            result.add(company);
        }
        //把最终结果加入分页结果中
        pageResult.setContent(result);
        //未分配校外指导老师学生数
        Integer allotStudentNum = epPracticeCompanyMapper.allotStudentNum(majorId, 0);
        //未选择实习单位的学生数
        Integer allotNoStudentNum = epPracticeCompanyMapper.allotStudentNum(majorId, 1);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("list",pageResult);
        map.put("allotStudentNum",allotStudentNum);
        map.put("allotNoStudentNum",allotNoStudentNum);
        return Result.ok(map);
    }

    @Override
    public Result chooseCompanyFindPageList(EpPracticeCompany epPracticeCompany,Long studentId) {
        HashMap<Object, Object> map = new HashMap<>();
        MybatisPageHelper.pageHelper(epPracticeCompany.getPage(),epPracticeCompany.getLimit());
        List<EpPracticeCompany> epPracticeCompanies = epPracticeCompanyMapper.chooseCompanyFindPageList(epPracticeCompany);
        PageResult pageResult = MybatisPageHelper.getPageResult(epPracticeCompanies);
        //当前学生选择的实习单位信息
        EpPracticeCompany chooseCompany = epPracticeCompanyMapper.chooseCompany(studentId);
        map.put("companyList",pageResult);
        map.put("chooseCompany",chooseCompany);
        return Result.ok(map);
    }
}
