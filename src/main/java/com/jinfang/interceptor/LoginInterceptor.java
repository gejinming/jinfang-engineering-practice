package com.jinfang.interceptor;
import com.jinfang.service.SystemService;
import com.jinfang.util.JwtTokenUtils;
import com.jinfang.vo.LoginUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {

    }
    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
        //此处为不需要登录的接口放行
        if (arg0.getRequestURI().contains("/login") || arg0.getRequestURI().contains("/register") || arg0.getRequestURI().contains("/error") || arg0.getRequestURI().contains("/static")) {
            return true;
        }else{
            //验证token
            final String headerToken=arg0.getHeader("token");
            arg1.setContentType("text/html;charset=utf-8");
            ServletOutputStream resultWriter = arg1.getOutputStream();
            //判断请求信息
            if(null==headerToken||headerToken.trim().equals("")){
                log.error("token is null ,please check ?");
                resultWriter.write("你没有token,需要登录".getBytes());
                resultWriter.flush();
                resultWriter.close();
                return false;
            }
            //判断token是否过期
            boolean isExpired = JwtTokenUtils.isTokenExpired(headerToken);
            if (isExpired) {
                log.error("token already expired or error ");
                resultWriter.write("token已经过期或者错误".getBytes());
                resultWriter.flush();
                resultWriter.close();
                return false;
                //throw new AuthException("Token expired");
            }
            //验证token信息
            LoginUserVo loginUserVo = JwtTokenUtils.getUserInfo(headerToken);
            boolean isExist = systemService.checkUserToken(loginUserVo);
            if (!isExist){
                log.error("错误：在数据库里查不到用户信息");
                return false;
            }

        }
        return  true;

    }
}
