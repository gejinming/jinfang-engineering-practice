package com.jinfang.service.Imp;

import com.jinfang.entity.EpGuideStudentnum;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpGuideStudentnumMapper;
import com.jinfang.page.MybatisPageHelper;
import com.jinfang.service.EpGuideStudentnumService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* ep_guide_studentnum校内指导教师指导学生数上限表
* @author GJM
* @date 2021/01/20
*/
@Service
public class EpGuideStudentnumServiceImp implements EpGuideStudentnumService {

	@Resource
	private EpGuideStudentnumMapper epGuideStudentnumMapper;


	@Override
	public int save(EpGuideStudentnum record) {
		return epGuideStudentnumMapper.save(record);
	}

	@Override
	public int delete(EpGuideStudentnum record) {
		return 0;
	}

	@Override
	public int delete(List<EpGuideStudentnum> records) {
		return 0;
	}

	@Override
	public EpGuideStudentnum findById(Long id) {
		return epGuideStudentnumMapper.findById(id);
	}

	@Override
	public int update(EpGuideStudentnum record) {
		return epGuideStudentnumMapper.update(record);
	}

	@Override
	public Result findPage(EpGuideStudentnum record) {
		MybatisPageHelper.pageHelper(record.getPage(),record.getLimit());
		return Result.ok(MybatisPageHelper.getPageResult(epGuideStudentnumMapper.findPage(record)));
	}

	@Override
	public int upStudentNum(Long majorId, Integer grade) {
		return epGuideStudentnumMapper.upStudentNum(majorId,grade);
	}
}
