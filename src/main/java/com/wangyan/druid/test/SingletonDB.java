package com.wangyan.druid.test;

import com.wangyan.druid.exception.OrmException;
import com.wangyan.druid.execute.DbRunner;
import com.wangyan.druid.execute.MySql;
import com.wangyan.druid.tran.Transaction;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * 说明: 调用该类可以操作数据库
 * 例如：orm.MyDB.getInstance().execute(sql,params)
 * @author chenghao
 * @date 2018/2/1 上午11:23
 */
public class SingletonDB{

    private static volatile SingletonDB instance = null;
    //事务管理器
    private Transaction tran = new Transaction();
    //数据库操作接口（可以根据配置文件动态选择数据库类型）
    private DbRunner dbRunner = new MySql();

    private SingletonDB(){}

    public static SingletonDB getInstance() {
        if (instance == null) {
            synchronized (SingletonDB.class) {
                if (instance == null) {
                    instance = new SingletonDB();
                }
            }
        }
        return instance;
    }

    /***
     * 获取连接
     * @author chenghao
     * @return java.sql.Connection
     */
    private Connection getConnection(DataSource druidDataSource){
        try {
            Connection connection = tran.getMasterConnection();//事务连接
            if (connection != null) {
                return connection;
            } else {
                return druidDataSource.getConnection();//普通连接
            }
        }catch (SQLException e) {
            throw new OrmException("获取Master连接错误", e);
        }
    }

    public int execute(DataSource druidDataSource,String sql,Object... values) {
        if (sql == null || sql.isEmpty()) {
            throw new NullPointerException("sql参数不能为空");
        }
        Connection conn = this.getConnection(druidDataSource);
        boolean closeConn = tran.getMasterConnection() == null;
        return dbRunner.execute(conn, closeConn, sql, values);
    }

    /***
     * 开始事务
     * @author chenghao
     */
    public void begin(DataSource druidDataSource) {
        tran.begin(this.getConnection(druidDataSource));
    }

    /***
     * 提交事务
     * @author chenghao
     */
    public void commit() {
        tran.commit();
    }

    /***
     * 回滚事务
     * @author chenghao
     */
    public void rollback() {
        tran.rollback();
    }
}