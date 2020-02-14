package com.menhu.enums;

/**
 * 数据状态枚举
 *
 * @author guoshiqi
 * @create 2016-12-10-11:51
 */
public enum Status {
    正常数据(0),
    删除数据(1);
    private int status;

    Status(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}