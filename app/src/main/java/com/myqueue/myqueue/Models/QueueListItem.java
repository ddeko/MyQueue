package com.myqueue.myqueue.Models;

import android.graphics.Bitmap;

/**
 * Created by 遥か連 on 3/16/2016.
 */
public class QueueListItem {

    private int imgLogoShop;
    private String txtNumberQueue;

    public QueueListItem(int imgLogoShop, String txtNumberQueue){
        super();
        this.setImgLogoShop(imgLogoShop);
        this.setTxtNumberQueue(txtNumberQueue);
    }


    public int getImgLogoShop() {
        return imgLogoShop;
    }

    public void setImgLogoShop(int imgLogoShop) {
        this.imgLogoShop = imgLogoShop;
    }

    public String getTxtNumberQueue() {
        return txtNumberQueue;
    }

    public void setTxtNumberQueue(String txtNumberQueue) {
        this.txtNumberQueue = txtNumberQueue;
    }
}
