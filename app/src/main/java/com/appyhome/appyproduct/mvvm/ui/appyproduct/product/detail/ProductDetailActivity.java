package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductDetailBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.ProductCartListActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.gallery.ProductGalleryActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.common.component.cart.SearchToolbarViewHolder;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import javax.inject.Inject;

public class ProductDetailActivity extends BaseActivity<ActivityProductDetailBinding, ProductDetailViewModel> implements ProductDetailNavigator, View.OnClickListener {

    @Inject
    public ProductDetailViewModel mMainViewModel;
    ActivityProductDetailBinding mBinder;
    @Inject
    int mLayoutId;

    private SearchToolbarViewHolder mSearchToolbarViewHolder;

    private int productId = 0;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
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
        mSearchToolbarViewHolder = new SearchToolbarViewHolder(this, mBinder.toolbar);
        productId = getIntent().getIntExtra("product_id", 0);
        loadImages();
    }

    @Override
    public void onClick(View view) {
        startActivity(ProductGalleryActivity.getStartIntent(this));
    }

    @Override
    public void gotoCart() {
        mMainViewModel.addProductToCart(productId, true);
    }

    @Override
    public void addedToCartAndBuyNow() {
        startActivity(ProductCartListActivity.getStartIntent(this));
    }

    @Override
    public void updateCartCount() {
        mSearchToolbarViewHolder.onBind(0);
    }


    @Override
    public void addToCart() {
        mMainViewModel.addProductToCart(productId, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSearchToolbarViewHolder.onBind(0);
    }

    private void loadImages() {
        for (String name : getViewModel().images) {
            DefaultSliderView vDefaultSliderView = new DefaultSliderView(this);
            // initialize a SliderLayout
            vDefaultSliderView
                    .image(name)
                    .setScaleType(BaseSliderView.ScaleType.FitCenterCrop);
            mBinder.slider.addSlider(vDefaultSliderView);
        }
        mBinder.slider.setPresetTransformer(SliderLayout.Transformer.Default);
        mBinder.slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mBinder.slider.setCustomIndicator((PagerIndicator)mBinder.customIndicator);
        mBinder.slider.stopAutoCycle();
    }

    @Override
    public ProductDetailViewModel getViewModel() {
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
