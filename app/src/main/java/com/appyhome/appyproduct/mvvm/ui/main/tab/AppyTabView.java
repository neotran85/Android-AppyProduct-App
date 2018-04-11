package com.appyhome.appyproduct.mvvm.ui.main.tab;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ViewTabLayoutBinding;

import javax.annotation.Nullable;

public class AppyTabView extends LinearLayout {

    private ViewTabLayoutBinding mBinder;

    private View mCurrentView;

    private AppyTabNavigator mNavigator;

    public AppyTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public AppyTabView(Context context) {
        this(context, null);
    }

    public void setUp(Context context, AppyTabNavigator navigator) {
        mBinder = ViewTabLayoutBinding
                .inflate(LayoutInflater.from(context), this, true);
        mBinder.setNavigator(this);
        mNavigator = navigator;
    }

    public View getCurrentTab() {
        return mCurrentView;
    }

    public void clickTab(View view) {
        if (mNavigator != null)
            mNavigator.onClickTab(view);
        if (mCurrentView != null) {
            mCurrentView.findViewWithTag("icon").setSelected(false);
            View titleView = mCurrentView.findViewWithTag("title");
            if (titleView instanceof TextView) {
                TextView t = (TextView) titleView;
                t.setTextColor(ContextCompat.getColor(getContext(), R.color.semi_gray));
            }
        }
        mCurrentView = view;
        view.findViewWithTag("icon").setSelected(false);
        View titleTab = view.findViewWithTag("title");
        if (titleTab instanceof TextView) {
            TextView t = (TextView) titleTab;
            t.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        }

    }
}
