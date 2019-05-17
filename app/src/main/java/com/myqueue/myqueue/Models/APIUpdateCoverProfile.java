package com.myqueue.myqueue.Models;

/**
 * Created by leowirasanto on 5/6/2016.
 */
public class APIUpdateCoverProfile {
    private String userid;
    private String coverProfile;
    private String urlCoverProfile;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCoverProfile() {
        return coverProfile;
    }

    public void setCoverProfile(String coverProfile) {
        this.coverProfile = coverProfile;
    }

    public String getUrlCoverProfile() {
        return urlCoverProfile;
    }

    public void setUrlCoverProfile(String urlCoverProfile) {
        this.urlCoverProfile = urlCoverProfile;
    }
}
