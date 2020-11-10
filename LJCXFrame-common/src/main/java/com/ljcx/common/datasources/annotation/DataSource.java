package com.ljcx.common.datasources.annotation;

import com.ljcx.common.datasources.DataSourceType;

import java.lang.annotation.*;

/**
 * 多数据源注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    DataSourceType value() default DataSourceType.MASTER;
}
