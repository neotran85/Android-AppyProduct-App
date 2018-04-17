package com.appyhome.appyproduct.mvvm.ui.common.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import ss.com.bannerslider.Slider;
import ss.com.bannerslider.adapters.SliderAdapter;

public class AppySliderPhotos extends Slider {

    public AppySliderPhotos(@NonNull Context context) {
        super(context);
    }

    public AppySliderPhotos(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AppySliderPhotos(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setAdapter(SliderAdapter adapter) {
        super.setAdapter(adapter);
        int count = adapter != null ? adapter.getItemCount() : 0;
        if (count <= 1) {
            if (indexOfChild(slideIndicatorsGroup) >= 0) {
                removeView(slideIndicatorsGroup);
            }
        }
    }
}
