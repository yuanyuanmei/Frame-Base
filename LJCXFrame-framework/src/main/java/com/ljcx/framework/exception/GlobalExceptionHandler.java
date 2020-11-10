package com.ljcx.framework.exception;

import com.ljcx.common.exception.BusinessException;
import com.ljcx.common.utils.ResponseInfo;
import com.ljcx.common.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

/**
 * 全局异常处理器
 * 
 * @author ruoyi
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler
{

    /**
     * 参数校验异常
     */
    @ExceptionHandler(ValidationException.class)
    public Object ValidationException(ValidationException e)
    {
        log.error(e.getMessage(), e);
        return new ResponseInfo().failed("参数异常：" + e.getMessage());
    }

    /**
     * 权限校验失败 如果请求为ajax返回json，普通请求跳转页面
     */
    @ExceptionHandler(AuthorizationException.class)
    public Object handleAuthorizationException(AuthorizationException e)
    {
        log.error(e.getMessage(), e);
        return new ResponseInfo().failed("权限不足 " + e.getMessage());
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
    public ResponseInfo handleException(HttpRequestMethodNotSupportedException e)
    {
        log.error(e.getMessage(), e);
        return new ResponseInfo().failed("不支持' " + e.getMethod() + "'请求");
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseInfo notFount(RuntimeException e)
    {
        log.error("运行时异常:", e);
        return new ResponseInfo().failed("运行时异常:" + e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseInfo handleException(Exception e)
    {
        log.error(e.getMessage(), e);
        return new ResponseInfo().failed("服务器错误，请联系管理员");
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Object businessException(HttpServletRequest request, BusinessException e)
    {
        log.error(e.getMessage(), e);
        if (ServletUtils.isAjaxRequest(request))
        {
            return new ResponseInfo().failed(e.getMessage());
        }
        else
        {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("errorMessage", e.getMessage());
            modelAndView.setViewName("error/business");
            return modelAndView;
        }
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public ResponseInfo validatedBindException(BindException e)
    {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return new ResponseInfo().failed(message);
    }



}
