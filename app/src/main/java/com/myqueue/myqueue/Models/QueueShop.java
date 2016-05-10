package com.myqueue.myqueue.Models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leowirasanto on 5/9/2016.
 */
public class QueueShop implements Serializable {
    String queue_id;
    String user_id;
    String shop_id;
    String no;
    String dummyname;
    String dummyphone;
    List<User> user;

    public String getQueue_id() {
        return queue_id;
    }

    public void setQueue_id(String queue_id) {
        this.queue_id = queue_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
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

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
