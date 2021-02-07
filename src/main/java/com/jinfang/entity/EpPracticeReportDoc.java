package com.jinfang.entity;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
*  文件表
* @author GJM 2021-02-04
*/
@Data
@Builder
public class EpPracticeReportDoc implements Serializable {


    private static final long serialVersionUID = -5830951159894431990L;
    /**
    * id
    */
    private Long id;

    /**
    * 文件原名称
    */
    private String originName;

    /**
    * 文件新名称
    */
    private String newName;

    /**
    * 文件类型
    */
    private String type;

    /**
    * 文件大小，存储字节
    */
    private Long size;

    /**
    * 存储路径(物理路径)
    */
    private String path;

    /**
    * 网络全路径
    */
    private String url;

    /**
    * create_date
    */
    private Date createDate;

    /**
    * modify_date
    */
    private Date modifyDate;

    /**
    * 是否已删除
    */
    private Integer isDel;


}
