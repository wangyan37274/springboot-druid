package com.wangyan.druid.execute;

import com.wangyan.druid.exception.OrmException;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/***
 * 操作Mysql数据库
 * <br>@see Mysql操作封装了DbUtils包
 * <br>Dbutils使用结果集的方法有query、insert、insertBatch三个。这些方法都在QueryRunner类中，
 * <br>需要注意的是insert和update方法都能执行 “insert”开头的sql语句，但是返回值有区别。
 * <br>insert 执行后返回的是表中的插入行生成的主键值，update 返回的是受语句影响的行数。
 * <br>所以，如果目标表中有主键且需要返回插入行的主键值就用 insert 方法，
 * <br>如果表没有主键或者不需要返回主键值可使用 update 方法。
 * <br>@author chenghao
 * <br>@version v1.0 2018/7/10 下午2:16
 */
public class MySql implements DbRunner {

    private QueryRunner queryRunner = new QueryRunner();

    /***
     * 执行sql语句
     * @author chenghao
     * @param conn 连接对象
     * @param closeConn 是否关闭连接
     * @param sql sql语句
     * @param param 参数
     * @return int 受影响行数
     */
    public int execute(Connection conn, boolean closeConn, String sql, Object... param) {
        //QueryRunner qr = new QueryRunner();
        try {
            int result = queryRunner.update(conn, sql, param);
            if (closeConn) {
                DbUtils.closeQuietly(conn);
            }
            return result;
        } catch (SQLException e) {
            DbUtils.closeQuietly(conn);
            throw new OrmException(String.format("*** MySql.execute错误 | message : %s | sql : %s", e.getMessage(), sql), e);
        }
    }


    /***
     * 获取实体并转换成map
     * @author chenghao
     * @param conn 连接对象
     * @param closeConn 是否关闭连接
     * @param sql sql语句
     * @param param 参数
     * @return List < Map< String,Object>>
     */
    public List<Map<String, Object>> queryMap(Connection conn, boolean closeConn, String sql, Object... param) {
        //QueryRunner qr = new QueryRunner();

        try {
            List<Map<String, Object>> result = queryRunner.query(conn, sql, new MapListHandler(), param);
            if (closeConn) {
                DbUtils.closeQuietly(conn);
            }
            return result;
        } catch (SQLException e) {
            DbUtils.closeQuietly(conn);
            throw new OrmException(String.format("*** MySql.queryMap错误 | message : %s | sql : %s", e.getMessage(), sql), e);
        }
    }

    /***
     * 获取实体列表
     * @author chenghao
     * @param cls 泛型对象
     * @param conn 连接对象
     * @param closeConn 是否关闭连接
     * @param sql sql语句
     * @param param 参数
     * @return java.util.List < T>
     */
    public <T> List<T> queryList(Class<T> cls, Connection conn, boolean closeConn, String sql, Object... param) {
        //QueryRunner qr = new QueryRunner();
        try {
            ResultSetHandler<List<T>> h = new BeanListHandler<T>(cls);
            List<T> result = queryRunner.query(conn, sql, h, param);
            if (closeConn) {
                DbUtils.closeQuietly(conn);
            }
            return result;
        } catch (SQLException e) {
            DbUtils.closeQuietly(conn);
            throw new OrmException(String.format("*** MySql.queryList错误 | message : %s | sql : %s", e.getMessage(), sql), e);
        }
    }

    /***
     * 获取单个实体
     * @author chenghao
     * @param cls 泛型对象
     * @param conn 连接对象
     * @param closeConn 是否关闭连接
     * @param sql sql语句
     * @param param 参数
     * @return T
     */
    public <T> T queryOne(Class<T> cls, Connection conn, boolean closeConn, String sql, Object... param) {
        //QueryRunner qr = new QueryRunner();
        try {
            ResultSetHandler<T> h = new BeanHandler<T>(cls);
            T result = queryRunner.query(conn, sql, h, param);
            if (closeConn) {
                DbUtils.closeQuietly(conn);
            }
            return result;
        } catch (SQLException e) {
            DbUtils.closeQuietly(conn);
            throw new OrmException(String.format("*** MySql.queryOne错误 | message : %s | sql : %s", e.getMessage(), sql), e);
        }
    }

    /***
     * 执行查询返回首行首列
     * @author chenghao
     * @param conn 连接对象
     * @param closeConn 是否关闭连接
     * @param sql sql语句
     * @param param 参数
     * @return java.lang.Object 首行首列
     */
    public Object queryScalar(Connection conn, boolean closeConn, String sql, Object... param) {
        //QueryRunner qr = new QueryRunner();
        try {
            ResultSetHandler<Object> h = new ScalarHandler<>();
            Object result = queryRunner.query(conn, sql, h, param);

            if (closeConn) {
                DbUtils.closeQuietly(conn);
            }
            return result;
        } catch (SQLException e) {
            DbUtils.closeQuietly(conn);
            throw new OrmException(String.format("*** MySql.queryScalar错误 | message : %s | sql : %s", e.getMessage(), sql), e);
        }
    }

    /***
     * 执行sql语句返回主键
     * @author chenghao
     * @param conn 连接对象
     * @param closeConn 是否关闭连接
     * @param sql sql语句
     * @param param 参数
     * @return java.lang.Object 主键
     */
    public Object insertReturnPk(Connection conn, boolean closeConn, String sql, Object... param) {
        //QueryRunner qr = new QueryRunner();
        try {
            ResultSetHandler<Object> h = new ScalarHandler<>();

            Object result = queryRunner.insert(conn, sql, h, param);
            if (closeConn) {
                DbUtils.closeQuietly(conn);
            }
            return result;
        } catch (SQLException e) {
            DbUtils.closeQuietly(conn);
            throw new OrmException(String.format("*** MySql.insertReturnPk错误 | message : %s | sql : %s", e.getMessage(), sql), e);
        }
    }

    /***
     * 执行操作返回受影响行数
     * @author chenghao
     * @param conn 连接对象
     * @param closeConn 是否关闭连接
     * @param sql sql语句
     * @param param 参数
     * @return int 受影响行数
     */
    public int update(Connection conn, boolean closeConn, String sql, Object... param) {
        //QueryRunner qr = new QueryRunner();
        try {
            int result = queryRunner.update(conn, sql, param);

            if (closeConn) {
                DbUtils.closeQuietly(conn);
            }
            return result;
        } catch (SQLException e) {
            DbUtils.closeQuietly(conn);
            throw new OrmException(String.format("*** MySql.update错误 | message : %s | sql : %s", e.getMessage(), sql), e);
        }
    }


    /***
     * 批量写入数据
     * @author chenghao
     * @param conn 连接对象
     * @param closeConn 是否关闭连接
     * @param sql sql语句
     * @param param 2维数组 事例:[0][0] [0][1]
     * @return int[]
     */
    public int[] batch(Connection conn, boolean closeConn, String sql, Object[][] param) {
        //QueryRunner qr = new QueryRunner();
        try {
            int[] result = queryRunner.batch(conn, sql, param);
            if (closeConn) {
                DbUtils.closeQuietly(conn);
            }
            return result;
        } catch (SQLException e) {
            DbUtils.closeQuietly(conn);
            throw new OrmException(String.format("*** MySql.batch错误 | message : %s | sql : %s", e.getMessage(), sql), e);
        }
    }

}
