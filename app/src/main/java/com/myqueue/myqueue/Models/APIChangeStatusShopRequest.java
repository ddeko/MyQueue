package com.myqueue.myqueue.Models;

/**
 * Created by Penopole on 4/25/2016.
 */
public class APIChangeStatusShopRequest {
    private String userid;
    private int isfull;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    public int getIsfull() {
        return isfull;
    }

    public void setIsfull(int isfull) {
        this.isfull = isfull;
    }
}
