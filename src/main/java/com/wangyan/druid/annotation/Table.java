package com.wangyan.druid.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 表内容注解
 * <br>@see
 * <br>@author chenghao
 * <br>@version v1.0 2018/7/10 上午11:57
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Table {

    /***
     * 表名称
     * @author chenghao
     * @return java.lang.String
     * @throws
     */
    String tableName();

    /***
     * 主键名称
     * @author chenghao
     * @return java.lang.String
     * @throws
     */
    String primaryKey();


    /***
     * 主键是否为自增类型
     * @author chenghao
     * @return boolean
     * @throws
     */
    boolean autoIncrement() default true;

    /***
     * 自增列名称，如果包含该自增类型列则在insert语句中不会出现该列
     * @author chenghao
     * @return java.lang.String
     * @throws
     */
    String sequenceName() default "";



}
