//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wangyan.druid.tran;
import java.sql.Connection;

public class Transaction {
    private static final ThreadLocal<TranStack> tranThread = new ThreadLocal<TranStack>() {
        protected TranStack initialValue() {
            return new TranStack();
        }
    };

    public Transaction() {
    }

    public void begin(Connection conn) {
        ((TranStack)tranThread.get()).addConn(conn);
    }

    public void commit() {
        ((TranStack)tranThread.get()).commit();
    }

    public void rollback() {
        ((TranStack)tranThread.get()).rollback();
    }

    public Connection getMasterConnection() {
        return ((TranStack)tranThread.get()).getConnection();
    }
}
