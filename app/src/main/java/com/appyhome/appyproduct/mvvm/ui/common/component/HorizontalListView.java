package com.appyhome.appyproduct.mvvm.ui.common.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

import java.util.ArrayList;

import javax.annotation.Nullable;

public abstract class HorizontalListView extends LinearLayout {

    protected SampleAdapter mAdapter;

    public HorizontalListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public void setAdapter(SampleAdapter adapter) {
        mAdapter = adapter;
        notifyAdapter();
    }

    public int getItemCount() {
        return mAdapter.getItemCount();
    }

    public void notifyAdapter() {
        if (mAdapter != null && getItemCount() > 0) {
            for (int i = 0; i < mAdapter.getItemCount(); i++) {
                View view = getItemView(i);
                this.addView(view);
            }
        }
    }

    public abstract View getItemView(int position);

}
