package com.wangyan.druid.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 列字段注解
 * <br>@see
 * <br>@author chenghao
 * <br>@version v1.0 2018/7/10 上午11:53
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Column {

    /***
     * 列名称
     * @author chenghao
     * @return java.lang.String
     * @throws
     */
    String columnName() default "";

    /***
     * 是否格式成UTC时间
     * @author chenghao
     * @return boolean
     * @throws
     */
    @Deprecated
    boolean forceToUtc() default false;


    /***
     * insert模版
     * @author chenghao
     * @return java.lang.String
     * @throws
     */
    @Deprecated
    String insertTemplate() default "";

    /***
     * update模版
     * @author chenghao
     * @return java.lang.String
     * @throws
     */
    @Deprecated
    String updateTemplate() default "";


    /***
     * 是否忽略
     * @author chenghao
     * @return boolean
     * @throws
     */
    boolean isIgnore() default false;


    /***
     * 是否只读
     * @author chenghao
     * @return boolean
     * @throws
     */
    boolean isReadOnly() default false;
}

