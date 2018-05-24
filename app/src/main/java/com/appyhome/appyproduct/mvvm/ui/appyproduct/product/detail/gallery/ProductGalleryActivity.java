package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductGalleryBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.VariantPhotosAdapter;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

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
        String modelId = getIntent().getStringExtra("variant_model_id");
        getViewModel().getProductVariantById(modelId);
    }

    @Override
    public void showGallery(ProductVariant variant) {
        mBinder.sliderPhotos.setAdapter(new VariantPhotosAdapter(variant));
        int startPosition = getIntent().getIntExtra("position", 0);
        mBinder.sliderPhotos.setSelectedSlide(startPosition, true);
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
        if (!isFinishing())
            AlertManager.getInstance(this).showLongToast(message);
    }

}
