package com.appyhome.appyproduct.mvvm.ui.tabs.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.appyhome.appyproduct.mvvm.data.model.api.BannerResponse;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemBannerBinding;
import com.appyhome.appyproduct.mvvm.ui.common.component.LinearListView;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

public class BannerListView extends LinearListView<BannerResponse> {

    public BannerListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public View getItemView(int position) {
        BannerResponse data = getItem(position);
        ViewItemBannerBinding binding = ViewItemBannerBinding.inflate(LayoutInflater.from(getContext()), this, false);
        Picasso.with(getContext())
                .load(data.image)
                .config(Bitmap.Config.ARGB_8888)
                .into(binding.ivBanner);
        return binding.getRoot();
    }

}
