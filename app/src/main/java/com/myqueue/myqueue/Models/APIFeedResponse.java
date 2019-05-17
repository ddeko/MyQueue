package com.myqueue.myqueue.Models;

import java.util.List;

/**
 * Created by 高橋六羽 on 3/24/2016.
 */
public class APIFeedResponse extends APIBaseResponse{
    List<Feed> feed;

    public List<Feed> getFeed() {
        return feed;
    }

    public void setFeed(List<Feed> feed) {
        this.feed = feed;
    }

}
