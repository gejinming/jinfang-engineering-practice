package com.jinfang.entity;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
*  ep_reply_group_student
* @author GJM 2021-02-18
*/
@Data
public class EpReplyGroupStudent implements Serializable {


    private static final long serialVersionUID = 5888932450919032490L;
    /**
    * id
    */
    private Long id;

    /**
    * 专业id
    */
    private Long majorId;

    /**
    * 届别
    */
    private Integer grade;

    /**
    * 组别
    */
    private String groupName;

    /**
    * 学生id
    */
    private Long studentId;


}
