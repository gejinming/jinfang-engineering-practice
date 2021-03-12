package com.jinfang.service;

import com.jinfang.entity.*;
import com.jinfang.entityEnum.LoginRole;
import com.jinfang.entityEnum.ReplyTeacherType;
import com.jinfang.entityEnum.SystemRole;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpTimeControlMapper;
import com.jinfang.util.DateUtil;
import com.jinfang.util.JwtTokenUtils;
import com.jinfang.util.PasswdKit;
import com.jinfang.vo.LoginUserVo;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 用户登录验证服务层
 * @author: Gjm
 * @create: 2021-01-12 11:45
 **/
@Slf4j
@Service
public class SystemService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CcteacherService ccteacherService;
    @Autowired
    private CcStudentService ccStudentService;
    @Autowired
    private EpReplyTeacherService epReplyTeacherService;
    @Autowired
    private EpOutAdviserService epOutAdviserService;
    @Autowired
    private EpTimeControlMapper epTimeControlMapper;


    private static volatile ConcurrentHashMap<Long, LoginUserVo> USER_META_MAP = new ConcurrentHashMap<>();
    //默认届别为当前年限
    private int grade = DateUtil.getYear(new Date());
    ;

    /*
     * @param loginName
     * @param password
     * @param role
     * @return com.jinfang.httpdto.Result
     * @description: 账号密码登录验证
     * @date 2021/1/13 10:28
     */
    public Result checkUserPassword(String loginName, String password, String role) {
        LoginUserVo loginUserVo = new LoginUserVo();
        Long shcoolId = sysUserService.findSchoolId("浙江科技学院");
        if (shcoolId == null){
            return Result.error("学校ID未找到，请联系管理员");
        }
        //String schoolId = "704041";
        if (loginName == null) {
            return Result.error(201, "账号不可为空");
        }
        if (password == null) {
            return Result.error(201, "密码不可为空");
        }
        if (role == null) {
            return Result.error(201, "登录角色不可为空");
        }
        String userId = "";
        String LoginUserName = "";
        HashMap<Object, Object> result = new HashMap<>();
        //登录菜单权限
        ArrayList<String> menuRoleList = new ArrayList<>();
        //教师登录
        if (role.equals(LoginRole.TEACHER.getRoleName())) {
            loginName = shcoolId.toString() + "-" + loginName;
            SysUser sysUser = sysUserService.findUserByName(loginName);
            if (sysUser == null) {
                return Result.error(201, "账号不存在");
            }
            if (!PasswdKit.validatePassword(password, sysUser.getPassword())) {
                return Result.error(201, "密码错误");
            }
            LoginUserName = sysUser.getName();
            userId = sysUser.getId().toString();

            menuRoleList = CheckTeacherLogin(sysUser);

        } else {
            CcStudent student = ccStudentService.findByStudentLogin(loginName, password);
            if (student == null) {
                return Result.error(201, "账号或密码错误");
            }
            LoginUserName = student.getName();
            //届别
            Integer periodDate = student.getPeriodDate();
            menuRoleList.add(SystemRole.STUDENT.getRoleName());
            userId = student.getId().toString();
            loginUserVo.setGrade(periodDate);
        }
        //发放token
        String token = JwtTokenUtils.createToken(userId, role);
        if (token == null) {
            return Result.error(520, "token生成失败");
        }
        //判断是否在当前专业时间开放内
        LoginUserVo loginUserVo1 = getLoginUserVo(token, false);
        boolean isUse = getMajorTimeControl(loginUserVo1.getMajorId(), grade);
        loginUserVo.setUserId(Long.parseLong(userId));
        loginUserVo.setLoginName(loginName);
        loginUserVo.setName(LoginUserName);
        loginUserVo.setRoles(menuRoleList);
        loginUserVo.setIsUse(isUse);
        result.put("token", token);
        result.put("loginUserVo", loginUserVo);
        return Result.ok(result);
    }

    /*
     * @Description:token登录
     * @Author: Gjm
     * @Date: 2021/1/27 10:11
     **/
    public Result tokenLogin(String token) {
        LoginUserVo loginUserVo = JwtTokenUtils.getUserInfo(token);
        Map<Object, Object> result = new HashMap();
        String userId = "";
        String LoginUserName = "";
        //登录菜单权限
        ArrayList<String> menuRoleList = new ArrayList<>();
        //教师角色
        if (loginUserVo.getRole().equals(LoginRole.TEACHER.getRoleName())) {
            SysUser sysUser = sysUserService.findById(loginUserVo.getUserId());
            if (sysUser == null) {
                return Result.error(201, "token验证失败，找不到用户信息");
            }
            userId = sysUser.getId().toString();
            LoginUserName = sysUser.getName();
            menuRoleList = CheckTeacherLogin(sysUser);
        } else {
            CcStudent student = ccStudentService.findById(loginUserVo.getUserId());
            if (student == null) {
                return Result.error(201, "token验证失败，找不到用户信息");
            }
            Integer periodDate = student.getPeriodDate();
            menuRoleList.add(SystemRole.STUDENT.getRoleName());
            userId = student.getId().toString();
            LoginUserName = student.getName();
            loginUserVo.setGrade(periodDate);
        }
        loginUserVo.setUserId(Long.parseLong(userId));
        //loginUserVo.setLoginName(loginName);
        //判断是否在当前专业时间开放内
        LoginUserVo loginUserVo1 = getLoginUserVo(token, false);
        boolean isUse = getMajorTimeControl(loginUserVo1.getMajorId(), grade);
        loginUserVo.setName(LoginUserName);
        loginUserVo.setRoles(menuRoleList);
        loginUserVo.setIsUse(isUse);
        result.put("token", token);
        result.put("loginUserVo", loginUserVo);
        return Result.ok(result);
    }

    /*
     * @param sysUser
     * @return java.util.List
     * @description: 登录教师的菜单权限
     * @date 2021/1/13 11:48
     */
    private ArrayList<String> CheckTeacherLogin(SysUser sysUser) {
        ArrayList<String> menuRoleList = new ArrayList<>();
        Long teacherId = sysUser.getId();
        //是否是校外老师
            /* String phone = loginName.substring(loginName.indexOf("-")+1);
            boolean outAdviser = epOutAdviserService.isOutAdviser(phone, null);*/
        if (sysUser.getType() == 2) {
            menuRoleList.add(SystemRole.OUTTEACHER.getRoleName());
        } else {
            //判断是否是专业负责人，这个是专业认证的独有的角色
            boolean schoolLeader = ccteacherService.isSchoolLeader(teacherId);
            if (schoolLeader) {
                menuRoleList.add(SystemRole.LEADER.getRoleName());
            }
            //判断是否是答辩组长
            boolean groupLeader = epReplyTeacherService.isGroupLeader(grade, teacherId, ReplyTeacherType.HEADMAN.getCode());
            //答辩组长和答辩教师是互斥关系，如果不是答辩组长再判断是否是答辩教师
            if (!groupLeader) {
                boolean replyTeacher = epReplyTeacherService.isGroupLeader(grade, teacherId, ReplyTeacherType.DEFENSE.getCode());
                if (replyTeacher) {
                    menuRoleList.add(SystemRole.DEFENSE.getRoleName());
                }
            } else {
                menuRoleList.add(SystemRole.HEADMAN.getRoleName());
            }
            //所有老师都是校内指导老师
            menuRoleList.add(SystemRole.TEACHER.getRoleName());
        }


        return menuRoleList;
    }

    /*
     * @param loginUserVo
     * @return boolean
     * @description: token验证
     * @date 2021/1/13 11:17
     */
    public boolean checkUserToken(LoginUserVo loginUserVo) {
        if (loginUserVo == null) {
            return false;
        }
        boolean isExist;
        //教师角色
        if (loginUserVo.getRole().equals(LoginRole.TEACHER.getRoleName())) {
            isExist = sysUserService.findUserById(loginUserVo.getUserId());


        } else {
            isExist = ccStudentService.findStudentById(loginUserVo.getUserId());
        }
        if (!isExist) {
            log.error("错误：在数据库里查不到用户信息");
            return false;
        }
        return true;
    }

    public LoginUserVo getLoginUserVo(String token, boolean refresh) {
        LoginUserVo loginUserVo = JwtTokenUtils.getUserInfo(token);
        //不刷新用户信息则从缓存里取
        if (!refresh && USER_META_MAP.get(loginUserVo.getUserId()) != null && !USER_META_MAP.isEmpty()) {
            return USER_META_MAP.get(loginUserVo.getUserId());
        } else {
            //教师角色
            if (loginUserVo.getRole().equals(LoginRole.TEACHER.getRoleName())) {
                //教师分为校内和校外，校外老师不一定在专业认证教师表里，
                CcTeacher teacher = ccteacherService.findById(loginUserVo.getUserId());
                //如果不存在，则继续判断是否在校外教师表里
                if (teacher == null) {
                    EpOutAdviser epOutAdviser = epOutAdviserService.findAdviserInfoBySysId(loginUserVo.getUserId());
                    if (epOutAdviser == null) {
                        log.error("-------校外指导老师信息获取失败--------");
                    }
                    loginUserVo.setMajorId(epOutAdviser.getMajorId());
                    loginUserVo.setAdviserId(epOutAdviser.getId());
                } else {
                    loginUserVo.setMajorId(teacher.getMajorId());
                    loginUserVo.setSchoolId(teacher.getSchoolId());
                }
            } else {
                CcStudent student = ccStudentService.findInfoById(loginUserVo.getUserId());
                if (student == null) {
                    log.error("-------学生信息获取失败--------");
                }
                loginUserVo.setMajorId(student.getMajorId());
                loginUserVo.setSchoolId(student.getSchoolId());
            }
            //把用户信息存入缓存里
            USER_META_MAP.put(loginUserVo.getUserId(), loginUserVo);
        }

        return loginUserVo;
    }
    /*
     * @param majorId:
     * @param grade:
     * @Description:判断登陆时间是否在时间该专业时间控制节点内
     * @Author: Gjm
     * @Date: 2021/2/20 11:48 上午
     * @return: boolean
     **/
    private boolean getMajorTimeControl(Long majorId, Integer grade) {
        Date date = new Date();
        if(majorId == null){
            log.error("err:专业ID未获取到");
        }
        EpTimeControl timeControl = epTimeControlMapper.findByGrade(grade, majorId);
        if (timeControl == null) {
            log.error("该专业："+majorId+"---未设置时间控制--");
            return false;
        }
        Date startTime = timeControl.getStartTime();
        Date endTime = timeControl.getEndTime();
        if (date.after(startTime) && date.before(endTime)) {
            return true;
        }
        return false;
    }

}
