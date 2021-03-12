package com.jinfang.aspect;

import com.alibaba.fastjson.JSONObject;
import com.jinfang.service.SystemService;
import com.jinfang.util.HttpUtils;
import com.jinfang.util.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统切面日志
 */
@Slf4j
@Aspect
@Component
public class LogTraceAspect {

    //统计请求的处理时间
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 在接口的每一个实现类的每一个方法开始之前执行一段代码
     */
    @Before("execution( * com.jinfang.service..*.*(..))")
    public void before(JoinPoint joinPoint){
        startTime.set(System.currentTimeMillis());
        Signature signature = joinPoint.getSignature();
        //从切面织入点处通过反射机制获取织入点处的方法
        JSONObject loggerTrace = new JSONObject();
        // 请求的类
        String className = joinPoint.getTarget().getClass().getName();
        //获取切入点所在的方法
        String methodName = signature.getName();
        loggerTrace.put("method", className + "." + methodName + "()");
        // 请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = JSONObject.toJSONString(args[0]);
            if (params.length() > 200) {
                params = params.substring(0, 200) + "...";
            }
            loggerTrace.put("params", params);
        } catch (Exception e) {
            log.error("访问出现异常==>{}",e.getMessage());
        }

        // 获取request
        HttpServletRequest request = HttpUtils.getHttpServletRequest();
        // 设置IP地址
        loggerTrace.put("IP", IPUtils.getIpAddr(request));
        loggerTrace.put("URL",request.getRequestURI().toString());

        log.info("访问通知：==>{}",loggerTrace.toJSONString());
    }
    /**
     * 在接口的每一个实现类的每一个方法开始之后执行一段代码
     */
   /* @After("execution( * com.jinfang.service..*.*(..))")
    public void after(JoinPoint joinPoint){
        String name = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("后置通知：name:{},args:{}",name,args);
    }*/
    /**
     * 方法正常结束后执行的代码
     * 返回通知是可以访问到方法的返回值的
     */
    @AfterReturning(value="execution( * com.jinfang.service..*.*(..))", returning="result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info("该方法访问结束 ：==>" + methodName + " 耗时==> " + (System.currentTimeMillis() - startTime.get()));
    }

    /**
     * 在方法出现异常时会执行的代码
     * 可以访问到异常对象，可以指定在出现特定异常时在执行通知代码
     */
    @AfterThrowing(value="execution( * com.jinfang.service..*.*(..))", throwing="ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        log.info("执行异常通知:==> " + methodName + " 异常信息==>: " + ex);
    }

/*
    //声明一个切点
    @Pointcut("execution( * com.jinfang.service..*.*(..))")
    public void logPointCut() {
    }

    *//**
     * 环绕通知输出日志
     * @param point
     * @return
     * @throws Throwable
     *//*
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long cost = System.currentTimeMillis() - beginTime;
        // 保存日志
        saveSysLog(point, cost);
        return result;
    }
    *//*
     * 设置输出日志内容
     * @param joinPoint:切点
     * @param cost:耗时
     * @Author: Gjm
     * @Date: 2021/3/4 2:46 下午
     * @return: void
     **//*
    private void saveSysLog(ProceedingJoinPoint joinPoint, long cost) {
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        JSONObject loggerTrace = new JSONObject();

        // 请求的类
        String className = joinPoint.getTarget().getClass().getName();
        //获取切入点所在的方法
        String methodName = signature.getName();
        loggerTrace.put("method", className + "." + methodName + "()");

        // 请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = JSONObject.toJSONString(args[0]);
            if (params.length() > 200) {
                params = params.substring(0, 200) + "...";
            }
            loggerTrace.put("params", params);
        } catch (Exception e) {
            log.error("环绕通知访问出现异常==>{}",e.getMessage());
        }

        // 获取request
        HttpServletRequest request = HttpUtils.getHttpServletRequest();
        // 设置IP地址
        loggerTrace.put("IP", IPUtils.getIpAddr(request));
        loggerTrace.put("URL",request.getRequestURI().toString());
        loggerTrace.put("totalTime", cost);

        log.info("访问通知：==>{}",loggerTrace.toJSONString());
    }*/

}
	