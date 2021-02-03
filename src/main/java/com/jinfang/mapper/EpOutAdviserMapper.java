package com.jinfang.mapper;

import com.jinfang.entity.EpOutAdviser;
import com.jinfang.entity.EpOutAdviserStudent;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author ：Gjm
 * @date ：Created in 2021/1/13 9:47
 * @description：校外指导老师接口
 * @version: 1.0
 */
public interface EpOutAdviserMapper {

    /*
     * @param phone
    	 * @param password
     * @return com.jinfang.entity.EpOutAdviser
     * @description: 根据手机号和密码查询指导老师
     * @date 2021/1/13 9:52
     */
    EpOutAdviser findOutAdviser(String phone,String password);
    /*
     * @param id
     * @return com.jinfang.entity.EpOutAdviser
     * @description: 通过专业认证用户表sys_user的id 查找校外教师信息
     * @date 2021/1/13 15:47
     */
    EpOutAdviser findAdviserInfoBySysId(Long id);

    int save(List<EpOutAdviser> list);

    int insert(EpOutAdviser epOutAdviser);

    int update(EpOutAdviser epOutAdviser);

    List<EpOutAdviser> findByCompanyId(Long companyId);
    /*
     * @Description:删除实习单位下的指导老师
     * @Author: Gjm
     * @Date: 2021/1/21 16:13
     **/
    int delete(Long companyId);

    /*
     * @Description:查询专业分配给校外指导老师的学生数
     * @Author: Gjm
     * @Date: 2021/1/22 15:23
     **/
    List<EpOutAdviser> findAdviserStudentNum(Long majorId);
    /*
     * @Description:查询专业 未分配给校外指导老师的学生数
     * @Author: Gjm
     * @Date: 2021/1/22 15:55
     **/
    List<EpOutAdviser> findNoAllotStudentNum(Long majorId);


    EpOutAdviser findById(Long id);


}
