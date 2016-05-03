package com.myqueue.myqueue.Models;

/**
 * Created by Penopole on 4/26/2016.
 */
public class APIMaxQueueResponse extends APIBaseResponse {
    private String currentnumber;

    public String getCurrentnumber() {
        return currentnumber;
    }

    public void setCurrentnumber(String currentnumber) {
        this.currentnumber = currentnumber;
    }
}
