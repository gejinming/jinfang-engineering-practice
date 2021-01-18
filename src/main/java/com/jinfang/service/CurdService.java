package com.jinfang.service;


import com.jinfang.httpdto.Result;

import java.util.List;

/**
 * 通用CURD接口
 */
public interface CurdService<T> {
	
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
	 * @param id
	 * @return
	 */
	int update(T record);

    Result findPage(T record);
	
}