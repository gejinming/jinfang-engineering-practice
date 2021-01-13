package com.jinfang.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * ep_reply_teache
 * @author GJM 2021-01-12
 */
@Data
@ApiModel("ep_reply_teache")
public class EpReplyTeacher {

    @ApiModelProperty("编号")
    private Long id;

    @ApiModelProperty("届别")
    private Integer grade;

    @ApiModelProperty("配置所属专业id")
    private Long majorId;

    @ApiModelProperty("组名")
    private String groupName;

    @ApiModelProperty("教师id")
    private Long teacherId;

    @ApiModelProperty("角色（1教师2组长）")
    private Integer roleId;

    @ApiModelProperty("删除标识")
    private Integer isDel;


}