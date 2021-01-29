package com.jinfang.service;

import com.jinfang.entity.EpReplyinfo;
import com.jinfang.httpdto.Result;

import java.util.List;

/**
 * @author ：Gjm
 * @date ：Created in 2021/1/20 11:31
 * @description：ep_replyinfo答辩地点时间信息
 * @version: 1.0
 */
public interface EpReplyinfoService extends CurdService<EpReplyinfo> {
    Result findReplyInfo(Long majorId,Integer page, Integer limit);
}
