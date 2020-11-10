package com.ljcx.framework.advice;

import com.alibaba.fastjson.JSONObject;
import com.ljcx.framework.annotations.ValidateCustom;
import com.ljcx.common.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
@Order(1)
/**
 * 拦截自定义验证注解
 */
public class ValidateInterceptor {

    // annotation表达式寻找自定义注解的位置
    @Before(value = "@annotation(com.ljcx.framework.annotations.ValidateCustom)")
    public void before(JoinPoint joinPoint) throws Exception {
        // 获取代理方法
        Method proxyMethod = ((MethodSignature)joinPoint.getSignature()).getMethod();
        // 通过代理方法获取目标方法
        Method targetMethod = joinPoint.getTarget().getClass().getMethod(proxyMethod.getName(),proxyMethod.getParameterTypes());
        // 通过目标方法获取自定义注解
        ValidateCustom custom = targetMethod.getAnnotation(ValidateCustom.class);
        //如果传入的只是ID的话，直接抛出异常
        if(custom.value().equals(Integer.class)){
            JSONObject jsonObject = JSONObject.parseObject(joinPoint.getArgs()[0].toString());
            if(jsonObject == null || jsonObject.get("id") == null){
                throw new ValidationException("id不能为空");
            }
        }

        //jdk8新特性filter过滤属性+三元运算符
        List<Object> objects = Arrays.stream(joinPoint.getArgs())
                .filter(Object -> Object.getClass().equals(String.class))
                .collect(Collectors.toList());
        Object obj = objects.size() > 0 ? objects.get(0) : null;
        log.info("传参格式为===============》，{}",obj.toString());
        Object validateCustom = null;
        //将json格式参数转换成对象
        if(obj != null && !custom.value().equals(Integer.class)){
            validateCustom  = JSONObject.parseObject(obj.toString(), custom.value());
            // 参数校验
            ValidationUtils.validate(validateCustom);
        }

    }
}
