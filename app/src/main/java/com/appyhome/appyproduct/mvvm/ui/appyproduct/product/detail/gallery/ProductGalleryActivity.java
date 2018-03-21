package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductGalleryBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import javax.inject.Inject;

public class ProductGalleryActivity extends BaseActivity<ActivityProductGalleryBinding, ProductGalleryViewModel> implements ProductGalleryNavigator, BaseSliderView.OnSliderClickListener {

    @Inject
    public ProductGalleryViewModel mMainViewModel;
    ActivityProductGalleryBinding mBinder;
    @Inject
    int mLayoutId;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ProductGalleryActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setNavigator(this);
        mBinder.setViewModel(mMainViewModel);
        mMainViewModel.setNavigator(this);
        int position = getIntent().getIntExtra("position", 0);
        loadImages(position);
    }

    private void loadImages(int startPosition) {
        for (String name : getViewModel().images) {
            DefaultSliderView vDefaultSliderView = new DefaultSliderView(this);
            // initialize a SliderLayout
            vDefaultSliderView
                    .image(name)
                    .setScaleType(BaseSliderView.ScaleType.CenterInside);
            mBinder.slider.addSlider(vDefaultSliderView);
            vDefaultSliderView.setOnSliderClickListener(this);
        }
        mBinder.slider.setPresetTransformer(SliderLayout.Transformer.Default);
        mBinder.slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mBinder.slider.stopAutoCycle();
        mBinder.slider.setCurrentPosition(startPosition);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public ProductGalleryViewModel getViewModel() {
        return mMainViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void showAlert(String message) {
        AlertManager.getInstance(this).showLongToast(message);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
