package com.jinfang.entity;

import java.io.Serializable;

import com.jinfang.page.PageEntity;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
*  ep_replyinfo答辩地点时间信息
* @author GJM 2021-01-20
*/
@Data
public class EpReplyinfo extends PageEntity implements Serializable {


    private static final long serialVersionUID = 5898177553737984953L;
    /**
    * id
    */
    private Long id;

    /**
    * 届别
    */
    private Integer grade;

    /**
    * 配置所属专业id
    */
    private Long majorId;

    /**
    * 开始时间
    */
    private Date startTime;

    /**
    * 结束时间
    */
    private Date endTime;

    /**
    * 地点
    */
    private String address;

    /**
    * 组别
    */
    private String groupName;

    /**
    * 创建时间
    */
    private Date createDate;

    /**
    * 修改时间
    */
    private Date modifyDate;

    /**
    * 删除标识
    */
    private Integer isDel;

    private String Teacher;

    private String Student;

    private Integer studentNum;


}
