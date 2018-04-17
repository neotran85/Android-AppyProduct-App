package com.appyhome.appyproduct.mvvm.utils.helper;


import android.content.Context;
import android.widget.ImageView;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.utils.config.GlideApp;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import ss.com.bannerslider.ImageLoadingService;

public class GlideImageLoadingService implements ImageLoadingService {
    private Context mContext;

    public GlideImageLoadingService(Context context) {
        mContext = context;
    }

    @Override
    public void loadImage(String url, ImageView imageView) {
        GlideApp.with(mContext)
                .load(url)
                .placeholder(R.mipmap.no_image_large)
                .error(R.mipmap.no_image_large)
                .fitCenter()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    @Override
    public void loadImage(int resource, ImageView imageView) {
        GlideApp.with(mContext)
                .load(resource)
                .placeholder(R.mipmap.no_image_large)
                .error(R.mipmap.no_image_large)
                .fitCenter()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    @Override
    public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
        GlideApp.with(mContext)
                .load(url)
                .placeholder(placeHolder)
                .error(errorDrawable)
                .fitCenter()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }
}
