package com.myqueue.myqueue.Models;

/**
 * Created by 高橋六羽 on 2016/03/22.
 */
public class Shop {
    String shop_id;
    String user_id;
    String latitude;
    String longitude;
    String address;
    String number;
    String category;
    String isfull;

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIsfull() {
        return isfull;
    }

    public void setIsfull(String isfull) {
        this.isfull = isfull;
    }
}
