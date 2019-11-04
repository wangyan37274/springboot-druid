package com.wangyan.druid.exception;

/**
 * orm异常
 * <br>@see
 * <br>@author chenghao
 * <br>@version v1.0 2018/7/11 上午11:05
 */
public class OrmException extends RuntimeException {
    /**
     * 未知异常
     */
    public static final int UNKNOWN_EXCEPTION = 0;
    /**
     * 网络异常
     */

    public static final int NETWORK_EXCEPTION = 1;

    /**
     * 超时异常
     */
    public static final int TIMEOUT_EXCEPTION = 2;

    /**
     * 业务异常
     */
    public static final int BIZ_EXCEPTION = 3;

    /**
     * 禁止异常
     */
    public static final int FORBIDDEN_EXCEPTION = 4;

    /**
     * 序列化异常
     */
    public static final int SERIALIZATION_EXCEPTION = 5;

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 4815426752583648735L;

    //code保持异常的兼容性
    private int code;

    /***
     * 构造函数
     * @author chenghao
     */
    public OrmException() {
        super();
    }

    /***
     * 构造函数
     * @author chenghao
     * @param message 异常内容
     * @param cause 异常对象
     */
    public OrmException(String message, Throwable cause) {
        super(message, cause);
    }

    /***
     * 构造函数
     * @author chenghao
     * @param message 异常内容
     */
    public OrmException(String message) {
        super(message);
    }

    /***
     * 构造函数
     * @author chenghao
     * @param cause 异常对象
     */
    public OrmException(Throwable cause) {
        super(cause);
    }

    /***
     * 构造函数
     * @author chenghao
     * @param code 异常编码
     */
    public OrmException(int code) {
        super();
        this.code = code;
    }

    /***
     * 构造函数
     * @author chenghao
     * @param code 异常编码
     * @param message 异常内容
     * @param cause 异常对象
     */
    public OrmException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    /***
     * 构造函数
     * @author chenghao
     * @param code 异常编码
     * @param message 异常内容
     */
    public OrmException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 构造函数
     * @param code  异常编码
     * @param cause 异常对象
     * @author chenghao
     */
    public OrmException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    /***
     * 获取异常编码
     * @author chenghao
     * @param
     * @return int
     * @throws
     */
    public int getCode() {
        return code;
    }

    /***
     * 设置异常代码
     * @author chenghao
     * @param code 异常编码
     * @return void
     */
    public void setCode(int code) {
        this.code = code;
    }

    /***
     * 是否未知异常
     * @author chenghao
     * @return boolean
     */
    public boolean isUnKnown() {
        return code == UNKNOWN_EXCEPTION;
    }

    /**
     * 是否业务异常
     * @return boolean
     * @author chenghao
     */
    public boolean isBiz() {
        return code == BIZ_EXCEPTION;
    }

    /***
     * 是否禁止异常
     * @author chenghao
     * @return boolean
     */
    public boolean isForbidded() {
        return code == FORBIDDEN_EXCEPTION;
    }

    /***
     * 是否超时异常
     * @author chenghao
     * @return boolean
     */
    public boolean isTimeout() {
        return code == TIMEOUT_EXCEPTION;
    }

    /***
     * 是否网络异常
     * @author chenghao
     * @return boolean
     */
    public boolean isNetwork() {
        return code == NETWORK_EXCEPTION;
    }

    /***
     * 是否序列化异常
     * @author chenghao
     * @return boolean
     */
    public boolean isSerialization() {
        return code == SERIALIZATION_EXCEPTION;
    }
}