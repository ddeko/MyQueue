package com.myqueue.myqueue.Models;

import java.util.List;

/**
 * Created by leowirasanto on 5/8/2016.
 */
public class APIQueueUserResponse extends APIBaseResponse{
    List<QueueUser> queue;

    public List<QueueUser> getQueue() {
        return queue;
    }

    public void setQueue(List<QueueUser> queue) {
        this.queue = queue;
    }
}
