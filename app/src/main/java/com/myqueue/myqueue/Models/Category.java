package com.myqueue.myqueue.Models;

import java.io.Serializable;

/**
 * Created by DedeEko on 4/22/2016.
 */
@SuppressWarnings("serial")
public class Category implements Serializable {

    String categoryname;
    private int category_id;

    public String getCategory_name() {
        return categoryname;
    }

    public void setCategory_name(String category_name) {
        this.categoryname = category_name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
