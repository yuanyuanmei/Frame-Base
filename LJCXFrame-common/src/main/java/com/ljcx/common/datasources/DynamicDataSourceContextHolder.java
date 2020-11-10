package com.ljcx.common.datasources;

import lombok.extern.slf4j.Slf4j;

/**
 * 创建用来保存数据源名字的线程安全的类。通过ThreadLocal栈封闭方式保证线程安全。
 */
@Slf4j
public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 设置数据源的变量
     */
    public static void setDataSourceType(String dsType) {
        log.info("切换到{}数据源", dsType);
        CONTEXT_HOLDER.set(dsType);
    }

    /**
     * 获得数据源的变量
     *
     * @return
     */
    public static String getDataSourceType() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清空数据源
     */
    public static void clearDataSourceType() {
        CONTEXT_HOLDER.remove();
    }


}
