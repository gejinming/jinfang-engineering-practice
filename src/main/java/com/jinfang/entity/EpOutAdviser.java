package com.jinfang.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * ep_out_adviser校外指导老师
 * @author GJM 2021-01-13
 */
@Data
@ApiModel("ep_out_adviser校外指导老师")
public class EpOutAdviser {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("单位id")
    private Long companyId;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("删除标识")
    private Integer isDel;

    @ApiModelProperty("专业")
    private Long majorId;
   

}