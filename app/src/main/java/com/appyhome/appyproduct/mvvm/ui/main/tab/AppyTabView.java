package com.appyhome.appyproduct.mvvm.ui.main.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

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

    private void setSelected(View view, boolean isSelected) {
        view.findViewWithTag("icon").setSelected(isSelected);
        view.findViewWithTag("icon").refreshDrawableState();
        view.findViewWithTag("title").setSelected(isSelected);
        view.findViewWithTag("title").refreshDrawableState();
    }

    public void clickTab(View view) {
        if (mNavigator != null)
            mNavigator.onClickTab(view);
        if (mCurrentView != null) {
            setSelected(mCurrentView, false);
        }
        mCurrentView = view;
        setSelected(view, true);
    }
}
