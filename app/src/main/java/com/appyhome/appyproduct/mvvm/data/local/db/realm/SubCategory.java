package com.appyhome.appyproduct.mvvm.data.local.db.realm;

import io.realm.annotations.PrimaryKey;

public class SubCategory extends Category {
    @PrimaryKey
    private String id;
    protected long idCategory;

    public long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(long idC) {
        idCategory = idC;
    }
}
