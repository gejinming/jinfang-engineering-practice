package com.jinfang.entity;

import java.io.Serializable;

import com.jinfang.page.PageEntity;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
*  cc_teacher
* @author gjm 2021-01-08
*/
@Data
public class CcTeacher extends PageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 编号，与sys_user的编号保持一致
    */
    private Long id;

    /**
    * 创建日期
    */
    private Date createDate;

    /**
    * 修改日期
    */
    private Date modifyDate;

    /**
    * 教师工号
    */
    private String code;

    /**
    * 教师姓名
    */
    private String name;

    /**
    * 性别，0:男，1:女
    */
    private Integer sex;

    /**
    * 生日
    */
    private Date birthday;

    /**
    * 籍贯
    */
    private String nativePlace;

    /**
    * 民族码
    */
    private String nation;

    /**
    * 政治面貌
    */
    private String politics;

    /**
    * 国籍/地区码
    */
    private String country;

    /**
    * 身份证号
    */
    private String idCard;

    /**
    * 最高学历
    */
    private String highestEducation;

    /**
    * 最高学位，1:学士，2:硕士，3:博士，4:其它，默认填其它
    */
    private Integer highestDegrees;

    /**
    * 本科毕业学校
    */
    private String bachelorSchool;

    /**
    * 本科毕业专业
    */
    private String bachelorMajor;

    /**
    * 硕士毕业学校
    */
    private String masterSchool;

    /**
    * 硕士毕业专业
    */
    private String masterMajor;

    /**
    * 博士毕业学校
    */
    private String doctorateSchool;

    /**
    * 博士毕业专业
    */
    private String doctorateMajor;

    /**
    * 来校日期
    */
    private Date comeSchoolTime;

    /**
    * 从教年月
    */
    private Date startEducationYear;

    /**
    * 职称
    */
    private Integer jobTitle;

    /**
    * 行政职务
    */
    private String administrative;

    /**
    * 手机号码1
    */
    private String mobilePhone;

    /**
    * 手机号码2，建议填短号
    */
    private String mobilePhoneSec;

    /**
    * 办公室电话号码1
    */
    private String officePhone;

    /**
    * 办公室电话号码2，建议填短号
    */
    private String officePhoneSec;

    /**
    * qq号码
    */
    private String qq;

    /**
    * 微信号
    */
    private String wechat;

    /**
    * 邮箱
    */
    private String email;

    /**
    * 办公室地址
    */
    private String officeAddress;

    /**
    * 照片
    */
    private String photo;

    /**
    * 是否已删除
    */
    private String isDel;

    /**
    * 当教师离开某一学校并未加入到系统中存在的另一所学校时，处于离职状态
    */
    private String isLeave;

    /**
    * 隶属专业编号
    */
    private Long majorId;

    /**
    * 隶属学院编号
    */
    private Long instituteId;

    /**
    * 隶属学校编号
    */
    private Long schoolId;


}
