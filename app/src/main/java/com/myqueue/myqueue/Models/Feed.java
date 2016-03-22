package com.myqueue.myqueue.Models;

import java.io.Serializable;

/**
 * Created by 高橋六羽 on 2016/03/22.
 */
@SuppressWarnings("serial")
public class Feed implements Serializable {

    String feed_id;
    String user_id;
    String description;
    String feedpicture;

    public String getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(String feed_id) {
        this.feed_id = feed_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFeedpicture() {
        return feedpicture;
    }

    public void setFeedpicture(String feedpicture) {
        this.feedpicture = feedpicture;
    }
}
