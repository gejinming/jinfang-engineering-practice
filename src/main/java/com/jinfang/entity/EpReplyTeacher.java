package com.jinfang.entity;
import com.jinfang.page.PageEntity;
import lombok.Data;


/**
 *  ep_reply_teacher答辩组教师管理
 * @author GJM 2021-01-14
 */
@Data
public class EpReplyTeacher extends PageEntity {


    /**
     * 编号
     */
    private Long id;

    /**
     * 届别
     */
    private Integer grade;

    /**
     * 配置所属专业id
     */
    private Long majorId;

    /**
     * 组名
     */
    private String groupName;

    /**
     * 教师id
     */
    private Long teacherId;
    /**
     * 教师名称
     */
    private String teacherName;
    /**
     * 教师名称
     */
    private String majorName;

    /**
     * 角色（1教师2组长）
     */
    private Integer roleId;

    /**
     * 删除标识
     */
    private Integer isDel;


}
