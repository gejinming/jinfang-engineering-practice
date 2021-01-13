package com.jinfang.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * ep_time_control时间控制表
 * @author GJM 2021-01-13
 */
@Data
@ApiModel("ep_time_control时间控制表")
public class EpTimeControl {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("届别")
    private String grade;

    @ApiModelProperty("配置所属专业id")
    private Long majorId;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("修改时间")
    private Date modifyDate;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("创建人")
    private Long userId;

    @ApiModelProperty("删除标识")
    private Integer isDel;



}