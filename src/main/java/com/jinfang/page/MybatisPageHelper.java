package com.jinfang.page;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinfang.util.ReflectionUtils;

import java.util.List;

public class MybatisPageHelper {

	public static final String findPage = "findPage";
	/*
	*//**
	 * 分页查询, 约定查询方法名为 “findPage”
	 * @param mapper Dao对象，MyBatis的 Mapper	
	 * @return
	 *//*
	public static PageResult findPage(Object mapper) {
		return findPage( mapper, findPage);
	}
	
	*//**
	 * 调用分页插件进行分页查询
	 * @param mapper Dao对象，MyBatis的 Mapper	
	 * @param queryMethodName 要分页的查询方法名
	 * @param args 方法参数
	 * @return
	 *//*
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static PageResult findPage( Object mapper, String queryMethodName, Object... args) {
		// 设置分页参数
		int pageNum = pageRequest.getPageNum();
		int pageSize = pageRequest.getPageSize();
		PageHelper.startPage(pageNum, pageSize);
		// 利用反射调用查询方法
		Object result = ReflectionUtils.invoke(mapper, queryMethodName, args);
		return getPageResult(pageRequest, new PageInfo((List) result));
	}
*/
	/**
	 * 将分页信息封装到统一的接口
	 * @param pageInfo
	 * @return
	 */
	public static PageResult getPageResult( PageInfo<?> pageInfo) {
		PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setTotalPages(pageInfo.getPages());
        pageResult.setContent(pageInfo.getList());
		return pageResult;
	}

}
