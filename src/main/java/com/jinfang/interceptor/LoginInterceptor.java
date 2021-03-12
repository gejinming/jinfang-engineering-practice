package com.jinfang.interceptor;
import com.alibaba.fastjson.JSONObject;
import com.jinfang.service.SystemService;
import com.jinfang.util.JwtTokenUtils;
import com.jinfang.vo.LoginUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description: 登录拦截器
 * @author: Gjm
 * @create: 2021-01-11 14:25
 **/
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    protected SystemService systemService;


    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
        //此处为不需要登录的接口放行
        String requestURI = arg0.getRequestURI();
        String method = arg0.getMethod();

        if (method.equals("OPTIONS") || arg0.getRequestURI().contains("/login")
                || arg0.getRequestURI().contains("/register") || arg0.getRequestURI().contains("/error") || arg0.getRequestURI().contains("/static")) {
            return true;
        }else{
            //验证token
            final String headerToken=arg0.getHeader("token");
            arg1.setContentType("text/html;charset=utf-8");
            PrintWriter out = null;
            JSONObject res = new JSONObject();
            //判断请求信息
            if(null==headerToken||headerToken.trim().equals("")){
                log.error("token为空，请携带token访问 ?");
                outMessage(arg1,404,"错误：token为空，请携带token访问 ?",false);
               return false;
            }
            //判断token是否过期
            boolean isExpired = JwtTokenUtils.isTokenExpired(headerToken);
            if (isExpired) {
                log.error("token过期或者错误，请重试");
                outMessage(arg1,405,"错误：token过期或者错误，请重试",false);
                return false;
                //throw new AuthException("Token expired");
            }
            //验证token信息
            LoginUserVo loginUserVo = JwtTokenUtils.getUserInfo(headerToken);
            boolean isExist = systemService.checkUserToken(loginUserVo);
            if (!isExist){
                log.error("错误：在数据库里查不到用户信息");
                outMessage(arg1,405,"错误：在数据库里查不到token包含的用户信息",false);
                return false;
            }
           // outMessage(arg1,200,"访问成功",true);
            return  true;

        }

    }

    private static void outMessage(HttpServletResponse arg1,Integer code,String message,boolean state){
        arg1.setContentType("text/html;charset=utf-8");
        PrintWriter out = null;
        JSONObject res = new JSONObject();
        res.put("code",code);
        res.put("success",state);
        res.put("msg",message);
        try {
            out = arg1.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.append(res.toString());
        out.flush();
        out.close();
    }
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {

    }
}
