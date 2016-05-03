package com.myqueue.myqueue.Models;

import java.io.Serializable;

/**
 * Created by DedeEko on 4/22/2016.
 */
@SuppressWarnings("serial")
public class Category implements Serializable {

    String category_id;
    String category_name;


    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
