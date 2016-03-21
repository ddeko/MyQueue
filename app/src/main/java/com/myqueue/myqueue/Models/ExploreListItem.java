package com.myqueue.myqueue.Models;

import android.graphics.Bitmap;

/**
 * Created by leowirasanto on 3/18/2016.
 */
public class ExploreListItem {
    private String shopName;
    private String shopAddress;
    private int picture;


    public ExploreListItem(String shopName, String shopAddress, int picture) {
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.picture = picture;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }
}
