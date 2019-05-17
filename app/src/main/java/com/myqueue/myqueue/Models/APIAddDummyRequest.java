package com.myqueue.myqueue.Models;

/**
 * Created by Penopole on 4/26/2016.
 */
public class APIAddDummyRequest {
    private String shop_id;
    private String dummyname;
    private String dummyphone;

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getDummyname() {
        return dummyname;
    }

    public void setDummyname(String dummyname) {
        this.dummyname = dummyname;
    }

    public String getDummyphone() {
        return dummyphone;
    }

    public void setDummyphone(String dummyphone) {
        this.dummyphone = dummyphone;
    }
}
