package com.jinfang.entity;

import com.jinfang.page.PageEntity;
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
public class EpTimeControl extends PageEntity {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("届别")
    private Integer grade;

    @ApiModelProperty("配置所属专业id")
    private Long majorId;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;
    @ApiModelProperty("开始时间字符串")
    private String startTimeString;

    @ApiModelProperty("结束时间字符串")
    private String endTimeString;

    @ApiModelProperty("修改时间")
    private Date modifyDate;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("创建人")
    private Long userId;

    @ApiModelProperty("删除标识")
    private Integer isDel;



}