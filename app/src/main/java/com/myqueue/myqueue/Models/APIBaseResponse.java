package com.myqueue.myqueue.Models;

import java.io.Serializable;

/**
 * Created by 高橋六羽 on 2016/03/11.
 */
public class APIBaseResponse {
    private int status;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
