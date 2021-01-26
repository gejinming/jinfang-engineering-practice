package com.jinfang.entity;

import java.io.Serializable;

import com.jinfang.page.PageEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
*  ep_practice_company实习单位
* @author GJM 2021-01-18
*/
@Data
public class EpPracticeCompany extends PageEntity implements Serializable {


    private static final long serialVersionUID = 5681423114031892603L;
    /**
    * id
    */
    private Long id;

    /**
    * 所属专业id
    */
    private Long majorId;

    /**
    * 单位名称
    */
    private String name;

    /**
    * 单位地址
    */
    private String address;

    /**
    * 联系电话
    */
    private String phone;

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
    /*
     *正在实习的学生
     */
    private Integer loadingStudentNum;
    /**
     * 校外指导老师数
     */
    private Integer outAdviserNum;
    /**
     * 合计实习学生数
     */
    private Integer sumStudentNum;
    /**
     * 校外指导老师列表
     */
    private List<EpOutAdviser> outAdvisers;

    private Long schoolId;

    private Integer grade;

    private ArrayList<HashMap<String, String>> allotAdviserStudentNumList;

    private ArrayList<HashMap<String, Integer>> noAllotStudentNums;

    private Long studentCompanyId;


}
