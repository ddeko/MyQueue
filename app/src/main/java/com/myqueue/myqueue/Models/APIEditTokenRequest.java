package com.myqueue.myqueue.Models;

/**
 * Created by 高橋六羽 on 4/14/2016.
 */
public class APIEditTokenRequest {
    String userid, token;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
