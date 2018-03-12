package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductGalleryBinding;
import com.appyhome.appyproduct.mvvm.databinding.ActivitySampleBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import javax.inject.Inject;

public class ProductGalleryActivity extends BaseActivity<ActivityProductGalleryBinding, ProductGalleryViewModel> implements ProductGalleryNavigator {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setNavigator(this);
        mBinder.setViewModel(mMainViewModel);
        mMainViewModel.setNavigator(this);

        for(String name : getViewModel().images){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .image(name)
                    .setScaleType(BaseSliderView.ScaleType.CenterInside);
            mBinder.slider.addSlider(textSliderView);
        }
        mBinder.slider.setPresetTransformer(SliderLayout.Transformer.Default);
        mBinder.slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
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
}
