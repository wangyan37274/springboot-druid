package com.wangyan.druid.execute;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/***
 * 操作Db的接口
 * <br>@see
 * <br>@author chenghao
 * <br>@version v1.0 2018/7/11 上午10:00
 */
public interface DbRunner {

    /***
     * 执行sql语句
     * @author chenghao
     * @param conn 连接对象
     * @param closeConn 是否关闭连接
     * @param sql sql语句
     * @param param 参数
     * @return int 受影响行数
     */
    int execute(Connection conn, boolean closeConn, String sql, Object... param);

    /***
     * 获取实体并转换成map
     * @author chenghao
     * @param conn 连接对象
     * @param closeConn 是否关闭连接
     * @param sql sql语句
     * @param param 参数
     * @return List <Map<String,Object>>
     */
    List<Map<String, Object>> queryMap(Connection conn, boolean closeConn, String sql, Object... param);

    /***
     * 获取实体列表
     * @author chenghao
     * @param cls 泛型对象
     * @param conn 连接对象
     * @param closeConn 是否关闭连接
     * @param sql sql语句
     * @param param 参数
     * @return java.util.List <T>
     */
    <T> List<T> queryList(Class<T> cls, Connection conn, boolean closeConn, String sql, Object... param);


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
    <T> T queryOne(Class<T> cls, Connection conn, boolean closeConn, String sql, Object... param);

    /***
     * 执行查询返回首行首列
     * @author chenghao
     * @param conn 连接对象
     * @param closeConn 是否关闭连接
     * @param sql sql语句
     * @param param 参数
     * @return java.lang.Object 首行首列
     */
    Object queryScalar(Connection conn, boolean closeConn, String sql, Object... param);

    /***
     * 执行sql语句返回主键
     * @author chenghao
     * @param conn 连接对象
     * @param closeConn 是否关闭连接
     * @param sql sql语句
     * @param param 参数
     * @return java.lang.Object 主键
     */
    Object insertReturnPk(Connection conn, boolean closeConn, String sql, Object... param);

    /***
     * 执行操作返回受影响行数
     * @author chenghao
     * @param conn 连接对象
     * @param closeConn 是否关闭连接
     * @param sql sql语句
     * @param param 参数
     * @return int 受影响行数
     */
    int update(Connection conn, boolean closeConn, String sql, Object... param);


    /***
     * 批量写入数据
     * @author chenghao
     * @param conn 连接对象
     * @param closeConn 是否关闭连接
     * @param sql sql语句
     * @param param 2维数组 事例:[0][0] [0][1]
     * @return int[]
     */
    int[] batch(Connection conn, boolean closeConn, String sql, Object[][] param);


}
