package com.myqueue.myqueue.Models;

/**
 * Created by 高橋六羽 on 2016/03/11.
 */
public class APILoginRequest {
    String email;
    String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
