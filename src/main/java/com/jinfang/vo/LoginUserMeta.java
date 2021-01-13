package com.jinfang.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * 登录元数据信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserMeta {

    /**
     * 角色名称： 学生/专业负责人/教师
     */
    @Builder.Default
    private Set<String> roles = new HashSet<>();

    /**
     * 教师ID，根据role为教师或专业负责人时才有值
     */
    private Long teacherId;

    /**
     * 教师姓名
     */
    private String teacherName;

    /**
     * 学生ID
     */
    private Long studentId;

    /**
     * 学生学号，当角色为学生时才有值
     */
    private String studentNo;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 届别
     */
    private String grade;

    /**
     * 归属院校ID
     */
    private Long schoolId;

    /**
     * 所属专业， 教师和学生都有归属专业，一般用于条件过滤
     */
    private Long majorId;

    /**
     * 是否为学生
     */
    @Builder.Default
    private Boolean isStudent = false;

    /**
     * 获取当前登录人ID
     */
    public Long getPrincipalId() {
        return getIsStudent() ? getStudentId() : getTeacherId();
    }

    /**
     * 获取当前登录人姓名
     */
    public String getPrincipalName() {
        return getIsStudent() ? getStudentName() : getTeacherName();
    }

    public String getTeacherIdText() {
        return getTeacherId() == null ? "" : getTeacherId().toString();
    }
}
