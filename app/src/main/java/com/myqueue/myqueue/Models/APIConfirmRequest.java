package com.myqueue.myqueue.Models;

/**
 * Created by 高橋六羽 on 2016/03/22.
 */
public class APIConfirmRequest {
    String userid;
    String confirmationcode;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getConfirmationcode() {
        return confirmationcode;
    }

    public void setConfirmationcode(String confirmationcode) {
        this.confirmationcode = confirmationcode;
    }
}
