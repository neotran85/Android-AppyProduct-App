package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.gallery.imageadapter;

import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductImage;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

public abstract class GalleryAdapter extends SampleAdapter<ProductImage, GalleryItemNavigator> {

    @Override
    public void onClick(View view) {

    }

    @Override
    protected BaseViewHolder getContentHolder(ViewGroup parent) {
        return null;
    }

    @Override
    protected void recycle() {
        if(mItems != null) {
            mItems.clear();
            mItems = null;
        }
    }

    @Override
    protected int getEmptyItemLayout() {
        return 0;
    }

}