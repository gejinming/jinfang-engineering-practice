package com.jinfang.entity;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
*  ep_task_book_plan任务书进度安排
* @author GJM 2021-01-25
*/
@Data
public class EpTaskBookPlan implements Serializable {


    private static final long serialVersionUID = -4455442768029094848L;
    /**
    * id
    */
    private Long id;

    /**
    * 任务书id
    */
    private Long bookId;

    /**
    * 开始时间
    */
    private Date startDate;

    /**
    * 结束时间
    */
    private Date endDate;

    /**
    * 内容
    */
    private String content;

    /**
    * 删除标识
    */
    private Integer isDel;

    /**
    * 是否为历史（0否，1是）
    */
    private Integer isHistory;

    public EpTaskBookPlan() {
    }

}
