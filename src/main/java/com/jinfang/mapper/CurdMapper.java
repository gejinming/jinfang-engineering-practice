package com.jinfang.mapper;

import java.util.List;

/**
 * @author ：Gjm
 * @date ：Created in 2021/1/14 14:25
 * @description：
 *  通用CURD接口
 * @version: 1.0
 */
public interface CurdMapper<T>{
    /**
     * 保存操作
     * @param record
     * @return
     */
    int save(T record);

    /**
     * 删除操作
     * @param record
     * @return
     */
    int delete(T record);

    /**
     * 批量删除操作
     * @param records
     */
    int delete(List<T> records);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    T findById(Long id);

    /**
     * 根据ID修改
     * @param record
     * @return
     */
    int update(T record);

    List<T> findPage(T record);
}
