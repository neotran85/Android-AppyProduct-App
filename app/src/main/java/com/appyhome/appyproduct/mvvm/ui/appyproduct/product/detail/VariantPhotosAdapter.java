package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail;

import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class VariantPhotosAdapter extends SliderAdapter {

    private ProductVariant variant;

    public VariantPhotosAdapter(ProductVariant var) {
        variant = var;
    }

    @Override
    public int getItemCount() {
        return variant.images != null ? variant.images.size() : 0;
    }

    private String getVariantURL(int position) {
        if(variant != null) {
            if(variant.images != null && position < variant.images.size()) {
                if(variant.images.get(position) != null) return variant.images.get(position).URL;
            }
        }
        return "";
    }
    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
        if (variant != null && variant.images != null && position < variant.images.size())
            imageSlideViewHolder.bindImageSlide(getVariantURL(position));
    }
}
