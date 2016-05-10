package com.myqueue.myqueue.Models;

import java.util.List;

/**
 * Created by leowirasanto on 5/9/2016.
 */
public class APIQueueShopResponse extends APIBaseResponse{
    List<QueueShop> queue;

    public List<QueueShop> getQueue() {
        return queue;
    }

    public void setQueue(List<QueueShop> queue) {
        this.queue = queue;
    }
}
