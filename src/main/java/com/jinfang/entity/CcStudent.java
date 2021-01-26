package com.jinfang.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * 学生表,表全名(certification student)
 * @author GJM 2021-01-12
 */
@Data
@ApiModel("学生表,表全名(certification student)")
public class CcStudent {

    @ApiModelProperty("编号")
    private Long id;

    @ApiModelProperty("创建日期")
    private Date createDate;

    @ApiModelProperty("修改日期")
    private Date modifyDate;

    @ApiModelProperty("学号")
    private String studentNo;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("性别，0:男，1:女")
    private Integer sex;

    @ApiModelProperty("身份证号")
    private String idCard;

    @ApiModelProperty("出生日期")
    private Date birthday;

    @ApiModelProperty("家庭地址")
    private String address;

    @ApiModelProperty("寝室号")
    private String domitory;

    @ApiModelProperty("学籍状态：1.在读，2:毕业，3:休学，4:转学，5:退学(辍学)")
    private Integer statue;

    @ApiModelProperty("政治面貌")
    private String politics;

    @ApiModelProperty("籍贯")
    private String nativePlace;

    @ApiModelProperty("国籍/地区码")
    private String country;

    @ApiModelProperty("民族码")
    private String nation;

    @ApiModelProperty("手机号码1")
    private String mobilePhone;

    @ApiModelProperty("手机号码2")
    private String mobilePhoneSec;

    @ApiModelProperty("qq")
    private String qq;

    @ApiModelProperty("微信号")
    private String wechat;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("照片")
    private String photo;

    @ApiModelProperty("入学时间")
    private Date matriculateDate;

    @ApiModelProperty("毕业时间")
    private Date graduateDate;

    @ApiModelProperty("年级，入学时间获得年级，例如2013级")
    private Integer grade;

    @ApiModelProperty("行政班编号")
    private Long classId;

    @ApiModelProperty("个人情况")
    private String personal;

    @ApiModelProperty("最高学历")
    private String highestEducation;

    @ApiModelProperty("是否已删除")
    private String isDel;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("密码")
    private String password;


    /**
     * 班级名称
     */
    private String className;

    /**
     * 专业ID
     */
    private Long majorId;

    /**
     * 专业名称
     */
    private String majorName;

    /**
     * 院校ID
     */
    private Long instituteId;

    /**
     * 院校名称
     */
    private String instituteName;


    /**
     * 学校ID
     */
    private Long schoolId;

    /**
     * 学校ID
     */
    private String schoolName;
    /**
     * 校外指导教师ID
     */
    private Long adviserId;

    private Long studentCompanyId;



}