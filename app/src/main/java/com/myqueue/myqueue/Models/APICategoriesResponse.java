package com.myqueue.myqueue.Models;

import java.util.List;

/**
 * Created by DedeEko on 4/22/2016.
 */
public class APICategoriesResponse extends APIBaseResponse {
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}

