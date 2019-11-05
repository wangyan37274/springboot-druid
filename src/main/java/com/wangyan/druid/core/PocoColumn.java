package com.wangyan.druid.core;

import com.wangyan.druid.annotation.Column;
import org.assertj.core.util.Strings;

import java.lang.reflect.Field;

/**
 * 实体的Column注解属性
 * <br>@see 
 * <br>@author chenghao 
 * <br>@version v1.0 2018/7/10 下午6:07
 */
public class PocoColumn {

    public String getColumnName() {
        return columnName;
    }

    @Deprecated
    public String getInsertTemplate() {
        return insertTemplate;
    }

    @Deprecated
    public String getUpdateTemplate() {
        return updateTemplate;
    }

    @Deprecated
    public boolean isForceToUtc() {

        return forceToUtc;
    }


    private String columnName = "";
    private boolean forceToUtc = false;
    private String insertTemplate = "";
    private String updateTemplate = "";
    private boolean isIgnore = false;
    private boolean isReadOnly = false;

    private String fieldName = "";

    public boolean isIgnore() {
        return isIgnore;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public String getFieldName() {
        return fieldName;
    }

    public PocoColumn(Field field) {

        Column column = field.getAnnotation(Column.class);
        if (column != null) {
            this.columnName = column.columnName();
            this.insertTemplate = column.insertTemplate();
            this.updateTemplate = column.updateTemplate();
            this.forceToUtc = column.forceToUtc();
            this.isIgnore = column.isIgnore();
            this.isReadOnly = column.isReadOnly();
        }
        this.fieldName = field.getName();
        if (Strings.isNullOrEmpty(this.columnName)) {
            this.columnName = this.fieldName;
        }
    }

}

