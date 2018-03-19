package com.appyhome.appyproduct.mvvm.ui.tabs.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.appyhome.appyproduct.mvvm.data.model.api.BannerResponse;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemBannerBinding;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

public class BannerListView extends LinearLayout {

    private BannerResponse[] mListItem;

    public BannerListView(Context context) {
        super(context);
    }

    public void setAdapter(BannerResponse[] listItems) {
        mListItem = listItems;
        notifyAdapter();
    }

    public void notifyAdapter() {
        if(mListItem != null && mListItem.length > 0) {
            for (int i = 0; i < mListItem.length; i++) {
                View view = getView(i);
                this.addView(view);
            }
        }
    }

    public BannerListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }
    public BannerResponse getItem(int i) {
        return mListItem != null && mListItem.length > 0 ? mListItem[i] : null;
    }
    public View getView(int position) {
        BannerResponse data = getItem(position);
        ViewItemBannerBinding binding = ViewItemBannerBinding.inflate(LayoutInflater.from(getContext()), this, false);
        Picasso.with(getContext())
                .load(data.image)
                .config(Bitmap.Config.ARGB_8888)
                .into(binding.ivBanner);
        return binding.getRoot();
    }

}
