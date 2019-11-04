package com.wangyan.druid.tran;

import com.wangyan.druid.exception.OrmException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Stack;

/**
 * 事务栈队列
 * <br>@see
 * <br>@author chenghao
 * <br>@version v1.0 2018/7/10 下午5:39
 */
class TranStack {

    private Stack<Connection> connections = new Stack<>();

    /***
     * 从栈中获取连接对象
     * @author chenghao
     * @return java.sql.Connection
     * @throws
     */
    public Connection getConnection() {
        if (connections.isEmpty()) {
            return null;
        }
        return connections.peek();
    }

    /***
     * 将连接加入到栈队列中
     * @author chenghao
     * @param connection 连接对象
     * @return void
     * @throws
     */
    public void addConn(Connection connection) {
        try {
            connection.setAutoCommit(false);
            connections.push(connection);
            //System.out.println(con.toString() + "--Conection---");
        } catch (SQLException e) {
            throw new OrmException("addConn错误", e);
        }
    }


    /***
     * 提交
     * @author chenghao
     * @return void
     * @throws
     */
    public void commit() {
        Connection conn = connections.peek();
        try {
            if (conn != null && !conn.isClosed()) {
                //System.out.println(connections.peek().toString() + "--Commit---");
                conn.commit();
                connections.pop();
            }
        } catch (SQLException e) {
            throw new OrmException("commit错误", e);
        } finally {
            dispose(conn);
        }

    }


    /***
     * 回滚
     * @author chenghao
     * @return void
     * @throws
     */
    public void rollback() {
        Connection conn = connections.peek();
        try {

            if (conn != null && !conn.isClosed()) {
                //System.out.println(connections.peek().toString() + "--Roolback---");
                conn.rollback();
                //connect.close();
                connections.pop();
            }
        } catch (SQLException e) {
            throw new OrmException("rollback错误", e);
        } finally {
            dispose(conn);
        }
    }


    /***
     * 关闭连接
     * @author chenghao
     * @param conn 连接对象
     * @return void
     * @throws
     */
    private void dispose(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.setAutoCommit(true);
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

