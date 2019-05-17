package com.myqueue.myqueue.Models;

import java.util.List;

/**
 * Created by 高橋六羽 on 3/24/2016.
 */
public class APIExploreResponse extends APIBaseResponse{
    private List<ShopWithUser> shop;

    public List<ShopWithUser> getShop() {
        return shop;
    }

    public void setShop(List<ShopWithUser> shop) {
        this.shop = shop;
    }

}
