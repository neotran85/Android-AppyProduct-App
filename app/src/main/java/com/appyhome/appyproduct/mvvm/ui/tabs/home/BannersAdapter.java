package com.appyhome.appyproduct.mvvm.ui.tabs.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.appyhome.appyproduct.mvvm.data.model.api.BannerResponse;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemBannerBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BannersAdapter extends BaseAdapter {

    Context mContext;

    private BannerResponse[] mListItem;

    private HomeNavigator mNavigator;

    public BannersAdapter() {
        mListItem = null;
    }

    public void setUp(Context context, BannerResponse[] listItem, HomeNavigator navigator) {
        this.mContext = context;
        this.mListItem = listItem;
        this.mNavigator = navigator;
        notifyDataSetChanged();
    }

    public int getCount() {
        return mListItem != null ? mListItem.length : 0;
    }

    public Object getItem(int i) {
        return mListItem != null && mListItem.length > 0 ? mListItem[i] : null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View arg1, ViewGroup viewGroup) {
        BannerResponse data = mListItem[position];
        ViewItemBannerBinding binding = ViewItemBannerBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        binding.setNavigator(mNavigator);
        Picasso.with(mContext)
                .load(data.image)
                .config(Bitmap.Config.ARGB_8888)
                .into(binding.ivBanner);
        return binding.getRoot();
    }
}
