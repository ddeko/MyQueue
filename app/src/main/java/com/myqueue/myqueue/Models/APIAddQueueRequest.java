package com.myqueue.myqueue.Models;

/**
 * Created by leowirasanto on 5/8/2016.
 */
public class APIAddQueueRequest {
    private String userid;
    private String shopid;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }
}
