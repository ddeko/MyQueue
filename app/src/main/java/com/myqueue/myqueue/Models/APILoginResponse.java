package com.myqueue.myqueue.Models;

import java.util.List;

/**
 * Created by 高橋六羽 on 2016/03/11.
 */
public class APILoginResponse extends APIBaseResponse {
    private List<User> user;

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
