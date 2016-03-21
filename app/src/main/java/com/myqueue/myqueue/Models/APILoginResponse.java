package com.myqueue.myqueue.Models;

import java.util.List;

/**
 * Created by 高橋六羽 on 2016/03/11.
 */
public class APILoginResponse extends APIBaseResponse {
    private List<User> user;
    private List<Shop> shop;

    public List<User> getUser() {
        return user;
    }

    public List<Shop> getShop() {
        return shop;
    }

    public void setShop(List<Shop> shop) {
        this.shop = shop;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
