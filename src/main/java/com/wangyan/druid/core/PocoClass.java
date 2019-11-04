package com.wangyan.druid.core;

import com.wangyan.druid.annotation.Table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Poco未初始化状态转换sql模型
 * <br>@see
 * <br>@author chenghao
 * <br>@version v1.0 2018/7/10 下午6:07
 */
public class PocoClass {

    //主键名称
    /**
     * 表名称
     */
    private String tableName;
    /**
     * 是否自增
     */
    private boolean autoIncrement = true;
    /**
     * 自增列名称
     */
    private String sequenceName;
    /**
     * 主键名称
     */
    private String primaryKey;
    /**
     * Poco的ClassName
     */
    private String className;
    /**
     *
     */
    private List<String> queryColumns = new ArrayList<>();
    private List<PocoColumn> pocoColumns = new ArrayList<>();


    public PocoClass(Class clazz) {

        initTable(clazz);
        initColumns(clazz);
    }

    //初始化table注解信息
    private <T> void initTable(Class<T> clazz) {

        Table table = clazz.getAnnotation(Table.class);
        if (table != null) {
            this.tableName = table.tableName();
            this.sequenceName = table.sequenceName();
            this.primaryKey = table.primaryKey();
            this.autoIncrement = table.autoIncrement();
        }
        this.className = clazz.getName();
    }

    //初始化column注解信息
    private void initColumns(Class clazz) {

        for (Field field : clazz.getDeclaredFields()) {
            PocoColumn pocoColumn = new PocoColumn(field);
            if (!pocoColumn.isIgnore()) {
                pocoColumns.add(pocoColumn);

                if (pocoColumn.getFieldName().equals(pocoColumn.getColumnName())) {
                    queryColumns.add(pocoColumn.getColumnName());
                } else {
                    //有别名的情况下使用as
                    queryColumns.add(pocoColumn.getColumnName().
                            concat(" as ".concat(pocoColumn.getFieldName())));
                }
            }
        }
    }

    /***
     * 获取count sql语句
     * @author chenghao
     * @return java.lang.String
     * @throws
     */
    public String countSql() {

        return String.format("SELECT COUNT(*) as count FROM %s ",
                this.tableName
        );
    }

    /***
     * 获取分页sql语句,调整分页逻辑，小于1000的page正常取，大于1000则使用高效分页逻辑
     * @author chenghao
     * @param page 页数
     * @param item 每页项数
     * @param addAnd 是否带and关键字
     * @return java.lang.String
     * @throws
     */
    public String pageSql(int page, int item, boolean addAnd) {
        if (item > 100 || item < 1) {
            throw new IllegalArgumentException("item参数不合法 需在1与100之间");
        }
        if (page < 1) {
            throw new IllegalArgumentException("page参数不合法，不能小于1");
        }
        if (page < 10000) {
            return String.format(" LIMIT %s,%s", (page - 1) * item, item);
        }
        //高效分页逻辑
        //含自增主键
        if (this.autoIncrement) {
            if (addAnd) {
                return String.format(
                        "%s %s > (SELECT %s FROM %s LIMIT %s, 1) LIMIT %s",
                        " AND",
                        this.primaryKey,
                        this.primaryKey,
                        this.tableName,
                        (page - 1) * item,
                        item
                );
            } else {
                return String.format(
                        "WHERE %s > (SELECT %s FROM %s LIMIT %s, 1) LIMIT %s",
                        this.primaryKey,
                        this.primaryKey,
                        this.tableName,
                        (page - 1) * item,
                        item
                );
            }
        } else {
            return String.format(" LIMIT %s,%s", (page - 1) * item, item);
        }
    }

    //TODO 待实验
    public String pageSql(String sqlHead, String whereSql, int page, int item) {
        //高效分页第2种写法
        return String.format(
                "%s INNER JOIN(SELECT %s FROM %s LIMIT %s %s) AS t_tmp USING(%S)",
                sqlHead,
                this.primaryKey,
                whereSql.replace(this.tableName, "t_tmp"),
                page,
                item,
                this.primaryKey
        );

//        SELECT <cols> FROM profiles INNER JOIN (
//                SELECT <nid> FROM profiles
//                // 查询条件WHERE t_tmp.sex='M' ORDER BY rating LIMIT 100000, 10
//        )AS t_tmp USING(nid)
    }

    /***
     * 获取查询sql语句，Where带主键
     * @author chenghao
     * @return java.lang.String
     * @throws
     */
    public String querySql() {
        return String.format("SELECT %s FROM %s WHERE %s = ?",
                String.join(",", queryColumns),
                this.tableName,
                this.primaryKey
        );
    }

    /***
     * 获取查询sql语句不带Where条件
     * @author chenghao
     * @return java.lang.String
     * @throws
     */
    public String queryNoneWhereSql() {
        return String.format("SELECT %s FROM %s ",
                String.join(",", queryColumns),
                this.tableName
        );
    }

    /***
     * 获取删除sql语句 带主键
     * @author chenghao
     * @return java.lang.String
     * @throws
     */
    public String deleteSql() {
        return String.format("DELETE FROM %s WHERE %s = ?",
                this.tableName,
                this.primaryKey
        );
    }

    /***
     * 获取批量删除in关键字的 sql语句 带主键
     * @author chenghao
     * @param inString in语句括号内的字符串，要以逗号分割
     * @return java.lang.String
     * @throws
     */
    public String deleteBatchSql(String inString) {
        return String.format("DELETE FROM %s WHERE %s in (%s)",
                this.tableName,
                this.primaryKey,
                inString
        );
    }


    /***
     * 获取Poco的主键属性名称
     * @author chenghao
     * @return java.lang.String
     * @throws
     */
    public String getPrimaryKey() {
        return primaryKey;
    }

    /***
     * 获取Poco的表属性名称
     * @author chenghao
     * @return java.lang.String
     * @throws
     */
    public String getTableName() {
        return tableName;
    }

    /***
     * 是否自增属性
     * @author chenghao
     * @return boolean
     * @throws
     */
    public boolean getAutoIncrement() {
        return autoIncrement;
    }

    /***
     * 获取Poco的自增属性名称
     * @author chenghao
     * @return java.lang.String
     * @throws
     */
    public String getSequenceName() {
        return sequenceName;
    }

    /***
     * 获取Poco的所有属性
     * @author chenghao
     * @return java.util.List <PocoColumn>
     * @throws
     */
    public List<PocoColumn> getPocoColumns() {
        return pocoColumns;
    }

    /***
     * 获取Poco的ClassName
     * @author chenghao
     * @return java.lang.String
     * @throws
     */
    public String getClassName() {
        return className;
    }
}
