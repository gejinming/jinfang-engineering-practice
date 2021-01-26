package com.jinfang.entity;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
*  ep_out_adviser_student校外老师分配的学生
* @author GJM 2021-01-21
*/
@Data
public class EpOutAdviserStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 学生选择实习单位id
     */
    private Long studentCompanyId;

    /**
     * 校外指导老师id
     */
    private Long adviserId;

    /**
    * 删除标识
    */
    private Integer isDel;


    private Long companyId;

    private String adviserName;

    private String companyName;

    private Integer studentNum;

}
