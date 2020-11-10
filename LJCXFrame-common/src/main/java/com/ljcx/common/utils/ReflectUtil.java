package com.ljcx.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtil {


    public static Object invoke(Object beanOrClass, String methodName, Object... params) {
        try {
            Class<?> targeClass = beanOrClass.getClass();
            Method[] methods = targeClass.getDeclaredMethods();
            for (Method method : methods) {
                if (methodName.equals(method.getName())) {
                    method.setAccessible(true);
                    return method.invoke(beanOrClass, params);
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取字段值，支持点属性
     *
     * @param bean
     * @param paraName
     * @return
     */
    public static Object getFieldValue(Object bean, String paraName) {
        if (StringUtils.isNull(bean)) {
            return null;
        }
        Field[] fs = bean.getClass().getDeclaredFields();
        for (Field f : fs) {
            try {
                if (paraName.equals(f.getName())) {
                    f.setAccessible(true);
                    return f.get(bean);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    public static final byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }
}
