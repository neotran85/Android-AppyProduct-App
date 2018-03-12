package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductDetailBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.ProductCartListActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.gallery.ProductGalleryActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.ui.common.component.cart.SearchToolbarViewHolder;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import javax.inject.Inject;

public class ProductDetailActivity extends BaseActivity<ActivityProductDetailBinding, ProductItemViewModel> implements ProductDetailNavigator, BaseSliderView.OnSliderClickListener {

    @Inject
    public ProductItemViewModel mMainViewModel;

    ActivityProductDetailBinding mBinder;

    @Inject
    int mLayoutId;

    private SearchToolbarViewHolder mSearchToolbarViewHolder;

    private boolean isBuyNow = false;

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
        loadImages();
    }

    @Override
    public void gotoCart() {
        isBuyNow = true;
        mMainViewModel.addProductToCart();
    }

    @Override
    public void addedToCartCompleted() {
        if (isBuyNow) {
            startActivity(ProductCartListActivity.getStartIntent(this));
        } else {
            showAlert(mMainViewModel.amountAdded.get() + " items added to your cart");
        }
        isBuyNow = false;
    }

    @Override
    public BaseViewModel getMainViewModel() {
        return mMainViewModel;
    }

    @Override
    public void updateCartCount() {
        mSearchToolbarViewHolder.onBind(0);
    }


    @Override
    public void addToCart() {
        mMainViewModel.addProductToCart();
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
            vDefaultSliderView.setOnSliderClickListener(this);
            mBinder.slider.addSlider(vDefaultSliderView);
        }
        mBinder.slider.setPresetTransformer(SliderLayout.Transformer.Default);
        mBinder.slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mBinder.slider.setCustomIndicator((PagerIndicator) mBinder.customIndicator);
        mBinder.slider.stopAutoCycle();
    }

    @Override
    public void addToFavorite() {
        mMainViewModel.updateProductFavorite(0);
    }

    @Override
    public ProductItemViewModel getViewModel() {
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
    public void showGallery(int position) {
        Intent intent = ProductGalleryActivity.getStartIntent(this);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void notifyFavoriteChanged(int position, boolean isFavorite) {
        // DO NOTHING HERE
    }

    @Override
    public void increaseAmount() {
        int amount = mMainViewModel.getIntegerAmountAdded() + 1;
        mMainViewModel.setIntegerAmountAdded(amount);
    }

    @Override
    public void decreaseAmount() {
        int amount = mMainViewModel.getIntegerAmountAdded() - 1;
        if (amount >= 1)
            mMainViewModel.setIntegerAmountAdded(amount);
    }

    @Override
    public void onItemClick(View view) {
        // DO NOTHING HERE
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        showGallery(mBinder.slider.getCurrentPosition());
    }
}
