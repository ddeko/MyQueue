package com.myqueue.myqueue.Models;

/**
 * Created by 高橋六羽 on 2016/03/22.
 */
public class NewsFeedListItem {
    private int shopPic;
    private String shopName;
    private int newsPic;
    private String description;

    public NewsFeedListItem(int shopPic, String shopName, int newsPic, String description) {
        this.shopPic = shopPic;
        this.shopName = shopName;
        this.newsPic = newsPic;
        this.description = description;
    }

    public int getShopPic() {
        return shopPic;
    }

    public void setShopPic(int shopPic) {
        this.shopPic = shopPic;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getNewsPic() {
        return newsPic;
    }

    public void setNewsPic(int newsPic) {
        this.newsPic = newsPic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
