package com.myqueue.myqueue.Models;

/**
 * Created by leowirasanto on 4/26/2016.
 */
public class APIUpdatePhotoProfile {
    private String userid;
    private String photoProfile;
    private String urlPhotoProfile;



    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhotoProfile() {
        return photoProfile;
    }

    public void setPhotoProfile(String photoProfile) {
        this.photoProfile = photoProfile;
    }

    public String getUrlPhotoProfile() {
        return urlPhotoProfile;
    }

    public void setUrlPhotoProfile(String urlPhotoProfile) {
        this.urlPhotoProfile = urlPhotoProfile;
    }
}
