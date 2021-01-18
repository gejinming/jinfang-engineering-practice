package com.jinfang.mapper;

import com.jinfang.entity.EpTimeControl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
* ep_time_control时间控制表
* @author GJM
* @date 2021/01/14
*/
public interface EpTimeControlMapper {

    /**
    * [新增]
    * @author GJM
    * @date 2021/01/14
    **/
    int insert(EpTimeControl epTimeControl);

    /**
    * [刪除]
    * @author GJM
    * @date 2021/01/14
    **/
    int delete(int id);

    /**
    * [更新]
    * @author GJM
    * @date 2021/01/14
    **/
    int update(EpTimeControl epTimeControl);

    /**
    * [查询] 根据主键 id 查询
    * @author GJM
    * @date 2021/01/14
    **/
    EpTimeControl findById(Long id);

    /*EpTimeControl findByGrade(Integer grade);*/

    /**
    * [查询] 分页查询
    * @author GJM
    * @date 2021/01/14
    **/
    List<EpTimeControl> findPage(EpTimeControl epTimeControl);

    EpTimeControl findByGrade(Integer grade,Long majorId);


}
