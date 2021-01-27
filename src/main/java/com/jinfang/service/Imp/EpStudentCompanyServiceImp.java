package com.jinfang.service.Imp;

import com.jinfang.entity.CcStudent;
import com.jinfang.entity.EpOutAdviserStudent;
import com.jinfang.entity.EpPracticeCompany;
import com.jinfang.entity.EpStudentCompany;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpOutAdviserStudentMapper;
import com.jinfang.mapper.EpPracticeCompanyMapper;
import com.jinfang.mapper.EpStudentCompanyMapper;
import com.jinfang.service.EpOutAdviserStudentService;
import com.jinfang.service.EpStudentCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description:学生选择实习单位
 * @author: Gjm
 * @create: 2021-01-22 17:19
 **/
@Service
public class EpStudentCompanyServiceImp implements EpStudentCompanyService {

    @Autowired(required = false)
    private EpStudentCompanyMapper epStudentCompanyMapper;
    @Autowired(required = false)
    private EpPracticeCompanyMapper epPracticeCompanyMapper;
    @Autowired(required = false)
    private EpOutAdviserStudentMapper epOutAdviserStudentMapper;

    @Override
    public int save(EpStudentCompany record) {

        return epStudentCompanyMapper.save(record);
    }

    @Override
    public int delete(EpStudentCompany record) {
        return 0;
    }

    @Override
    public int delete(List<EpStudentCompany> records) {
        return 0;
    }

    @Override
    public EpStudentCompany findById(Long id) {
        return null;
    }

    @Override
    public int update(EpStudentCompany record) {

        return epStudentCompanyMapper.update(record);
    }

    @Override
    public Result findPage(EpStudentCompany record) {
        return null;
    }

    @Override
    public List<CcStudent> findCompanyStudentList(Long companyId, Integer grade,Integer isAllot) {
        return epStudentCompanyMapper.findCompanyStudentList(companyId,grade,isAllot);
    }

    @Override
    public Result chooseCompany(EpStudentCompany record) {
        Long studentId = record.getStudentId();
        Integer grade = record.getGrade();
        Date date = new Date();
        //判断是否已经选择实习单位
        EpPracticeCompany chooseCompany = epPracticeCompanyMapper.chooseCompany(studentId,grade);
        if (chooseCompany!=null){
            //判断是否已经分配了校外指导老师
            EpOutAdviserStudent studentAdviser = epOutAdviserStudentMapper.findStudentAdviser(studentId,grade);
            if (studentAdviser!=null){
                return Result.error(400,"已经分配了校外指导老师不能修改!");
            }else{
                //修改

                Long studentCompanyId = chooseCompany.getStudentCompanyId();
                record.setId(studentCompanyId);
                record.setModifyDate(date);
                int update = update(record);
                if (update>0){
                    return Result.ok("实习单位修改成功！");
                }
                return Result.error("实习单位修改失败！");
            }
        }
        record.setCreateDate(date);
        record.setModifyDate(date);
        int save = save(record);
        if (save>0){
            return Result.ok("实习单位选择成功！");
        }
        return  Result.error("实习单位选择失败！");
    }
}
