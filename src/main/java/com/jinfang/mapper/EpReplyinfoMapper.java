package com.jinfang.mapper;

import com.jinfang.entity.EpReplyinfo;

import java.util.List;

/**
 * @author ：Gjm
 * @date ：Created in 2021/1/20 11:29
 * @description：ep_replyinfo答辩地点时间信息
 * @version: 1.0
 */
public interface EpReplyinfoMapper extends CurdMapper<EpReplyinfo> {

    List<EpReplyinfo> findReplyInfo(Long majorId);

}
