package com.jinfang.service.Imp;

import com.jinfang.entity.CcStudent;
import com.jinfang.entity.EpReplyGroupStudent;
import com.jinfang.entity.EpReplyinfo;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpReplyGroupStudentMapper;
import com.jinfang.mapper.EpReplyinfoMapper;
import com.jinfang.page.MybatisPageHelper;
import com.jinfang.service.EpReplyGroupStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @Description：TODO
 * @Author：GJM
 * @Date：2021/2/18 2:52 下午
 */
@Service
public class EpReplyGroupStudentServiceImp implements EpReplyGroupStudentService {
    @Autowired
    private EpReplyGroupStudentMapper epReplyGroupStudentMapper;
    @Autowired
    private EpReplyinfoMapper epReplyinfoMapper;
    @Override
    public int save(List<EpReplyGroupStudent> list) {
        return epReplyGroupStudentMapper.save(list);
    }

    @Override
    public int delete(Long id) {
        return epReplyGroupStudentMapper.delete(id);
    }

    @Override
    public Result findPage(EpReplyinfo epReplyinfo) {
        List<CcStudent> studentList = findUnAllocatStudentList(epReplyinfo.getMajorId(), epReplyinfo.getGrade());
        int studentNum = studentList.size();
        HashMap<Object, Object> result = new HashMap<>();
        MybatisPageHelper.pageHelper(epReplyinfo.getPage(),epReplyinfo.getLimit());
        List<EpReplyinfo> page = epReplyinfoMapper.findPage(epReplyinfo);
        for (EpReplyinfo temp : page){
            Integer grade = temp.getGrade();
            String groupName = temp.getGroupName();
            if (grade==null || groupName == null){
                return Result.error("未获取到届别或组别，请重试！");
            }
            List<CcStudent> allocatStudentList = findAllocatStudentList(epReplyinfo.getMajorId(), grade, groupName);
            temp.setStudentNum(allocatStudentList.size());
        }
        result.put("findPage",MybatisPageHelper.getPageResult(page));
        result.put("studentNum",studentNum);
        return Result.ok(result);
    }

    @Override
    public List<CcStudent> findUnAllocatStudentList(Long majorId, Integer grade, String studentName, String studentNo, String className) {
        return epReplyGroupStudentMapper.findUnAllocatStudentList(majorId,grade,studentName,studentNo,className);
    }

    @Override
    public List<CcStudent> findAllocatStudentList(Long majorId, Integer grade, String groupName) {
        return epReplyGroupStudentMapper.findAllocatStudentList(majorId,grade,groupName);
    }
    public List<CcStudent> findUnAllocatStudentList(Long majorId, Integer grade) {
        return findUnAllocatStudentList(majorId,grade,null,null,null);
    }
}
