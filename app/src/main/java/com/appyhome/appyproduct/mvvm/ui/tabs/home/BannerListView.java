package com.appyhome.appyproduct.mvvm.ui.tabs.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.appyhome.appyproduct.mvvm.data.model.api.BannerResponse;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemBannerBinding;
import com.appyhome.appyproduct.mvvm.ui.common.component.LinearListView;
import com.appyhome.appyproduct.mvvm.utils.config.GlideApp;

import javax.annotation.Nullable;

public class BannerListView extends LinearListView<BannerResponse> {

    public BannerListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public View getItemView(int position) {
        BannerResponse data = getItem(position);
        ViewItemBannerBinding binding = ViewItemBannerBinding.inflate(LayoutInflater.from(getContext()), this, false);
        GlideApp.with(getContext())
                .load(data.image)
                .into(binding.ivBanner);
        return binding.getRoot();
    }

}
