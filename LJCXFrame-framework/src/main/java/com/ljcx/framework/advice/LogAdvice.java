
package com.ljcx.framework.advice;

import com.alibaba.fastjson.JSON;
import com.ljcx.framework.annotations.LogAnnotation;
import com.ljcx.framework.sys.beans.SysLogBean;
import com.ljcx.framework.sys.service.IGenerator;
import com.ljcx.framework.sys.service.SysLogService;
import com.ljcx.common.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * 系统日志，切面处理类
 *
 */
@Aspect
@Component
@Slf4j
public class LogAdvice {
	@Autowired
	private SysLogService sysLogService;

	@Autowired
	private IGenerator generator;

	@Pointcut("@annotation(com.ljcx.framework.annotations.LogAnnotation)")
	public void logPointCut() {

	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;

		//保存日志
		saveSysLog(point, time);

		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		SysLogBean sysLog = new SysLogBean();
		LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
		if(logAnnotation != null){
			//注解上的描述
			sysLog.setOperation(logAnnotation.value());
		}

		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");

		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = JSON.toJSONString(args[0]);
			sysLog.setParams(params);
		}catch (Exception e){

		}

		//获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		//设置IP地址
		sysLog.setIp(IPUtils.getIpAddr(request));
		//用户名
		Object userTemp=SecurityUtils.getSubject().getPrincipal();
		if(Objects.isNull(userTemp)){//未登录
			return;
		}
		Map map = generator.convert(userTemp, Map.class);
		sysLog.setUsername((String) map.get("account"));
		sysLog.setTime(time);
		sysLog.setCreateDate(new Date());
		//保存系统日志
		sysLogService.save(sysLog);
	}
}
