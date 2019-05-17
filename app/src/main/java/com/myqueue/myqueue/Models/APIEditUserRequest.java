package com.myqueue.myqueue.Models;

/**
 * Created by 高橋六羽 on 3/28/2016.
 */
public class APIEditUserRequest {
    String userid, name, phone;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
