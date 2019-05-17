package com.myqueue.myqueue.Models;

import java.io.Serializable;

/**
 * Created by 高橋六羽 on 2016/03/22.
 */
@SuppressWarnings("serial")
public class Queue implements Serializable {
    String queue_id;
    String user_id;
    String shop_id;
    String no;

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
}
