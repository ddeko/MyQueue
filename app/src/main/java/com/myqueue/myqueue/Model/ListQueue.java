package com.myqueue.myqueue.Model;

import android.graphics.Bitmap;

/**
 * Created by 遥か連 on 3/16/2016.
 */
public class ListQueue {

    private Bitmap imgLogoShop;
    private String txtNumberQueue;

    public ListQueue(Bitmap imgLogoShop, String txtNumberQueue){
        super();
        this.setImgLogoShop(imgLogoShop);
        this.setTxtNumberQueue(txtNumberQueue);
    }


    public Bitmap getImgLogoShop() {
        return imgLogoShop;
    }

    public void setImgLogoShop(Bitmap imgLogoShop) {
        this.imgLogoShop = imgLogoShop;
    }

    public String getTxtNumberQueue() {
        return txtNumberQueue;
    }

    public void setTxtNumberQueue(String txtNumberQueue) {
        this.txtNumberQueue = txtNumberQueue;
    }
}
