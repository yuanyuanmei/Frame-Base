package com.ljcx.quartz.util;

import com.ljcx.common.utils.StringUtils;
import com.ljcx.common.utils.spring.SpringUtil;
import com.ljcx.quartz.beans.SysJob;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * 任务执行工具
 */
public class JobInvokeUtil {

    /**
     * 执行方法
     */
    public static void invokeMethod(SysJob sysJob) throws Exception
    {
        String invokeTarget = sysJob.getInvokeTarget();
        String beanName = getBeanName(invokeTarget);
        String methodName = getMethodName(invokeTarget);
        List<Object[]> methodParams = getMethodParams(invokeTarget);

        if(!isValidClassName(beanName)){
            Object bean = SpringUtil.getBean(beanName);
            invokeMethod(bean, methodName, methodParams);
        }
        else {
            Object bean = Class.forName(beanName).newInstance();
            invokeMethod(bean,methodName,methodParams);
        }
    }

    private static void invokeMethod(Object bean, String methodName, List<Object[]> methodParams) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if(StringUtils.isNotNull(methodParams) && methodParams.size() > 0){
            Method method = bean.getClass().getDeclaredMethod(methodName, getMethodParamsType(methodParams));
            method.invoke(bean, getMethodParamsValue(methodParams));
        }
        else
        {
            Method method = bean.getClass().getDeclaredMethod(methodName);
            method.invoke(bean);
        }
    }

    /**
     * 校验是否为class包名
     * @param invokeTarget
     * @return
     */
    public static boolean isValidClassName(String invokeTarget){
        return StringUtils.countMatches(invokeTarget, ".") > 1;
    }

    /**
     * 获取参数类型
     * @param methodParams
     * @return
     */
    public static Class<?>[] getMethodParamsType(List<Object[]> methodParams)
    {
        Class<?>[] classs = new Class<?>[methodParams.size()];
        int index = 0;
        for(Object[] os : methodParams){
            classs[index] = (Class<?>) os[1];
            index ++;
        }
        return classs;
    }

    /**
     * 获取参数值
     */
    public static Object[] getMethodParamsValue(List<Object[]> methodParams)
    {
        Object[] classs = new Object[methodParams.size()];
        int index = 0;
        for(Object[] os : methodParams){
            classs[index] = (Object) os[1];
            index ++;
        }

        return classs;
    }

    /**
     * 获取bean方法
     * @param invokeTarget
     * @return
     */
    public static String getMethodName(String invokeTarget) {
        String methodName = StringUtils.substringBefore(invokeTarget, "(");
        return StringUtils.substringAfter(methodName,".");
    }

    public static List<Object[]> getMethodParams(String invokeTarget){
        String methodStr = StringUtils.substringBetween(invokeTarget, "(", ")");
        if (StringUtils.isEmpty(methodStr))
        {
            return null;
        }
        String[] methodParams = methodStr.split(",");
        List<Object[]> classs = new LinkedList<>();
        for (int i = 0; i < methodParams.length; i++)
        {
            String str = StringUtils.trimToEmpty(methodParams[i]);
            // String字符串类型，包含'
            if (StringUtils.contains(str, "'"))
            {
                classs.add(new Object[] { StringUtils.replace(str, "'", ""), String.class });
            }
            // boolean布尔类型，等于true或者false
            else if (StringUtils.equals(str, "true") || StringUtils.equalsIgnoreCase(str, "false"))
            {
                classs.add(new Object[] { Boolean.valueOf(str), Boolean.class });
            }
            // long长整形，包含L
            else if (StringUtils.containsIgnoreCase(str, "L"))
            {
                classs.add(new Object[] { Long.valueOf(StringUtils.replaceIgnoreCase(str, "L", "")), Long.class });
            }
            // double浮点类型，包含D
            else if (StringUtils.containsIgnoreCase(str, "D"))
            {
                classs.add(new Object[] { Double.valueOf(StringUtils.replaceIgnoreCase(str, "D", "")), Double.class });
            }
            // 其他类型归类为整形
            else
            {
                classs.add(new Object[] { Integer.valueOf(str), Integer.class });
            }
        }
        return classs;
    }

    /**
     * 获取bean名称
     * @param invokeTarget
     * @return
     */
    public static String getBeanName(String invokeTarget)
    {
        String beanName = StringUtils.substringBefore(invokeTarget, "(");
        return StringUtils.substringBeforeLast(beanName, ".");
    }


}
