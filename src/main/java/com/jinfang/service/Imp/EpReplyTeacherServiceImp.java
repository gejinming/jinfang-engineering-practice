package com.jinfang.service.Imp;

import com.github.pagehelper.PageHelper;
import com.jinfang.entity.EpReplyTeacher;
import com.jinfang.entity.EpTimeControl;
import com.jinfang.entityEnum.ReplyTeacherType;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpReplyTeacherMapper;
import com.jinfang.page.MybatisPageHelper;
import com.jinfang.service.EpReplyTeacherService;
import com.jinfang.vo.IntoTeacherVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description: 答辩组教师表实现类
 * @author: Gjm
 * @create: 2021-01-12 17:37
 **/
@Service
public class EpReplyTeacherServiceImp implements EpReplyTeacherService {

    @Autowired(required = false)
    private EpReplyTeacherMapper epReplyTeacherMapper;

    @Override
    public List<EpReplyTeacher> findByTeacher(Integer grade, Long teacherId,Integer roleType) {

        return epReplyTeacherMapper.findByTeacher(grade,teacherId,roleType);
    }

    public boolean isGroupLeader(Integer grade, Long teacherId,Integer roleType){
        return epReplyTeacherMapper.findByTeacher(grade,teacherId,roleType).size()>0;
    }
    /*
     * @Description:拉入教师
     * @Author: Gjm
     * @Date: 2021/1/15 11:10
     **/
    @Override
    public int save(List<EpReplyTeacher> vo) {

        return epReplyTeacherMapper.save(vo);
    }

    @Override
    public boolean isGradeOpen(Integer grade, Long majorId) {
        Map<String, Object> gradeIsOpen = epReplyTeacherMapper.findGradeIsOpen(grade, majorId);
        if (gradeIsOpen==null){
            return false;
        }
        return true;
    }

    @Override
    public int save(EpReplyTeacher record) {
        return epReplyTeacherMapper.save(record);
    }

    @Override
    public int delete(EpReplyTeacher record) {
        return 0;
    }

    @Override
    public int delete(List<EpReplyTeacher> records) {
        return 0;
    }

    @Override
    public EpReplyTeacher findById(Long id) {
        return null;
    }

    @Override
    public int update(EpReplyTeacher record) {
        //修改教师组长之前先把存在的组长设为教师
        if (record.getRoleId()== ReplyTeacherType.HEADMAN.getCode()){
            epReplyTeacherMapper.updateByGroupName(record.getGroupName());
        }
        return epReplyTeacherMapper.update(record);
    }

    @Override
    public Result findPage(EpReplyTeacher record) {
        MybatisPageHelper.pageHelper(record.getPage(),record.getLimit());
        List<EpReplyTeacher> list = epReplyTeacherMapper.findPage(record);
        return Result.ok( MybatisPageHelper.getPageResult(list));
    }
}
